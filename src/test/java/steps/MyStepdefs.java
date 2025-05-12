package steps;

import reporting.Log;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import utilities.HelperClass;

public class MyStepdefs {
    WebDriver driver;

    @Given("User is able to print both console and file logs using slf implementation")
    public void user_is_able_to_print_both_console_and_file_logs_using_slf_implementation() {
        Log.info("Step executed successfully");
        try {
            Log.info("Validated from step definition");
            Log.info("Log method called successfully");
        } catch (Exception e) {
            System.out.println("Error in logging: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Given("Validate all the config files are read from properties")
    public void validateAllTheConfigFilesAreReadFromProperties() {
        String env = HelperClass.getEnv();
        Log.info("Environment is -------------------"+env);
        String flag = HelperClass.getLogFlag();
        Log.info("Flag is--------------- "+flag);
    }

    @Given("retry validation")
    public void retryValidation() {
            int a,b;
            a =180;
            b=0;
            Log.info("Variable a "+a);
            Log.info("Variable b"+b);
            int result = a / b;
            Log.info(String.valueOf(result));


}
}

