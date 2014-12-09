package main.java;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;


/**
 * Created by rob on 12/2/14.
 */
public class PasswordManager {

    private static Hashtable<String, Password> passwordTable = new Hashtable<>();
    private static final Random RANDOM = new SecureRandom();

    public PasswordManager(){
    }

    public static void addPassword(String resource, String password){
        Password passwordObject;
        byte[] salt = getNextSalt();
        byte[] hashedPassword = hash(password, salt);
        passwordObject = new Password(password, hashedPassword, salt);
        passwordTable.put(resource, passwordObject);
    }

    public static boolean comparePassword(String resource, String password){
        boolean matches = false;
        if(passwordTable.containsKey(resource)){
            Password passwordObject = passwordTable.get(resource);
            byte[] hashedInput = hash(password, passwordObject.getSalt());
            if(Arrays.equals(hashedInput, passwordObject.getSaltedPassword())){
                matches = true;
            }
        }
        return matches;
    }

    public static void listAllPasswords(){
        Set<String> keys = passwordTable.keySet();
        for(String key: keys){
            System.out.println(key + ": " + passwordTable.get(key).toString());
        }
    }

    public static void listPassword(String resource){
        if(passwordTable.containsKey(resource)){
            System.out.println(resource + ": " + passwordTable.get(resource).toString());
        }
        else{
            System.out.println(resource + " does not have a password set.");
        }
    }

    public static String getPassword(String resource){
        String password = "";
        if(passwordTable.containsKey(resource)){
            password = passwordTable.get(resource).getPassword();
        }
        return password;
    }

    private static byte[] getNextSalt(){
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private static byte[] hash(String password, byte[] salt){

        byte[] hash = {};
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(salt);
            hash = messageDigest.digest(password.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e){
            System.out.println("Dont have MD5");
        } catch (UnsupportedEncodingException e){
            System.out.println("Dont have UTF-8 Encoding");
        }
        return hash;
    }

    public static boolean hasPassword(String resource) {
        boolean password = false;
        if(passwordTable.containsKey(resource)){
            password = true;
        }
        return password;
    }

    public static void updateDirectory(String oldResource, String newResource){
        if(passwordTable.containsKey(oldResource)){
            Password password = passwordTable.remove(oldResource);
            passwordTable.put(newResource, password);
        }
    }
}
