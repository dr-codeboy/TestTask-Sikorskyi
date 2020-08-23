package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchPage extends BasePage {

    // Тестовое задание №1
    @Test
    public void testDiscountDrillsPrices() {
        WebDriverWait wait = new WebDriverWait(getDriver(),10);
        WebElement drill = getDriver().findElement(By.xpath("//a[text()='Дрели']"));
        drill.click();
        int i = 1;
        // Переменная requiredNumberOfProductsTest1 находится в классе BasePage и по умолчанию равна 3
        // измените ее для проверки другого количества товаров
        while (i <= requiredNumberOfProductsTest1) {
            WebElement discountDrill = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[contains(@class,'sticker_discount')]/../../preceding-sibling:: a)[" + i + "]")));
            discountDrill.click();
            WebElement oldPrice = getDriver().findElement(By.cssSelector("span.item_old_price.old-price"));
            assertThat(oldPrice.isDisplayed()).as("Old price of element #" + i + "has not been displayed").isTrue();
            WebElement newPrice = getDriver().findElement(By.xpath("(//span[@class='price'])[2]"));
            assertThat(newPrice.isDisplayed()).as("New price of element #" + i + " has not been displayed").isTrue();
            getDriver().navigate().back();
            WebElement plus15ItemsKey = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-blue.show-more-link")));
            plus15ItemsKey.sendKeys(Keys.ENTER);
            i++;
        }
    }

    // Тестовое здание №2
    @Test
    public void testPerforatorsPrices() {

        WebElement perforators = getDriver().findElement(By.xpath("//a[@title='Перфораторы']"));
        perforators.click();
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        int j = 1;
        // Переменная requiredNumberOfPagesTest2 находится в классе BasePage и по умолчанию равна 2
        // измените ее для проверки другого количества страниц
        while (j <= requiredNumberOfPagesTest2) {
            plus15ItemsKey.click();
            j++;
        }
        int i = 0;
        while (i < (requiredNumberOfPagesTest2 * 15)) {
            ++i;
            WebElement price = getDriver().findElement(By.xpath("(//span[@class='price'])[" + i + "]"));
            assertThat(price.isDisplayed()).as("Price of element#" + i + " has not been displayed").isTrue();
        }
    }

    // Тестовое задание №3
    @Test
    public void outputScrewdriversNames() {

        WebElement screwdriver = getDriver().findElement(By.xpath("//a[@title='Шуруповерты']"));
        screwdriver.click();
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        // Переменная requiredNumberOfPagesTest3 находится в классе BasePage и по умолчанию равна 3
        // измените ее для проверки другого количества страниц
        int j = 1;
        while (j <= requiredNumberOfPagesTest3) {
            plus15ItemsKey.click();
            j++;
        }
        List<WebElement> screwdriverList = getDriver().findElements(By.xpath("//img[contains(@src,'United_states')]/../../following:: h4[1]/a/span"));
        for (WebElement we : screwdriverList) {
            System.out.println(we.getText());
        }
    }

    //     Тестовое задание №4
    @Test
    public void newCircularSawPriceCalculation() {
        WebElement circularSaws = getDriver().findElement(By.xpath("//a[@title='Болгарки']"));
        circularSaws.click();
        WebElement plus15ItemsKey = getDriver().findElement(By.cssSelector("a.btn-blue.show-more-link"));
        int j = 1;
        // Переменная requiredNumberOfPagesTest4 находится в классе BasePage и по умолчанию равна 2
        // измените ее для проверки другого количества страниц
        while (j <= requiredNumberOfPagesTest4) {
            plus15ItemsKey.click();
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
        StringBuilder result = new StringBuilder();
        boolean isIncorrectPrice = true;
        for (int i = 0; i < expectedPriceList.size(); i++) {
            if (!actualNewPriceList.get(i).equals(expectedPriceList.get(i))) {
                isIncorrectPrice = false;
                result.append("В товаре с названием: ").append(nameListInString.get(i)).append(" обнаружено расхождение фактической цены которая равна: ")
                        .append(actualNewPriceList.get(i)).append(" и ожидаемой цены которая равна: ").append(expectedPriceList.get(i)).append("\n");
            }
        }
        assertThat(isIncorrectPrice).as(result.toString()).isTrue();
    }
}