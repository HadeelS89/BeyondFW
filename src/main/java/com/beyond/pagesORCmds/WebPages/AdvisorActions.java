package com.beyond.pagesORCmds.WebPages;


import com.beyond.common.Base;
import com.beyond.helpers.ActionsHelper;
import com.beyond.pagesORCmds.Data;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


@Getter
public class AdvisorActions extends Base {

    public AdvisorActions(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[starts-with (@class,'ms-Button') and contains (text(), 'New Project')]")
    private List<WebElement> addNewProjectBtn;

    @FindBy(xpath="//div[starts-with (@class,'ms-StackItem') and contains (text(), 'Create Project')]")
    private List<WebElement> createProjectTag;
    @FindBy(xpath="//input[starts-with (@class,'ms-TextField') and @type ='text']")
    private WebElement projectNameFields;
    @FindBy(xpath="//textarea[starts-with (@class,'ms-TextField') and (@name ='description')]")
    private WebElement descriptionField;
    @FindBy(xpath="//span[starts-with (@class,'ms-Dropdown') and starts-with(@id,'Dropdown')]")
    private WebElement unitSystemDDL;
    @FindBy(xpath="//span[starts-with (@class,'ms-Dropdown-optionText') and contains (text(),'Metric System')]")
    private WebElement unitDDLContentMetric;
    @FindBy(xpath="//span[starts-with (@class,'ms-Button-label') and contains (text(),'Next') ]")
    private WebElement nextDescriptionButton;
    @FindBy(css = "input[type='file']")
    private List<WebElement> uploadArea;
    @FindBy(xpath="//span[starts-with (@class,'ms-Button-label') and contains (text(),'Upload') ]")
    private WebElement uploadButton;
    @FindBy(xpath="//a//span[starts-with(@class,'root')]")
    private List<WebElement> tableHeaderEdit;

    public void click_addNewProject() {

        ActionsHelper.waitForListExistance(getAddNewProjectBtn(), Data.waitTime);
        getAddNewProjectBtn().get(0).click();

        ActionsHelper.waitForListExistance(getCreateProjectTag(), Data.waitTime);
        ActionsHelper.highlightElement(getCreateProjectTag().get(0),driver);

    }

    public  void description_MetricSys(){

        getProjectNameFields().sendKeys(Data.projectName);
        getDescriptionField().sendKeys(Data.descriptionField);
        getUnitSystemDDL().click();
        getUnitDDLContentMetric().click();
        ActionsHelper.waitForExistance(getNextDescriptionButton(),Data.waitTime);
        getNextDescriptionButton().click(); // click on next button

    }

    public void upload_WellData(){

        ActionsHelper.waitForExistance(getUnitSystemDDL(),Data.waitTime);
        getUnitSystemDDL().click();// common locators for ddl

        ActionsHelper.ddlContent("Well data"); // for well data

        getUploadArea().get(0).sendKeys(ActionsHelper.getDataProviderPath("PERF.csv"));// for uplaod

        ActionsHelper.retryClick(getUploadButton(),Data.waitTime);// click upload button
        ActionsHelper.waitForExistance(getNextDescriptionButton(),Data.waitTime);
        getNextDescriptionButton().click();
    }


}
