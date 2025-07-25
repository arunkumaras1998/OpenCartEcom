package testCases;

import utilities.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC003_LoginDDT extends BaseClass {
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataProviderTest")
    public void verify_loginDDT(String userName, String password, String exp) {
        logger.info("****** Starting TC003_LoginDDT ******");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmailAddress(userName);
            loginPage.setPassword(password);
            loginPage.clickLogin();

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExists();

            // Data is Valid - login success - test pass - logout
            //                 login failed - test failed

            // Data is InValid - login success - test fail - logout
            //                 login failed - test pass

            if (exp.equalsIgnoreCase("valid")) {
                if (targetPage == true) {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            } else if (exp.equalsIgnoreCase("invalid")) {
                if (targetPage == true) {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }

        logger.info("****** Finished TC003_LoginDDT ******");
    }
}
