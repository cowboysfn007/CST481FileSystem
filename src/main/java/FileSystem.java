package main.java;

import java.util.Arrays;

/**
 * Created by rob on 11/6/14.
 */
public class FileSystem {

    private String currentUser;
    private Metadata metadata;

    public FileSystem(){
        metadata = new Metadata(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }

    public void setUser(String user){
        currentUser = user;
    }

    public void changeDirectory(String input){
        System.out.println(input);
    }

    public void printWorkingDirectory(){
        System.out.println("nananananananaananan batman!!");
    }

    public void read(String resource){
        //TODO, use the metadata function to find out if user has permission to do requested function, sample below.
        System.out.println(metadata.hasPermission(currentUser, "r", resource));

        //TODO Read logic goes here!!!
    }

    public void write(String parameters){
        String[] parameterSplit = parameters.split(" ", 2);
        String resource = parameterSplit[0];
        String value = parameterSplit[1];

        //TODO, use the metadata.hasPermission function to find out if the current user has permission to do requested function, then do it, sample below
        System.out.println(metadata.hasPermission(currentUser, "w", resource));
        System.out.println(value);

        //TODO Write logic goes here!!!
    }

    public void changeMetadata(String parameters){
        metadata.changeRule(parameters);
        metadata.saveChanges(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }

    public void listMetadata(){
        metadata.listRules();
    }

    public void removeMetadata(String ruleNumber){
        metadata.removeRule(ruleNumber);
        metadata.saveChanges(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }

    public void help(){
        System.out.println("cm (“change metadata”) <resource> [Owner: <user> | <ACE: <principal><type><perm>]  ex. cm foo1.txt [Owner: user1 | ACE: user2 allow rw]");
    }
}
