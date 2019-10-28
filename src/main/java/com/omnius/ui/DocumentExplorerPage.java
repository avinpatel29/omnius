package com.omnius.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import java.awt.*;
import java.util.List;

public class DocumentExplorerPage extends PageBase {

    public DocumentExplorerPage(WebDriver driver) {
        super(driver);
    }
    Actions action = new Actions(driver);
    JavascriptExecutor js = (JavascriptExecutor)driver;

    @FindBy(how = How.XPATH, using = "//button[contains(@class,'picnicButtonSizeSmall picnicButtonShapeRound')]")
    private WebElement COLLECTIONS_ADD_BUTTON;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'picnicDialogSizeWidthSmall')]//container//platform-new-collection-dialog")
    private WebElement NEW_COLLECTION_DIALOGBOX;

    @FindBy(how = How.XPATH, using = "//input[@placeholder='Name']")
    private WebElement COLLECTION_NAME;

    @FindBy(how = How.XPATH, using = "//button[@class='picnicButtonColorGreen picnicButton']")
    private WebElement CREATE_BUTTON;

    @FindBy(how = How.XPATH, using = "//button[@class='picnicButton']")
    private WebElement CANCEL_BUTTON;

    @FindBy(how = How.XPATH, using = "//paragraph[contains(@class,'picnicMessengerColorWarning')]")
    private WebElement COLLECTION_WARNING_MESSAGE;

    @FindBy(how = How.XPATH, using = "//button[@class='picnicButtonSizeSmall picnicButtonColorGreen picnicButton']")
    private WebElement UPLOAD_BUTTON;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'echoTableSelectable')]//main//table/tbody/tr")
    private List<WebElement> COLLECTION_ROWS;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'echoTableSelectable')]//main//table")
    private WebElement COLLECTION_TABLE;

    @FindBy(how = How.XPATH, using = "//*[contains(@class,'picnicProgressBar')]//done")
    private WebElement LOADING;

    @FindBy(how = How.XPATH, using = "//*[@class='picnicGrid picnicGridColumns8 picnicGridGapColumnsNormal']")
    private WebElement FINISHED_UPLOADING_MESSAGE;

    @FindBy(how = How.XPATH, using = "//*[@class='picnicTextUnderline picnicMousePointer']")
    private WebElement CLEAR_FINISHED_LINK;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Succeeded')]")
    private WebElement SUCCEEDED_MESSAGE;

    @FindBy(how = How.XPATH, using = "//rejected[@class='becauseOfType']")
    private WebElement REJECTED_MESSAGE;

    @FindBy(how = How.XPATH, using = "//*[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable']//input[@placeholder='Search...']")
    private WebElement DOCUMENT_SEARCH;

    @FindBy(how = How.XPATH, using = "//*[@class='echoTableHeightFull picnicHeightLimiter echoTableSearchable echoTableSelectable']//input[@placeholder='Search...']")
    private WebElement COLLECTION_SEARCH;

    @FindBy(how = How.XPATH, using = " //*[@class='picnicTableReactiveRows']/thead/th[3]/icons[1]")
    private WebElement PROCESSION_DROPDOWN;

    @FindBy(how = How.XPATH, using = " //*[@class='picnicTableReactiveRows']/thead/th[4]/icons[1]")
    private WebElement VALIDATION_DROPDOWN;

    @FindBy(how = How.XPATH, using = "//grid[@class='picnicGrid picnicGridColumns11 picnicGridGapColumnsNormal']//middle//font//font")
    private WebElement DOCUMENT_STATUS_MESSAGE;

    @FindBy(how = How.XPATH, using = "//icon[@class='tableHeaderIconAction picnicPopupTrigger']")
    private WebElement DOCUMENT_STATUS_SORTING_BUTTON;

    @FindBy(how = How.XPATH, using = "//icon[@class='tableHeaderIconAction picnicPopupTrigger']")
    private WebElement COLLECTION_STATUS_SORTING_BUTTON;

    @FindBy(how = How.XPATH, using = "//container[@class='picnicPopupContents']//main//list")
    private WebElement COLLECTION_STATUSES_DROPDOWN;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Apply')]")
    private WebElement COLLECTION_APPLY_BUTTON;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Reset all')]")
    private WebElement COLLECTION_RESETALL_BUTTON;

    @FindBy(how = How.XPATH, using = "//table[@class='picnicTableReactiveRows']")
    private WebElement DOCUMENTS_TABLE;


    public void createCollection(String collectionName){
     clickAndWait(COLLECTIONS_ADD_BUTTON,NEW_COLLECTION_DIALOGBOX);
     inputText(COLLECTION_NAME,collectionName);
     click(CREATE_BUTTON);
     if(isElementPresent(COLLECTION_WARNING_MESSAGE))
        log.info("Warning message is shown while creating new collection");
     else
        waitForElementToBePresent(COLLECTIONS_ADD_BUTTON);
    }

    public boolean checkCollectiontWarningMessage(){
        return isElementPresent(COLLECTION_WARNING_MESSAGE);
    }

    public void selectCollection(String collectionName){
        waitForElementToBePresent(COLLECTIONS_ADD_BUTTON);
        List<WebElement> rowCount = COLLECTION_TABLE.findElements(By.tagName("tr"));
        for (int i=0;i<rowCount.size();i++){
            if(rowCount.get(i).findElements(By.tagName("td")).get(0).getText().equals(collectionName)){
                rowCount.get(i).findElements(By.tagName("td")).get(0).click();
                break;
            }
        }
        waitForElementToBePresent(UPLOAD_BUTTON);
    }

    public void searchCollection(String collectionName) throws Exception {
       waitForElementTobeClickable(COLLECTION_SEARCH);
       inputText(COLLECTION_SEARCH,collectionName);
        Thread.sleep(2000);
        List<WebElement> rowCount = COLLECTION_TABLE.findElements(By.tagName("tr"));
        if(rowCount.size()==1){
            rowCount.get(0).findElements(By.tagName("td")).get(0).click();
            waitForElementToBePresent(UPLOAD_BUTTON);
        }else
            throw new Exception("Collection Not found");
    }


    public String uploadDocuments(String file, boolean validFileType) throws AWTException, InterruptedException {
        String msg;
        click(UPLOAD_BUTTON);
        uploadFile(file);
        waitForElementToBePresent(FINISHED_UPLOADING_MESSAGE);
        if(validFileType) {
            waitForElementToBePresent(SUCCEEDED_MESSAGE);
            msg=getText(SUCCEEDED_MESSAGE);
        }
        else{
            waitForElementToBePresent(REJECTED_MESSAGE);
            msg=getText(REJECTED_MESSAGE);
        }
        return msg;
    }

    public void sortCollectionStatus(String status) throws Exception {
        waitForElementTobeClickable(COLLECTION_STATUS_SORTING_BUTTON);
        clickAndWait(COLLECTION_STATUS_SORTING_BUTTON,COLLECTION_STATUSES_DROPDOWN);
        List<WebElement> rowCount = COLLECTION_TABLE.findElements(By.tagName("item"));

        for (int i=0;i<rowCount.size();i++){
            if(rowCount.get(i).findElements(By.tagName("label")).get(0).getText().equals(status)){
                rowCount.get(i).findElements(By.tagName("label")).get(0).click();
                break;
            }
        }
        clickAndWait(COLLECTION_APPLY_BUTTON,COLLECTION_TABLE);
        click(COLLECTION_STATUS_SORTING_BUTTON);
    }

    public void searchDocuments(String documentName) throws Exception {
        waitForElementTobeClickable(DOCUMENT_SEARCH);
        inputText(DOCUMENT_SEARCH,documentName);
        Thread.sleep(2000);
        List<WebElement> rowCount = DOCUMENTS_TABLE.findElements(By.tagName("tr"));
        for (int i=0;i<rowCount.size();i++){
            Assert.assertEquals(rowCount.get(i).findElements(By.tagName("td")).get(1).getText(), documentName);
        }
    }
}
