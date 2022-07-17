package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.pagesORCmds.Data_cmd;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TC006_ExecuteCreateStudyCommand {
    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name

    CommandMethods commandMethods;
    public static String dirPath = System.getProperty("user.dir");

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

}
