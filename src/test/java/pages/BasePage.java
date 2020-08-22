package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public abstract class BasePage {
    public static int requiredNumberOfPagesTest1 = 3; // тест выдаст ошибку при значении переменной больше 5(пяти), из за того что на первых двух страницах больше нету акционных товаров.
    public static int requiredNumberOfPagesTest2 = 1;
    public static int requiredNumberOfPagesTest3 = 3;
    public static int requiredNumberOfPagesTest4 = 2;
    private static WebDriver driver;
    public static WebDriver getDriver(){
        return driver;
    }

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://kulibin.com.ua/");
        driver.manage().window().maximize();

    }
    @BeforeMethod
    public void openElectroinstrumentSection() throws InterruptedException {
        WebDriver driver = getDriver();
        WebElement menu = driver.findElement(By.id("catalog-menu"));
        menu.click();
        // абсолютный путь на электроинструменты за 2 дня 2 раза поменялся в самом DOMe
        try {
            WebElement electroinstrument = driver.findElement(By.xpath("//*[text()='Электроинструмент']"));
            electroinstrument.click();
            Thread.sleep(1000);
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
