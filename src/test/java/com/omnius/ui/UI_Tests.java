package com.omnius.ui;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.omnius.ui.TestBaseUI;
import java.awt.*;
import java.util.Date;

import static java.lang.System.getProperty;

public class UI_Tests extends TestBaseUI  {

    String collectionName;

    @Test(priority = 1, description="Login with valid credentials")
    public void login_with_validUser() throws ConfigurationException {
        Assert.assertFalse(container.loginPage.login(data.get("username"),data.get("password")),"Login was successful");
    }

    @Test(priority = 2, description="Login with valid credentials")
    public void login_with_invalidUser() throws ConfigurationException {
        Assert.assertFalse(container.loginPage.login(data.get("username"),data.get("password")), "Login failed");
        Assert.assertEquals(container.loginPage.getAlertMessage(),"Invalid username or password.");
    }

    @Test(priority = 3, description="API_Tests cases to create a collection with valid name")
    public void createCollection_validName() throws ConfigurationException {
        collectionName="Test_"+String.valueOf(new Date().getTime()).substring(7);
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.createCollection(collectionName);
    }

    @Test(priority = 4, description="API_Tests cases to create a collection with invalid name")
    public void createCollection_invalidName() throws ConfigurationException {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.createCollection("    ");
        Assert.assertTrue(container.documentexplorerPage.checkCollectiontWarningMessage());
    }

    @Test(priority = 5, description="API_Tests case to add PDF file types for document uploads")
    public void addPDF_collection() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        String documentStatus=container.documentexplorerPage.uploadDocuments(data.get("filename"), true);
        Assert.assertEquals(documentStatus,data.get("documentStatusMsg"), "Document status is incorrect");
    }

    @Test(priority = 6, description="API_Tests case to add JPG file types for document uploads")
    public void addJPG_collection() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        String documentStatus=container.documentexplorerPage.uploadDocuments(data.get("filename"), true);
        Assert.assertEquals(documentStatus,data.get("documentStatusMsg"), "Document status is incorrect");
    }

    @Test(priority = 7, description="API_Tests case to add PNG file types for document uploads")
    public void addPNG_collection() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        String documentStatus=container.documentexplorerPage.uploadDocuments(data.get("filename"), true);
        Assert.assertEquals(documentStatus,data.get("documentStatusMsg"), "Document status is incorrect");
    }

    @Test(priority = 8, description="API_Tests case to add TIFF file types for document uploads")
    public void addTIFF_collection() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        String documentStatus=container.documentexplorerPage.uploadDocuments(data.get("filename"), true);
        Assert.assertEquals(documentStatus,data.get("documentStatusMsg"), "Document status is incorrect");
    }

    @Test(priority = 9, description="API_Tests case to add unsupported file types for document uploads")
    public void addOthers_collection() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        String documentStatus=container.documentexplorerPage.uploadDocuments(data.get("filename"),false);
        Assert.assertEquals(documentStatus,data.get("documentStatusMsg"), "Document status is incorrect");
    }

    @Test(priority = 10, description="To search for collection and select it")
    public void collection_search() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
    }

    @Test(priority = 11, description="API_Tests case to sort the collection as per the selection passed by")
    public void collection_sort() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.sortCollectionStatus("Done");
    }

    @Test(priority = 12, description="API_Tests case to search documents and verify the search results")
    public void documents_search() throws Exception {
        container.loginPage.login(data.get("username"),data.get("password"));
        container.trainerConsolePage.clickDocumentExplorer();
        container.documentexplorerPage.searchCollection(collectionName);
        container.documentexplorerPage.searchDocuments(data.get("filename"));
    }

}
