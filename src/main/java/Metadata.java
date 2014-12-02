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
    private Hashtable<String, Password> passwords;

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


        }catch (IOException e){
            System.out.println("Bad File" + e.getMessage());
        }
    }

    public boolean hasPermission(String user, String action,  String resource){

        //Determine if file or folder
        Pattern filePattern = Pattern.compile("(.txt)$");
        Matcher fileMatcher = filePattern.matcher(resource);
        Boolean isFile = false;
        boolean hasPermission = true;   //open policy on folders
        if(fileMatcher.find()){
            isFile = true;
            hasPermission = false;      //closed policy on files
        }

        if(isFile){
            if(files.containsKey(resource)){
                if(files.get(resource).getOwner().equals(user)){
                    hasPermission = true;
                }
                ArrayList<Permission> filePermissions = files.get(resource).getPermissions();
                for(Permission permission: filePermissions){
                    if(permission.getUser().equals(user) &&  permission.getAccess().equals("allow") && permission.getAction().contains(action)){
                        hasPermission = true;
                    }
                    else if(permission.getUser().equals(user) &&  permission.getAccess().equals("deny") && permission.getAction().contains(action)){
                        hasPermission =false;
                    }
                }
            }
        }
        else{
            if(directories.containsKey(resource)){
                if(directories.get(resource).getOwner().equals(user)){
                    hasPermission = true;
                }
                ArrayList<Permission> directoryPermissions = directories.get(resource).getPermissions();
                for(Permission permission: directoryPermissions){
                    if(permission.getUser().equals(user) &&  permission.getAccess().equals("allow") && permission.getAction().contains(action)){
                        hasPermission = true;
                    }
                    else if(permission.getUser().equals(user) &&  permission.getAccess().equals("deny") && permission.getAction().contains(action)){
                        hasPermission =false;
                    }
                }
            }

        }

        return hasPermission;

    }

    public void listRules(){
        Set<String> directoryKeys = directories.keySet();
        int i = 0;
        int j;
        for(String key: directoryKeys){
            j = 0;
            for(Permission permission: directories.get(key).getPermissions()){
                System.out.println(i + "-" + j + ": " + "Directory: " + directories.get(key).getName() + " Owner: " + directories.get(key).getOwner() + " ACE: " + permission.getUser() + " " + permission.getAccess() + " " + permission.getAction());
                j++;
            }
            i++;
        }

        Set<String> fileKeys = files.keySet();
        for(String key: fileKeys){
            j=0;
            for(Permission permission: files.get(key).getPermissions()){
                System.out.println(i + "-" + j + ": " + "File: " + files.get(key).getName() + " Owner: " + files.get(key).getOwner() +  " ACE: " + permission.getUser() + " " + permission.getAccess() + " " + permission.getAction());
                j++;
            }
            i++;
        }

    }

    public void removeRule(String ruleNumber){
        String[] numberSplit = ruleNumber.split("-");

        Set<String> directoryKeys = directories.keySet();
        int i = 0;
        int j;
        for(String key: directoryKeys){
            j = 0;
            for(Permission permission: directories.get(key).getPermissions()){
                if(i == Integer.parseInt(numberSplit[0])  && j == Integer.parseInt(numberSplit[1])){
                    directories.get(key).getPermissions().remove(j);
                    return;
                }
                j++;
            }
            i++;
        }

        Set<String> fileKeys = files.keySet();
        for(String key: fileKeys){
            j=0;
            for(Permission permission: files.get(key).getPermissions()){
                if(i == Integer.parseInt(numberSplit[0])  && j == Integer.parseInt(numberSplit[1])){
                    files.get(key).getPermissions().remove(j);
                    return;
                }
                j++;
            }
            i++;
        }

    }

    public void changeRule(String parameters){
        //Parse Input
        String[] parameterSplit =  parameters.split(" ",2);
        String fileName = parameterSplit[0];
        String rules = parameterSplit[1];
        rules = rules.substring(1, rules.length()-1);
        String ruleSplit[] = rules.split("\\|");

        //Determine if file or folder
        Pattern filePattern = Pattern.compile("(.txt)$");
        Matcher fileMatcher = filePattern.matcher(fileName);
        Boolean isFile = false;
        if(fileMatcher.find()){
            isFile = true;
        }

        //Determine if changing owner or adding permission
        Pattern ownerLine = Pattern.compile("^(Owner:)");
        Matcher ownerMatcher;
        Pattern permissionPattern = Pattern.compile("^(ACE:)");
        Matcher permissionMatcher;


        for(String rule: ruleSplit){
            rule = rule.trim();
            ownerMatcher = ownerLine.matcher(rule);
            permissionMatcher = permissionPattern.matcher(rule);
            if(ownerMatcher.find()){
                if(isFile){
                    files.get(fileName).setOwner(rule.split(" ")[1]);
                }
                else{
                    directories.get(fileName).setOwner(rule.split(" ")[1]);
                }
            }
            else if(permissionMatcher.find()){
                String permissionSplit[] = rule.split(" ");
                if(isFile){
                    files.get(fileName).appendPermission(new Permission(permissionSplit[1], permissionSplit[2], permissionSplit[3]));
                }
                else{
                    directories.get(fileName).appendPermission(new Permission(permissionSplit[1], permissionSplit[2], permissionSplit[3]));
                }
            }
            else{
                System.out.println("Improper Format for: " + rule);
            }
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

            //Write directories
            Set<String> directoryKeys = directories.keySet();
            for(String key: directoryKeys){
                bw.write("Directory: " + directories.get(key).toString());
                bw.newLine();
            }

            //Write Files
            Set<String> fileKeys = files.keySet();
            for(String key: fileKeys){
                bw.write("File: " + files.get(key).toString());
                bw.newLine();
            }

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
