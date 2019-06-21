package web;

import com.sun.org.glassfish.gmbal.Description;
import fw.pages.BasePage;
import fw.pages.LoginPage;
import fw.utils.RunResourcesByTest;
import fw.utils.ChromeDriverInit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Properties;

public class TestWeb  {

    private LoginPage loginPage;
    private BasePage basePage;
 //   private Properties getProperties;
    private Properties runProperties;
    WebDriver driver;


// выполняем действия перед тестом
    @BeforeTest()
    public void doBeforeTest() throws IOException {
        // инициализируем webdariver
        driver = new ChromeDriverInit().chromeInit();
        // раскрываем окно браузера на весь экран
        driver.manage().window().maximize();
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        runProperties = new RunResourcesByTest().getProperties();
    }

// выполняем действия после теста
    @AfterTest(alwaysRun = true)
    //закрываем браузер если тест закончился или упал
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Description("Пробуем открыть ресурс и выполнить авторизацию")
    @Test(description = "Тестовый тест", suiteName = ""
            , groups = {"regression"})
    public void TEST_1() throws Exception {
        basePage.openPage(runProperties.getProperty("url"));
        loginPage.entry();
        loginPage.authorization(runProperties.getProperty("login"), runProperties.getProperty("password"));
        basePage.openHomeLink();
        basePage.selectTextHello(runProperties.getProperty("helloSmall"));
        basePage.selectTextHello(runProperties.getProperty("helloBig"));
        driver.close();
        driver.quit();
        }
}
