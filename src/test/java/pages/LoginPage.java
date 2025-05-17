package pages;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import reporting.Log;
import utilities.ConfigReader;
import pages.BasePage;

/**
 * Page Object for the Login Page
 */
public class LoginPage extends BasePage {

    private static final ConfigReader configReader = new ConfigReader("config.properties");



    // Page Elements
    @FindBy(xpath ="//*[@id='user-phone-no']")
    private WebElement uname;

    @FindBy(xpath = "//*[@type='password']")
    private WebElement pword;

    @FindBy(xpath = "//*[@id=\"root\"]/div[3]/div/div[1]/div[2]/form/div[6]/button")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class,'error-message')]")
    private WebElement errorMessage;

    @FindBy(linkText = "Forgot Password?")
    private WebElement forgotPasswordLink;

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    /**
     * Navigates to the login page
     */
    public void navigateToLoginPage() {
        String baseUrl = System.getProperty("baseUrl", configReader.getProperty("baseUrl"));
        navigateTo(baseUrl);
        Log.info("Navigated to login page");
    }

    public void enterCredentials(String username,String password){
        type(uname,username);
        type(pword,password);
        click(loginButton);
    }

  }