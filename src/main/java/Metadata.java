package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rob on 11/16/14.
 */
public class Metadata {

    private Hashtable<String, User> users;
    private Hashtable<String, Directory> directories;
    private Hashtable<String, File> files;

    public Metadata(java.io.File metadataFile){
        try{
            users = new Hashtable<>();
            directories = new Hashtable<>();
            files = new Hashtable<>();
            BufferedReader br = new BufferedReader(new FileReader(metadataFile));
            String line;
            Pattern userLine = Pattern.compile("^(Users)");
            Pattern directoryLine = Pattern.compile("^(Directory)");
            Pattern fileLine = Pattern.compile("^(File)");
            Matcher userMatcher;
            Matcher directoryMatcher;
            Matcher fileMatcher;

            while((line = br.readLine()) != null){
                userMatcher = userLine.matcher(line);
                if(userMatcher.find()){
                    buildUsers(line);
                }
                directoryMatcher = directoryLine.matcher(line);
                if(directoryMatcher.find()){
                    buildDirectory(line);
                }
                fileMatcher = fileLine.matcher(line);
                if(fileMatcher.find()){
                    buildFile(line);
                }
            }

            /*System.out.println(files.get("foo1.txt").getName());
            System.out.println(files.get("foo1.txt").getOwner());
            System.out.println(files.get("foo1.txt").getPermissions().get(1).getUser());
            System.out.println(files.get("foo1.txt").getPermissions().get(1).getAction());
            System.out.println(files.get("foo1.txt").getPermissions().get(1).getAccess());*/


        }catch (IOException e){
            System.out.println("Bad File" + e.getMessage());
        }
    }

    public void listRules(){
        Set<String> directoryKeys = directories.keySet();
        int i = 0;
        int j;
        for(String key: directoryKeys){
            j = 0;
            for(Permission permission: directories.get(key).getPermissions()){
                System.out.println(i + "-" + j + ": " + "Directory: " + directories.get(key).getName() + " ACE: " + permission.getUser() + " " + permission.getAccess() + " " + permission.getAction());
                j++;
            }
            i++;
        }

        Set<String> fileKeys = files.keySet();
        for(String key: fileKeys){
            j=0;
            for(Permission permission: files.get(key).getPermissions()){
                System.out.println(i + "-" + j + ": " + "File: " + files.get(key).getName() + " ACE: " + permission.getUser() + " " + permission.getAccess() + " " + permission.getAction());
                j++;
            }
            i++;
        }

    }

    public void saveChanges(java.io.File metadataFile){
        try{
            FileWriter fw = new FileWriter(metadataFile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            //Write Users
            bw.write("Users: ");
            Set<String> userKeys = users.keySet();
            for(String key: userKeys){
                bw.write(users.get(key).getName() + " ");
            }
            bw.newLine();

            Set<String> directoryKeys = directories.keySet();
            for(String key: directoryKeys){
                bw.write("Directory: " + directories.get(key).toString());
            }
            bw.newLine();

            bw.close();
        }catch(IOException e){System.out.println(e.getMessage());}
    }

    private void buildUsers(String userLine){
        String[] userList = userLine.split(" ");
        for(int i = 1; i < userList.length; i++){
            users.put(userList[i], new User(userList[i]));
        }
    }

    private void buildDirectory(String directoryLine){
        String[] directorySplit = directoryLine.split(" ");
        String directoryName = directorySplit[1];
        String directoryOwner = directorySplit[3];
        ArrayList<Permission> permissions = new ArrayList<>();

        for(int i = 4; i < directorySplit.length; i += 4){
            permissions.add(new Permission(directorySplit[i+1], directorySplit[i+2], directorySplit[i+3]));
        }

        directories.put(directoryName, new Directory(directoryName, directoryOwner, permissions));
    }

    private void buildFile(String fileLine){
        String[] fileSplit = fileLine.split(" ");
        String fileName = fileSplit[1];
        String fileOwner = fileSplit[3];
        ArrayList<Permission> permissions = new ArrayList<>();

        for(int i = 4; i < fileSplit.length; i += 4){
            permissions.add(new Permission(fileSplit[i+1], fileSplit[i+2], fileSplit[i+3]));
        }

        files.put(fileName, new File(fileName, fileOwner, permissions));
    }
}
