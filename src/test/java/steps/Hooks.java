package steps;

import configuration.ScenarioStorage;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import reporting.Log;

import java.io.IOException;

public class Hooks {
    @Before
    public void beforeScenario(Scenario scenario) throws IOException {
        ThreadContext.put("scenario", scenario.getName());
        ScenarioStorage.putScenario(scenario);
        Log.info("Validating Scenario ==>"+scenario.getName());

    }


}
