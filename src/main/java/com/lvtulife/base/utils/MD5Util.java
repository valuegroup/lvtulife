package com.lvtulife.base.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author 孙宇
 */
public class MD5Util {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "q";
        String salt = "lvtulife";
        System.out.println(md5(password));

        md5_32(password); // 使用简单的MD5加密方式
        sha_256(password); // 使用256的哈希算法(SHA)加密
        sha_SHA_256(password); // 使用SHA-256的哈希算法(SHA)加密
        md5_SystemWideSaltSource(password, salt); // 使用MD5再加全局加密盐加密的方式加密
    }

    public static void md5_32(String password) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版
        md5.setEncodeHashAsBase64(false);
        String pwd = md5.encodePassword(password, null);
        System.out.println("MD5: " + pwd + " len=" + pwd.length());
    }

    public static void sha_256(String password) throws NoSuchAlgorithmException {
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        sha.setEncodeHashAsBase64(true);
        String pwd = sha.encodePassword(password, null);
        System.out.println("哈希算法 256: " + pwd + " len=" + pwd.length());
    }


    public static void sha_SHA_256(String password) {
        ShaPasswordEncoder sha = new ShaPasswordEncoder();
        sha.setEncodeHashAsBase64(false);
        String pwd = sha.encodePassword(password, null);
        System.out.println("哈希算法 SHA-256: " + pwd + " len=" + pwd.length());
    }


    public static String md5_SystemWideSaltSource(String password, String salt) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);

        // 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
        String pwd = md5.encodePassword(password, salt);
        System.out.println("MD5 SystemWideSaltSource: " + pwd + " len=" + pwd.length());
        return pwd;
    }


    /**
     * md5加密
     *
     * @param password
     * @return
     */
    public static String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
