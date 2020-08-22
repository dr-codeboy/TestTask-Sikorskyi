package pages;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchPage extends BasePage {

    // Тестовое задание №1
    @Test
    public void testDiscountDrillsPrices() throws InterruptedException {

        WebElement drill = getDriver().findElement(By.xpath("//a[text()='Дрели']"));
        drill.click();
        Thread.sleep(1000);
        int i = 1;
        // Переменная requiredNumberOfPagesTest1 находится в классе BasePage и по умолчанию равна 3
        // измените ее для проверки другого числа страниц
        while (i <= requiredNumberOfPagesTest1) {
            WebElement discountDrill = getDriver().findElement(By.xpath("(//span[contains(@class,'sticker_discount')]/../../preceding-sibling:: a[@href])[" + i + "]"));
            discountDrill.click();
            Thread.sleep(1000);
            WebElement oldPrice = getDriver().findElement(By.cssSelector("span.item_old_price.old-price"));
            assertThat(oldPrice.isDisplayed()).as("Old price of element #" + i + "has not been displayed").isTrue();
            WebElement newPrice = getDriver().findElement(By.xpath("(//span[@class='price'])[2]"));
            assertThat(newPrice.isDisplayed()).as("New price of element #" + i + " has not been displayed").isTrue();
            getDriver().navigate().back();
            Thread.sleep(1000);
            WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
            plus15ItemsKey.sendKeys(Keys.ARROW_UP);
            plus15ItemsKey.sendKeys(Keys.ARROW_UP);
            Thread.sleep(3000);
            plus15ItemsKey.click();
            Thread.sleep(2000);
            i++;
        }
    }

    // Тестовое здание №2
    @Test
    public void testPerforatorsPrices() throws InterruptedException {

        WebElement perforators = getDriver().findElement(By.xpath("//a[@title='Перфораторы']"));
        perforators.click();
        Thread.sleep(1000);
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        int j = 1;
        // Переменная requiredNumberOfPagesTest2 находится в классе BasePage и по умолчанию равна 2
        // измените ее для проверки другого числа страниц
        while (j <= requiredNumberOfPagesTest2) {
            plus15ItemsKey.click();
            Thread.sleep(1000);
            j++;
        }
        int i = 0;
        while (i < (requiredNumberOfPagesTest2 * 15)) {
            ++i;
            WebElement price = getDriver().findElement(By.xpath("(//span[@class='price'])[" + i + "]"));
            assertThat(price.isDisplayed()).as("Price on element#" + i + " has not been displayed").isTrue();
            System.out.println("price of the perforator #" + i + " = " + price.getText());
        }
    }

    // Тестовое задание №3
    @Test
    public void outputScrewdriversNames() throws InterruptedException {

        WebElement screwdriver = getDriver().findElement(By.xpath("//a[@title='Шуруповерты']"));
        screwdriver.click();
        Thread.sleep(1000);
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        // Переменная requiredNumberOfPagesTest3 находится в классе BasePage и по умолчанию равна 3
        // измените ее для проверки другого числа страниц
        int j = 1;
        while (j <= requiredNumberOfPagesTest3) {
            plus15ItemsKey.click();
            Thread.sleep(2000);
            j++;
        }
        List<WebElement> screwdriverList = getDriver().findElements(By.xpath("//img[contains(@src,'United_states')]/../../following:: h4[1]/a/span"));
        for (WebElement we : screwdriverList) {
            System.out.println(we.getText());
        }
    }

    //     Тестовое задание №4
    @Test
    public void newPriceCalculation() throws InterruptedException {
        WebElement circularSaws = getDriver().findElement(By.xpath("//a[@title='Болгарки']"));
        circularSaws.click();
        Thread.sleep(1000);
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        int j = 1;
        // Переменная requiredNumberOfPagesTest4 находится в классе BasePage и по умолчанию равна 2
        // измените ее для проверки другого числа страниц
        while (j <= requiredNumberOfPagesTest4) {
            plus15ItemsKey.click();
            Thread.sleep(2000);
            j++;
        }
        List<WebElement> sawsList = getDriver().findElements(By.xpath("//span[contains(@class,'sticker_discount')]"));
        List<Double> pureDiscountPercentList = new ArrayList<>();
        for (WebElement we : sawsList) {
            String s = (we.getText()).replace(" %", "").replace("-", "");
            double i = Double.parseDouble(s);
            pureDiscountPercentList.add(i / 100);
        }

        List<WebElement> sawsOldPriceList = getDriver().findElements(By.xpath("//span[contains(@class,'sticker_discount')]/../../following:: span[@class='old-price']"));
        List<Double> pureOldPriceList = new ArrayList<>();
        for (WebElement we : sawsOldPriceList) {
            String s = (we.getText()).replace("грн.", "").replace(" ", "");
            double i = Double.parseDouble(s);
            pureOldPriceList.add(i);
        }
        List<Integer> expectedPriceList = new ArrayList<>();
        for (int i = 0; i < pureDiscountPercentList.size(); i++) {
            expectedPriceList.add((int) (pureOldPriceList.get(i) - (pureOldPriceList.get(i) * pureDiscountPercentList.get(i))));
        }
        List<WebElement> sawsNewPriceList = getDriver().findElements(By.xpath("//span[@class='old-price']/following-sibling:: span[@class='price']"));
        List<Integer> actualNewPriceList = new ArrayList<>();
        for (WebElement we : sawsNewPriceList) {
            String s = (we.getText()).replace("грн.", "").replace(" ", "");
            int i = Integer.parseInt(s);
            actualNewPriceList.add(i);
        }
        List<WebElement> nameList = getDriver().findElements(By.xpath("//span[contains(@class,'sticker_discount')]/../../preceding-sibling:: a[@title]"));
        List<String> nameListInString = new ArrayList<>();
        for (WebElement we : nameList) {
            nameListInString.add(we.getAttribute("title"));
        }
        for (int i = 0; i < expectedPriceList.size(); i++) {
            assertThat(actualNewPriceList.get(i)).as("В товаре с названием: " + nameListInString.get(i) + " обнаружено расхождение фактической цены которая равна: " + actualNewPriceList.get(i) + " и ожидаемой цены которая равна: " + expectedPriceList.get(i)).isEqualTo(expectedPriceList.get(i));
        }


    }
}



