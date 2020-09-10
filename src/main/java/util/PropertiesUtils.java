package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * properties文件操作相关工具
 * Created by wangqiang on 2017/9/14.
 */
public class PropertiesUtils
{
    /**
     * 统一的字符串分隔符
     */
    private static final String StrSplit = ";";

    public static Properties getProperties(String fileName)
    {
        Properties properties = new Properties();
        ClassLoader classLoader = PropertiesUtils.class.getClassLoader();

        try
        {
            InputStream in = classLoader.getResourceAsStream(fileName);
            if (in == null)
            {
                fileName = System.getProperty("user.dir") + "/" + fileName;
                in = new BufferedInputStream(new FileInputStream(fileName));
                //                in = classLoader.getResourceAsStream(System.getProperty("user.dir") + "/" + fileName);
                if (in == null)
                {
                    Log.logger.error("class loader get resource failure, fileName:{}", fileName);
                    return null;
                }
            }
            properties.load(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return properties;
    }

    public static int getInt(Properties properties, String key, int defaultValue)
    {
        String property = properties.getProperty(key);
        if (property == null)
        {
            Log.logger.error("properties key:{} is null", key);
            return defaultValue;
        }

        Integer result = Integer.valueOf(property);
        if (result == null)
        {
            Log.logger.error("properties key:{} is null", key);
            return defaultValue;
        }
        return result;
    }

    public static String getString(Properties properties, String key, String defaultValue)
    {
        String result = properties.getProperty(key);
        if (result == null)
        {
            Log.logger.error("properties key:{} is null", key);

            return defaultValue;
        }
        return result;
    }

    public static String[] getStringArray(Properties properties, String key, String[] defaultValue)
    {
        String result = properties.getProperty(key);
        if (result == null)
        {
            Log.logger.error("properties key:{} is null", key);

            return defaultValue;
        }
        return result.split(StrSplit);
    }
}
