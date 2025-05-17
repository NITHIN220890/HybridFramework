package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import reporting.Log;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe driver manager class that handles WebDriver initialization and cleanup
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ConfigReader configReader = new ConfigReader("config.properties");

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets the WebDriver instance for the current thread
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initializeDriver();
        }
        return driverThreadLocal.get();
    }

    /**
     * Initializes a new WebDriver instance for the current thread
     */
    public static void initializeDriver() {
        String browser = configReader.getProperty("browser");
        // Check if browser is overridden by system property
        String sysBrowser = System.getProperty("browser");
        if (sysBrowser != null && !sysBrowser.isEmpty()) {
            browser = sysBrowser;
        }

        Log.info("Initializing " + browser + " browser");

        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
                driver = setupFirefoxDriver();
                break;
            case "edge":
                driver = setupEdgeDriver();
                break;
            case "safari":
                driver = setupSafariDriver();
                break;
            case "chrome-headless":
                driver = setupHeadlessChromeDriver();
                break;
            default:
                Log.info("Browser type not specified or recognized. Defaulting to Chrome.");
                driver = setupChromeDriver();
        }

        configureDriver(driver);
        driverThreadLocal.set(driver);
        Log.info("WebDriver initialized successfully for thread: " + Thread.currentThread().getId());
    }

    /**
     * Sets common configuration for all WebDriver instances
     *
     * @param driver WebDriver instance
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();

        // Get implicitWait with default of 10 seconds
        String implicitWaitStr = configReader.getProperty("implicitWaitTimeout");
        // Check if implicitWait is overridden by system property
        String sysImplicitWait = System.getProperty("implicitWaitTimeout");
        if (sysImplicitWait != null && !sysImplicitWait.isEmpty()) {
            implicitWaitStr = sysImplicitWait;
        }

        // Get pageLoadTimeout with default of 30 seconds
        String pageLoadTimeoutStr = configReader.getProperty("pageLoadTimeout");
        // Check if pageLoadTimeout is overridden by system property
        String sysPageLoadTimeout = System.getProperty("pageLoadTimeout");
        if (sysPageLoadTimeout != null && !sysPageLoadTimeout.isEmpty()) {
            pageLoadTimeoutStr = sysPageLoadTimeout;
        }

        // Parse timeout values
        int implicitWait;
        int pageLoadTimeout;

        try {
            implicitWait = Integer.parseInt(implicitWaitStr);
        } catch (NumberFormatException e) {
            Log.info("Invalid implicitWaitTimeout value: " + implicitWaitStr + ". Using default: 10");
            implicitWait = 10;
        }

        try {
            pageLoadTimeout = Integer.parseInt(pageLoadTimeoutStr);
        } catch (NumberFormatException e) {
            Log.info("Invalid pageLoadTimeout value: " + pageLoadTimeoutStr + ". Using default: 30");
            pageLoadTimeout = 30;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().deleteAllCookies();

        Log.info("Configured WebDriver with implicitWait=" + implicitWait + "s, pageLoadTimeout=" + pageLoadTimeout + "s");
    }

    /**
     * Sets up Chrome WebDriver
     *
     * @return configured ChromeDriver instance
     */
    private static WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Configure download directory
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", System.getProperty("user.dir") + "/downloads");
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    /**
     * Sets up headless Chrome WebDriver
     *
     * @return configured headless ChromeDriver instance
     */
    private static WebDriver setupHeadlessChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return new ChromeDriver(options);
    }

    /**
     * Sets up Firefox WebDriver
     *
     * @return configured FirefoxDriver instance
     */
    private static WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        // Add Firefox-specific options if needed

        return new FirefoxDriver(options);
    }

    /**
     * Sets up Edge WebDriver
     *
     * @return configured EdgeDriver instance
     */
    private static WebDriver setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        // Add Edge-specific options if needed

        return new EdgeDriver(options);
    }

    /**
     * Sets up Safari WebDriver
     *
     * @return configured SafariDriver instance
     */
    private static WebDriver setupSafariDriver() {
        SafariOptions options = new SafariOptions();
        // Add Safari-specific options if needed

        return new SafariDriver(options);
    }

    /**
     * Quits the WebDriver instance for the current thread
     */
    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            try {
                Log.info("Quitting WebDriver for thread: " + Thread.currentThread().getId());
                driverThreadLocal.get().quit();
            } catch (Exception e) {
                Log.info("Error quitting WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}