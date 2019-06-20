package fw.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import sun.misc.IOUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;



public class CommonPage extends Page {


    public CommonPage(WebDriver driver) {
        super(driver);
    }


    public HashMap<String, String> tabs = new HashMap();

    /**
     * В зависимости от указанного стенда инициализирует тестовые данные
     */
//    public void initRunProperties() {
//        String myResource = IOUtils.toString(this.getClass().getResourceAsStream("yourfile.xml")).replace("\n","");
//    }

}
