package main.java;

import java.util.ArrayList;

/**
 * Created by rob on 11/16/14.
 */
public class Directory {

    private String name;
    private String owner;
    private ArrayList<Permission> permissions;

    public Directory(String name, String owner, ArrayList<Permission> permissions){
        this.name = name;
        this.owner = owner;
        this.permissions = permissions;
    }

    public String toString(){
        String permissionString = "";
        for(Permission permission: permissions){
            permissionString += permission.toString();
        }

        String password = PasswordManager.getPassword(name);
        String passwordString = "";
        if(!password.isEmpty()){
            passwordString = " Passwd: " + password;
        }

        return name + " Owner: " + owner + permissionString + passwordString;
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

    public void setName(String name){
        this.name = name;
    }


}
