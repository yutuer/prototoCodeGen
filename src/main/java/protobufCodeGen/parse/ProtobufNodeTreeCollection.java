package protobufCodeGen.parse;

import protobufCodeGen.listener.EventHelper;
import protobufCodeGen.listener.ICollectionNotify;
import protobufCodeGen.listener.ICollectionParseEndListener;
import protobufCodeGen.out.IOutPut;
import protobufCodeGen.codegen.template.PrintEnum;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 解析文件集合
 * @Author zhangfan
 * @Date 2020/8/31 15:14
 * @Version 1.0
 */
public class ProtobufNodeTreeCollection implements ICollectionNotify
{

    private ProtobufNodeTree[] protobufFiles;

    /**
     * 用于 打印输出模板
     */
    private Queue<String> queue = new LinkedList<>();

    /**
     * 监听器链表
     */
    private EventHelper header;

    /**
     * 输入一段名称, 打印一段代码
     */
    public void printCode(PrintEnum printEnum, IOutPut outPut)
    {
        outPut.init();

        while (!queue.isEmpty())
        {
            String className = pop();

            ProtobufFileNodeTxt nodeTxt = findNodeTxt(className);
            if (nodeTxt != null)
            {
                if (printEnum == PrintEnum.CopyFrom)
                {
                    outPut.append(nodeTxt.genCopyFromString());
                }
                else
                {
                    outPut.append(nodeTxt.genWriteToString());
                }
            }
        }
    }

    public void parse(String[] args)
    {
        protobufFiles = new ProtobufNodeTree[args.length];
        for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];
            if (arg.endsWith(".proto"))
            {
                ProtobufNodeTree protobufFile = new ProtobufNodeTree(this, arg.substring(0, arg.indexOf(".proto")));
                protobufFile.start(arg);

                protobufFiles[i] = protobufFile;
            }
        }

        EventHelper.notifyListener(header, this);
    }

    public ProtobufFileNodeTxt findNodeTxt(String className)
    {
        for (int i = 0; i < protobufFiles.length; i++)
        {
            ProtobufNodeTree protobufNodeTree = protobufFiles[i];
            if (protobufNodeTree.contains(className))
            {
                return protobufNodeTree.getNodeTxtByName(className);
            }
        }
        return null;
    }

    public void addTemplateString(String name)
    {
        queue.add(name);
    }

    public String pop()
    {
        return queue.poll();
    }

    @Override
    public void addListener(ICollectionParseEndListener listener)
    {
        header = EventHelper.addListener(header, this, listener);
    }

    @Override
    public void removeListener(ICollectionParseEndListener listener)
    {

    }

    public void initListeners(String[] listeners)
    {
        for (int i = 0; i < listeners.length; i++)
        {
            String listener = listeners[i];
            try
            {
                Class<?> aClass = Class.forName(listener);
                Object o = aClass.newInstance();
                addListener(ICollectionParseEndListener.class.cast(o));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
