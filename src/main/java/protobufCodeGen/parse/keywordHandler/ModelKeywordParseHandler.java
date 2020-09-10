package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufFileNodeTxt;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description model 解析器
 * @Author zhangfan
 * @Date 2020/9/1 10:34
 * @Version 1.0
 */
public class ModelKeywordParseHandler implements IKeywordParseHandler
{
    /**
     * 字符串缓冲区
     */
    private StringBuilder sb = new StringBuilder();

    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        /*
            由message 注册. 遇到空白符后结束 解析完后, 销毁自己. 注册左括号.
         */
        if (isBlank(in) || '{' == in)
        {
            if (sb.length() == 0)
            {
                return;
            }

            String name = sb.toString();
            ProtobufFileNodeTxt protobufFileNodeTxt = nodeTree.newProtobufFileNodeTxt(name);
            nodeTree.pushProtobufFileNodeTxtToCur(protobufFileNodeTxt);

            //添加到树中
            nodeTree.addRoot(protobufFileNodeTxt);

            clearStringBuilder(sb);

            nodeTree.removeKeyworldHandler(IKeywordParseHandler.Model);
        }
        else
        {
            sb.append(in);
        }
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ ModelKeywordParseHandler ]");
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ ModelKeywordParseHandler ]");

        // '{' 可能和类名称连接在一起, 所以放在这里注册, 这样才能重新把'{'交给 左括号解析器
        nodeTree.registerKeywordHandler(IKeywordParseHandler.LeftKuohao, nodeTree.LeftKuohaoHandler);
    }
}
