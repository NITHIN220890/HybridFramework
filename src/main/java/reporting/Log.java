package reporting;

import configuration.ScenarioStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.HelperClass;

public class Log {

    // Updated to use the correct class for the logger name
    public static Logger logger = LoggerFactory.getLogger(Log.class);

    public static void info(String message) {
        boolean flag = HelperClass.getLogFlag().equals("true");
        Loginfo(message, flag);
    }

    public static void Loginfo(String message, boolean flag) {
        logger.info(message);
        boolean extentFlag =HelperClass.getExtentReportFlag().equals("true");
        if(extentFlag){
            StepLogger.log(message);
        }
        // For cucumber report generation --- need to fix
        if (flag) {
            ScenarioStorage.getScenario().log(message);
        }
    }
}