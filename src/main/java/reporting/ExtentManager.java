package reporting;




import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utilities.HelperClass;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> stepNode = new ThreadLocal<>();
    private static final ConcurrentMap<String, ExtentTest> testMap = new ConcurrentHashMap<>();

    static {
        String extentFlag= HelperClass.getExtentReportFlag();
        System.out.println(extentFlag);
        if(extentFlag.equalsIgnoreCase("true")) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/ExtentReport/Reports.html");
            reporter.config().setTheme(Theme.DARK);
            reporter.config().setDocumentTitle("Automation Test Report");
            reporter.config().setReportName("Parallel Test Execution Report");
            extentReports.attachReporter(reporter);
        }}

    public static synchronized ExtentReports getInstance() {
        return extentReports;
    }

    public static synchronized void startScenario(String scenarioName) {
        ExtentTest test = extentReports.createTest(scenarioName);
        scenarioTest.set(test);
        testMap.put(Thread.currentThread().getName(), test);
    }

    public static ExtentTest getScenarioTest() {
        return scenarioTest.get();
    }

    public static synchronized void startStep(String stepName) {
        ExtentTest step = getScenarioTest().createNode(stepName);
        stepNode.set(step);
    }

    public static ExtentTest getStepNode() {
        return stepNode.get();
    }

    public static synchronized void endScenario() {
        extentReports.flush();
        scenarioTest.remove();
        stepNode.remove();
    }
}

