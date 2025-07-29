package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private String reportName;

    @Override
    public void onStart(ITestContext context) {
        /*
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt = new Date();
        String currentDateFormat = df.format(dt);
        */
        // instead of using above we can optimize in one line
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
        reportName = "Test-Report-" + timeStamp + ".html";
        String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportName;
        sparkReporter = new ExtentSparkReporter(reportPath); // specified the location of the report
        sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of the report
        sparkReporter.config().setReportName("opencart Functional Testing"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // We can get the os and browser name from TestNG XML file where we defined using parameter annotation
        extent.setSystemInfo("Operating System", context.getCurrentXmlTest().getParameter("os"));
        extent.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));

        // get the groups name which are you provided in TestNG XML file
        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logResult(result, Status.PASS, result.getName() + " executed successfully");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        logResult(result, Status.FAIL, result.getName() + " failed.");
        test.log(Status.INFO, result.getThrowable().getMessage());
        try {
            String screenshotPath = new BaseClass().captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logResult(result, Status.SKIP, result.getName() + " was skipped");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        }
    }

    private void logResult(ITestResult result, Status status, String message) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // To display the groups in the report
        test.log(status, message);
    }


    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
// below code is written for opening extent report automatically in the browser after test execution done
        String pathOfExtentReport = System.getProperty("user.dir") + File.separator + "reports" + File.separator + reportName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(extentReport.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        try {
            URL url =
                    new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
            // Create an Email Message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("email.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("arun1998.ag12@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("arun1998.ag12@gmail.com"); // Sender
            email.setSubject("Test Reports");
            email.setMsg("Please find the attached report...");
            email.addTo("ArunTest@gmail.com");
            email.attach(url, "extent report", "Please check report");
            email.send(); // send the email

        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }
}
