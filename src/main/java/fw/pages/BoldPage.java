package fw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BoldPage extends Page {

    public BoldPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Метод  проверяет контерйнер с формой для создания новой доски
     */
    // "Открываем страницу:
    public void selectFormNewBold() {
        LOG.info("Проверяем форму для создания новой доски" );
        By.cssSelector("div[class='form-container']");
    }

    /**
     * Метод добавляет заголовок на новой доске
     */
    // "Открываем страницу:
    public void inputHeadlineNewBold(String textHeadline ) {
        LOG.info("Находим поле для ввода заголовка на новой доске" );
        WebElement  headline =  driver.findElement(By.cssSelector("input[placeholder='Добавить заголовок доски']"));
        headline.click();
        LOG.info("Вводим в найденное поле следующий заголовок: " + textHeadline);
        headline.sendKeys(textHeadline);
    }


    /**
     * Метод выбирает тип новой доски из списка
     */
    // "Открываем страницу:
    public void selectTypeNewBold(String textTypeBold ) {
        LOG.info("Находим список типа новой доски и выбираем из списка: " + textTypeBold);
        findByXpath("*//button[text()='"+textTypeBold+"']").click();
    }



    /**
     * Метод выбирает тему новой доски из списка
     */
    // "Открываем страницу:
    public void selectChapterNewBold( ) {
        LOG.info("Находим список тем новой доски ");
        findByCss("ul li button span[class='icon-sm icon-overflow-menu-horizontal']").click();
        LOG.info("Проверяет, что на экране появилась форма выбора фона доски");
        findByCss("div[class='pop-over is-shown']");
        LOG.info("Находим ссылку Подробнее");
        findByCss("ul[class='background-grid'] li");

    }
}
