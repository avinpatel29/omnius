package com.omnius.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(how = How.XPATH, using = "//input[@id='username']")
    private WebElement USERNAME;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    private WebElement PASSWORD;
    
    @FindBy(how = How.XPATH, using = "//input[@id='kc-login']")
    private WebElement LOGIN_BUTTON;

    @FindBy(how = How.XPATH, using = "//span[@class='kc-feedback-text']")
    private WebElement ALERT_MESSAGE;

    @FindBy(how = How.XPATH, using = "//section[2]//grid[1]//contents[1]//buttons[1]//a[1]")
    private WebElement CASEREVIWERE_BUTTON;

    
    public boolean login(String username, String password) {
    	enterUsername(username);
    	enterPassword(password);
    	boolean flag= clickLoginButton();
    	return flag;
    }

	private boolean clickLoginButton() {
        boolean loginSuccess = false;
		click(LOGIN_BUTTON);
		waitForPageToLoad();
		if(isElementPresent(CASEREVIWERE_BUTTON)){
            log.info("Login was successful");
            loginSuccess=true;
        }else if (isElementPresent(ALERT_MESSAGE)) {
            log.info("Login failed");
        }
		return loginSuccess;
	}

	private void enterPassword(String password) {
		inputText(PASSWORD, password);
	}

	private void enterUsername(String username) {
		inputText(USERNAME, username);
	}

	public String getAlertMessage(){
        waitForElementToBePresent(ALERT_MESSAGE);
        return getText(ALERT_MESSAGE);
    }
}
