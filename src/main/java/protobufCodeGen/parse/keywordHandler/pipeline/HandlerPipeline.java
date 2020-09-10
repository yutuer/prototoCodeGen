package protobufCodeGen.parse.keywordHandler.pipeline;

import protobufCodeGen.parse.ProtobufNodeTree;
import protobufCodeGen.parse.keywordHandler.EmptyHandler;
import protobufCodeGen.parse.keywordHandler.IKeywordParseHandler;

import java.nio.CharBuffer;

/**
 * @Description 需要一个循环中可增删的双向列表
 * @Author zhangfan
 * @Date 2020/9/1 14:56
 * @Version 1.0
 */
public class HandlerPipeline
{
    private HandlerContext head = new HandlerContext("head", new EmptyHandler());

    private HandlerContext tail = new HandlerContext("tail", new EmptyHandler());

    /**
     * 当前大小
     */
    private int size;

    /**
     * 遍历中大小是否改变
     */
    private boolean isChange;

    /**
     * 遍历前的版本号,  如果有变更, 重头找, 找到非版本号的, 从那里开始遍历, 如果版本号一样, 则跳过
     */
    private int version;

    public HandlerPipeline()
    {
        head.setNext(tail);
        tail.setPrev(head);
    }

    public void addHandler(String id, IKeywordParseHandler handler)
    {
        addBefore(tail.getId(), id, handler);
    }

    public void addAfter(String findId, String id, IKeywordParseHandler handler)
    {
        HandlerContext find = findById(findId);
        if (find == null)
        {
            return;
        }

        HandlerContext next = find.getNext();

        HandlerContext context = new HandlerContext(id, handler);
        context.setNext(next);
        next.setPrev(context);

        find.setNext(context);
        context.setPrev(find);

        size++;
    }

    public void addBefore(String findId, String id, IKeywordParseHandler handler)
    {
        HandlerContext find = findById(findId);
        if (find == null)
        {
            return;
        }

        HandlerContext prev = find.getPrev();

        HandlerContext context = new HandlerContext(id, handler);
        context.setNext(find);
        find.setPrev(context);

        prev.setNext(context);
        context.setPrev(prev);

        size++;

        isChange = true;
    }

    public IKeywordParseHandler removeHandler(String id)
    {
        if (size == 0)
        {
            return null;
        }

        HandlerContext context = head;
        for (; context != null && size > 0; )
        {
            HandlerContext c = context.getNext();
            if (context.getId().equals(id))
            {
                removeContext(context);

                return context.getHandler();
            }
            context = c;
        }
        return null;
    }

    private void removeContext(HandlerContext context)
    {
        HandlerContext prev = context.getPrev();
        HandlerContext next = context.getNext();

        prev.setNext(next);
        next.setPrev(prev);

        size--;

        isChange = true;
    }

    /**
     * 迭代处理所有的handler
     *
     * @param cb
     * @param c
     * @param nodeTree
     */
    public void iteratorHandler(CharBuffer cb, char c, ProtobufNodeTree nodeTree)
    {
        version++;

        HandlerContext context = head;
        for (; context != null; )
        {
            HandlerContext next = _next(context, version, cb, c, nodeTree);
            context = next;
        }
    }

    private HandlerContext _next(HandlerContext context, int version, CharBuffer cb, char c, ProtobufNodeTree nodeTree)
    {
        if (context.getVersion() == version)
        {
            return context.getNext();
        }
        else
        {
            context.setVersion(version);

            context.getHandler().doLogic(cb, c, nodeTree);
            if (isChange)
            {
                isChange = false;
                return head;
            }
            else
            {
                return context.getNext();
            }
        }
    }

    private HandlerContext findById(String id)
    {
        HandlerContext context = head;
        for (; context != null; context = context.getNext())
        {
            if (context.getId().equals(id))
            {
                return context;
            }
        }
        return null;
    }

}
