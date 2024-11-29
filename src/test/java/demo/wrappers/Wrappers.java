package demo.wrappers;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    static WebDriver driver;
    static WebDriverWait wait;

    // Wrapper for clicking an element
    public static void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            System.out.println("Clicked on the element: " + locator.toString());
        } catch (Exception e) {
            System.out.println("Failed to click on the element: " + locator.toString());
            e.printStackTrace();
        }
    }
    public static void findElementAndClick(WebDriver driver, By locator) throws InterruptedException {
        WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        Thread.sleep(3000);
                     
    }
    public static String findElementAndPrint(WebDriver driver, By locator, int elementNo) {
        WebElement we = driver.findElements(locator).get(elementNo);
        String txt = we.getText();
        return txt;
    }
    public static String findElementAndPrintWE(WebDriver driver, By locator, int elementNo, WebElement we) {
        WebElement element = we.findElements(locator).get(elementNo);
        String txt = element.getText();
        return txt;
    }
    

    // Wrapper for sending keys to an element
    public static void sendKeys(WebDriver driver, By locator, String text) {
        System.out.println("Sending Keys");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
            
        } catch (Exception e) {
            System.out.println("Failed to send keys to the element: " + e.getMessage());
           
        }
    }

    // Wrapper for getting text from an element
    public String getText(By locator) {
            return driver.findElement(locator).getText();
    }

    // Wrapper for scrolling into view
    public static void scrollToElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            System.out.println("Scrolled to the element: " + locator.toString());
        } catch (Exception e) {
            System.out.println("Failed to scroll to the element: " + locator.toString());
            e.printStackTrace();
        }
    }

    public static void clickonText(ChromeDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Wrapper for waiting until an element is visible
    public void waitForVisibility(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Element is visible: " + locator.toString());
        } catch (Exception e) {
            System.out.println("Element is not visible: " + locator.toString());
            e.printStackTrace();
        }
    }

    public static void clickTillUnclickable(WebDriver driver, By locator, Integer maxIterations) throws InterruptedException {
        Integer numIter = 0;
        while (numIter < maxIterations) {
            try {
                findElementAndClick(driver, locator);
            } catch (TimeoutException e) {
                
                System.out.println("Stopping -" + e.getMessage());
                break;
            }
        }
    }

    public static long convertToNumericValue(String value) {
        // Trim the string to remove any leading or trailing spaces
        value = value.trim().toUpperCase();

        // Check if the last character is non-numeric and determine the multiplier
        char lastChar = value.charAt(value.length() - 1);
        int multiplier = 1;
        switch (lastChar) {
            case 'K':
                multiplier = 1000;
                break;
            case 'M':
                multiplier = 1000000;
                break;
            case 'B':
                multiplier = 1000000000;
                break;
            default:
                // If the last character is numeric, parse the entire string
                if (Character.isDigit(lastChar)) {
                    return Long.parseLong(value);
                }
                throw new IllegalArgumentException("Invalid format: " + value);
        }

        // Extract the numeric part before the last character
        String numericPart = value.substring(0, value.length() - 1);
        double number = Double.parseDouble(numericPart);
        return (long) (number * multiplier);
    }

    }