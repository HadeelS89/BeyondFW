package Beyond.Web.cases;
import com.beyond.common.Base;
import com.beyond.helpers.ReadWriteHelper;
import com.beyond.pagesORCmds.WebPages.AdvisorActions;
import com.beyond.pagesORCmds.Data;
import com.beyond.pagesORCmds.authorization_pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.logging.Logger;

@Listeners(com.beyond.reporting.Listeners.class)
public class InfillAdvisorTest extends Base {

    protected static Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    LoginPage loginPage;
    AdvisorActions advisorActions;


    @Test(description = "Login to infill advisor ",
            retryAnalyzer = com.beyond.helpers.RetryAnalyzer.class)
    public void addNewStudentWithNoEID() throws Exception {
        //Login as Program Manager
        loginPage = new LoginPage(driver);
        loginPage.loginToInfillAdvisor(ReadWriteHelper.readCredentialsXMLFile("infilAdvisor",
                "username"),
                ReadWriteHelper.readCredentialsXMLFile("infilAdvisor", "password"));
        Assert.assertTrue(loginPage.getProjectTag().get(0).getText().equalsIgnoreCase(Data.projectTag));


    }

   // @Test(description = "Click add new project button", priority = 1 ,retryAnalyzer = com.beyond.helpers.RetryAnalyzer.class)
    public void clickAddNewProject(){
        advisorActions = new AdvisorActions(driver);
        advisorActions.click_addNewProject();
        Assert.assertTrue(advisorActions.getCreateProjectTag().get(0).getText().equalsIgnoreCase(Data.createProjectTag));

    }


  //  @Test(description = "Fill description field with metric Systems ", priority = 2,retryAnalyzer = com.beyond.helpers.RetryAnalyzer.class)
    public void fillDescription_tab_metric_system(){
        advisorActions = new AdvisorActions(driver);
        advisorActions.description_MetricSys();
        Assert.assertTrue(advisorActions.getCreateProjectTag().get(0).getText().equalsIgnoreCase(Data.createProjectTag));

    }

  //  @Test(description = "Data upload for Well Data ", priority = 3,retryAnalyzer = com.beyond.helpers.RetryAnalyzer.class)
    public void upload_WellData(){
        advisorActions = new AdvisorActions(driver);
        advisorActions.upload_WellData();
       // Assert.assertTrue(advisorActions.getCreateProjectTag().get(0).getText().equalsIgnoreCase(Data.createProjectTag));

    }


}