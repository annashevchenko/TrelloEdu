package fw.pages;

import org.openqa.selenium.By;
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
         * Метод указывает выбронное фото и запоминает его атрибут стиль
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
        findByCss("a[class='board-tile'] div[title='" + textName + "']").click();
    }

    /**
     * Метод находит заголовок доски
     */
    public void findBoldByNameHeader(String textName) throws Exception {
        LOG.info("Проверяем, что успешно загрузилась страница доски");
        waitFor(By.xpath("*//span[text()='"+textName+"']"),2,10).click();
    }

    /**
     * Метод находит меню на доске
     */
    public void findBoldMenu() throws Exception {
        LOG.info("Находим меню на доске");
        waitFor(By.cssSelector("div[class='board-menu-container']"), 2, 10);
    }

    /**
     * Метод создает описание
     */
    public void createBoldDescripton(String text, String textDescription ) throws Exception {
        LOG.info("Находим в меню Добавьте описание для доски");
        findByXpath("*//a//div[contains(text(), '" +text +"')]").click();
        LOG.info("Находим поле для ввода описания");
        WebElement description = driver.findElement(By.cssSelector("div[class='editable'] [class='description-content js-desc-content']"));
        description.click();
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
}