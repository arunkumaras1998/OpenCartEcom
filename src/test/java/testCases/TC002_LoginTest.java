package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
    @Test(groups = {"Sanity", "Master"}, description = "Verifies valid login to user account")
    public void verify_login() {
        logger.info("****** Starting TC002_LoginTest ******");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            String email = prop.getProperty("email");
            String password = prop.getProperty("password");
            loginPage.setEmailAddress(email);
            logger.info("Entered Email: {}", email);
            loginPage.setPassword(password);
            logger.info("Entered password");
            loginPage.clickLogin();
            logger.info("Clicked on Login button");


            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean isMyAccountPageDisplayed = myAccountPage.isMyAccountPageExists();
            Assert.assertTrue(isMyAccountPageDisplayed, "Login Failed: My Account page not found");
            logger.info("Login successful. My Account page displayed.");
//        Assert.assertEquals(targetPage,true,"Test Failed");
        } catch (Exception e) {
            logger.error("Exception occured during login test: {}", e.getMessage(), e);
            Assert.fail("Login test failed due to exception: " + e.getMessage());
        }
        logger.info("****** Finished TC002_LoginTest ******");
    }
}
