package protobufCodeGen.listener;

import protobufCodeGen.parse.ProtobufNodeTreeCollection;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description 通用的事件
 * @Author zhangfan
 * @Date 2020/9/3 10:31
 * @Version 1.0
 */
public class GenericEventHelper extends EventHelper
{
    private List<ICollectionParseEndListener> listenerList = new LinkedList<>();

    public GenericEventHelper(ICollectionNotify collectionNotify, ICollectionParseEndListener listener0, ICollectionParseEndListener listener1)
    {
        super(collectionNotify);
        listenerList.add(listener0);
        listenerList.add(listener1);
    }

    @Override
    protected EventHelper addListener(ICollectionParseEndListener listener)
    {
        listenerList.add(listener);
        return this;
    }

    @Override
    protected EventHelper removeListener(ICollectionParseEndListener listener)
    {
        listenerList.remove(listener);
        return this;
    }

    @Override
    protected void notifyListener(ProtobufNodeTreeCollection collection)
    {
        for (ICollectionParseEndListener iCollectionParseEndListener : listenerList)
        {
            iCollectionParseEndListener.onParseEnd(collection);
        }
    }
}
