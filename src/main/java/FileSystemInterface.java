package main.java;

import java.io.*;
import java.util.Scanner;
/**
 * Created by rob on 11/6/14.
 */
public class FileSystemInterface {


    public static void main(String args[]){
    	
    	//clearConsole();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String input;
        String command;
        String parameters;
        String location = "home$ ";
        FileSystem fileSystem = new FileSystem();

        while(!exit){
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
    
    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
