package com.omnius.ui;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static java.lang.System.getProperty;


/**
 * @author avinashpatel
 */
public class PageBase {

    public WebDriver driver;
    private static JavascriptExecutor js;
    static Logger log = Logger.getLogger(PageBase.class);

    /**
     * Constructor of the class.
     *
     * @param driver - driver
     */
    public PageBase(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }

    public void enterURL(String url) {
        log.info("Executing GET URL command on the browser");
        driver.get(url);
        waitForPageToLoad();
    }

    /**
     * Method to clear and input the text for an WebElement.
     *
     * @param element - WebElement to be clickable
     * @param value   - Value to be inserted
     */
    public void inputText(WebElement element, String value) {
        log.info("Executing INPUT text command for the element:" +element.toString() + " with value: "+value);
        waitForElementTobeClickable(element);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Method to click on an element.
     *
     * @param element - WebElement to be clicked.
     */
    public void click(WebElement element) {
        log.info("Executing CLICK command on the element: "+element);
        if (isElementPresent(element)) {
            waitForElementTobeClickable(element);
            element.click();
        } else {
            System.out.println("Element not visible" + element);
        }

    }


    /**
     * Method to click on an element(click Element) and wait for other element
     * (wait Element).
     *
     * @param clickElement - WebElement to be clicked
     * @param waitElement  - WebElement to wait for after clicking
     */
    public void clickAndWait(WebElement clickElement, WebElement waitElement) {
        log.info("Executing CLICK command on the element: "+clickElement);
        clickElement.click();
        log.info("Waiting for the element: "+waitElement+" to be clickable");
        WebDriverWait wait = new WebDriverWait(driver, 90);
        wait.until(ExpectedConditions.elementToBeClickable(waitElement));
    }

    /**
     * Method to verify whether the element is present or not.
     *
     * @param element - WebElement to be check whether present or not.
     *
     * @return - TRUE, if element is present.
     */
    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    /**
     * Method to get text from the WebElement
     *
     * @param element - WebElement from which the text is to be fetched.
     *
     * @return - Text, if present. - Null, if not present
     */
    public String getText(WebElement element) {
        try {
            return element.getText();
        } catch (NoSuchElementException e) {
            return null;
        }

    }

    /**
     * Method to wait for the element on the web page to be clickable
     *
     * @param element - WebElement to be clickable
     */
    public void waitForElementTobeClickable(WebElement element) {
        WebDriverWait w = new WebDriverWait(driver, 20);
        try {
            log.info("Waiting for the element:"+element +" to be clickable");
            w.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void waitForElementTillnotPresent(By element) {
        WebDriverWait w = new WebDriverWait(driver, 20);
        try {
            log.info("Waiting for the element:"+element +" not to be visible");
            w.until(ExpectedConditions.invisibilityOfElementLocated(element));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void waitForElementToBePresent(WebElement element) {
        WebDriverWait w = new WebDriverWait(driver, 30);
        try {
            log.info("Waiting for the element:"+element +" to be visible");
            w.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to click on an element using Javascript executer.
     *
     * @param element - WebElement to be clicked
     */
    public void jsclick(WebElement element) {

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }


 

    /**
     * Method to check if the element is selected or not.
     *
     * @param element - WebElement to be checked
     *
     * @return TRUE, if WebElement is selected FALSE, if WebElement is not
     * selected
     */
    public boolean isElementSelected(WebElement element) {

        try {
            return element.isSelected();
        } catch (NoSuchElementException e) {
            return false;
        }

    }



    /**
     * Method to check if the text is present or not.
     *
     * @param waitElement  Element to be present
     * @param textToAppear Text to appear
     *
     * @return True, if both of them present
     * False, if element is not present
     */
    public boolean isTextPresent(WebElement waitElement, String textToAppear) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.textToBePresentInElement(waitElement, textToAppear));
        if (isElementPresent(waitElement))
            return true;
        else
            return false;
    }



    /**
     *  Method which waits for the complete page to load
     */
    public  void waitForPageToLoad() {
        String pageLoadStatus = null;
        do {
            JavascriptExecutor js = (JavascriptExecutor) driver;
             pageLoadStatus = (String)js.executeScript("return document.readyState");
        } while ( !pageLoadStatus.equals("complete") );
    }
    
    public void uploadFile(String file) throws AWTException {
        StringSelection filename = new StringSelection(getProperty("user.dir") + file);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filename,null);
        Robot robot = new Robot();
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}