package protobufCodeGen.listener;

import protobufCodeGen.parse.ProtobufNodeTreeCollection;

/**
 * @Description TODO
 * @Author zhangfan
 * @Date 2020/9/3 10:19
 * @Version 1.0
 */
public class SingleEventHelper extends EventHelper
{
    private final ICollectionParseEndListener listener;

    public SingleEventHelper(ICollectionNotify collectionNotify, ICollectionParseEndListener listener)
    {
        super(collectionNotify);
        this.listener = listener;
    }

    @Override
    protected EventHelper addListener(ICollectionParseEndListener listener)
    {
        return new GenericEventHelper(collectionNotify, this.listener, listener);
    }

    @Override
    protected EventHelper removeListener(ICollectionParseEndListener listener)
    {
        return this.listener == listener ? null : this;
    }

    @Override
    protected void notifyListener(ProtobufNodeTreeCollection collection)
    {
        if (listener != null)
        {
            listener.onParseEnd(collection);
        }
    }
}
