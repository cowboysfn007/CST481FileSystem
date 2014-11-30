package main.java;

import java.io.File;


/**
 * Created by rob on 11/6/14.
 */
public class FileSystem extends FileSystemInterface{

    private String rootDir = "src/main/resources/dataset";
    private String workingDir = "";
    private Metadata metadata;

    public FileSystem(){
        metadata = new Metadata(new java.io.File(rootDir + "/FS_Meta1.txt"));
    }

    public void setUser(String input){
        System.out.println("wooo!!");
    }

    public void changeDirectory(String input){

        if (input.equals("..")) {
            int lastIndex = workingDir.lastIndexOf("/");
            if (lastIndex != -1) {
                workingDir = workingDir.substring(0,lastIndex);
            }
        }else {
            String temp = rootDir + workingDir + "/" + input;
            File dir = new File(temp);

            if (dir.exists() && !(input.contains(".") && input.length() < 2)) {
                workingDir = workingDir + "/" + input;
            } else System.out.println("cd: " + input + ": No such file or directory found");
        }
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
        metadata.saveChanges(new java.io.File(rootDir + "/FS_Meta1.txt"));
    }

    public void listMetadata(){
        metadata.listRules();
    }

    public void removeMetadata(String ruleNumber){
        metadata.removeRule(ruleNumber);
        metadata.saveChanges(new java.io.File(rootDir + "/FS_Meta1.txt"));
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void help(){
        System.out.println("cm (“change metadata”) <resource> [Owner: <user> | <ACE: <principal><type><perm>]  ex. cm foo1.txt [Owner: user1 | ACE: user2 allow rw]");
    }
}
