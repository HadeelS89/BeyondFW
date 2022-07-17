package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.common.Base;
import com.beyond.helpers.ReadWriteHelper;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.testng.annotations.Test;

public class TC001_GetLatestImageFromDockerFile extends Base {

    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name

    CommandMethods commandMethods;



    @Test(groups = {"smoke"},description = "TC001: get latest image from docker file  ",
            priority = 0,retryAnalyzer = com.beyond.helpers.RetryAnalyzer.class)
    public void getLatestImage() throws Exception {

        commandMethods = new CommandMethods();


        commandMethods.runDocker_Command(ReadWriteHelper.readCommand("getLatest_Image")
                + " " + projectName + ":" + dockerImageNumber);
        System.out.println("test command = " + ReadWriteHelper.readCommand("getLatest_Image")
                + " " + projectName + ":" + dockerImageNumber);
        // Assert.assertTrue();
    }

}
