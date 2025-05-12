package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.HelperClass;

public class Retry implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetry = Integer.parseInt(HelperClass.getRetryCount()); // Retry max 2 times

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetry) {
            retryCount++;
            return true; // Retry the test
        }
        return false;
    }
}
