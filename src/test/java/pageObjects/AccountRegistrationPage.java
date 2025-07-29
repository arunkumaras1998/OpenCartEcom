package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "input-firstname")
    WebElement txtFirstName;
    @FindBy(id = "input-lastname")
    WebElement txtLastName;
    @FindBy(id = "input-email")
    WebElement txtEmail;
    @FindBy(id = "input-telephone")
    WebElement txtTelephone;
    @FindBy(id = "input-password")
    WebElement txtPassword;
    @FindBy(id = "input-confirm")
    WebElement txtConfirmPassword;
    @FindBy(name = "agree")
    WebElement chkdPolicy;
    @FindBy(xpath = "//input[@value ='Continue']")
    WebElement btnContinue;
    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setFirstName(String fname) {
        logger.info("Setting first name: {}", fname);
        safeSendKeys(txtFirstName, fname);
    }

    public void setLastName(String lname) {
        logger.info("Setting last name: {}", lname);
        safeSendKeys(txtLastName, lname);
    }

    public void setEmail(String email) {
        logger.info("Setting email: {}", email);
        safeSendKeys(txtEmail, email);
    }

    public void setTelephone(String telephone) {
        logger.info("Setting telephone: {}" + telephone);
        safeSendKeys(txtTelephone, telephone);
    }

    public void setPassword(String pwd) {
        logger.info("Setting password.");
        safeSendKeys(txtPassword, pwd);
    }

    public void setConfirmPasssword(String pwd) {
        logger.info("Confirming password.");
        safeSendKeys(txtConfirmPassword, pwd);
    }

    public void checkPolicy() {
        logger.info("Checking policy aggrement checkbox");
        safeClick(chkdPolicy);
    }

    public void clickContinue() {
        //Sol1
        logger.info("Clicking continue button");
        safeClick(btnContinue);

        //sol2
        //btnContinue.submit();
        //sol3
        //Actions ac = new Actions(driver);
        //ac.moveToElement(btnContinue).click().build().perform();
        //sol4
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].click();", btnContinue);
        //sol5
        //btnContinue.sendKeys(Keys.RETURN);
        //sol6
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
    }

    public String getConfirmationMessage() {
        try {
            waitForVisibility(msgConfirmation);
            String message = msgConfirmation.getText();
            logger.info("Confirmation message: {}", message);
            return message;
        } catch (Exception e) {
            logger.error("Failed to get confirmation message.", e);
            return null;
        }
    }


}
