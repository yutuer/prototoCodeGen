package protobufCodeGen.parse.keywordHandler;

import util.Log;
import protobufCodeGen.parse.ProtobufFileNodePropertyTxt;
import protobufCodeGen.parse.ProtobufFileNodeTxt;
import protobufCodeGen.parse.ProtobufNodeTree;

import java.nio.CharBuffer;

/**
 * @Description 行属性解析器
 * @Author zhangfan
 * @Date 2020/9/1 17:31
 * @Version 1.0
 */
public class LinePropertyHandler implements IKeywordParseHandler
{

    /**
     * 分隔符
     */
    private static final String Split = "\\s+";

    private StringBuilder sb = new StringBuilder();

    @Override
    public void doLogic(CharBuffer cb, char in, ProtobufNodeTree nodeTree)
    {
        if ('o' == in || 'r' == in)
        {
            clearStringBuilder(sb);

            // 需要加上已经解析的一位
            cb.position(cb.position() - 1);

            while (cb.hasRemaining())
            {
                char c = cb.get();
                if (c == ';')
                {
                    break;
                }
                else
                {
                    sb.append(c);
                }
            }

            String line = sb.toString();

            ProtobufFileNodePropertyTxt propertyTxt = parse(line);
            if (propertyTxt != null)
            {
                ProtobufFileNodeTxt protobufFileNodeTxt = nodeTree.getProtobufFileNodeTxtToCur();
                if (protobufFileNodeTxt != null)
                {
                    protobufFileNodeTxt.addPropertyTxt(propertyTxt);
                }
            }
        }
    }

    private ProtobufFileNodePropertyTxt parse(String line)
    {
        if (line == null || line.isEmpty())
        {
            System.out.printf("parse line:{}, error\n", line);
            return null;
        }

        // 先用 = 拆
        String[] equalSplit = line.split("=");
        String trim = equalSplit[1].trim();
        int order = Integer.parseInt(trim);

        // 再用 " "拆
        String[] s = equalSplit[0].trim().split(Split);
        ProtobufFileNodePropertyTxt protobufFileNodePropertyTxt = new ProtobufFileNodePropertyTxt(s[0].trim(), s[1].trim(), s[2].trim(), order);
        return protobufFileNodePropertyTxt;
    }

    @Override
    public void onAdd(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("添加 [ LinePropertyHandler ]");
    }

    @Override
    public void onRemove(ProtobufNodeTree nodeTree)
    {
        Log.logger.info("移除 [ LinePropertyHandler ]");
    }
}
