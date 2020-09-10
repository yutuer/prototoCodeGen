package util;

/**
 * @Description
 * @Author zhangfan
 * @Date 2020/9/2 17:29
 * @Version 1.0
 */
public class Util
{
    /**
     * 首字母小写
     *
     * @param name
     * @return
     */
    public static String firstLetterLower(String name, int index)
    {
        final int LowerIndex = index;
        return name.substring(0, LowerIndex).toLowerCase() + name.substring(LowerIndex);
    }

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String firstLetterUpper(String name, int index)
    {
        final int upperIndex = index;
        return name.substring(0, upperIndex).toUpperCase() + name.substring(upperIndex);
    }

    /**
     * 得到>= x的2次幂
     *
     * @param x
     * @return
     */
    public static int intToMaxTowPower(int x)
    {
        if ((x & (x - 1)) == 0)
        {
            return x;
        }

        // 左边的0的个数
        int leftZeroCount = Integer.numberOfLeadingZeros(x);
        // 无符号右移
        return Integer.MIN_VALUE >>> (leftZeroCount - 1);
    }

    public static void main(String[] args)
    {
        System.out.println(intToMaxTowPower(0b10011));
    }

}
