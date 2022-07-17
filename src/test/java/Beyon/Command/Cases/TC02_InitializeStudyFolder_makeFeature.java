package Beyon.Command.Cases;

import com.beyond.helpers.ActionsHelper;
import com.beyond.helpers.ReadWriteHelper;
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


@Listeners()

public class TC02_InitializeStudyFolder_makeFeature {

    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name
    public static String dirPath = System.getProperty("user.dir");
    CommandMethods commandMethods;

    /*

    Given get latest image
    When Execute intilize study command
    Then Verify the study folder should be created
    When Copy dataset from NAS drive
    And Execute make featuer command
    Then Delete The created study folder

     */
   // @Parameters({"getLatest_Image"})
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


        commandMethods.RunContainerAndEx(ReadWriteHelper.readCommand("dockerPath"),
                ReadWriteHelper.readCommand("projectDirPath").replace("${dirPath}", dirPath), projectName + ":" + dockerImageNumber
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
        Assert.assertTrue(ActionsHelper.isFileDownloaded(ReadWriteHelper.readCommand("studyFolderName"), "false"));
    }



    @Test(groups = {"smoke"},description = "Copy dataset from NAS drive ", priority = 3)
    public void copyFile() throws Exception {

// return true if file here
        commandMethods = new CommandMethods();
        commandMethods.copyFiles("Study", ReadWriteHelper.readCommand("studyFolderName"));
        System.out.println("copy file content done ");
        Thread.sleep(1500);
    }



    @Test(groups = {"smoke"},description = "Execute initialize study command  ", priority = 4)
    public void execute_MakeFeature() throws Exception {

        commandMethods = new CommandMethods();

        commandMethods.RunContainerAndEx(ReadWriteHelper.readCommand("dockerPath"),
                ReadWriteHelper.readCommand("projectDirPath").replace("${dirPath}", dirPath), projectName + ":" + dockerImageNumber
                , ReadWriteHelper.readCommand("makeFeature"));
        String expectedResult = "Closed: make-features";

        String actualResults = Files.readString(Paths.get(dirPath + "/src/main/resources/DataProvider/ActualResults.txt"));

        System.out.println("actualResults feature" + actualResults);
        assertThat(actualResults, containsString(expectedResult));

        commandMethods.file.delete();// delete the file
    }


    @Test(groups = {"smoke"},description = "Delete The created study folder ", priority = 5)
    public void deleteFile() throws Exception {
        FileUtils.forceDelete(new File("src/main/resources/DataProvider/" + ReadWriteHelper.readCommand("studyFolderName")));
        Thread.sleep(3000);
// return true if file here
        Assert.assertTrue(!ActionsHelper.isFileDownloaded(ReadWriteHelper.readCommand("studyFolderName"), "false"));
    }
}
