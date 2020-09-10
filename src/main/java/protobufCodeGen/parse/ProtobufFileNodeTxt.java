package protobufCodeGen.parse;

import protobufCodeGen.codegen.template.CopyFromTemplate;
import protobufCodeGen.codegen.template.WriteToTemplate;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 节点字符串表示
 * @Author zhangfan
 * @Date 2020/8/31 14:49
 * @Version 1.0
 */
public class ProtobufFileNodeTxt
{

    private StringBuilder genSB = new StringBuilder();

    /**
     * 属于哪个树
     */
    private ProtobufNodeTree nodeTree;

    /**
     * 节点名称
     */
    private String nodeName;

    private ProtobufFileNodePropertyTxt[] propertyTxts;

    private List<ProtobufFileNodePropertyTxt> listTmp;

    public static ProtobufFileNodeTxt newProtobufFileNodeTxt(String content)
    {
        return null;
    }

    public ProtobufFileNodeTxt(ProtobufNodeTree nodeTree, String nodeName)
    {
        this.nodeTree = nodeTree;
        this.nodeName = nodeName;
        listTmp = new ArrayList<>();
    }

    public void addPropertyTxt(ProtobufFileNodePropertyTxt propertyTxt)
    {
        listTmp.add(propertyTxt);
    }

    public String getNodeName()
    {
        return nodeName;
    }

    /**
     * 属性提取完毕
     */
    public void end()
    {
        propertyTxts = listTmp.toArray(new ProtobufFileNodePropertyTxt[0]);

        // for gc
        listTmp = null;
    }

    @Override
    public String toString()
    {
        return "ProtobufFileNodeTxt{" +
                "nodeName='" + nodeName + '\'' +
                ", propertyTxts=" + Arrays.toString(propertyTxts) +
                '}';
    }

    /**
     * prefix.nodeName.Builder %s = prefix.nodeName.newBuilder();
     *
     * @return %s的值
     */
    public String getWriterBuilderName()
    {
        return Util.firstLetterLower(nodeName, 2) + "Builder";
    }

    /**
     * public void copyFrom(%s.%s %s)
     * 获得第3个%s的值,
     *
     * @return
     */
    public String getCopyFromMethodBuilderName()
    {
        return Util.firstLetterLower(nodeName, 2);
    }

    /**
     * 生成writeTo字符串
     *
     * @return
     */
    public String genWriteToString()
    {
        genSB.delete(0, genSB.length());

        genSB.append(getHeader());

        String parentBuilderName = getWriterBuilderName();
        for (ProtobufFileNodePropertyTxt propertyTxt : propertyTxts)
        {
            String s = propertyTxt.genWriteToString(nodeTree, parentBuilderName);
            genSB.append(s);
        }

        genSB.append(String.format(WriteToTemplate.Tail, Util.firstLetterLower(nodeName, 1)));

        return genSB.toString();
    }

    /**
     * 生成CopyFrom字符串
     *
     * @return
     */
    public String genCopyFromString()
    {
        genSB.delete(0, genSB.length());

        String copyFromMethodBuilderName = getCopyFromMethodBuilderName();
        genSB.append(String.format(CopyFromTemplate.MethodHeader, nodeTree.prefix, nodeName, copyFromMethodBuilderName));

        for (ProtobufFileNodePropertyTxt propertyTxt : propertyTxts)
        {
            String s = propertyTxt.genCopyFromString(nodeTree, copyFromMethodBuilderName);
            genSB.append(s);
        }

        genSB.append(String.format(CopyFromTemplate.End));

        return genSB.toString();
    }

    /**
     * 获得builder字符串
     *
     * @return
     */
    public String getHeader()
    {
        return String.format(WriteToTemplate.Header, nodeTree.prefix, nodeName, getWriterBuilderName(), nodeTree.prefix, nodeName);
    }

}
