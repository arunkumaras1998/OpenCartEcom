package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmailAddress;
    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;
    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;

    public void setEmailAddress(String email) {
        logger.info("Setting email address");
        safeSendKeys(txtEmailAddress, email);
    }

    public void setPassword(String password) {
        logger.info("Setting password");
        safeSendKeys(txtPassword, password);
    }

    public void clickLogin() {
        logger.info("clicking on login button");
        safeClick(btnLogin);
    }
}
