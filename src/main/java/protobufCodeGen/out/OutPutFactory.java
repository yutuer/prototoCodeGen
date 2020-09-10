package protobufCodeGen.out;

import protobufCodeGen.ProtobufCodeGen;
import util.PropertiesUtils;

/**
 * @Description 输出工厂类
 * @Author zhangfan
 * @Date 2020/9/5 12:53
 * @Version 1.0
 */
public class OutPutFactory
{
    public static IOutPut getInstance(int output)
    {
        if (output == 0)
        {
            return new ConsoleOutput();
        }
        else if (output == 1)
        {
            return new FileOutPut(PropertiesUtils.getString(ProtobufCodeGen.properties, "outputFile", "out.txt"));
        }
        return null;
    }
}
