package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 注释关键字
 * @Author zhangfan
 * @Date 2020/8/31 17:07
 * @Version 1.0
 */
public class CommentKeywordHandler implements IKeywordParseHandler
{

    /**
     * 字符串缓冲区
     */
    private StringBuilder sb = new StringBuilder();

    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        // 有2个//之后, 去掉自己和之后的所有字符, 直到换行符. 此handler会常驻
        if ('/' == in)
        {
            if (sb.length() == 0)
            {
                sb.append(in);
            }
            else if (sb.length() == 1)
            {
                if (sb.charAt(0) == '/')
                {
                    clearStringBuilder(sb);

                    // 跳过这一行
                    while (cb.hasRemaining())
                    {
                        char c = cb.get();
                        if (c == '\n')
                        {
                            //  往回定一位
                            cb.position(cb.position() - 1);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ CommentHandler ]: {}", nodeTree.prefix);
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ CommentHandler ]: {}", nodeTree.prefix);
    }
}
