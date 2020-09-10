package protobufCodeGen.parse.keywordHandler.pipeline;

import protobufCodeGen.parse.keywordHandler.IKeywordParseHandler;

/**
 * @Description 不用接口了, 优先实现功能
 * @Author zhangfan
 * @Date 2020/9/1 14:56
 * @Version 1.0
 */
public class HandlerContext
{
    /**
     * 唯一标识符
     */
    private final String id;

    private HandlerContext prev;
    private HandlerContext next;

    private IKeywordParseHandler handler;

    private int version;

    public HandlerContext(String id, IKeywordParseHandler handler)
    {
        this.id = id;
        this.handler = handler;
    }

    public IKeywordParseHandler getHandler()
    {
        return handler;
    }

    public HandlerContext getPrev()
    {
        return prev;
    }

    public void setPrev(HandlerContext prev)
    {
        this.prev = prev;
    }

    public HandlerContext getNext()
    {
        return next;
    }

    public void setNext(HandlerContext next)
    {
        this.next = next;
    }

    public String getId()
    {
        return id;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
