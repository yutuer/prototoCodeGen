package protobufCodeGen.parse.keywordHandler;

import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 关键字解析处理器
 * @Author zhangfan
 * @Date 2020/8/31 15:31
 * @Version 1.0
 */
public interface IKeywordParseHandler
{
    String Message = "message";
    String LeftKuohao = "{";
    String RightKuohao = "}";
    String Comment = "//";
    String Model = "Model";
    String LineProperty = "Line";

    /**
     * 处理逻辑
     *
     * @param cb
     * @param nodeTree
     */
    void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree);

    void onAdd(ProtobufNodeTree nodeTree);

    void onRemove(ProtobufNodeTree nodeTree);

    default boolean isBlank(char in)
    {
        return in == ' ' || in == '\n';
    }

    default void clearStringBuilder(StringBuilder sb)
    {
        if (sb != null)
        {
            sb.delete(0, sb.length());
        }
    }
}
