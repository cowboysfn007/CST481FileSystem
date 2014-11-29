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

    public void setUser(String input){
        System.out.println("wooo!!");
    }

    public void changeDirectory(String input){
        System.out.println(input);
    }

    public void printWorkingDirectory(){
        System.out.println("nananananananaananan batman!!");
    }

    public void read(String input){
        System.out.println("wooo!!");
    }

    public void write(String input){
        System.out.println("wooo!!");
    }

    public void changeMetadata(String input){
        System.out.println("wooo!!");
    }

    public void listMetadata(){
        metadata.listRules();
    }

    public void removeMetadata(String ruleNumber){
        System.out.println("wooo!!");
        metadata.removeRule(ruleNumber);
        metadata.saveChanges(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }
}
