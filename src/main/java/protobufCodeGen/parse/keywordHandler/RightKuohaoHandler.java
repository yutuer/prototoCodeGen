package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 右括号
 * @Author zhangfan
 * @Date 2020/8/31 17:02
 * @Version 1.0
 */
public class RightKuohaoHandler implements IKeywordParseHandler
{
    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        if ('}' != in)
        {
            return;
        }

        nodeTree.removeKeyworldHandler(IKeywordParseHandler.RightKuohao);
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ RightKuohaoHandler ]");
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ RightKuohaoHandler ]");

        nodeTree.removeKeyworldHandler(IKeywordParseHandler.LeftKuohao);
        nodeTree.removeKeyworldHandler(IKeywordParseHandler.LineProperty);
        nodeTree.registerKeywordHandler(IKeywordParseHandler.Message, nodeTree.MessageHandler);

        // 弹出对象,结束属性设置
        nodeTree.popProtobufFileNodeTxtToCur().end();
    }
}
