package fw.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromeDriverInit {

    public RemoteWebDriver chromeInit() {
        // Telling the system where to find the Chrome driver
//        System.setProperty(
//                "webdriver.chrome.driver",
//                "/home/phoenix/projects/TrelloEdu/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();

    }


//    pubilc class WebDriverFactory {
//
//        private static WebDriver webDriver;
//
//        public  static WebDriver getWebDriver() {
//            if (webDriver == null) {
//                webDriver = // здесь код для создания объекта WebDriver
//            }
//            return webDriver;
//        }
//    }

}
