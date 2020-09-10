package protobufCodeGen.out;

/**
 * @Description 输出方式
 * @Author zhangfan
 * @Date 2020/9/2 20:26
 * @Version 1.0
 */
public interface IOutPut
{

    /**
     * append输出
     *
     * @param content
     */
    void append(String content);

    /**
     * 初始化
     */
    void init();

}
