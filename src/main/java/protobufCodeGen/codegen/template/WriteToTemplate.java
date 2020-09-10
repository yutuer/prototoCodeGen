package protobufCodeGen.codegen.template;

/**
 * @Description TODO
 * @Author zhangfan
 * @Date 2020/9/1 21:47
 * @Version 1.0
 */
public class WriteToTemplate
{
    /**
     * 生成builder
     * <p>
     * DataStruct.DataWikiStatistics.Builder DataWikiStatisticsBuilder = DataStruct.DataWikiStatistics.newBuilder();
     */
    public static final String Header = "%s.%s.Builder %s = %s.%s.newBuilder(); \n";

    //wikiStatistics.writeTo(DataWikiStatisticsBuilder);
    public static final String WriteTo = "%s.writeTo(%s); \n";

    //%sBuilder.clear();
    public static final String Clear = "%s.clear(); \n";

    //ParentBuilder.setDataWikiStatistics(DataWikiStatisticsBuilder)
    public static final String Set = "%s.set%s(%s); \n";
    public static final String Add = "%s.add%s(%s); \n";

    public static final String Tail = "%sBuilder.build();\n\n\n";


    public static final String ForILine1 = "for(int i = 0, size = %s.size(); i < size; i++)\n{\n";
    // ParentBuilder.add(a[i])
    public static final String ForILine2 = "\t%s.add%s(%s[i]);\n}\n";

    public static final String ForEachLine1 = "while(iterator.hasNext())\n{\n";
    public static final String ForEachLine2 = "\titerator.advance(); \n \n";
    public static final String ForEachLine3 = "\t%s %s = iterator.value(); \n ";

    public static final String ForEachAdd = "\t%s.add%s(%s.toDB());\n}\n";
}
