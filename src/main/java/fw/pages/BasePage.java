package fw.pages;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class BasePage extends Page {





    public BasePage(WebDriver driver) {
        super(driver);
    }



    /**
     * Метод  открывает страницу по URL в браузере
     */
    // "Открываем страницу:
    public void openPage(String urlText) {
        LOG.info("Открываем страницу: " + urlText);
        String getHost = "https://";
        driver.get(getHost + urlText);
    }


    /**
     * Метод  находит ссылку Главная страница и нажимае ее
     */
    public void openHomeLink() throws Exception {
        LOG.info("Находим ссылку  Главная страница и нажимаем ее");
        waitFor(By.xpath("*//span[text()='Главная страница']"), 2, 10).click();
       // findByXpath("*//span[text()='Главная страница']").click();
    }


    /**
     * Метод  находит контейнер на главной странице и проверяет приветствие
     */
    public void selectCreateNewBold() throws Exception {
        LOG.info("Находим контейнер с приветствием");
        waitFor(By.cssSelector("p[class='bold']"),2,10).click();
    }



    /**
     * Метод  находит кнопку 'Создать новую доску' и нажимает на нее
     */
    public void selectTextHello(String textHello) throws Exception {
        LOG.info("Находим контейнер с приветствием");
        waitFor(By.xpath("*//span[text()='"+textHello+"']"),2,10).click();
    }


}
