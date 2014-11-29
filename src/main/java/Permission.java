package main.java;

/**
 * Created by rob on 11/16/14.
 */
public class Permission {

    private String user;
    private String access;
    private String action;

    public Permission(String user, String access, String action){
        this.user = user;
        this.access = access;
        this.action = action;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
