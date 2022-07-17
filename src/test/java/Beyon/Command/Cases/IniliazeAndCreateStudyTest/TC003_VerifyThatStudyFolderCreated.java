package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.helpers.ActionsHelper;
import com.beyond.pagesORCmds.Data_cmd;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC003_VerifyThatStudyFolderCreated {

    @Test(groups = {"smoke"},description = "Verify the study folder is created ", priority = 2)
    public void verifyStudyFolder() throws Exception {

// return true if file here
        Assert.assertTrue(ActionsHelper.isFileDownloaded(Data_cmd.studyFolderName, "false"));
    }

}
