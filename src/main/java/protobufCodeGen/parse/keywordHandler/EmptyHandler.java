package protobufCodeGen.parse.keywordHandler;

import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 空实现
 * @Author zhangfan
 * @Date 2020/9/1 14:59
 * @Version 1.0
 */
public class EmptyHandler implements IKeywordParseHandler
{
    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {

    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {

    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {

    }
}
