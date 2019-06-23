package web;

import fw.pages.BasePage;
import fw.pages.LoginPage;
import fw.utils.ChromeDriverInit;
import fw.utils.RunResourcesByTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class TestWeb {

    private LoginPage loginPage;
    private BasePage basePage;
    private Properties runProperties;
    private WebDriver driver;

    @BeforeSuite
    public static void init() throws IOException {

        String filename;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filename = "chromedriver.exe";
        } else {
            filename = "chromedriver";
        }
        Path tempChromeDriverPath = Paths.get(filename);
        if (!Files.exists(tempChromeDriverPath)) {
            try (InputStream inputStream = TestWeb.class.getClassLoader().getResourceAsStream("drivers/" + filename)) {
                Files.copy(inputStream, tempChromeDriverPath);
            }
            tempChromeDriverPath.toFile().setExecutable(true);
        }
        System.setProperty("webdriver.chrome.driver", tempChromeDriverPath.toAbsolutePath().toString());
    }


    // выполняем действия перед тестом
    @BeforeTest()
    public void doBeforeTest() throws IOException {
        // инициализируем webdriver
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

    @Test(description = "Пробуем открыть ресурс и выполнить авторизацию")
    public void test() throws Exception {
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
