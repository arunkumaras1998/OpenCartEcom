package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.apache.logging.log4j.Logger; // log4j
import org.apache.logging.log4j.LogManager; //log4j
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger; //Log4j
    public Properties prop;

    @BeforeClass(alwaysRun = true)
    @Parameters({"os", "browser"})
    public void setUp(String os, String br) throws IOException {

        // Loading config.properties file
        String configPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";
        FileReader reader = new FileReader(configPath);
        prop = new Properties();
        prop.load(reader);

        // Initialize Logger
        logger = LogManager.getLogger(this.getClass());

        // Browser initialization
        switch (br.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + br);

        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("appURLTest"));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit(); // close all windows and safely end session
            }
        } catch (Exception e) {
            logger.warn("Exception while quiting browser: {}", e.getMessage(), e);
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String generateAlphaNumeric() {
        String alpha = RandomStringUtils.randomAlphabetic(3);
        String num = RandomStringUtils.randomNumeric(3);
        return (alpha + "$@" + num);
    }

    public String userDefinedRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public String captureScreenshot(String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String targetFilePath = System.getProperty("user.dir") + File.separator
                + "screenshots" + File.separator
                + testName + "_" + timeStamp + ".png";

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(targetFilePath);

        // below code is coping sourceFile to targetFilePath
        try {
            Files.createDirectories(targetFile.getParentFile().toPath()); // to ensure folder is existing
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved: {}", targetFilePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage(), e);
        }
        return targetFilePath;
    }
}
