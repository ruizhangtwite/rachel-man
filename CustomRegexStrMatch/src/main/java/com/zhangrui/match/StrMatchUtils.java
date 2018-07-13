package com.zhangrui.match;

/**
 * 用于检测字符串是否匹配对应的正则
 *
 * @author zr
 * @date 2018/7/13
 */
public class StrMatchUtils {

    /**
     * 处理含有.和*的字符串，返回是否匹配对应的正则表达式，（只处理含有一个.或者*的字符串）
     * 设计思想：
     * 对于正则表达式，存在3种情况  .sss(*sss) sss.(sss*)  ss.sss(ss*sss)
     *
     * @param str 待匹配的参数
     * @param regex 正则表达式
     * @return 传入的值是否匹配对应的正则表达式
     */
    public static boolean regexMatch(String str, String regex){
        if (str == null || regex == null) return false;

        if (str.length() < regex.length()){
            return false;
        }
        //没有关键字的时候，就是比较两者的值是否相等
        if (regex.indexOf(".") == -1 && regex.indexOf("*") == -1){
            return str.equals(regex);
        }

        /**
         * 1、将regex进行3分段
         * 2、判断左分段和右分段，在待匹配字符串中的位置索引
         * 3、基于索引判断
         */
        boolean hasDot = regex.indexOf(".") > -1;
        int regexIndex = hasDot ? regex.indexOf(".") : regex.indexOf("*");
        String leftRegex = regex.substring(0, regexIndex);
        String rightRegex = regex.substring(regexIndex + 1, regex.length());

        if (leftRegex.length() > 0){
            int leftStrIndex = str.indexOf(leftRegex);
            if (rightRegex.length() > 0 ){ //ss.sss  或者 ss*sss
                int rightStrIndex = str.indexOf(rightRegex, regexIndex + 1); //一定要是开始于regexIndex + 1
                if (leftStrIndex != 0 || rightStrIndex == -1 ){
                    return false;
                }
                if (hasDot){
                    return rightStrIndex - leftStrIndex == leftRegex.length() + 1;
                }

                return rightStrIndex - leftStrIndex > leftRegex.length();
            } else {
                if (leftStrIndex != 0) return false;
                if (hasDot){
                    return str.length() - leftStrIndex == leftRegex.length() + 1;
                }

                return str.length() - leftRegex.length() > 0;
            }
        } else {
            int rightStrIndex = str.indexOf(rightRegex, regexIndex + 1);
            if (hasDot) {
                return rightStrIndex == 1;
            }
            return rightStrIndex >= 1;
        }
    }

}
