package com.beyond.pagesORCmds.authorization_pages;


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
public class LoginPage extends Base {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "TextField2")
    private WebElement userName;
    @FindBy(id = "TextField5")
    private WebElement password;
    @FindBy(id = "id__8")
    private WebElement LoginButton;
    @FindBy(xpath="//div[@class='ms-StackItem css-147']")
    private List<WebElement> projectTag;



    public void loginToInfillAdvisor(String email, String password) {

        ActionsHelper.waitVisibility(getUserName(), Data.waitTime, driver);
        getUserName().sendKeys(email);
        getPassword().sendKeys(password);
        ActionsHelper.waitVisibility(getLoginButton(), Data.waitTime, driver);
        getLoginButton().click();
        ActionsHelper.waitForListExistance(getProjectTag(), Data.waitTime);
        ActionsHelper.highlightElement(getProjectTag().get(0),driver);

    }


}
