package com.zhru.jdk.stringvstringbuilder;

/**
 * {@link StringBuilder}　
 * java.lang.StringBuilder#append(java.lang.CharSequence)连接
 * @Author zhru
 * @Date 2019-11-22
 **/
public class StringBuilderConcat {

    public static void main(String[] args) {
        if (args == null || args.length == 0) return;
        int num = Integer.valueOf(args[0]);
        long startTime = System.currentTimeMillis();
        statement(num);
        long endTime = System.currentTimeMillis();
        System.out.printf(" %s 次运算耗时 %s 毫秒", num, endTime - startTime);
    }

    private static String statement(int num) {
        StringBuilder result = new StringBuilder();
        if (num <= 0) {
            return result.toString();
        }

        for (int i = 0; i < num; i++) {
            result.append(lineForItem(i)); // String concatentation
        }

        return result.toString();
    }

    private static String lineForItem(int i) {
        return "zhru";
    }
}
