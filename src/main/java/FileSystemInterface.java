package main.java;

import java.util.Scanner;
/**
 * Created by rob on 11/6/14.
 */
public class FileSystemInterface {


    public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String input;
        String command;
        String location = "home$ ";
        FileSystem fileSystem = new FileSystem();

        while(!exit){
            System.out.println(location);
            input = scanner.nextLine();
            command = input.split(" ")[0];

            switch (command) {
                case "set_user":    fileSystem.setUser(input);
                    break;
                case "cd":          fileSystem.changeDirectory(input);
                    break;
                case "pwd":         fileSystem.printWorkingDirectory();
                    break;
                case "read":        fileSystem.read(input);
                    break;
                case "write":       fileSystem.write(input);
                    break;
                case "cm":          fileSystem.changeMetadata(input);
                    break;
                case "lm":          fileSystem.listMetadata();
                    break;
                case "rm":          fileSystem.removeMetadata(input);
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
