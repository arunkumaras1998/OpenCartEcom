package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class MyAccountPage extends BasePage {
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement msgHeading;
    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
    WebElement btnLogout;

    public boolean isMyAccountPageExists() {
        try {
            waitForVisibility(msgHeading);
            boolean headingFlag = msgHeading.isDisplayed();
            logger.info("Message heading is displayed...");
            return headingFlag;
        } catch (Exception e) {
            logger.error("Failed to get message heading.", e);
            return false;
        }
    }

    public void clickLogout() {
        logger.info("Clicking on the logout button.");
        safeClick(btnLogout);
    }
}
