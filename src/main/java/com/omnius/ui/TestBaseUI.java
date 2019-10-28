package com.omnius.ui;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.omnius.commonutilities.GetConfig;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static java.lang.System.getProperty;

/**
 * @author Avinash Patel
 */
public class TestBaseUI {

	static Logger log = Logger.getLogger(TestBaseUI.class);
    protected static Map<String, String> data = null;
    protected int dataIndex = 0;
    protected String classname = null;
    protected static String packageName = null;
    public PageContainer container;
    public  WebDriver driver;
    public String sheetName;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeClass
    public void beforeClass() throws IOException, ConfigurationException {
    	 sheetName=this.getClass().getSimpleName();
        setLogger();
    }


    @BeforeTest
    public void startReport() throws ConfigurationException {

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/TestReport/UI_Automation.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", GetConfig.getProperties("browser"));
        extent.setSystemInfo("Env", System.getProperty("env"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    /**
     * Method to intialize extent reporting before each method
     *
     * @param method
     * @throws Exception
     */
    @BeforeMethod
    public void init_browser(Method method) throws Exception {
        Test test1= method.getAnnotation(Test.class);
        log.info("------------------------------------------------");
        log.info("Starting executing :" +method.getName());
        test = extent.createTest(method.getName(), test1.description());
        try {
            initDriver();
            data = getTestData(method.getName());
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Method to initiate the driver based on platform & browser
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    public void initDriver() throws ConfigurationException, IOException {
        String browser = GetConfig.getProperties("browser");
        String platform = getProperty("os.name");
        if (platform.toLowerCase().contains("win") == true) {
            log.info("Running on Windows Operating System with " + browser + " browser");
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Win_chromePath"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Win_firefoxPath"));
                driver = new FirefoxDriver();
            }
        } else if (platform.toLowerCase().contains("mac") == true) {
            log.info("Running on Mac Operating System with " + browser + " browser");
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Mac_chromePath"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Mac_firefoxPath"));
                driver = new FirefoxDriver();
            }
        } else if (platform.toLowerCase().contains("lin") == true) {
            log.info("Running on Linux Operating System with " + browser + " browser");
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Linux_chromePath"));
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",
                        getProperty("user.dir") + GetConfig.getProperties("Linux_firefoxPath"));
                driver = new FirefoxDriver();
            }
        }
        container = new PageContainer(driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(GetConfig.getProperties("URL"));
    }



    /**
     * Method to update the test result in both extent reporting & JIRA-ATM
     *
     * @throws Exception
     */
    @AfterMethod
    public void tearDown_browser(ITestResult result) throws Exception {
            log.info("Finished executing testcase :" +result.getMethod().getMethodName());
            log.info("------------------------------------------------");
            if(result.getStatus() == ITestResult.FAILURE) {
                test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
                test.fail(result.getThrowable());
            }
            else if(result.getStatus() == ITestResult.SUCCESS) {
                test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
            }
            else {
                test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
                test.skip(result.getThrowable());
            }
        quitDriver();
    }



    @AfterClass
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }


    /**
     * Method to quit the driver
     */
    public void quitDriver() {

        driver.quit();
    }


    /**
     * Method to set the logger
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    public void setLogger() throws ConfigurationException, IOException {
        PropertyConfigurator.configure(getProperty("user.dir") + "/src/main/resources/Log4j.properties");
        ConsoleAppender console = new ConsoleAppender();
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);
        FileAppender fa = new FileAppender();
        fa.setFile(getProperty("user.dir") + GetConfig.getProperties("start.log") + "OmniUs_log.txt");
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Level.INFO);
        fa.setAppend(false);
        fa.activateOptions();
        Logger.getRootLogger().addAppender(fa);
    }

    protected Map<String, String> getTestData(String testName)
            throws BiffException, IOException, ConfigurationException {
        Sheet dataSheet = null;
        data = new HashMap<String, String>();
        if (dataIndex == -1) {
            return data;
        }
        log.info("Fetching test data for test - " + testName);
        dataSheet = Workbook
                .getWorkbook(new File(getProperty("user.dir") + GetConfig.getProperties("data.spreadsheet.name")))
                .getSheet(sheetName);
        if (dataSheet == null) {
            log.error("The required sheet " + sheetName + " is not present in the Testdata excel");
        }
        dataIndex = dataSheet.findCell(testName).getRow();
        for (int i = 0; i < dataSheet.getColumns(); i++) {
            String key = dataSheet.getCell(i, 0).getContents();
            String value = dataSheet.getCell(i, dataIndex).getContents();
            data.put(key, value);
        }
        return data;
    }
}
