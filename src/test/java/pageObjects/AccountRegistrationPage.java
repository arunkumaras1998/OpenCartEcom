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
        txtFirstName.sendKeys(fname);
    }

    public void setLastName(String lname) {
        txtLastName.sendKeys(lname);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setTelePhone(String telephone) {
        txtTelephone.sendKeys(telephone);
    }

    public void setPassword(String pwd) {
        txtPassword.sendKeys(pwd);
    }

    public void setConfirmPasssword(String pwd) {
        txtConfirmPassword.sendKeys(pwd);
    }

    public void checkPolicy() {
        chkdPolicy.click();
    }

    public void clickContinue() {
        //Sol1
        btnContinue.click();
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
            return (msgConfirmation.getText());
        } catch (Exception e) {
            return (e.getMessage());
        }
    }


}
