package com.beyond.pagesORCmds.commands;

import com.beyond.helpers.ActionsHelper;
import com.beyond.helpers.ReadWriteHelper;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;


import org.testng.Assert;

//common staps
public class CommandMethods {
    public static String dirPath = System.getProperty("user.dir");
    static int exitCode;
    public static File file;

    public static void RunContainerAndEx(String dockerPath, String projectPath, String latestImage, String command) throws IOException, InterruptedException {


        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(dockerPath, "run", "--rm", "-v", projectPath, "-v", "/Volumes:/Volumes", "-p",
                        "8501:8501", "--name=container", latestImage, command)
                .inheritIO();

        System.out.println("command =" + dockerPath + " run " + "--rm -v " + projectPath + " -v " + "/Volumes:/Volumes" + " -p" +
                "8501:8501" + " --name=container " + latestImage + command);


        file = new File(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/ActualResults.txt");

        processBuilder.redirectErrorStream(true);

        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(file));

        Process process = processBuilder.start();

        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        exitCode = process.waitFor();
// check if file downloaded
        ActionsHelper.isFileDownloaded("ActualResults.txt", "false");
    }


    public static String containerID;

    public static void runDockerCommand1(String Command) throws IOException, InterruptedException {
        // String dockerDirPath = ReadWriteHelper.readCommand("macDockerDirPath");

        ProcessBuilder processBuilder = new ProcessBuilder(Command.split("\\s+")).inheritIO();


        Process process = processBuilder.start();


        process.waitFor();


        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("Reader line " + reader.readLine());


            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("line h= " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

            int expectedExistCode = 0;
            int actualExistCode = exitCode;
            //Assert.assertEquals(expectedExistCode, actualExistCode);

        } catch (Exception e) {


        }

    }

    public static void runDockerCommandWithoutSplit(String Command) throws IOException, InterruptedException {
        // String dockerDirPath = ReadWriteHelper.readCommand("macDockerDirPath");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("/usr/local/bin/docker images ", Command).inheritIO();


        Process process = processBuilder.start();


        process.waitFor();


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        runDockerCommandWithoutSplit(" awk '{print $1}'  awk 'NR==2'");
    }

