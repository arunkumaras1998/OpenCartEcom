package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"}, description = "Verifies account registration with valid user data")
    public void verify_account_registration() {

        logger.info("***** Starting TC001_AccountRegistrationTest *****");
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();

            // Generate Test Data
            String firstName = randomString();
            String lastName = randomString();
            String email = randomString() + "@gmail.com";
            String telephone = randomNumber();
            String password = generateAlphaNumeric();

            logger.info("Generate Test Data - First Name: {}, Last Name: {}, Email: {}, Telephone{}, Password: {}", firstName, lastName, email, telephone, password);

            // Fill Registration Form
            AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
            regPage.setFirstName(firstName);
            regPage.setLastName(lastName);
            regPage.setEmail(email);
            regPage.setTelephone(telephone);
            regPage.setPassword(password);
            regPage.setConfirmPasssword(password);
            regPage.checkPolicy();
            regPage.clickContinue();

            // Validating Confirmation Message
            logger.info("Validating confirmation Message...");
            String confirmationMsg = regPage.getConfirmationMessage();

            Assert.assertEquals(confirmationMsg, "Your Account Has Been Created!", "Confirmation message mismatch!");

            logger.info("Account registration successful. Confirmation: {}", confirmationMsg);


            //Assert.assertEquals(confMsg, "Your Account Has Been Created!!!");
        } catch (Exception e) {
            logger.error("Exception occurred during registration test: ", e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }

}
