package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.helpers.ReadWriteHelper;
import com.beyond.pagesORCmds.Data_cmd;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TC002_ExecuteInitializeStudyCommand {


    String dockerImageNumber = System.getProperty("image_name");//TAG//this need to be added
    String projectName = System.getProperty("project_name");//image name
    public static String dirPath = System.getProperty("user.dir");
    CommandMethods commandMethods;


    @Test(groups = {"smoke"},description = "TC002: Execute initialize study command  ", priority = 1)
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

}
