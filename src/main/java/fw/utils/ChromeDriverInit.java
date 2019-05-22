package fw.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class ChromeDriverInit {


    public static void chromeInit(String[] args) throws InterruptedException, IOException {
        // Telling the system where to find the Chrome driver
        System.setProperty(
                "webdriver.chrome.driver",
                "C:/CodeProjects/TrelloEdu/src/test/resources/drivers");

        WebDriver webDriver = new ChromeDriver();

    }


}
