package protobufCodeGen;

import protobufCodeGen.parse.ProtobufCodeKey;
import protobufCodeGen.parse.ProtobufNodeTreeCollection;
import util.Log;
import util.PropertiesUtils;

import java.util.Properties;

/**
 * @Description 门面类
 * @Author zhangfan
 * @Date 2020/8/31 14:27
 * @Version 1.0
 */
public class ProtobufCodeGen
{

    /**
     * 全局的配置文件引用
     */
    public static Properties properties;

    public static void main(String[] args)
    {
        if (args == null || args.length < 2)
        {
            Log.logger.error("输入文件参数!!!");
            System.exit(1);
        }

        if ("-c".equals(args[0]))
        {
            String propertiesName = args[1];

            properties = PropertiesUtils.getProperties(propertiesName);

            ProtobufNodeTreeCollection protobufNodeTreeCollection = new ProtobufNodeTreeCollection();
            protobufNodeTreeCollection.initListeners(PropertiesUtils.getString(properties, ProtobufCodeKey.listener, "").split(";"));

            protobufNodeTreeCollection.parse(PropertiesUtils.getString(properties, ProtobufCodeKey.protoFiles, "").split(";"));
        }
    }

}
