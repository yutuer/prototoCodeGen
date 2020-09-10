package protobufCodeGen.parse;

import protobufCodeGen.parse.keywordHandler.pipeline.HandlerPipeline;
import protobufCodeGen.parse.keywordHandler.*;
import util.Log;

import java.io.*;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 节点树
 * @Author zhangfan
 * @Date 2020/8/31 14:42
 * @Version 1.0
 */
public class ProtobufNodeTree
{
    public final IKeywordParseHandler MessageHandler = new MessageKeywordHandler();
    public final IKeywordParseHandler CommentHandler = new CommentKeywordHandler();
    public final IKeywordParseHandler LeftKuohaoHandler = new LeftKuohaoHandler();
    public final IKeywordParseHandler RightKuohaoHandler = new RightKuohaoHandler();
    public final IKeywordParseHandler ModelParseHandler = new ModelKeywordParseHandler();
    public final IKeywordParseHandler LinePropertyHandler = new LinePropertyHandler();

    /**
     *
     */
    private ProtobufNodeTreeCollection protobufNodeTreeCollection;

    /**
     * 前缀
     */
    public final String prefix;

    /**
     * 节点集合
     */
    private Map<String, ProtobufFileNodeTxt> roots = new HashMap<>();

    // 可能在循环中加入或者移除, 所以要使用双向链表
    private HandlerPipeline keyHandlers = new HandlerPipeline();

    /**
     * 当前解析中的类
     */
    private ProtobufFileNodeTxt curNodeTxt;

    public ProtobufNodeTree(ProtobufNodeTreeCollection protobufNodeTreeCollection, String prefix)
    {
        this.protobufNodeTreeCollection = protobufNodeTreeCollection;

        prefix.replace("\\", "/");
        this.prefix = prefix.substring(prefix.lastIndexOf('/') + 1);
    }

    /**
     * 扩容size
     *
     * @param cb
     * @param size
     * @return
     */
    private CharBuffer kuorong(CharBuffer cb, int size)
    {
        if (size > 0)
        {
            cb.flip();
            CharBuffer allocate = CharBuffer.allocate(cb.remaining() + size);

            allocate.put(cb);
            return allocate;
        }
        return cb;
    }

    /**
     * 传入一个文件名称, 开始解析为 NodeTree
     *
     * @param fileName
     */
    public void start(String fileName)
    {
        char[] data = new char[1 << 10];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))))
        {
            CharBuffer cb = CharBuffer.allocate(0);

            int read = -1;
            while ((read = br.read(data)) != -1)
            {
                cb = kuorong(cb, read);
                cb.put(data, 0, read);
            }

            cb.flip();

            beginParse(cb.array());

            Log.logger.info(this);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void addRoot(ProtobufFileNodeTxt nodeTxt)
    {
        roots.put(nodeTxt.getNodeName(), nodeTxt);
    }

    /**
     * 只有一个名字
     *
     * @param name
     * @return
     */
    public ProtobufFileNodeTxt newProtobufFileNodeTxt(String name)
    {
        ProtobufFileNodeTxt txt = new ProtobufFileNodeTxt(this, name);
        return txt;
    }

    /**
     * 压栈
     *
     * @param nodeTxt
     */
    public void pushProtobufFileNodeTxtToCur(ProtobufFileNodeTxt nodeTxt)
    {
        curNodeTxt = nodeTxt;
    }

    /**
     * 获取 但是不移除
     *
     * @return
     */
    public ProtobufFileNodeTxt getProtobufFileNodeTxtToCur()
    {
        final ProtobufFileNodeTxt t = curNodeTxt;
        return t;
    }

    /**
     * 弹出
     *
     * @return
     */
    public ProtobufFileNodeTxt popProtobufFileNodeTxtToCur()
    {
        final ProtobufFileNodeTxt t = curNodeTxt;
        curNodeTxt = null;
        return t;
    }

    /**
     * 注册关键字解析handler
     * <p>
     * 1个词只能有一种解析方式
     *
     * @param parseHandler
     */
    public void registerKeywordHandler(String keyword, IKeywordParseHandler parseHandler)
    {
        keyHandlers.addHandler(keyword, parseHandler);

        parseHandler.onAdd(this);
    }

    public void removeKeyworldHandler(String keyword)
    {
        IKeywordParseHandler removeHandler = keyHandlers.removeHandler(keyword);
        if (removeHandler != null)
        {
            removeHandler.onRemove(this);
        }
    }

    /**
     * 开始解析
     *
     * @param data 数据
     */
    public void beginParse(char[] data)
    {
        registerKeywordHandler(IKeywordParseHandler.Message, MessageHandler);
        registerKeywordHandler(IKeywordParseHandler.Comment, CommentHandler);

        beginParse0(data);
    }

    private void beginParse0(char[] data)
    {
        CharBuffer cb = CharBuffer.wrap(data);

        while (cb.hasRemaining())
        {
            char c = cb.get();
            iteratorHandler(cb, c);
        }
    }

    /**
     * 迭代处理
     *
     * @param cb
     * @param c
     */
    private void iteratorHandler(CharBuffer cb, char c)
    {
        keyHandlers.iteratorHandler(cb, c, this);
    }

    @Override
    public String toString()
    {
        return "ProtobufNodeTree{" +
                "prefix='" + prefix + '\'' +
                ", roots=" + roots +
                "}\n";
    }

    public boolean contains(String className)
    {
        return roots.containsKey(className);
    }

    /**
     * 从本tree获取
     *
     * @param className
     * @return
     */
    public ProtobufFileNodeTxt getNodeTxtByName(String className)
    {
        return roots.get(className);
    }

    /**
     * 全局搜索
     *
     * @param className
     * @return
     */
    public ProtobufFileNodeTxt getNodeTxtByNameSearchAll(String className)
    {
        return getProtobufNodeTreeCollection().findNodeTxt(className);
    }

    public ProtobufNodeTreeCollection getProtobufNodeTreeCollection()
    {
        return protobufNodeTreeCollection;
    }

}
