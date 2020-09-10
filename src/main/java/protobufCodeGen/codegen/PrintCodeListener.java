package protobufCodeGen.codegen;

import protobufCodeGen.ProtobufCodeGen;
import protobufCodeGen.listener.ICollectionParseEndListener;
import protobufCodeGen.out.OutPutFactory;
import protobufCodeGen.parse.ProtobufCodeKey;
import protobufCodeGen.parse.ProtobufNodeTreeCollection;
import protobufCodeGen.out.IOutPut;
import protobufCodeGen.codegen.template.PrintEnum;
import util.PropertiesUtils;

import java.util.Properties;

/**
 * @Description
 * @Author zhangfan
 * @Date 2020/9/3 10:47
 * @Version 1.0
 */
public class PrintCodeListener implements ICollectionParseEndListener
{
    @Override
    public void onParseEnd(ProtobufNodeTreeCollection protobufNodeTreeCollection)
    {
        Properties properties = ProtobufCodeGen.properties;

        protobufNodeTreeCollection.addTemplateString(PropertiesUtils.getString(properties, ProtobufCodeKey.protoName, ""));
        IOutPut outPut = OutPutFactory.getInstance(PropertiesUtils.getInt(properties, ProtobufCodeKey.output, 0));
        protobufNodeTreeCollection.printCode(PrintEnum.values()[PropertiesUtils.getInt(properties, ProtobufCodeKey.printEnum, 0)], outPut);
    }
}
