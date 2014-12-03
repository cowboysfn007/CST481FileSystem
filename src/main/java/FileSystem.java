package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


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
        currentUser = metadata.getDefaultUser();
    }

    public void setUser(String user){
        if(metadata.userExists(user)){
            currentUser = user;
            workingDir = "";
        }
        else{
            System.out.println("User does not exist");
        }


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

    public void read(String resource)  {
        //TODO, use the metadata function to find out if user has permission to do requested function, sample below.
        System.out.println(metadata.hasPermission(currentUser, "r", resource));

        //TODO Read logic goes here!!!
        Path foo1 = Paths.get("src/main/resources/dataset/top/foo1.txt");
//        return Files.readAllLines(foo1, Charset.defaultCharset());
       if(Files.exists(foo1) && Files.isReadable(foo1)) {

        try {
            // File reader
            BufferedReader reader = Files.newBufferedReader(foo1, Charset.defaultCharset());

            String line;
            // read each line
            while((line = reader.readLine()) != null) {
                System.out.println(line);
                // tokenize each number
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                while (tokenizer.hasMoreElements()) {
                    // parse each integer in file
                    int element = Integer.parseInt(tokenizer.nextToken());
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       }
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
//        Path path = Paths.get(aFileName);
//        Files.write(path, aLines, ENCODING);
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

    public void setPassword(String resource) {
        if (resource != null) {
            System.out.print("Enter new password for " + resource + ": ");
            Scanner scan = new Scanner(System.in);
            String password = scan.nextLine();
            //new Password(password);
        }
    }

    public void listPasswords() {
        System.out.println(PasswordManager.comparePassword("dir1", "test"));
        System.out.println(PasswordManager.comparePassword("dir1", "fail"));
        System.out.println(PasswordManager.comparePassword("dir2", "password"));
        System.out.println(PasswordManager.comparePassword("dir2", "password2"));
    }
    public void help(){
        System.out.println("cm (“change metadata”) <resource> [Owner: <user> | <ACE: <principal><type><perm>]  ex. cm foo1.txt [Owner: user1 | ACE: user2 allow rw]");
    }

    public String getCurrentUser(){
        return currentUser;
    }
}
