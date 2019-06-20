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

}
