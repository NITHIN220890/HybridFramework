package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelperClass {
    private static final ConfigReader configReader = new ConfigReader("config.properties");
    private static String env;
    private static String AddExtentReporting;
    private static String retryCount;
    private static String AddLogInReport;
    public static final ThreadLocal<Map<String, List<String>>> dataMap = ThreadLocal.withInitial(HashMap::new);


    public static String getEnv() {
        if (env == null) {
            env = System.getProperty("env", configReader.getProperty("env"));
        }

        return env;
    }

    public static String getLogFlag() {

        if (AddLogInReport == null) {
            AddLogInReport = System.getProperty("AddLogInReport", configReader.getProperty("AddLogInReport"));

        }
        return AddLogInReport;
    }

    public static String getRetryCount() {

        if (retryCount == null) {
            retryCount = System.getProperty("retryCount", configReader.getProperty("retryCount"));
        }
        return retryCount;
    }


    public static String getExtentReportFlag() {

        if (AddExtentReporting == null) {
            AddExtentReporting = System.getProperty("AddExtentReporting", configReader.getProperty("AddExtentReporting"));
        }
        return AddExtentReporting;
    }



    public static void addMultiValuesMap(String key, String value) {
        Map<String, List<String>> map = dataMap.get();
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

    }
}
