package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Description TODO
 * @Author zhangfan
 * @Date 2020/9/1 20:45
 * @Version 1.0
 */
public class Log
{
    // 不使用 slf4j的.  是因为它会使用对象数组, 会带来额外的gc
    public static final Logger logger = LogManager.getLogger(Log.class);

}
