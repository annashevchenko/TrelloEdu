package web;

import fw.pages.BasePage;
import fw.pages.BoldPage;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class TestWeb {

    private LoginPage loginPage;
    private BasePage basePage;
    private Properties runProperties;
    private BoldPage boldPage;
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
        boldPage = new BoldPage(driver);
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

    @Test(description = "Открываем ресурс, выполняем авторизацию и проверяем приветственное сообщение на домашней странице"
            , groups = {"regression", "smoke"})
    public void test_1() throws Exception {
        authorization();
        basePage.openHomeLink();
        basePage.selectTextHello(runProperties.getProperty("helloSmall"));
        basePage.selectTextHello(runProperties.getProperty("helloBig"));

    }


    @Test(description = "Создание новой доски"
            , groups = {"regression", "smoke"})
    public void test_2() throws Exception {
        authorization();
        basePage.selectCreateNewBold();
        boldPage.selectFormNewBold();
        String boldName = runProperties.getProperty("boldName")+ new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.inputHeadlineNewBold(boldName);
        boldPage.selectTypeNewBold("Приватная");
        boldPage.selectChapterNewBold();
        boldPage.selectLinkMorePhotoNewBold();
        String findPhoto = boldPage.findStylePhotosNewBold(boldPage.findRandListPhotosNewBold());
        boldPage.selectCloseFormPhotoNewBold();
        String findChapter = boldPage.checkChapterNewBold();
        assertThat("Тема новой доски верна", findPhoto, equalTo(findChapter));
        boldPage.selectButtonCreateNewBold();
        boldPage.selectCheckCreateNewBold(boldName);
    }



    @Test(description = "Просмотр созданной доски"
            , groups = {"regression", "smoke"})
    public void test_3() throws Exception {
        //тут должен быть запрос в БД, чтобы определить наличие созданных досок у пользователя
        //проверяем, если доска найдена открываем ее, если нет, создаем сначала новую доску и выполняем просмотр
       // List<Map<String, String>> groupCulture = databaseCaller.executeQuery("select colom from table where");
       // String count = groupCulture.get(0).get("count");
        authorization();
        basePage.selectCreateNewBold();
        boldPage.selectFormNewBold();
        String boldName = runProperties.getProperty("boldName")+ new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.inputHeadlineNewBold(boldName);
        boldPage.selectTypeNewBold("Приватная");
        boldPage.selectChapterNewBold();
        boldPage.selectLinkMorePhotoNewBold();
        String findPhoto = boldPage.findStylePhotosNewBold(boldPage.findRandListPhotosNewBold());
        boldPage.selectCloseFormPhotoNewBold();
        String findChapter = boldPage.checkChapterNewBold();
        assertThat("Тема новой доски верна", findPhoto, equalTo(findChapter));
        boldPage.selectButtonCreateNewBold();
        boldPage.selectCheckCreateNewBold(boldName);
    }





// общие методы для тестов
//*********************************************************************************************************************
    //выполняем авторизацию
    public void authorization() {
        basePage.openPage(runProperties.getProperty("url"));
        loginPage.entry();
        loginPage.authorization(runProperties.getProperty("login"), runProperties.getProperty("password"));
    }
}
