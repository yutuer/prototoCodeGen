package protobufCodeGen.listener;

/**
 * @Description 能添加移除 ICollectionParseEndListener监听器
 * @Author zhangfan
 * @Date 2020/9/3 10:01
 * @Version 1.0
 */
public interface ICollectionNotify
{

    void addListener(ICollectionParseEndListener listener);

    void removeListener(ICollectionParseEndListener listener);

}
