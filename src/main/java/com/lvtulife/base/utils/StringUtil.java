package com.lvtulife.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author 孙宇
 */
public class StringUtil {

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 替换字符串并让它的下一个字母为大写
     *
     * @return
     */
    public static String replaceUnderlineAndFirstToUpper(String str) {
        StringBuffer temp = new StringBuffer(str);
        int count = temp.indexOf("_");
        while (count != 0) {
            int num = temp.indexOf("_", count);// 从字符串的指定位置开始
            count = num + 1;
            if (num != -1) {
                char ss = temp.charAt(count);
                char ia = (char) (ss - 32); // 位运算转大写
                temp.replace(count, count + 1, ia + "");
            }
        }
        return temp.toString().replaceAll("_", "");
    }

    /**
     * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->hello_world
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线小写方式命名的字符串
     */
    public static String replaceFirstUpperToUnderline(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成小写
            result.append(name.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成小写
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * str转换为Integer 如果为NUll则转换为NUll
     *
     * @return
     */
    public static Integer strToInteger(String str) {
        if (isNumeric(str)) {
            return Integer.parseInt(str);
        } else {
            return null;
        }
    }

    /**
     * str转换为Byte
     *
     * @param str
     * @return
     */
    public static Byte strToByte(String str) {
        if (isNumeric(str)) {
            return Byte.parseByte(str);
        } else {
            return null;
        }
    }

    /**
     * @标题: isNumeric
     * @描述: 判断字符串是否是整数 ture则是数字
     * @处理步骤:
     * @作者: ljc
     * @参数: @param str
     * @参数: @return
     * @返回类型: boolean
     * @throws抛出
     */
    public static boolean isNumeric(String str) {
        if (!StringUtils.isBlank(str)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为正负的数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveNegativeNumeric(String str) {
        if (!StringUtils.isBlank(str)) {
            Pattern pattern = Pattern.compile("^-?[0-9]+");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为正负含小数点的数
     *
     * @param str
     * @return
     */
    public static boolean isAllNumeric(String str) {
        if (!StringUtils.isBlank(str)) {
            Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    /**
     * @标题: isDate
     * @描述: 是否时间类型
     * @处理步骤:
     * @作者: ljc
     * @参数: @param strExp
     * @参数: @return
     * @返回类型: boolean
     * @throws抛出
     */
    public boolean isDate(String strExp, String format) {
        if (StringUtils.isBlank(strExp) || StringUtils.isBlank(format)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            if (strExp.equals(sdf.format(sdf.parse(strExp)))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断小数
     *
     * @param str
     * @return
     */
    public boolean isDouble(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches("[\\d.]+");
    }

    /**
     * 格式化字符串
     * <p>
     * 例：formateString("xxx{0}bbb",1) = xxx1bbb
     *
     * @param str
     * @param params
     * @return
     */
    public static String formateString(String str, String... params) {
        for (int i = 0; i < params.length; i++) {
            str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
        }
        return str;
    }

    /**
     * @param str
     * @return
     * @方法描述：首字母小写
     * @参数描述：
     */
    public static String capitalToLower(String str) {
        if (str == null || "".equals(str.trim()))
            return str;
        return String.valueOf(Character.toLowerCase(str.charAt(0))) + str.substring(1);
    }

    /**
     * @param str
     * @return
     * @方法描述：首字母大写
     * @参数描述：
     */
    public static String capitalToUpper(String str) {
        if (str == null || "".equals(str.trim()))
            return str;
        return String.valueOf(Character.toUpperCase(str.charAt(0))) + str.substring(1);
    }

    public static String splitJointUrl(String urlHead, String urlModule, String urlMethod) {
        String url = "";
        if (StringUtils.isBlank(urlMethod)) {
            // 三者都为空，判断最后一个为空即可
            url = "";
        } else if (StringUtils.isBlank(urlHead) && StringUtils.isBlank(urlModule)) {
            // 来自其他包的如 /druid  /monitoring
            url = StringUtil.formateString("/{0}", urlMethod);
        } else {
            // 拼接成完整的URL 如/system/sysUser/list
            url = StringUtil.formateString("/{0}/{1}/{2}", urlHead, urlModule, urlMethod);
        }
        return url;
    }

    public static boolean isNumberic(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


}
