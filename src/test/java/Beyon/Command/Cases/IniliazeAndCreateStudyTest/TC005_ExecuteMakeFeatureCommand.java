package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.pagesORCmds.Data_cmd;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TC005_ExecuteMakeFeatureCommand {


    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name

    CommandMethods commandMethods;
    public static String dirPath = System.getProperty("user.dir");

    @Test(groups = {"smoke"},description = "Execute make a feature command  ")
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

}