    //get all the images
    public void runDocker_Command(String command) throws IOException, InterruptedException {


//clear the file before run
        ReadWriteHelper.clearTheFile(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/ActualResults.txt");


        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+")).inheritIO();


        // print console on text file
        File file = new File(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/ActualResults.txt");

        processBuilder.redirectErrorStream(true);

        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(file));
        // processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(file))
        Process process = processBuilder.start();

        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);

        }
//String results = reader.readLine();
//        System.out.println("output =" + results);


//                String contents = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +
//                        "/src/main/resources/DataProvider/ActualResults.txt")));
        // System.out.println("hadeel test "+contents);
        exitCode = process.waitFor();


    }


    public static void getContainerID(String Command) throws IOException, InterruptedException {


        ProcessBuilder pb = new ProcessBuilder(Command.split("\\s+"));

        Process p = pb.start();

        BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));

        containerID = output.readLine();

        System.out.println("Container id =" + containerID);


    }

    public static void RunCommandsInsidContainer(String command) throws IOException, InterruptedException {

        // String Command = ReadWriteHelper.ReadData(string);


        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("/usr/local/bin/docker", "exec",
                "-i", containerID, "infill", command).inheritIO();

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // process.waitFor();

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

        int expectedExistCode = 0;
        int actualExistCode = exitCode;
        Assert.assertEquals(expectedExistCode, actualExistCode);

    }

    public static void runCmd(String command) throws IOException, InterruptedException {

        // String Command = ReadWriteHelper.ReadData(string);


        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("/usr/local/bin/docker", command).inheritIO();

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // process.waitFor();

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

        int expectedExistCode = 0;
        int actualExistCode = exitCode;
        //  Assert.assertEquals(expectedExistCode, actualExistCode);

    }

    public static void checkExistOfStudy() throws InterruptedException {

        Thread.sleep(3000);
        File file = new File("StudyFolder/Study3");
        System.err.println("File Folder Exist" + " " + isFileDirectoryExists(file));
        System.err.println("Directory Exists" + " " + isDirectoryExists("StudyFolder/Study3/data"));

    }

    public static boolean isFileDirectoryExists(File file) {
        if (file.exists()) {

            return true;


        }
        return false;
    }

    public static boolean isDirectoryExists(String directoryPath) {
        if (!Paths.get(directoryPath).toFile().isDirectory()) {
            System.err.print("\nFile Folder Not Exist");

            return false;
        }
        return true;


    }

    public static void copyFiles(String sourceFile, String destinationFile)//file need to passed as param
    {

        File source = new File("src/main/resources/DataProvider/" + sourceFile);// check the file
        File dest = new File("src/main/resources/DataProvider/" + destinationFile);//study3
        try {
            FileUtils.copyDirectory(source, dest);
            System.err.print("\nStudy data has been copied");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteTheStudy() throws IOException {

        FileUtils.forceDelete(new File("StudyFolder/Study3"));

    }


    public void RunCommandsInsidContainerUsingLabels(String dockerPath, String projectPath, String
            latestImage, String command) throws IOException, InterruptedException {


        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(dockerPath, "run",
                "--rm", "-v", projectPath, "-v", "/Volumes:/Volumes", "-p",
                "8501:8501", "--name=container", latestImage, command,
                "--labels", "oil:cumulative,gas:cumulative", "--cv-splits",
                "10", "--n-periods", "12", "--interpolate-labels").inheritIO();


        file = new File(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/ActualResults.txt");

        processBuilder.redirectErrorStream(true);

        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(file));

        Process process = processBuilder.start();

        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        exitCode = process.waitFor();
// check if file downloaded
        ActionsHelper.isFileDownloaded("ActualResults.txt", "false");
    }

    public void RunContainerAndExTRain(String dockerPath, String projectPath, String
            latestImage, String command ,String model663, String templateSimpleRF) throws IOException, InterruptedException
    {



        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(dockerPath, "run" , "--rm", "-v", projectPath,
                "-v", "/Volumes:/Volumes", "-p", "8501:8501", "--name=container",
                latestImage, command ,model663, "--template", templateSimpleRF).inheritIO();


        file = new File(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/ActualResults.txt");

        processBuilder.redirectErrorStream(true);

        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(file));

        Process process = processBuilder.start();

        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));


        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        exitCode = process.waitFor();

    }

    public static void runPredictPlan(String command, String plan, String model) throws IOException, InterruptedException {


        String Command = ReadWriteHelper.readCommand(command);
        String Plan = ReadWriteHelper.readCommand(plan);
        String Model = ReadWriteHelper.readCommand(model);

        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("/usr/local/bin/docker", "exec", "-i", containerID,
                "infill", Command, Plan, "--model", Model).inheritIO();

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // process.waitFor();

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

        int expectedExistCode = 0;
        int actualExistCode = exitCode;
        Assert.assertEquals(expectedExistCode, actualExistCode);

    }


    public static void copyFileContent(String path1, String path2) throws IOException {


        String file1 = ReadWriteHelper.readCommand(path1);
        String file2 = ReadWriteHelper.readCommand(path2);


        File source = new File(file1);
        File dest = new File(file2);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // this command must have general data and the file should store inside the project not on user directory
    //  should pass it as param also
    public static void exCommand() throws IOException, InterruptedException {

        String command = "/usr/local/bin/docker run --entrypoint \"\" " +
                "-d -v /Users/musab_daja/Documents/Study3:/study -v /Volumes:/Volumes -p 8501:8501 " +
                " --name=container registry.infill.beyond.ai/infill-advisor-ds:721 sleep infinity";

        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));

        Process process = processBuilder.start();

        process.waitFor();


        // ProcessBuilder proc = new ProcessBuilder(command.split("\\s+")).inheritIO();

    }


}
