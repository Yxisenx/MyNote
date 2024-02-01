package cn.onecolour.shiroSpring.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md2Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 散列算法工具类
 */
public class DigestsUtil {
    // hash算法
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";
    public static final String MD2 = "MD2";
    public static final String MD5 = "MD5";

    // 散列次数
    public static final Integer ITERATIONS = 3;

    /**
     * @param input 需要散列字符串
     * @param salt  盐字符串
     * @return sha1
     */
    private static String sha1(String input, String salt, Integer iterations) {
        return new SimpleHash(SHA1, input, salt, iterations).toString();
    }

    private static String sha256(String input, String salt, Integer iterations) {
        return new SimpleHash(SHA256, input, salt, iterations).toString();
    }
    private static String sha384(String input, String salt, Integer iterations) {
        return new SimpleHash(SHA384, input, salt, iterations).toString();
    }

    private static String sha512(String input, String salt, Integer iterations) {
        return new SimpleHash(SHA512, input, salt, iterations).toString();
    }
    private static String md2(String input, String salt, Integer iterations) {
        return new Md2Hash(input, salt, iterations).toString();
    }
    private static String md5(String input, String salt, Integer iterations) {
        return new Md5Hash(input, salt, iterations).toString();
    }

    /**
     * @return salt
     * @Description: 随机获得salt字符串
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().toHex();
    }


    /**
     *
     * @param hashMethod 加密方式
     * @param passwordPlain 密码原文
     * @param salt 盐
     * @param iterations 散列次数
     * @Description: 生成密码字符密文和salt密文
     * @return map{salt, password}
     */
    public static Map<String, String> encrypt(String hashMethod, String passwordPlain, String salt, Integer iterations) {
        Map<String, String> map = new HashMap<>();
        String password = encryptWith(hashMethod, passwordPlain, salt, iterations);
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }
    public static Map<String, String> encrypt(String hashMethod, String passwordPlain, Integer iterations) {
        String salt = generateSalt();
        return encrypt(hashMethod, passwordPlain, salt, iterations);
    }
    public static Map<String, String> encrypt(String hashMethod,String passwordPlain, String salt) {
        return encrypt(hashMethod, passwordPlain, salt,ITERATIONS);
    }

    public static Map<String, String> encrypt(String hashMethod, String passwordPlain) {
        return encrypt(hashMethod, passwordPlain, ITERATIONS);
    }

    /**
     * @param hashMethod    Hash加密方式
     * @param passwordPlain 密码原文
     * @param salt          盐
     * @param iterations    迭代次数
     * @return 加密后的密码
     */

    private static String encryptWith(String hashMethod, String passwordPlain, String salt, Integer iterations) {
        if (hashMethod.equals(SHA1) || hashMethod.equals("SHA1")) {
            return sha1(passwordPlain, salt, iterations);
        }
        if (hashMethod.equals(SHA256) || hashMethod.equals("SHA256")) {
            return sha256(passwordPlain, salt, iterations);
        }
        if (hashMethod.equals(SHA384) || hashMethod.equals("SHA384")) {
            return sha384(passwordPlain, salt, iterations);
        }
        if (hashMethod.equals(SHA512) || hashMethod.equals("SHA512")) {
            return sha512(passwordPlain, salt, iterations);
        }
        if (hashMethod.equals(MD2) || hashMethod.equals("md2")) {
            return md2(passwordPlain, salt, iterations);
        }
        if (hashMethod.equals(MD5) || hashMethod.equals("md5")) {
            return md5(passwordPlain, salt, iterations);
        }
        throw new IllegalArgumentException("加密方法有误");
    }

    /**
     * @Description: SHA-1加密
     */
    public static Map<String, String> encryptWithSHA1(String passwordPlain,String salt, Integer iterations) {
        return encrypt(SHA1, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithSHA1(String passwordPlain,Integer iterations) {
        return encrypt(SHA1, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithSHA1(String passwordPlain,String salt) {
        return encrypt(SHA1, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithSHA1(String passwordPlain) {
        return encrypt(SHA1, passwordPlain);
    }

    /**
     * @Description: SHA-256加密
     */
    public static Map<String, String> encryptWithSHA256(String passwordPlain,String salt, Integer iterations) {
        return encrypt(SHA256, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithSHA256(String passwordPlain,Integer iterations) {
        return encrypt(SHA256, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithSHA256(String passwordPlain,String salt) {
        return encrypt(SHA256, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithSHA256(String passwordPlain) {
        return encrypt(SHA256, passwordPlain);
    }

    /**
     * @Description: SHA-384加密
     */
    public static Map<String, String> encryptWithSHA384(String passwordPlain,String salt, Integer iterations) {
        return encrypt(SHA384, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithSHA384(String passwordPlain,Integer iterations) {
        return encrypt(SHA384, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithSHA384(String passwordPlain,String salt) {
        return encrypt(SHA384, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithSHA384(String passwordPlain) {
        return encrypt(SHA384, passwordPlain);
    }

    /**
     * @Description: SHA-512加密
     */
    public static Map<String, String> encryptWithSHA512(String passwordPlain,String salt, Integer iterations) {
        return encrypt(SHA512, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithSHA512(String passwordPlain,Integer iterations) {
        return encrypt(SHA512, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithSHA512(String passwordPlain,String salt) {
        return encrypt(SHA512, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithSHA512(String passwordPlain) {
        return encrypt(SHA512, passwordPlain);
    }

    /**
     * @Description: MD2加密
     */
    public static Map<String, String> encryptWithMD2(String passwordPlain,String salt, Integer iterations) {
        return encrypt(MD2, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithMD2(String passwordPlain,Integer iterations) {
        return encrypt(MD2, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithMD2(String passwordPlain,String salt) {
        return encrypt(MD2, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithMD2(String passwordPlain) {
        return encrypt(MD2, passwordPlain);
    }

    /**
     * @Description: MD5加密
     */
    public static Map<String, String> encryptWithMD5(String passwordPlain,String salt, Integer iterations) {
        return encrypt(MD5, passwordPlain,salt,iterations);
    }
    public static Map<String, String> encryptWithMD5(String passwordPlain,Integer iterations) {
        return encrypt(MD5, passwordPlain,iterations);
    }
    public static Map<String, String> encryptWithMD5(String passwordPlain,String salt) {
        return encrypt(MD5, passwordPlain,salt);
    }
    public static Map<String, String> encryptWithMD5(String passwordPlain) {
        return encrypt(MD5, passwordPlain);
    }
}
