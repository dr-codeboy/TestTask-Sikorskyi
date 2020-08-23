package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    static WebDriver driver;
    public static int requiredNumberOfProductsTest1 = 3;
    public static int requiredNumberOfPagesTest2 = 2;
    public static int requiredNumberOfPagesTest3 = 3;
    public static int requiredNumberOfPagesTest4 = 2;



    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://kulibin.com.ua/");
        driver.manage().window().maximize();

    }
    @BeforeMethod
    public void openElectroinstrumentSection() {
        WebElement menu = driver.findElement(By.id("catalog-menu"));
        menu.click();
        // путь на электроинструменты за 2 дня 2 раза поменялся
        try {
            WebElement electroinstrument = driver.findElement(By.xpath("//*[text()='Электроинструмент']"));
            electroinstrument.click();
        }
        catch (NoSuchElementException e){
            System.out.println("Путь в 'Электроинструмент' был изменен, если его проправить программа заработает :D");
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @AfterMethod
    public void goBack() {
        driver.get("https://kulibin.com.ua/");
    }
}
