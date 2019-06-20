package web;

import com.sun.org.glassfish.gmbal.Description;
import fw.pages.BasePage;
import fw.pages.LoginPage;
import fw.utils.App;
import fw.utils.ChromeDriverInit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestWeb  {

    private LoginPage loginPage;
    private BasePage basePage;

    WebDriver driver;



    @BeforeTest()
    public void doBeforeTest() {
        driver = new ChromeDriverInit().chromeInit();
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
    }



    @Description("Пробуем вызвать хром драйвер")
    @Test(description = "Тестовый тест", suiteName = ""
            , groups = {"regression"})
    public void TEST_1() {
        basePage.openPage("trello.com");
        loginPage.entry();
        loginPage.authorization("trelloTestMy@yandex.ru", "Qwerty645");
        driver.close();
        driver.quit();

        }
}
