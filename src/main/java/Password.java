package main.java;

/**
 * Created by rparmer on 12/1/14.
 */
public class Password {

    private String password;
    private String saltPassword;

    public Password(String password) {
        this.password = password;
        this.saltPassword = createSaltPassword(password);
    }

    public Password(){
        password = "";
        saltPassword = "";
    }

    public void setPassword(String password){
        this.password = password;
        this.saltPassword = password + "";
    }

    public String createSaltPassword(String passwd) {
        String salted = "";

        return salted;
    }

    public boolean isSet(){
        boolean isSet = false;
        if(password != null && !password.isEmpty()){
            isSet = true;
        }
        return isSet;
    }

    public String getPassword(){
        return password;
    }
}
