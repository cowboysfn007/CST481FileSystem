package main.java;

import java.io.File;


/**
 * Created by rob on 11/6/14.
 */
public class FileSystem extends FileSystemInterface{

    private String rootDir = "src/main/resources/dataset/top";
    private String workingDir = "";
    private String currentUser;
    private Metadata metadata;

    public FileSystem(){
        metadata = new Metadata(new java.io.File("src/main/resources/dataset/FS_Meta1.txt"));
    }

    public void setUser(String user){
        currentUser = user;
        workingDir = "";
    }

    public void changeDirectory(String input){
        //Split input to check for permissions
        String[] inputArray = input.split("/");

        //Check user permissions to directory
        Boolean allow = true;
        for (int i=0; i<inputArray.length; i++) {
            allow = metadata.hasPermission(currentUser, "r", inputArray[i]);
            if (allow == false) break;
        }

        //If user is allowed access to directory change directory
        if (allow) {
            if (input.equals("..")) {
                int lastIndex = workingDir.lastIndexOf("/");
                if (lastIndex != -1) {
                    workingDir = workingDir.substring(0, lastIndex);
                }
            } else {
                String temp = rootDir + workingDir + "/" + input;
                File dir = new File(temp);

                if (dir.exists() && !(input.contains(".") && input.length() < 2)) {
                    workingDir = workingDir + "/" + input;
                } else System.out.println("cd: " + input + ": No such file or directory found");
            }
        }else System.out.println("Error: You do not have access to directory");
    }

    public void printWorkingDirectory(){
        System.out.println(workingDir);
    }

    public void read(String resource){
        //TODO, use the metadata function to find out if user has permission to do requested function, sample below.
        System.out.println(metadata.hasPermission(currentUser, "r", resource));

        //TODO Read logic goes here!!!
    }

    public void write(String parameters){
        String[] parameterSplit = parameters.split(" ", 2);
        if(parameterSplit.length < 2){
            System.out.println("Incorrect Write Operation");
            return;
        }
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

    public String getWorkingDir() {
        return workingDir;
    }

    public void help(){
        System.out.println("cm (“change metadata”) <resource> [Owner: <user> | <ACE: <principal><type><perm>]  ex. cm foo1.txt [Owner: user1 | ACE: user2 allow rw]");
    }
}
