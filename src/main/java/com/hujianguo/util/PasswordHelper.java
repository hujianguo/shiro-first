package com.hujianguo.util;


import com.hujianguo.domain.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    //private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    /**
     * 加密加盐
     *
     * @param user
     */
    public static void encryptPassword(User user) {
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        user.setPassword(newPassword);

    }

    public static String encryptPassword(String username, String password) {
        String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(username), hashIterations).toHex();
        return newPassword;
    }

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        encryptPassword(user);
        System.out.println(user);
    }
}
