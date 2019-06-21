package web;

import com.sun.org.glassfish.gmbal.Description;
import fw.pages.BasePage;
import fw.pages.LoginPage;
import fw.utils.RunResourcesByTest;
import fw.utils.ChromeDriverInit;
import org.openqa.selenium.WebDriver;
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



    @BeforeTest()
    public void doBeforeTest() throws IOException {
        driver = new ChromeDriverInit().chromeInit();
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        runProperties = new RunResourcesByTest().getProperties();

    }





    @Description("Пробуем открыть ресурс и выполнить авторизацию")
    @Test(description = "Тестовый тест", suiteName = ""
            , groups = {"regression"})
    public void TEST_1() {
        basePage.openPage(runProperties.getProperty("url"));
        loginPage.entry();
        loginPage.authorization(runProperties.getProperty("login"), runProperties.getProperty("password"));
        driver.close();
        driver.quit();
        }
}
