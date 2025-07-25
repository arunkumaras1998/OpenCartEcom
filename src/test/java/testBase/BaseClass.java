package testBase;

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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
//                + File.separator + "src"
//                + File.separator + "test"
//                + File.separator + "resources"
//                + File.separator + "config.properties");
        prop = new Properties();
        prop.load(reader);

        // Specified the Logger to setUp() method
        logger = LogManager.getLogger(this.getClass());

        switch (br.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid Browser Name..........");

        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("appURLTest"));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }

    public String randomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(8);
        return generatedString;
    }

    public String randomNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String generateAlphaNumberic() {
        String generatedString = RandomStringUtils.randomAlphabetic(3);
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        return (generatedString + "$@" + generatedNumber);
    }

    public String captureScreenshot(String testName) {
        String timeStamp = new SimpleDateFormat("yyyy.DD.mm.HH.mm.ss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        // below code is coping sourceFile to targetFilePath
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}
