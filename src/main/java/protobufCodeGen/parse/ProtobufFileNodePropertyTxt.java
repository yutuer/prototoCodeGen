package protobufCodeGen.parse;

import protobufCodeGen.codegen.template.CopyFromTemplate;
import protobufCodeGen.codegen.template.WriteToTemplate;
import util.Util;

/**
 * @Description 表示txtNode中的一条属性
 * @Author zhangfan
 * @Date 2020/8/31 14:49
 * @Version 1.0
 */
public class ProtobufFileNodePropertyTxt
{
    /**
     * 单例, 但是这样无法并发
     */
    private static StringBuilder genSB = new StringBuilder();

    public final String qualified;
    public final String type;
    public final String name;
    public final int order;

    public ProtobufFileNodePropertyTxt(String qualified, String type, String name, int order)
    {
        this.qualified = qualified;
        this.type = type;
        this.name = name;
        this.order = order;
    }

    /**
     * 是否是主属性(int32, int64, bool string等)
     *
     * @return
     */
    public boolean isPrimary()
    {
        if ("string".equals(type) || "int64".equals(type) || "int32".equals(type) || "bool".equals(type))
        {
            return true;
        }
        return false;
    }

    /**
     * 是否是数组
     *
     * @return
     */
    public boolean isRepeate()
    {
        return "repeated".equals(qualified);
    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    private String firstLetterUpper(String name)
    {
        return Util.firstLetterUpper(name, 1);
    }

    /**
     * 得到map 字段名称
     *
     * @param name
     * @return
     */
    private String getMapName(String name)
    {
        return name + "s";
    }

    /**
     * 变量名
     *
     * @param name
     * @return
     */
    private String getVariableName(String name)
    {
        return name;
    }

    /**
     * 以后会使用字符串模板
     *
     * @param nodeTree
     * @param copyFromMethodBuilderName
     * @return
     */
    public String genCopyFromString(ProtobufNodeTree nodeTree, String copyFromMethodBuilderName)
    {
        StringBuilder genSB = getGenSB();
        clearSB(genSB);

        if (isRepeate())
        {
            if (isPrimary())
            {
                String firstLetterUpper = firstLetterUpper(name);

                genSB.append(String.format(CopyFromTemplate.ForPrimaryLine1, copyFromMethodBuilderName, firstLetterUpper));
                genSB.append(String.format(CopyFromTemplate.ForPrimaryLine2, name, copyFromMethodBuilderName, firstLetterUpper));
            }
            else
            {
                ProtobufFileNodeTxt nodeTxtByNameSearchAll = nodeTree.getNodeTxtByNameSearchAll(type);
                if (nodeTxtByNameSearchAll != null)
                {
                    // 压栈
                    nodeTree.getProtobufNodeTreeCollection().addTemplateString(type);

                    // 空一行
                    genSB.append('\n');

                    String firstLetterUpper = firstLetterUpper(name);
                    String variableName = getVariableName(name);

                    // 2种循环的都写上, 自己到时候可以删除
                    genSB.append(String.format(CopyFromTemplate.ForNotPrimaryLine1, copyFromMethodBuilderName, firstLetterUpper));
                    genSB.append(String.format(CopyFromTemplate.ForNotPrimaryLine2, firstLetterUpper, variableName, firstLetterUpper));
                    genSB.append(String.format(CopyFromTemplate.ForNotPrimaryLine3, variableName, copyFromMethodBuilderName, firstLetterUpper));
                    genSB.append(String.format(CopyFromTemplate.ForNotPrimaryLine4, getMapName(variableName), variableName, variableName));
                }
            }
        }
        else
        {
            if (isPrimary())
            {
                genSB.append(String.format(CopyFromTemplate.Get, name, copyFromMethodBuilderName, firstLetterUpper(name)));
            }
            else
            {
                ProtobufFileNodeTxt nodeTxtByNameSearchAll = nodeTree.getNodeTxtByNameSearchAll(type);
                if (nodeTxtByNameSearchAll != null)
                {
                    // 压栈
                    nodeTree.getProtobufNodeTreeCollection().addTemplateString(type);

                    // 空一行
                    genSB.append('\n');

                    genSB.append(String.format(CopyFromTemplate.CopyFronMethod, name, copyFromMethodBuilderName, firstLetterUpper(name)));
                }
            }
        }

        return genSB.toString();
    }

    /**
     * 以后会使用字符串模板
     *
     * @param nodeTree
     * @param parentBuilderName
     * @return
     */
    public String genWriteToString(ProtobufNodeTree nodeTree, String parentBuilderName)
    {
        StringBuilder genSB = getGenSB();
        clearSB(genSB);

        if (isRepeate())
        {
            if (isPrimary())
            {
                genSB.append(String.format(WriteToTemplate.ForILine1, name));
                genSB.append(String.format(WriteToTemplate.ForILine2, parentBuilderName, firstLetterUpper(name), name));
            }
            else
            {
                ProtobufFileNodeTxt nodeTxtByNameSearchAll = nodeTree.getNodeTxtByNameSearchAll(type);
                if (nodeTxtByNameSearchAll != null)
                {
                    // 压栈
                    nodeTree.getProtobufNodeTreeCollection().addTemplateString(type);

                    // 空一行
                    genSB.append('\n');

                    // 2种循环的都写上, 自己到时候可以删除
                    genSB.append(String.format(WriteToTemplate.ForILine1, name));
                    genSB.append(String.format(WriteToTemplate.ForILine2, parentBuilderName, firstLetterUpper(name), name));

                    genSB.append(String.format(WriteToTemplate.ForEachLine1, name));
                    genSB.append(String.format(WriteToTemplate.ForEachLine2));
                    genSB.append(String.format(WriteToTemplate.ForEachLine3, firstLetterUpper(name), name));

                    genSB.append(String.format(WriteToTemplate.ForEachAdd, parentBuilderName, firstLetterUpper(name), name));
                }
            }
        }
        else
        {
            if (isPrimary())
            {
                genSB.append(String.format(WriteToTemplate.Set, parentBuilderName, firstLetterUpper(name), name));
            }
            else
            {
                ProtobufFileNodeTxt nodeTxtByNameSearchAll = nodeTree.getNodeTxtByNameSearchAll(type);
                if (nodeTxtByNameSearchAll != null)
                {
                    // 压栈
                    nodeTree.getProtobufNodeTreeCollection().addTemplateString(type);

                    // 空一行
                    genSB.append('\n');

                    genSB.append(nodeTxtByNameSearchAll.getHeader());
                    genSB.append(String.format(WriteToTemplate.Clear, nodeTxtByNameSearchAll.getWriterBuilderName()));
                    genSB.append(String.format(WriteToTemplate.WriteTo, parentBuilderName, nodeTxtByNameSearchAll.getWriterBuilderName()));
                    genSB.append(String.format(WriteToTemplate.Set, parentBuilderName, firstLetterUpper(name), nodeTxtByNameSearchAll.getWriterBuilderName()));
                }
            }
        }
        return genSB.toString();
    }

    @Override
    public String toString()
    {
        return "ProtobufFileNodePropertyTxt{" +
                "qualified='" + qualified + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }

    private StringBuilder getGenSB()
    {
        return genSB;
    }

    private void clearSB(StringBuilder sb)
    {
        if (sb != null)
        {
            sb.delete(0, sb.length());
        }
    }

}
