package com.omnius.ui;

import org.openqa.selenium.WebDriver;


public class PageContainer {

    public WebDriver driver;
    public LoginPage loginPage;
    public TrainerConsolePage trainerConsolePage;
    public DocumentExplorerPage documentexplorerPage;

    /**
     * Constructor of the class
     *
     * @param driver WebDriver
     */
    public PageContainer(WebDriver driver) {
        this.driver = driver;
        initPages();
    }

    /**
     * Intialise & use the methods implemented in the pages
     */
    public void initPages() {
        loginPage= new LoginPage(driver);
        trainerConsolePage = new TrainerConsolePage(driver);
        documentexplorerPage= new DocumentExplorerPage(driver);
       
    }

}
