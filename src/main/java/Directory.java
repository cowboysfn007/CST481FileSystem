package main.java;

import java.util.ArrayList;

/**
 * Created by rob on 11/16/14.
 */
public class Directory {

    private String name;
    private String owner;
    private ArrayList<Permission> permissions;
    private Password password;

    public Directory(String name, String owner, ArrayList<Permission> permissions, Password password){
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

    public String getOwner(){
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public void appendPermission(Permission permission){
        permissions.add(permission);
    }

    public ArrayList<Permission> getPermissions(){
        return permissions;
    }

    public String getName(){
        return name;
    }


}
