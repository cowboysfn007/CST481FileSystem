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
        return name + " Owner: " + owner + permissionString;
    }

    public String getOwner(){
        return owner;
    }

    public ArrayList<Permission> getPermissions(){
        return permissions;
    }

    public String getName(){
        return name;
    }


}
