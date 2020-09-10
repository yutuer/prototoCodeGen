package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 左括号
 * @Author zhangfan
 * @Date 2020/8/31 17:02
 * @Version 1.0
 */
public class LeftKuohaoHandler implements IKeywordParseHandler
{
    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        if ('{' != in)
        {
            return;
        }

        nodeTree.registerKeywordHandler(IKeywordParseHandler.LineProperty, nodeTree.LinePropertyHandler);
        nodeTree.registerKeywordHandler(IKeywordParseHandler.RightKuohao, nodeTree.RightKuohaoHandler);
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ LeftKuohaoHandler ]");
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ LeftKuohaoHandler ]");
    }


}
