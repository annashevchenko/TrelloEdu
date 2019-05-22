package fw.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class ChromeDriverInit {

    public ChromeDriver chromeInit(){
        // Telling the system where to find the Chrome driver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:/CodeProjects/TrelloEdu/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();

    }


}
