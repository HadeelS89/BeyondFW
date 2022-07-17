package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.pagesORCmds.Data_cmd;
import com.beyond.pagesORCmds.commands.CommandMethods;
import org.testng.annotations.Test;

public class TC004_CopyDatasetFromNASDrive {


    CommandMethods commandMethods;
    @Test(groups = {"smoke"},description = "Copy dataset from NAS drive ")
    public void copyFile() throws Exception {

// return true if file here
        commandMethods = new CommandMethods();
        commandMethods.copyFiles("Study", Data_cmd.studyFolderName);
        System.out.println("copy file content done ");
        Thread.sleep(1500);
    }
}
