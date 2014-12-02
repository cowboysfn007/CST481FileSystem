package main.java;

import java.io.*;
import java.util.Scanner;
/**
 * Created by rob on 11/6/14.
 */
public class FileSystemInterface {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String input;
        String command;
        String parameters;
        FileSystem fileSystem = new FileSystem();

        while(!exit){
            String directory = fileSystem.getWorkingDir();
            String location = "home" + directory + "$ ";
            System.out.print(location);
            input = scanner.nextLine();
            String[] inputSplit = input.split(" ");
            command = inputSplit[0];

            if(inputSplit.length > 1){
                parameters =  input.split(" ",2)[1];
            }else parameters = null;


            switch (command) {
                case "set_user":    fileSystem.setUser(parameters);
                    break;
                case "cd":          fileSystem.changeDirectory(parameters);
                    break;
                case "pwd":         fileSystem.printWorkingDirectory();
                    break;
                case "read":        fileSystem.read(parameters);
                    break;
                case "write":       fileSystem.write(parameters);
                    break;
                case "cm":          fileSystem.changeMetadata(parameters);
                    break;
                case "lm":          fileSystem.listMetadata();
                    break;
                case "rm":          fileSystem.removeMetadata(parameters);
                    break;
                case "passwd":      fileSystem.setPassword(parameters);
                    break;
                case "lp":          fileSystem.listPasswords();
                    break;
                case "help":        fileSystem.help();
                    break;
                case "exit":        System.out.println("Exiting Program...");
                                    exit = true;
                    break;
                default:            System.out.println("Invalid Input");
                    break;
            }
        }
    }
}
