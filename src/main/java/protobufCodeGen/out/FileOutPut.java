package protobufCodeGen.out;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author zhangfan
 * @Date 2020/9/9 17:04
 * @Version 1.0
 */
public class FileOutPut implements IOutPut
{

    /**
     * 路径
     */
    private String path;

    public FileOutPut(String path)
    {
        this.path = path;
    }

    /**
     * 初始化操作在这里做
     */
    @Override
    public void init()
    {
        File file = new File(path);
        if (file.isFile() && file.exists())
        {
            file.delete();
        }
    }

    @Override
    public void append(String content)
    {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path, true)))
        {
            bos.write(content.getBytes(Charset.forName("UTF8")));
            bos.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
