package protobufCodeGen.codegen.template;

/**
 * @Description 模板类
 * @Author zhangfan
 * @Date 2020/9/1 21:43
 * @Version 1.0
 */
public class CopyFromTemplate
{

    public static final String MethodHeader = "public void copyFrom(%s.%s %s)\n{\n";

    public static final String Get = "\tthis.%s = %s.get%s(); \n";

    public static final String CopyFronMethod = "\tthis.%s.copyFrom(%s.get%s()); \n";

    public static final String ForPrimaryLine1 = "for (int i = 0; i < %s.get%sCount(); i++)\n{\n";
    public static final String ForPrimaryLine2 = "\t%ss.add(%s.get%s(i));\n}\n";

    public static final String ForNotPrimaryLine1 = "for (int i = 0; i < %s.get%sCount(); i++)\n{\n";
    public static final String ForNotPrimaryLine2 = "\tSelf%sObj %s = newSelf%sObj();\n";
    public static final String ForNotPrimaryLine3 = "\t%s.copyFrom(%s.get%s(i));\n";
    // 可选的put into map
    public static final String ForNotPrimaryLine4 = "\t%s.put(%s.getId(), %s);\n}\n";

    public static final String End = "}\n";


}
