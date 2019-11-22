package com.zhru.jdk.stringvstringbuilder;

/**
 * {@link String}　通过 + 号连接
 * @Author zhru
 * @Date 2019-11-22
 **/
public class StringConcat {

    public static void main(String[] args) {
        if (args == null || args.length == 0) return;
        int num = Integer.valueOf(args[0]);
        long startTime = System.currentTimeMillis();
        statement(num);
        long endTime = System.currentTimeMillis();
        System.out.printf(" %s 次运算耗时 %s 毫秒", num, endTime - startTime);
    }

    /**
     * 拼接字符串(+)
     * @param num 拼接次数
     * @return　拼接后的字符串
     */
    private static String statement(int num) {
        String result = "";
        if (num <= 0) {
            return result;
        }

        for (int i = 0; i < num; i++) {
            result += lineForItem(i); // String concatentation
        }

        return result;
    }

    private static String lineForItem(int i) {
        return "zhru";
    }
}
