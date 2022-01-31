package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class SearchSteps {
    String placeName;
    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
    }

    @Given("Город поиска: {string}")
    public void городПоиска(String placeName) {

        this.placeName = placeName;
    }

    @When("Выполняется поиск отелей по городу")
    public void выполняетсяПоискОтелейПоГороду() {
        driver.get("https://www.booking.com/index.ru.html");
        driver.findElement(By.xpath("//input[@id='ss']")).sendKeys(placeName);
        driver.findElement(By.xpath("//span[contains(text(),'Проверить цены')]")).click();
    }

    @Then("Отель имеет название: {string} с рейтингом: {string}")
    public void ОтельИмеетНазвание(String placeName, String rating) {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-testid='title']")).getText(), placeName, "Расположение отеля, не совпадает с указанным городом");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-testid='review-score']/div[contains(text(),*)]")).getText(), rating, "Рейтинг отеля не совпадает с указанным");
    }

    @After
    public void tearDown() {
        System.out.println("teardown");
        driver.quit();
    }

}