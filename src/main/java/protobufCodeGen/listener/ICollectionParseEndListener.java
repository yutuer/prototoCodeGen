package protobufCodeGen.listener;

import protobufCodeGen.parse.ProtobufNodeTreeCollection;

/**
 * @Description 监听器, collection解析完毕后会执行
 * @Author zhangfan
 * @Date 2020/9/3 9:59
 * @Version 1.0
 */
public interface ICollectionParseEndListener
{

    /**
     * 解析完毕
     *
     * @param collection
     */
    void onParseEnd(ProtobufNodeTreeCollection collection);
}
