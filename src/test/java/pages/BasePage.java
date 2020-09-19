package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    WebDriver driver;
    int requiredNumberOfProductsTest1 = 3;
    int requiredNumberOfPagesTest2 = 5;
    int requiredNumberOfPagesTest3 = 3;
    int requiredNumberOfPagesTest4 = 2;
    final String MAIN_URL = "https://kulibin.com.ua/";
    final By PLUS_15_KEY = (By.cssSelector("a.btn-blue.show-more-link"));

    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(MAIN_URL);
        driver.manage().window().maximize();
    }
    @BeforeMethod
    void openElectroinstrumentSection() {
        WebElement menu = driver.findElement(By.id("catalog-menu"));
        menu.click();
        // путь на электроинструменты за 2 дня 2 раза поменялся
        try {
            WebElement electroinstrument = driver.findElement(By.xpath("//a[contains(@href,'/elektroinstrument')]"));
            electroinstrument.click();
        }
        catch (NoSuchElementException e){
            System.out.println("Путь в 'Электроинструмент' был изменен, если его проправить программа заработает :D");
        }
    }

    @AfterMethod
    void goBack() {
        driver.get(MAIN_URL);
    }

    @AfterSuite
    void tearDown() {
        driver.quit();
    }

}
