package fw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class BoldPage extends Page {

    public BoldPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Метод  проверяет контерйнер с формой для создания новой доски
     */
    // "Открываем страницу:
    public void selectFormNewBold() {
        LOG.info("Проверяем форму для создания новой доски");
        By.cssSelector("div[class='form-container']");
    }

    /**
     * Метод добавляет заголовок на новой доске
     */
    // "Открываем страницу:
    public void inputHeadlineNewBold(String textHeadline) {
        LOG.info("Находим поле для ввода заголовка на новой доске");
        WebElement headline = driver.findElement(By.cssSelector("input[placeholder='Добавить заголовок доски']"));
        headline.click();
        LOG.info("Вводим в найденное поле следующий заголовок: " + textHeadline);
        headline.sendKeys(textHeadline);
    }


    /**
     * Метод выбирает тип новой доски из списка
     */
    // "Открываем страницу:
    public void selectTypeNewBold(String textTypeBold) {
        LOG.info("Находим список типа новой доски и выбираем из списка: " + textTypeBold);
        findByXpath("*//button[text()='" + textTypeBold + "']").click();
    }


    /**
     * Метод выбирает тему новой доски из списка
     */
    public void selectChapterNewBold() {
        LOG.info("Находим список тем новой доски ");
        findByCss("ul li button span[class='icon-sm icon-overflow-menu-horizontal']").click();
        LOG.info("Проверяет, что на экране появилась форма выбора фона доски");
        findByCss("div[class='pop-over is-shown']");
    }



        /**
         * Метод находит ссылку Подробнее у Фотографии и нажмаем на нее
         */
        public void selectLinkMorePhotoNewBold() {
            LOG.info("Находим ссылку Подробнее у Фотографии и нажмаем на нее");
            List<WebElement> linksMore = driver.findElements(By.xpath("*//span[text()='Подробнее']"));
            linksMore.get(0).click();
            findByCss("div[class='pop-over is-shown']");
        }


        /**
         * Метод находит список Фотографий и выбираем рандомное фото
         */
        public Integer findRandListPhotosNewBold() {
            LOG.info("Находим список Фотографий и выбираем одно рандомное фото");
            List<WebElement> photosList = driver.findElements(By.cssSelector("ul[class='background-grid'] li"));
            Random rand = new Random();
            int randElement = rand.nextInt(photosList.size());
            return randElement;
        }


    /**
     * Метод находит список всех Досок пользователя и сохраняет их в сортированный список
     */
    public List<String> findListBolds() {
        LOG.info("Находим список всех Досок пользователя:");
        List<WebElement> boldsList = driver.findElements(By.cssSelector("div[class='board-tile-details-name']"));
        LOG.info("Вытаскиваем атрибут с названием доски");
        List<String> boldsNames =  new ArrayList<>();
        for(WebElement element : boldsList){
           String boldName = element.getAttribute("title");
            LOG.info("находим атрибут с названием доски, и записываем название в список: " + boldName);
            boldsNames.add(boldName);
            }
        LOG.info("Сортируем полученный список");
            Collections.sort(boldsNames);
            return boldsNames;
    }



        /**
         * Метод указывает выбранное фото и запоминает его атрибут стиль
         */
        public String findStylePhotosNewBold(int numPhoto) {
            LOG.info("Находим атрибут style у выбранного фото и сохраняем значение этого поля");
            List<WebElement> photosList = driver.findElements(By.cssSelector("ul[class='background-grid'] li div[role='button']"));
            WebElement selectPhoto = photosList.get(numPhoto);
            String stylePhoto = selectPhoto.getAttribute("style");
            selectPhoto.click();
            return stylePhoto;
        }


    /**
     * Метод находит ссылку Подробнее у Фотографии и нажмаем на нее
     */
    public void selectCloseFormPhotoNewBold() {
        LOG.info("Закрываем форму после выбора фото");
        findByCss("a[class='pop-over-header-close-btn icon-sm icon-close']").click();
    }


    /**
     * Метод проверяет, что к новой доске применилась выбранная  тема
     */
   public String  checkChapterNewBold() {
        LOG.info("Закрываем форму после выбора фото");
        String chapterNewBold =findByCss("div[class='form-container'] div").getAttribute("style");
        return chapterNewBold;
    }



    /**
     * Метод нажимает кнопку Создать доску
     */
    // "Открываем страницу:
    public void selectButtonCreateNewBold() {
        LOG.info("Находим кнопку Создать доску и нажимаем ее: ");
        findByXpath("*//button//span[text()='Создать доску']").click();
    }

    /**
     * Метод  проверяет, что новая доска создалась
     */
    public void selectCheckCreateNewBold(String text) throws Exception {
        LOG.info("Находим на сранице наименование созданной доски");
        waitFor(By.xpath("*//span[text()='"+text+"']"),2,10).click();
    }



    /**
     * Метод находит Доску по имени
     */
    public void findBoldByName(String textName)  {
        LOG.info("Находим Доску  по имени, последнему успешно созданной  у пользователя");
        findByCss("div[title='" + textName + "']").click();
    }

    /**
     * Метод находит заголовок доски
     */
    public void findBoldByNameHeader(String textName) throws Exception {
        LOG.info("Проверяем, что успешно загрузилась страница доски");
        waitFor(By.xpath("*//span[text()='"+textName+"']"),2,10).click();
    }

    /**
     * Метод открывает меню на доске
     */
    public void selectLinkMenu() throws Exception {
        LOG.info("Находим ссылку меню на доске");
        waitFor(By.xpath("*//a//span[text()='Меню']"), 2, 10).click();
    }

    /**
     * Метод находит меню на доске
     */
    public void findBoldMenu() throws Exception {
        LOG.info("Находим меню на доске");
        waitFor(By.cssSelector("div[class='board-menu js-fill-board-menu']"), 2, 10);
    }



    /**
     * Метод находит ссылку на описание Доски
     */
    public void selectBoldDescripton() throws Exception {
        LOG.info("Находим в меню ссылку О Доске, Добавьте описание для доски");
        findByCss("a[class='board-menu-navigation-item-link js-about-this-board']").click();
    }


    /**
     * Метод создает описание
     */
    public void createBoldDescripton( String textDescription ) throws Exception {
        LOG.info("Находим в меню Добавьте описание для доски");
        findByCss("a[class='description-fake-text-area hide-on-edit js-edit-desc js-hide-with-draft large']").click();
        waitFor(By.cssSelector("div[class='editable editing']"), 2, 10);
        LOG.info("Находим поле для ввода описания");
        WebElement description = driver.findElement(By.cssSelector("textarea[class='field field-autosave js-description-draft description board-description']"));
       // description.click();
        LOG.info("Вводим текст описания");
        description.sendKeys(textDescription);
    }




    /**
     * Метод нажимает кнопку сохранить описание
     */
    public void selectSaveBoldDescripton() throws Exception {
        LOG.info("Находим меню на доске");
        findByCss("input[class='primary confirm mod-submit-edit js-save-edit']").click();
    }





    // МЕТОДЫ ДЛЯ РАБОТЫ С МЕНЮ НА ДОСКЕ
    //*******************************************************************************************************************

    /**
     * Метод закрывает меню
     */
    public void selectCloseMenuBold(String textOperation)  {
        LOG.info("Закрываем меню на доске");
        findByCss("a[title='"+textOperation+"']").click();
    }


    /**
     * Метод открывает меню на доске
     */
    public void selectLinksMenu(String text)  {
        LOG.info("Находим ссылку меню на доске");
        findByXpath("*//ul[@class='board-menu-navigation']//li//a[contains(text(), '"+text+"')]").click();
    }


    /**
     * Метод открывает меню на доске
     */
    public void selectTypeBackRoundBold(String textType) throws Exception {
        for ( int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим ссылку меню на доске " + textType);
            try {
                findByXpath("*//div[@class='title'][text()='"+textType+"']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось нажать выбранную ссылку: " + e.getCause());
                continue;
            }
            return;
        }
       throw new Exception("Не удалось нажать на ссылку: " + textType);
    }




    /**
     * Метод находит список Фотографий
     */
    public void findRandListPhotosMenuBold() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим список Фотографий");
            try {
                findByCss("div[class='board-backgrounds-photos']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти список элементов: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти список элементов");
    }


    /**
     * Метод находит список Цветов
     */
    public void findRandListColorsMenuBold() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим список Цветов");
            try {
                findByCss("div[class='board-menu-content-frame']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти список элементов: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти список элементов");
    }






     public void selectRandPhotoInListMenuBold() {
        LOG.info("Выбираем в списке Фотографий рандомное фото");
        List<WebElement> photosList = driver.findElements(By.cssSelector("div[class='board-background-select']"));
        Random rand = new Random();
        int randElement = rand.nextInt(photosList.size());
        WebElement selectPhoto = photosList.get(randElement);
        LOG.info("Устанавливаем выбранное фото на фон");
        selectPhoto.click();
    }




    public void selectRandColorInListMenuBold() {
        LOG.info("Выбираем в списке Цветов рандомный цвет");
        List<WebElement> photosList = driver.findElements(By.cssSelector("div[class='board-backgrounds-section-tile']"));
        Random rand = new Random();
        int randElement = rand.nextInt(photosList.size());
        WebElement selectPhoto = photosList.get(randElement);
        LOG.info("Устанавливаем выбранный цвет на фон");
        selectPhoto.click();
    }



    /**
     * Метод находит заголовок
     */
    public void selectAbouteBold(String text) {
        LOG.info("Находим в меню заголовок: " + text );
        By.xpath("*//h3[text()='" + text + "']");
    }

    /**
     * Метод находит заголовок
     */
    public void selectHeaderTwoBold(String text) {
        LOG.info("Находим в меню заголовок: " + text );
        By.xpath("*//h2[text()='" + text + "']");
    }


    /**
     * Метод находит сообщение
     */
    public void selectMessageInBold(String text) {
        LOG.info("Находим в меню сообщение: " + text );
        By.xpath("*//span[text()='" + text + "']");
    }

    /**
     * Метод находит текст
     */
    public void selectTextInBold(String text) {
        LOG.info("Находим в меню текст: " + text );
        By.xpath("*//a[text()='" + text + "']");
    }

    /**
     * Метод проверяет информацию об авторе: UserNameAll
     */
    public void selectFindAllUserName(String textAllUserName) {
        LOG.info("Находим  и проверяет информацию об авторе: UserNameAll ->" + textAllUserName );
        By.xpath("**//div[@class='mini-profile']//a[text()='"+textAllUserName+"']']");
    }


    /**
     * Метод проверяет информацию об авторе: UserName
     */
    public void selectFindUserName(String textUserName) {
        LOG.info("Находим  и проверяет информацию об авторе: UserName ->" + textUserName );
        By.xpath("**//div[@class='mini-profile']//p[text()='"+textUserName+"']']");
    }




    /**
     * Метод проверяет информацию об авторе: ссылка изменить профиль
     */
    public void selectFindlinkEditProfile(String text) {
        LOG.info("Находим  и проверяет информацию об авторе: ссылка-> " + text );
        By.xpath("**//div[@class='mini-profile']//a[text()='"+text+"']']");
    }



    /**
     * Метод находит кнопку Изменить описание о доске
     */
    public void selectFindButtomEditDescription(String text) {
        LOG.info("Находим кнопку Изменить описание о доске" );
        By.xpath("**//div[@class='window-module']//a[text()='"+text+"']']");
    }



    /**
     * Метод находит  описание о доске
     */
    public void selectFindBoldDescription(String text) {
        LOG.info("Находим  описание о доске" );
        By.xpath("**//div[@class='u-gutter']//p[text()='"+text+"']']");
    }



    // МЕТОДЫ ДЛЯ РАБОТЫ С СО СПИСКОМ
    // *******************************************************************************************************************


    /**
     * Метод создает новый список на доске
     */
    public void selectAddListInBold() {
        LOG.info("Находим элемент на доске для создания нового списка");
        findByCss("a[class='open-add-list js-open-add-list'] span[class='placeholder']").click();
        LOG.info("Проверяем, что поле для ввода наименования списка стало активным");
        findByCss("a[class='open-add-list js-open-add-list'][tabindex='-1']");
        }



    /**
     * Метод указывает название нового списка на доске
     */
    public void selectinputNameListInBold(String textName) {
        LOG.info("Вводим название нового списка");
        WebElement nameList = driver.findElement(By.cssSelector("input[class='list-name-input'][name='name']"));
        nameList.sendKeys(textName);
    }

    /**
     * Метод нажимает кнопку Сохранить Список
     */
    public void selectButtonSaveListInBold() {
        LOG.info("Находим и нажимаем кнопку 'Добавить список'");
        findByCss("input[class='primary mod-list-add-button js-save-edit'][value='Добавить список']").click();
    }


    /**
     * Метод находит созданный список на Доске
     */
    public void selectListInBoldByName(String textNameList) {
        LOG.info("Находим список на доске");
        findByXpath("*//h2[text()='"+textNameList+"']");
    }


}