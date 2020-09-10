package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description message 解析器, 并发解析, 不单例
 * @Author zhangfan
 * @Date 2020/8/31 17:01
 * @Version 1.0
 */
public class MessageKeywordHandler implements IKeywordParseHandler
{

    /**
     * 前面的字符是否是空格或者换行(因为m要是开头才行)
     */
    private boolean prefixIsBlank = true;

    /**
     * 字符串缓冲区
     */
    private StringBuilder sb = new StringBuilder();

    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        /*
         对message 关键字进行解析
         */
        if (sb.length() == 0)
        {
            if (prefixIsBlank && 'm' == in)
            {
                // 开始计数message
                sb.append(in);
            }
        }
        else
        {
            if (isBlank(in))
            {
                String s = sb.toString();
                if (Message.equals(s))
                {
                    // 清空缓冲区
                    clearStringBuilder(sb);

                    doLogic0(nodeTree);
                }
            }
            else
            {
                // 继续添加进缓冲区,直到遇到空白符
                sb.append(in);
            }
        }

        prefixIsBlank = isBlank(in);
    }

    private void doLogic0(ProtobufNodeTree nodeTree)
    {
        // 移除自己
        nodeTree.removeKeyworldHandler(IKeywordParseHandler.Message);
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ MessageHandler: {}]", nodeTree.prefix);
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ MessageHandler: {}]", nodeTree.prefix);

        // 添加关键词解析
        nodeTree.registerKeywordHandler(IKeywordParseHandler.Model, nodeTree.ModelParseHandler);
    }
}
