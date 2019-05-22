package web;

import com.sun.org.glassfish.gmbal.Description;
import fw.utils.ChromeDriverInit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestWeb  {

    WebDriver driver;
    @BeforeTest()
    public void doBeforeTest() {
         driver = new ChromeDriverInit().chromeInit();

    }

//


    @Description("Пробуем вызвать хром драйвер")
    @Test(description = "Тестовый тест", suiteName = ""
            , groups = {"regression", "smoke", "mpgu"})
    public void TEST_1() {

        driver.get("http://www.google.com");
        driver.close();
        driver.quit();

        }



}
