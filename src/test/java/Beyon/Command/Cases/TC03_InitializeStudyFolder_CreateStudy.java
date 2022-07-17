package Beyon.Command.Cases;

import com.beyond.common.Base;
import com.beyond.helpers.ActionsHelper;
import com.beyond.helpers.ReadWriteHelper;
import com.beyond.pagesORCmds.Data_cmd;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


@Listeners
public class TC03_InitializeStudyFolder_CreateStudy extends Base {

    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name
    public static String dirPath = System.getProperty("user.dir");
    CommandMethods commandMethods;

    /*

  Given Execute docker login command
    When Execute intilize study command
    Then Verify the study folder should be created
    When Copy dataset from NAS drive
    And Execute make featuer command
    And Execute create study command
    Then Delete The created study folder

     */

    @Test(groups = {"smoke"},description = "get latest image from docker file  ", priority = 0)
    public void getLatestImage() throws Exception {

        commandMethods = new CommandMethods();


        commandMethods.runDocker_Command(ReadWriteHelper.readCommand("getLatest_Image")
                + " " + projectName + ":" + dockerImageNumber);
        System.out.println("test command = " + ReadWriteHelper.readCommand("getLatest_Image")
                + " " + projectName + ":" + dockerImageNumber);
        // Assert.assertTrue();
    }



    @Test(groups = {"smoke"},description = "Execute initialize study command  ", priority = 1)
    public void initialise_Study() throws Exception {

        commandMethods = new CommandMethods();


        commandMethods.RunContainerAndEx(Data_cmd.dockerPath,
                Data_cmd.projectDirPath.replace("${dirPath}", dirPath), projectName + ":" + dockerImageNumber
                , ReadWriteHelper.readCommand("infill_InitializeStudy"));
        String expectedResult = "Closed: initialize-study";

        String actualResults = Files.readString(Paths.get(dirPath + "/src/main/resources/DataProvider/ActualResults.txt"));

        System.out.println("actualResults" + actualResults);
        assertThat(actualResults, containsString(expectedResult));

        commandMethods.file.delete();// delete the file
    }


    @Test(groups = {"smoke"},description = "Verify the study folder is created ", priority = 2)
    public void verifyStudyFolder() throws Exception {

// return true if file here
        Assert.assertTrue(ActionsHelper.isFileDownloaded(Data_cmd.studyFolderName, "false"));
    }



    @Test(groups = {"smoke"},description = "Copy dataset from NAS drive ", priority = 3)
    public void copyFile() throws Exception {

// return true if file here
        commandMethods = new CommandMethods();
        commandMethods.copyFiles("Study", Data_cmd.studyFolderName);
        System.out.println("copy file content done ");
        Thread.sleep(1500);
    }



    @Test(groups = {"smoke"},description = "Execute make a feature command  ", priority = 4)
    public void execute_MakeFeature() throws Exception {

        commandMethods = new CommandMethods();

        commandMethods.RunContainerAndEx(Data_cmd.dockerPath,
                Data_cmd.projectDirPath.replace("${dirPath}", dirPath), projectName + ":" + dockerImageNumber
                , Data_cmd.makeFeature);
        String expectedResult = "Closed: make-features";

        String actualResults = Files.readString(Paths.get(dirPath + "/src/main/resources/DataProvider/ActualResults.txt"));

        System.out.println("actualResults feature" + actualResults);
        assertThat(actualResults, containsString(expectedResult));

        commandMethods.file.delete();// delete the file
    }

    @Test(groups = {"Regression"},description = "Execute create study command  ", priority = 5)
    public void execute_CreateStudy() throws Exception {

        commandMethods = new CommandMethods();

        commandMethods.RunCommandsInsidContainerUsingLabels(Data_cmd.dockerPath,
                Data_cmd.projectDirPath.replace("${dirPath}", dirPath), projectName + ":" + dockerImageNumber
                , Data_cmd.createStudy);
        String expectedResult = "Closed: create-study-configuration";

        String actualResults = Files.readString(Paths.get(dirPath + "/src/main/resources/DataProvider/ActualResults.txt"));

        System.out.println("actualResults create study feature" + actualResults);
        assertThat(actualResults, containsString(expectedResult));

        commandMethods.file.delete();// delete the file
    }

    @Test(groups = {"smoke"},description = "Delete The created study folder ", priority = 6)
    public void deleteFile() throws Exception {
        FileUtils.forceDelete(new File("src/main/resources/DataProvider/" + Data_cmd.studyFolderName));
        Thread.sleep(3000);
// return true if file here
        Assert.assertTrue(!ActionsHelper.isFileDownloaded(Data_cmd.studyFolderName, "false"));
    }
}
