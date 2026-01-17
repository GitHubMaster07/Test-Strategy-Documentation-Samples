package examples.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By errorMsg = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage open() {
        driver.get("https://example.com/login");
        return this;
    }

    public LoginPage enterUsername(String value) {
        driver.findElement(username).sendKeys(value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        driver.findElement(password).sendKeys(value);
        return this;
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}
