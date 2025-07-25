package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_account_registration() {

        logger.info("***** Starting TC001_AccountRegistrationTest *****");
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on My Account Link ");
            hp.clickRegister();
            logger.info("Clicked on Register Link ");

            logger.info("Provide Customer Details....");
            AccountRegistrationPage registrationPage = new AccountRegistrationPage(driver);
            registrationPage.setFirstName(randomString());
            registrationPage.setLastName(randomString());
            registrationPage.setEmail(randomString() + "@gmail.com");
            registrationPage.setTelePhone(randomNumber());
            String password = generateAlphaNumberic();
            registrationPage.setPassword(password);
            registrationPage.setConfirmPasssword(password);
            registrationPage.checkPolicy();
            registrationPage.clickContinue();

            logger.info("Validating Expected Message...");
            String confMsg = registrationPage.getConfirmationMessage();

            if (confMsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            } else {
                logger.error("Test Failed....");
                logger.debug("Debug Logs....");
                Assert.assertTrue(false);
            }

            //Assert.assertEquals(confMsg, "Your Account Has Been Created!!!");
        } catch (Exception e) {
            Assert.fail();
        }
        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }

}
