package assigment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by adrian on 04.01.2015.
 */
public class Answer {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        String input;
        System.out.println("===Welcome to the JavaFile Manager===");
        System.out.println("Please select the root of your file system, if empty it will default to the install directory of this application:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!(input = bufferedReader.readLine()).equals("") && !input.equals("EXIT") && !(input.equals
                    ("CONTINUE"))) {
                path = (input.equals("")) ? System.getProperty("user.dir"): input;
                System.out.println(path);
                File fileSystem = new File(path);
                if (fileSystem.exists()&&fileSystem.isDirectory()){
                    System.out.println("if that is OK please select CONTINUE option, if not enter a new path or select EXIT to choose the default path");
                }else {
                    System.out.println("The path provided does not exist or is not a directory, please try again or " +
                            "choose EXIT");
                }
            }
            if (input.equals("EXIT"))path = System.getProperty("user.dir");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (path.equals(System.getProperty("user.dir"))){
            System.out.println("You have chosen this application's root directory");
        }else{
            System.out.println("You have chosen: "+path+" as your root directory");
        }
        displayCommands();
        try{
            while (!(input=bufferedReader.readLine()).equals("QUIT")){
                switch (input){
                    case "LIST":
                        displayFiles(path);
                        break;
                    case "INFO":
                        String filePath = bufferedReader.readLine();
                        displayInfo(filePath);
                        break;
                    case "CREATE_DIR":
                        String dirName = bufferedReader.readLine();
                        System.out.println("you can choose to add the path for the new directory or just hit enter to" +
                                " create it in the current directory: "+path);
                        String dirPath = bufferedReader.readLine();
                        if (dirPath.equals("")){
                            createDir(dirName,dirPath);
                        }else {
                            createDir(dirName);
                        }
                        break;
                    case "RENAME":
                        //
                        break;
                    default:
                        System.out.println("Command not recognised, try:");
                        displayCommands();
                }
            }
        }catch ( Exception e){
            e.printStackTrace();
        }
        
    }

    private static void createDir(String dirName, String dirPath) {
        //
    }
    private static void createDir(String dirName){
        //
        
    }

    private static void displayInfo(String filePath) {
        //
    }

    private static void displayCommands() {
        System.out.println("You can use the following commands:");
        System.out.println("      * LIST: to view the files in the directory");
        System.out.println("      * INFO: to view the file's details (requires you to input file name)");
        System.out.println("      * CREATE_DIR: to create a new directory (requires you to input directory name and path[optional])");
        System.out.println("      * RENAME: to rename a file (requires you to input the chosen file's name and new name)");
        System.out.println("      * COPY: to copy a file (requires you to input the chosen file's name and new destination)");
        System.out.println("      * MOVE: to move a file (requires you to input the chosen file's name and new " +
                "destination)");
        System.out.println("      * DELETE: to copy a file (requires you to input the chosen file's name " +
                "[!!!Important the file can't be restored])");
        System.out.println("      * HELP: to view this message again");
        System.out.println("      * QUIT: to exit the application)");
    }

    private static void displayFiles(String path) {
        File rootDirectory = new File(path);
        File[] files = rootDirectory.listFiles();
        System.out.println("||    NUME     ||    TIP    ||    CALE    ||    DIMENSIUNE    ||    DATA CREARII    " +
                "||    DATA ULTIMEI MODIFICARI    ||");
        for (File file:files){
            System.out.println("||"+file.getName()+"||"+showType(file)+"||"+file.getPath()+"||"+file.length()
                    +"||"+getCreationDate(file)+"||"+getLastModifiedDate(file)+"||");
        }
    }

    private static String getLastModifiedDate(File file) {
        Instant instant = Instant.ofEpochMilli(file.lastModified());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(dateTimeFormatter);
    }

    private static String getCreationDate(File file) {
        Path filePath = file.toPath();
        BasicFileAttributes attributes = null;
        try {
            attributes = Files.readAttributes(filePath,BasicFileAttributes.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        long milliseconds = attributes.creationTime().to(TimeUnit.MILLISECONDS);
        if((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE)) {
            Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
            StringBuilder rez = new StringBuilder();
            rez.append(creationDate.getDate());
            rez.append("/");
            rez.append(creationDate.getMonth() + 1);
            rez.append("/");
            rez.append(creationDate.getYear() + 1900);
            return rez.toString();
        }else {
            return "Not Found";
        }
        }

    private static String showType(File file) {
        if (file.isDirectory()){
            return "DIRECTORY";
        }else if (file.isFile()){
            return "FISIER";
        }else {
            return "NECUNOSCUT";
        }
    }
}
//0348426009