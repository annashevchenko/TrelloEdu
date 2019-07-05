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
import java.util.*;

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



    @Test(description = "Просмотр всех имеющихся досок у пользователя"
            , groups = {"regression", "smoke"})
    public void test_3() throws Exception {
        //тут должен быть запрос в БД, чтобы определить наличие созданных досок у пользователя
        //проверяем, если доска найдена открываем ее, если нет, создаем сначала новую доску и выполняем просмотр
        //List<Map<String, String>> bolds = databaseCaller.executeQuery("select name from table where");
        //ArrayList<String> boldsFromDB = (databaseCaller.getColumnAsList(bolds, "name"));
        //Collections.sort(boldsFromDB);
        authorization();
        basePage.selectSection("Доски");
        boldPage.findListBolds();
        // сравниваем полученные списки из БД и на странице
       // assertThat("имена досок верны", boldsFromDB, equalTo(boldPage.findListBolds()));
    }


    @Test(description = "Открытие созданной доски"
            , groups = {"regression", "smoke"})
    public void test_4() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        String name = "TestNewBold26.06.2019 16:18";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
    }


//    @Test(description = "добавить описание на доске"
//            , groups = {"regression", "smoke"})
//    public void test_5() throws Exception {
//        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
//        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
//        //String name = boldsName.get(0).get("name");
//        String name = "TestNewBold26.06.2019 16:18";
//        authorization();
//        basePage.selectSection("Доски");
//        boldPage.findBoldByName(name);
//        boldPage.findBoldByNameHeader(name);
//        boldPage.selectLinkMenu();
//        boldPage.findBoldMenu();
//        boldPage.selectBoldDescripton();
//        boldPage.createBoldDescripton( "Это тестовая доска");
//        boldPage.selectSaveBoldDescripton();
//        boldPage.selectCloseMenuBold();
//    }



    @Test(description = "Проверяем информацию в меню о Доске"
            , groups = {"regression", "smoke"})
    public void test_6() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = "TestNewBold26.06.2019 16:18";
        String userNameAll = "trelloTest";
        String userName = "trelloTest";
        String descriptionBold = "Это тестовая доска";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectLinkMenu();
        boldPage.findBoldMenu();
        boldPage.selectBoldDescripton();
        boldPage.selectAbouteBold("О доске");
        boldPage.selectAbouteBold("Автор");
        boldPage.selectFindAllUserName(userNameAll);
        boldPage.selectFindUserName(userName);
        boldPage.selectFindlinkEditProfile("Изменить профиль");
        boldPage.selectAbouteBold("Описание");
        // если на доске есть описание, б.д. кнопка изменить
        if(descriptionBold != null && !descriptionBold.isEmpty()) {
            boldPage.selectFindButtomEditDescription("Изменить");
            boldPage.selectFindBoldDescription(descriptionBold);
        }
        else
        {
            boldPage.selectTextInBold(runProperties.getProperty("textDesctiption"));
        }
        boldPage.selectHeaderTwoBold("Участники могут…");
        boldPage.selectMessageInBold("Комментировать карточки");
        boldPage.selectMessageInBold("Изменить права доступа…");
        boldPage.selectCloseMenuBold("Закрыть меню доски.");
    }




    @Test(description = "Изменяем  фон Доски в меню (Фотографии)"
            , groups = {"regression", "smoke"})
    public void test_7() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = "TestNewBold26.06.2019 16:18";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectLinkMenu();
        boldPage.findBoldMenu();
        boldPage.selectLinksMenu("Сменить фон");
        boldPage.selectAbouteBold("Смена фона");
        boldPage.selectTypeBackRoundBold("Фотографии");
        boldPage.findRandListPhotosMenuBold();
        boldPage.selectRandPhotoInListMenuBold();
        boldPage.selectCloseMenuBold("Вернуться.");
    }



    @Test(description = "Изменяем  фон Доски в меню (Цвета)"
            , groups = {"regression", "smoke"})
    public void test_8() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = "TestNewBold04.07.2019 12:28";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectLinkMenu();
        boldPage.findBoldMenu();
        boldPage.selectLinksMenu("Сменить фон");
        boldPage.selectAbouteBold("Смена фона");
        boldPage.selectTypeBackRoundBold("Цвета");
        boldPage.findRandListColorsMenuBold();
        boldPage.selectRandColorInListMenuBold();
        boldPage.selectCloseMenuBold("Вернуться.");
    }

    @Test(description = "Изменяем  фон Доски в меню (Цвета)"
            , groups = {"regression", "smoke"})
    public void test_9() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = "TestNewBold04.07.2019 12:28";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectLinkMenu();
        boldPage.findBoldMenu();
        boldPage.selectLinksMenu("Сменить фон");
        boldPage.selectAbouteBold("Смена фона");
        boldPage.selectTypeBackRoundBold("Цвета");
        boldPage.findRandListColorsMenuBold();
        boldPage.selectRandColorInListMenuBold();
        boldPage.selectCloseMenuBold("Вернуться.");
    }

    @Test(description = "Создание нового списка на доске"
            , groups = {"regression", "smoke"})
    public void test_10() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = "TestNewBold04.07.2019 12:28";
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectAddListInBold();
        String listName = runProperties.getProperty("nameNewList")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectinputNameListInBold(listName);
        boldPage.selectButtonSaveListInBold();
        boldPage.selectListInBoldByName(listName);

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
