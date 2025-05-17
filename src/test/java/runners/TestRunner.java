package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import retry.Retry;
import retry.RetryListener;

import java.util.Locale;


@CucumberOptions(
        features = "src/test/resources/features/sm.feature",
        glue = {"steps"},  // Use array format to specify packages
        plugin = {
           "html:target/cucumber-report.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "reporting.ExtentReportListener"

        })
@Listeners(RetryListener.class)  // Attach the RetryListener
public class TestRunner extends AbstractTestNGCucumberTests {

    // DataProvider for parallel execution
    @Override
    @DataProvider(parallel = false)

    public Object[][] scenarios() {
        return super.scenarios();
    }

    // Run Cucumber scenarios and apply retry logic
    @Test(groups={"cucumber"},dataProvider = "scenarios", retryAnalyzer = Retry.class)
    public void runScenario(PickleWrapper pickle, FeatureWrapper feature) {
        super.runScenario(pickle, feature);
    }
}