package protobufCodeGen.listener;

import protobufCodeGen.parse.ProtobufNodeTreeCollection;

/**
 * @Description 一个辅助类, 用于封装所有类型的监听器,
 * 因为可能有的监听器很特别, 会有额外的参数(泛型)
 * 也可以理解为统一接口
 * 参考 {@link com.sun.javafx.binding.ExpressionHelper}
 * @Author zhangfan
 * @Date 2020/9/3 10:05
 * @Version 1.0
 */
public abstract class EventHelper
{
    public static EventHelper addListener(EventHelper header, ICollectionNotify collection,
                                          ICollectionParseEndListener listener)
    {
        if (listener == null || collection == null)
        {
            throw new NullPointerException();
        }
        return header == null ? new SingleEventHelper(collection, listener) : header.addListener(listener);
    }

    public static EventHelper removeListener(EventHelper header,
                                             ICollectionParseEndListener listener)
    {
        return header == null ? null : header.removeListener(listener);
    }

    /**
     * 通知监听器
     *
     * @param collection
     */
    public static void notifyListener(EventHelper header, ProtobufNodeTreeCollection collection)
    {
        header.notifyListener(collection);
    }

    ///////////////////////////////////////
    protected ICollectionNotify collectionNotify;

    public EventHelper(ICollectionNotify collectionNotify)
    {
        this.collectionNotify = collectionNotify;
    }

    protected abstract EventHelper addListener(ICollectionParseEndListener listener);

    protected abstract EventHelper removeListener(ICollectionParseEndListener listener);

    protected abstract void notifyListener(ProtobufNodeTreeCollection collection);
}
