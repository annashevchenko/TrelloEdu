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
import static org.testng.AssertJUnit.assertFalse;


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
        String name = runProperties.getProperty("testBoldName01");
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
        String name = runProperties.getProperty("testBoldName01");
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
        String name = runProperties.getProperty("testBoldName01");
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
        String name = runProperties.getProperty("testBoldName02");
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
        String name = runProperties.getProperty("testBoldName02");
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
        String name = runProperties.getProperty("testBoldName02");
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



    @Test(description = "Создание новой карточки в списке"
            , groups = {"regression", "smoke"})
    public void test_11() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }

        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        boldPage.selectfindCardInListBold(cardName);
        //проверяем создание карточки в базе
    }


    @Test(description = "Удаление карточки в списке"
            , groups = {"regression", "smoke"})
    public void test_12() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();
        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
       // boldPage.selectButtonCloseFormCard();
        boldPage.selectIsCardInListPresent(cardName);
    }


    @Test(description = "Создание описание карточки и добавление комментария в карточку"
            , groups = {"regression", "smoke"})
    public void test_13() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();
        String textDescription = runProperties.getProperty("cardDescription")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddDescriptionCard(textDescription);
        boldPage.selectButtonSaveDescriptionCard();
        String textComment = runProperties.getProperty("cardComment")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCommentCard(textComment);
        boldPage.selectButtonSaveCommentCard();
        // сравниваем описание
         assertThat("описание карточки верно", textDescription, equalTo(boldPage.selectCheckDescriptionCard()));
        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }




    @Test(description = "Добавление Метки в карточку"
            , groups = {"regression", "smoke"})
    public void test_14() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();
        boldPage.selectAddLabelInCard();
        boldPage.findLabelByColor("orange");
        boldPage.findLabelByColor("green");
        boldPage.selectButtonCloseLabel();
        boldPage.selectCheckLabelCard();
        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }



    @Test(description = "Добавление Участников в карточку"
            , groups = {"regression", "smoke"})
    public void test_15() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();
        boldPage.selectAddMemberInCard();
        boldPage.selectInputNameMemberCard(runProperties.getProperty("nameMemberCard"));
        boldPage.selectButtonPutMemberCard();
        boldPage.selectCheckMemberCard();
        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }


    @Test(description = "Добавление Вложение в карточку(с компьютера)"
            , groups = {"regression", "smoke"})
    public void test_16() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();


        boldPage.selectAddAttachmentInCard();
        boldPage.selectInputAttachmentForCard("0_19d97_5ba96e3f_orig.jpeg");


        boldPage.selectCheckAttachmentInCard();

        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }



    @Test(description = "Добавление Чек-Листа в карточку"
            , groups = {"regression", "smoke"})
    public void test_17() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();
        boldPage.selectAddCheckListnCard();
        String checkListName = runProperties.getProperty("testNameCheckList")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectInputNameCheckListCard(checkListName);
        boldPage.selectButtonCreateCheckListCard();

        String elementNameCheckList = runProperties.getProperty("testNameElementCheckList")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddElementInCheckListCard(elementNameCheckList);

        boldPage.selectFormEmojiElementCheckListCard();
        boldPage.selectAddEmojiElementCheckListCard("thumbsup");
        boldPage.selectFormMentionElementCheckListCard();
        boldPage.selectAddMentionElementCheckListCard("trelloTest (usertest534)");
        boldPage.selectButtonAddElementInCheckListCard();
        boldPage.selectCheckBoxElementCheckListCard();

        boldPage.selectCheckElementListCard(elementNameCheckList + " ");
        boldPage.selectCheckProgressBarListCard();

        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }



    @Test(description = "Добавление Срока в карточку"
            , groups = {"regression", "smoke"})
    public void test_18() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();

        boldPage.selectAddTimeInCard();
        boldPage.selectAddDateInTimeCard(10);
        boldPage.selectAddTimeInTimeCard("11:00");
        boldPage.selectAddCustomReminderCard("60");
        boldPage.selectButtonSaveTimeToCard();

        boldPage.selectCheckBoxTimeInCard();

        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
    }




    @Test(description = "Перемещение карточки в список другой доски"
            , groups = {"regression", "smoke"})
    public void test_19() throws Exception {
        // тут д.б. запрос в БД на последнюю успешно созданную доску у пользователя и последний успешно созданный список на доске
        //List<Map<String, String>> boldsName = databaseCaller.executeQuery("SELECT name from tableNameBords where condition order by datecreate desc");
        //String name = boldsName.get(0).get("name");
        //String userName = boldsName.get(0).get("name");
        String name = runProperties.getProperty("testBoldName02");
        String nameList = runProperties.getProperty("testListName02");
        authorization();
        basePage.selectSection("Доски");
        boldPage.findBoldByName(name);
        boldPage.findBoldByNameHeader(name);
        boldPage.selectListInBoldByName(nameList);
        int index = boldPage.findIndexListByName(nameList);
        //выполняем проверку наличия других карточек у списка, в зависимости от этого выбираем нужный метод
        if (boldPage.selectCountCardInListBold() == 0) {
            boldPage.selectAddCardInListBold(index);
        }
        else {
            boldPage.selectAddAnotherCardInListBold(index);
        }
        String cardName = runProperties.getProperty("nameNewCard")+ " " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        boldPage.selectAddCardInListBold(cardName);
        boldPage.selectButtonSaveCardInListBold();
        //проверяем создание карточки в базе
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();

        boldPage.selectMoveCard();
        boldPage.selectBoldByMoveCard(runProperties.getProperty("testBoldName03"));
        boldPage.selectListByMoveCard(runProperties.getProperty("testListName03"));

        boldPage.selectButtonMoveCard();
        boldPage.selectIsCardInListPresent(cardName);

        basePage.openPage(runProperties.getProperty("url")+ runProperties.getProperty("urlBold03"));
        boldPage.selectCardInListByName(cardName);
        boldPage.selectCardForm();

        boldPage.selectButtonArchiveCard();
        boldPage.selectButtonDeleteCardOnForm();
        boldPage.selectConfirmationDeleteCardOnForm();
        boldPage.selectIsCardInListPresent(cardName);
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
