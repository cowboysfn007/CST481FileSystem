package main.java;

import javax.swing.*;
import java.io.*;
import java.io.File;
import java.util.Arrays;


/**
 * Created by rob on 11/6/14.
 */
public class FileSystem extends FileSystemInterface{

    private String rootDir = "src/main/resources/dataset";
    private String workingDir = "";
    private String currentUser;
    private Metadata metadata;

    public FileSystem(){
        metadata = new Metadata(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }

    public void setUser(String input){
        System.out.println("wooo!!");
    }

    public void changeDirectory(String input){

        String test = rootDir + workingDir + "/" + input;
        File temp = new File(test);

        if (temp.exists()) {
            workingDir = workingDir + "/" + input;
        }
        else System.out.println("cd: " + input + " No such file or directory found");
    }

    public void printWorkingDirectory(){
        System.out.println(workingDir);
    }

    public void read(String input){
        System.out.println("wooo!!");
    }

    public void write(String input){
        System.out.println("wooo!!");
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
    
    public String getWorkingDir() {
        return workingDir;
    }

    public void help(){
        System.out.println("cm (“change metadata”) <resource> [Owner: <user> | <ACE: <principal><type><perm>]  ex. cm foo1.txt [Owner: user1 | ACE: user2 allow rw]");
    }
}
