package fw.pages;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class CommonPage extends Page {


    public CommonPage(WebDriver driver) {
        super(driver);
    }


    public HashMap<String, String> tabs = new HashMap();

    /**
     * В зависимости от указанного стенда инициализирует тестовые данные
     */
}
