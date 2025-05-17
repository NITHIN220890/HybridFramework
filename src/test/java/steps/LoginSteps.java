package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.LoginPage;
import reporting.Log;

/**
 * Step definitions for Login feature
 */
public class LoginSteps {

    // Page Objects
    private final LoginPage loginPage = new LoginPage();


    @Given("user navigates to the login page")
    public void userNavigatesToLoginPage() {
        loginPage.navigateToLoginPage();
        Log.info("Verified user is on login page");
    }




    @And("User enters the credentials to valdate successfully logged in")
    public void userEntersTheCredentialsToValdateSuccessfullyLoggedIn() {
        loginPage.enterCredentials("8825121979","Siechem@123");
    }

}