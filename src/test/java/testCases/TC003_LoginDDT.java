package testCases;

import utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC003_LoginDDT extends BaseClass {
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataProviderTest",
            description = "Data-driven test to verify login with multiple credential sets")
    public void verify_loginDDT(String userName, String password, String exp) {
        logger.info("****** Starting TC003_LoginDDT for user: {} ******", userName);
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked 'My Account'");

            homePage.clickLogin();
            logger.info("Clicked 'Login'");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmailAddress(userName);
            logger.info("Entered username: {}", userName);

            loginPage.setPassword(password);
            logger.info("Entered password (hidden)");

            loginPage.clickLogin();
            logger.info("Clicked Login button");

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();

            // Data is Valid - login success - test pass - logout
            //                 login failed - test failed

            // Data is InValid - login success - test fail - logout
            //                 login failed - test pass

            if (exp.equalsIgnoreCase("valid")) {
                Assert.assertTrue(targetPage, "Login failed for valid credentials: " + userName);
                logger.info("Login successful for user: {}", userName);
                myAccountPage.clickLogout();
                logger.info("Logged out successfully");

//                if (targetPage == true) {
//                    myAccountPage.clickLogout();
//                    Assert.assertTrue(true);
//                } else {
//                    Assert.assertTrue(false);
//                }

            } else if (exp.equalsIgnoreCase("invalid")) {
                Assert.assertFalse(targetPage, "Login succeeded for invalid credentials: " + userName);
                logger.info("Login failed as expected for invalid user: {}", userName);

//                if (targetPage == true) {
//                    myAccountPage.clickLogout();
//                    Assert.assertTrue(false);
//                } else {
//                    Assert.assertTrue(true);
//                }

            }
            else {
                logger.warn("Unexpected 'exp' value: {}", exp);
                Assert.fail("Invalid expected result value: " + exp);
            }
        } catch (Exception e) {
            logger.error("Exception in verify_loginDDT for user: {}", userName, e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.info("****** Finished TC003_LoginDDT for user: {} ******", userName);
    }
}
