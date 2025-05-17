package pages;

import drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import reporting.Log;
import utilities.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Base class for all Page Objects with common WebDriver operations
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    private static final ConfigReader configReader = new ConfigReader("config.properties");

    // Default timeout values
    private int DEFAULT_WAIT_TIMEOUT;

    /**
     * Constructor initializes WebDriver and utilities
     */
    public BasePage() {
        // Initialize DEFAULT_WAIT_TIMEOUT
        String timeoutStr = configReader.getProperty("explicitWaitTimeout");
        int waitTimeout = 15; // Default value

        try {
            waitTimeout = Integer.parseInt(timeoutStr);
        } catch (NumberFormatException e) {
            Log.info("Invalid explicitWaitTimeout value: " + timeoutStr + ". Using default: 15");
        }

        DEFAULT_WAIT_TIMEOUT = waitTimeout;

        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;

        // Initialize WebElements annotated with @FindBy
        PageFactory.initElements(driver, this);
    }

    /**
     * Navigates to specified URL
     */
    public void navigateTo(String url) {
        try {
            Log.info("Navigating to URL: " + url);
            driver.get(url);
        } catch (Exception e) {
            Log.info("Failed to navigate to URL: " + url);
            throw e;
        }
    }

    /**
     * Refreshes current page
     */
    public void refreshPage() {
        Log.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Navigates back in browser history
     */
    public void navigateBack() {
        Log.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigates forward in browser history
     */
    public void navigateForward() {
        Log.info("Navigating forward");
        driver.navigate().forward();
    }

    /**
     * Gets current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Gets page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Clicks on element
     */
    public void click(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            highlightElement(element);
            element.click();
            Log.info("Clicked on element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to click on element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Clicks on element using JavaScript
     */
    public void jsClick(WebElement element) {
        try {
            waitForElementVisibility(element);
            highlightElement(element);
            js.executeScript("arguments[0].click();", element);
            Log.info("JS clicked on element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to JS click on element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Types text into element
     */
    public void type(WebElement element, String text) {
        try {
            waitForElementVisibility(element);
            highlightElement(element);
            element.sendKeys(text);
            Log.info("Typed text '" + text + "' into element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to type text into element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Clears element and types text
     */
    public void clearAndType(WebElement element, String text) {
        try {
            waitForElementVisibility(element);
            highlightElement(element);
            element.clear();
            element.sendKeys(text);
            Log.info("Cleared and typed text '" + text + "' into element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to clear and type text into element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Gets text from element
     */
    public String getText(WebElement element) {
        try {
            waitForElementVisibility(element);
            highlightElement(element);
            String text = element.getText();
            Log.info("Got text '" + text + "' from element: " + getElementDescription(element));
            return text;
        } catch (Exception e) {
            Log.info("Failed to get text from element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Gets attribute value from element
     */
    public String getAttributeValue(WebElement element, String attribute) {
        try {
            waitForElementVisibility(element);
            String value = element.getAttribute(attribute);
            Log.info("Got attribute '" + attribute + "' with value '" + value + "' from element: " + getElementDescription(element));
            return value;
        } catch (Exception e) {
            Log.info("Failed to get attribute '" + attribute + "' from element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Waits for element to be visible
     */
    public WebElement waitForElementVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element to be visible with custom timeout
     */
    public WebElement waitForElementVisibility(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element to be clickable
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element to be clickable with custom timeout
     */
    public WebElement waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element to be invisible
     */
    public boolean waitForElementInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for page to load completely
     */
    public void waitForPageLoad() {
        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Selects option by visible text
     */
    public void selectByVisibleText(WebElement dropdown, String text) {
        try {
            waitForElementVisibility(dropdown);
            Select select = new Select(dropdown);
            select.selectByVisibleText(text);
            Log.info("Selected '" + text + "' from dropdown: " + getElementDescription(dropdown));
        } catch (Exception e) {
            Log.info("Failed to select from dropdown: " + getElementDescription(dropdown));
            throw e;
        }
    }

    /**
     * Selects option by value
     */
    public void selectByValue(WebElement dropdown, String value) {
        try {
            waitForElementVisibility(dropdown);
            Select select = new Select(dropdown);
            select.selectByValue(value);
            Log.info("Selected value '" + value + "' from dropdown: " + getElementDescription(dropdown));
        } catch (Exception e) {
            Log.info("Failed to select value from dropdown: " + getElementDescription(dropdown));
            throw e;
        }
    }

    /**
     * Selects option by index
     */
    public void selectByIndex(WebElement dropdown, int index) {
        try {
            waitForElementVisibility(dropdown);
            Select select = new Select(dropdown);
            select.selectByIndex(index);
            Log.info("Selected index '" + index + "' from dropdown: " + getElementDescription(dropdown));
        } catch (Exception e) {
            Log.info("Failed to select index from dropdown: " + getElementDescription(dropdown));
            throw e;
        }
    }

    /**
     * Moves to element
     */
    public void moveToElement(WebElement element) {
        try {
            waitForElementVisibility(element);
            actions.moveToElement(element).perform();
            Log.info("Moved to element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to move to element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Double-clicks element
     */
    public void doubleClick(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            actions.doubleClick(element).perform();
            Log.info("Double-clicked on element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to double-click on element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Right-clicks element
     */
    public void rightClick(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            actions.contextClick(element).perform();
            Log.info("Right-clicked on element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to right-click on element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Switches to frame
     */
    public void switchToFrame(WebElement frameElement) {
        try {
            waitForElementVisibility(frameElement);
            driver.switchTo().frame(frameElement);
            Log.info("Switched to frame: " + getElementDescription(frameElement));
        } catch (Exception e) {
            Log.info("Failed to switch to frame: " + getElementDescription(frameElement));
            throw e;
        }
    }

    /**
     * Switches to default content
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        Log.info("Switched to default content");
    }

    /**
     * Executes JavaScript
     */
    public Object executeScript(String script, Object... args) {
        return js.executeScript(script, args);
    }

    /**
     * Scrolls to element
     */
    public void scrollToElement(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Log.info("Scrolled to element: " + getElementDescription(element));
        } catch (Exception e) {
            Log.info("Failed to scroll to element: " + getElementDescription(element));
            throw e;
        }
    }

    /**
     * Checks if element is displayed
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if element is enabled
     */
    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Checks if element is selected
     */
    public boolean isElementSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Takes screenshot
     */
    public void takeScreenshot() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            // Implementation for saving screenshot can be added here
            Log.info("Screenshot taken");
        } catch (Exception e) {
            Log.info("Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Highlights element
     */
    private void highlightElement(WebElement element) {
        try {
            String highlightElements = configReader.getProperty("highlightElements");
            if (highlightElements == null || highlightElements.isEmpty()) {
                highlightElements = "true"; // Default value
            }

            if (Boolean.parseBoolean(highlightElements)) {
                String originalStyle = element.getAttribute("style");
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                        element, "border: 2px solid red; background-color: yellow;");

                // Revert back to original style after a brief highlight
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                        element, originalStyle);
            }
        } catch (Exception e) {
            // Ignore any highlighting errors
        }
    }

    /**
     * Gets element description for logging
     */
    private String getElementDescription(WebElement element) {
        try {
            // Try to get a useful description of the element for logging
            String id = element.getAttribute("id");
            String name = element.getAttribute("name");
            String text = element.getText();

            if (id != null && !id.isEmpty()) {
                return "id=" + id;
            } else if (name != null && !name.isEmpty()) {
                return "name=" + name;
            } else if (text != null && !text.isEmpty() && text.length() < 30) {
                return "text=" + text;
            } else {
                return element.getTagName();
            }
        } catch (Exception e) {
            return "unknown element";
        }
    }
}