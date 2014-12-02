package main.java;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by rob on 11/16/14.
 */
public class File {

    private String name;
    private String owner;
    private ArrayList<Permission> permissions;
    private Password password;

    public File(String name, String owner, ArrayList<Permission> permissions, Password password){
        this.name = name;
        this.owner = owner;
        this.permissions = permissions;
        this.password = password;
    }

    public String toString(){
        String permissionString = "";
        for(Permission permission: permissions){
            permissionString += permission.toString();
        }
        if(passwordSet()){
            permissionString += "Passwd: " + password.getPassword();
        }
        return name + " Owner: " + owner + permissionString;
    }

    public boolean passwordSet(){
        boolean isSet = false;
        if(password.isSet()){
            isSet = true;
        }
        return isSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }

    public void appendPermission(Permission permission){
        permissions.add(permission);
    }
}
