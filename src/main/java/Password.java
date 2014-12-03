package main.java;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by rparmer on 12/1/14.
 */
public class Password {

    private String password;
    private byte[] saltedPassword;
    private byte[] salt;

    public Password(String password, byte[] saltedPassword, byte[] salt) {
        this.password = password;
        this.saltedPassword = saltedPassword;
        this.salt = salt;
    }

    public Password(){
        password = "";
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public byte[] getSalt(){
        return salt;
    }

    public byte[] getSaltedPassword(){
        return saltedPassword;
    }

    public String toString(){
        return "Clear Text: " + password + " Salt: " + Arrays.toString(salt) + " Salted Password: " + Arrays.toString(saltedPassword);
    }


}
