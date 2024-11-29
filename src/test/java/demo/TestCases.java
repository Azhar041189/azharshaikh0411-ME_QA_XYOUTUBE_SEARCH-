package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

import java.time.Duration;
import java.util.logging.Level;
import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
//import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        static ChromeDriver driver;
        Wrappers actions;
        SoftAssert softAssert = new SoftAssert();

        public static void scrollToElementAndClick(WebElement aboutLink) {
                try {
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].scrollIntoView(true);", aboutLink); // Scroll to the element
                        js.executeScript("arguments[0].click();", aboutLink); // Click using JS Executor
                } catch (Exception e) {
                        System.out.println("Unable to click on the element: " + e.getMessage());
                }
        }

        @BeforeMethod
        public void goToYT() throws InterruptedException {
                driver.get("https://www.youtube.com");
                Thread.sleep(3000);
        }
        @Test
        public void testCase01() {
                try {
                    System.out.println("Start TestCase01");
            
                    String currentUrl = driver.getCurrentUrl();
                    Assert.assertTrue(currentUrl.contains("youtube.com"), "The URL is incorrect!");
                    System.out.println(currentUrl);
            
                    WebElement aboutLink = driver.findElement(By.xpath("//div[@id='footer']//a[contains(text(), 'About')]"));
                    scrollToElementAndClick(aboutLink);
                    System.out.println("Clicked on About Link");
                    
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    wait.until(ExpectedConditions.urlContains("about.youtube"));
                    String aboutPageUrl = driver.getCurrentUrl();
                    Assert.assertTrue(aboutPageUrl.contains("about.youtube"), "The About page URL is incorrect!");
                    System.out.println(aboutPageUrl);
                    /*WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
                    List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
                        for (WebElement paragraph : paragraphs) {
                                 wait2.until(ExpectedConditions.visibilityOf(paragraph));
                            String text = paragraph.getText();
                                          System.out.println("Paragraph text: " + text);   
                        }   */
                    WebElement aboutMsg = driver.findElement(By.xpath("//h1[contains(text(), 'About YouTube')]"));
                    String aboutMsgText = aboutMsg.getText();
                    Assert.assertTrue(aboutMsgText.contains("About YouTube"), "The About message is incorrect!");
                    System.out.println(aboutMsgText);
                    List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
                    for (WebElement paragraph : paragraphs) {
                                String text = paragraph.getText();
                                if (!text.isEmpty()) {
                                 System.out.println(text);
                        }
                        paragraphs = driver.findElements(By.tagName("p"));
                        Assert.assertTrue(paragraphs.size() > 0, "No paragraphs found on the page.");
                        }
                

                    System.out.println("End TestCase01");
            
                } catch (Exception e) {
                    System.err.println("Test Case Failed: " + e.getMessage());
                }
            }

        @Test
        public void testCase02() throws InterruptedException {
                System.out.println("Start TestCase02");
                WebElement MoviesLink = driver.findElement(By.xpath("//a[@title= \"Movies\"]"));
                scrollToElementAndClick(MoviesLink);
                System.out.println("Clicked on Movies Link");
                Thread.sleep(1000);
                Wrappers.clickTillUnclickable(driver, By.xpath(
                                "//span[contains(text(), \"Top selling\")]//ancestor::div[@id ='dismissible']//div[@id ='right-arrow']//button"),
                                3);
                Thread.sleep(1000);
                
                try {
                WebElement ratingBadge = driver.findElement(By.cssSelector(".rating-badge"));
                softAssert.assertTrue(ratingBadge.getText().contains("A"), 
                    "Movie is not marked as Mature");
                } catch (Exception e) {
                softAssert.fail("Could not find rating badge: " + e.getMessage());
                }
    
               
                try {
                WebElement categoryLabel = driver.findElement(By.cssSelector(".category-label"));
                String category = categoryLabel.getText();
                softAssert.assertTrue(
                    category.equals("Comedy") || 
                    category.equals("Animation") || 
                    category.equals("Drama"),
                    "Unexpected category: " + category
                 );
                 } catch (Exception e) {
                softAssert.fail("Could not find category label: " + e.getMessage());
                }
        }

        @Test
        public void testCase03() throws InterruptedException {
                System.out.println("Start TestCase03");
                Wrappers.findElementAndClick(driver, By.xpath("//a[@title= \"Music\"]"));
                Thread.sleep(1000);
                Wrappers.clickTillUnclickable(driver, By.xpath(
                                "//span[contains(text(), \"India's Biggest Hits\")]//ancestor::div[@id ='dismissible']//div[@id ='right-arrow']//button"),
                                3);
                Thread.sleep(1000);
                By locator_TrackCount = By.xpath(
                                "//span[contains(., \"Biggest Hits\")]//ancestor::div[6]//div[@id = 'contents']//yt-thumbnail-badge-view-model//div[@class = \"badge-shape-wiz__text\"]");
                String res = Wrappers.findElementAndPrint(driver, locator_TrackCount,
                                driver.findElements(locator_TrackCount).size() - 1);
                System.out.println("Track Count: " + res);
                Thread.sleep(1000);
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(Wrappers.convertToNumericValue(res.split(" ")[0]) <= 50);
                System.out.println("End TestCase03");
        }
        @Test
        public void testCase04() throws InterruptedException {
                System.out.println("Start TestCase04");
                Wrappers.findElementAndClick(driver, By.xpath("//a[@title= \"News\"]"));
                Thread.sleep(1000);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement contnetCards = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                                "//div[@id = 'rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id = 'contents']")));
                Thread.sleep(1000);
                long sumOfVotes = 0;
                for (int i = 0; i < 3; i++) {
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='header']"), i,
                                        contnetCards));
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='body']"), i,
                                        contnetCards));
                        try {
                                String res = Wrappers.findElementAndPrintWE(driver,
                                                By.xpath("//span[@id='vote-count-middle']"), i, contnetCards);
                                sumOfVotes += Wrappers.convertToNumericValue(res);
                        } catch (NoSuchElementException e) {
                                System.out.println("vote not present - " + e.getMessage());
                        }
                        System.out.println(sumOfVotes);
                        Thread.sleep(1000);
                }

                System.out.println("End TestCase04");
        }
        @Test
        public void testCase05(String searchWords) throws InterruptedException {
                System.out.println("Start TestCase05");
                Wrappers.sendKeys(driver, By.xpath("//input[@id = 'search']"), searchWords);
                Thread.sleep(1000);
                long tally = 0;
                int iter = 1;
                while (tally < 100000000 || iter > 5) {
                        String res = Wrappers.findElementAndPrint(driver,
                                        By.xpath("(//div[@class='style-scope ytd-video-renderer'])"), 0);
                        res = res.split(" ")[0];
                        System.out.println(res);
                        tally += Wrappers.convertToNumericValue(res);
                        Thread.sleep(1000);
                }
                Thread.sleep(1000);
                System.out.println("End TestCase05");
        }

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}