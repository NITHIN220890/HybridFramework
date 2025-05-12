package configuration;

import io.cucumber.java.Scenario;


import java.util.HashMap;

public class ScenarioStorage {

    private static final HashMap<Thread, Scenario> map = new HashMap<>();

    public static void putScenario(Scenario scenario) {
        map.put(Thread.currentThread(), scenario);
    }

    public static Scenario getScenario() {
        return map.get(Thread.currentThread());
    }
}
