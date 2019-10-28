package com.omnius.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.Set;

public class TrainerConsolePage extends PageBase {

    public TrainerConsolePage(WebDriver driver) {
        super(driver);
    }
   Actions action = new Actions(driver);
   JavascriptExecutor js = (JavascriptExecutor)driver;
    
    @FindBy(how = How.XPATH, using = "//section[2]//grid[1]//contents[1]//buttons[1]//a[1]")
    private WebElement CASEREVIWERE_BUTTON;
    
    @FindBy(how = How.XPATH, using = "//*[@class='omnibusLayout']//header//left//logo//a")
    private WebElement OMNIUS_LOGO_BUTTON;

    @FindBy(how = How.XPATH, using = "//button[@class='picnicButtonSizeSmall picnicButtonShapeRound picnicButtonColorGreen picnicButtonContentIconOnly picnicButton']")
    private WebElement COLLECTIONS_ADD_BUTTON;

    @FindBy(how = How.XPATH, using = "//*[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable echoTableSelectable']//input[@placeholder='Search...']")
    private WebElement COLLECTION_SEARCH;

    public void clickDocumentExplorer(){
        click(CASEREVIWERE_BUTTON);
       String mainWindow= driver.getWindowHandle();
       Set<String> handles = driver.getWindowHandles();
       for (String handle: handles){
           if (!handle.equals(mainWindow)){
               driver.switchTo().window(handle);
               break;
           }
       }
       waitForElementTobeClickable(COLLECTIONS_ADD_BUTTON);
       waitForPageToLoad();
    }

}
