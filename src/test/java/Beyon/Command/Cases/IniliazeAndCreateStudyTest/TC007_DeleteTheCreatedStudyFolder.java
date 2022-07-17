package Beyon.Command.Cases.IniliazeAndCreateStudyTest;

import com.beyond.helpers.ActionsHelper;
import com.beyond.pagesORCmds.Data_cmd;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TC007_DeleteTheCreatedStudyFolder {


    @Test(groups = {"smoke"},description = "Delete The created study folder ")
    public void deleteFile() throws Exception {
        FileUtils.forceDelete(new File("src/main/resources/DataProvider/" + Data_cmd.studyFolderName));
        Thread.sleep(3000);
// return true if file here
        Assert.assertTrue(!ActionsHelper.isFileDownloaded(Data_cmd.studyFolderName, "false"));
    }
}

