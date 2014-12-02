package main.java;

/**
 * Created by rparmer on 12/1/14.
 */
public class Password {

    private String resource;
    private String password;
    private String saltPassword;

    public Password(String resource, String password) {
        this.resource = resource;
        this.password = password;
        this.saltPassword = createSaltPassword(password);
    }

    public String createSaltPassword(String passwd) {
        String salted = "";

        return salted;
    }
}
