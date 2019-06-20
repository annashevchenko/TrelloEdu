package fw.pages;

import com.google.common.base.Function;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Page {
  //инициализация логера
    protected  Logger LOG = Logger.getLogger(Page.class);
    public HashMap<String, String> tabs = new HashMap();

    private boolean acceptNextAlert = true;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int timeOut;



    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10L);
    }

    protected WebElement findByCss(String cssSelector) {
        return this.driver.findElement(By.cssSelector(cssSelector));
    }

    protected WebElement findByCss(String cssSelector, WebElement parent) {
        return parent.findElement(By.cssSelector(cssSelector));
    }

    protected WebElement findByXpath(String xPathSelector) {
        return this.driver.findElement(By.xpath(xPathSelector));
    }

    protected List<WebElement> findListByCss(String cssSelector) {
        return this.driver.findElements(By.cssSelector(cssSelector));
    }

    protected List<WebElement> findListByXpath(String xPathSelector) {
        return this.driver.findElements(By.xpath(xPathSelector));
    }

    protected Select selectorSelectByVisibleText(WebElement select, String visibleText) {
        Select select1 = new Select(select);
        select1.selectByVisibleText(visibleText);
        return select1;
    }

    protected void clickByVisibleText(String visibleText) {
        this.driver.findElement(By.xpath("*//a[text()='" + visibleText + "']")).click();
    }

    protected WebElement findWebElementByVisibleText(String visibleText) {
        return this.driver.findElement(By.xpath(".//*[normalize-space(text()) = '" + visibleText + "']"));
    }

    protected WebElement findWebElementByVisibleText(WebElement webElement, String visibleText) {
        return webElement.findElement(By.xpath(".//*[normalize-space(text()) = '" + visibleText + "']"));
    }

    protected WebElement findElementsByVisibleContainText(String containsText) {
        return this.driver.findElement(By.xpath(String.format(".//*[contains(text(),'%s')]", containsText)));
    }

    protected WebElement findElementsByVisibleContainText(WebElement webElement, String containsText) {
        return webElement.findElement(By.xpath(String.format(".//*[contains(text(),'%s')]", containsText)));
    }

    protected List<WebElement> findListWebElementsByVisibleText(String visibleText) {
        return this.driver.findElements(By.xpath(".//*[normalize-space(text()) = '" + visibleText + "']"));
    }

    protected void selectScrollHeight() {
        this.reporter("Прокручиваем скролл вниз страницы");
        JavascriptExecutor jse = (JavascriptExecutor)this.driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", new Object[0]);
    }

    protected void switchTo(String frame) {
        this.driver.switchTo().frame(frame);
    }

    protected void swithToDefault() {
        this.driver.switchTo().defaultContent();
    }

    protected void reporter(String message) {
        this.LOG.info(message);
        ReportDataCollector.append(String.format("<br>| %s |", message));
    }

    protected void openNewTab(String url) {
        this.tabs = new HashMap();
        ArrayList<String> windowsList = new ArrayList(this.driver.getWindowHandles());
        String lastWindow = (String)windowsList.get(windowsList.size() - 1);
        this.tabs.put(this.driver.getTitle(), lastWindow);
        String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='new tab';d.body.appendChild(a);a.click();a.parentNode.removeChild(a);";
        ((JavascriptExecutor)this.driver).executeScript(String.format(script, url), new Object[0]);
        windowsList = new ArrayList(this.driver.getWindowHandles());
        lastWindow = (String)windowsList.get(windowsList.size() - 1);
        this.LOG.info("Открываю новую вкладку с URL'ом ");
        this.tabs.put(this.driver.getTitle(), lastWindow);
    }

    protected void changeTabByTitle(String title) {
        this.driver.switchTo().window((String)this.tabs.get(title));
    }

    protected void changeTabByWindowsNumber(int number) {
        ArrayList<String> tabs = new ArrayList(this.driver.getWindowHandles());
        this.driver.switchTo().window((String)tabs.get(number));
    }

    protected boolean isElementPresent(By by) {
        try {
            this.driver.findElement(by);
            return true;
        } catch (NoSuchElementException var3) {
            return false;
        }
    }

    protected boolean isAlertPresent() {
        try {
            this.driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException var2) {
            return false;
        }
    }

    protected String closeAlertAndGetItsText() {
        String var3;
        try {
            Alert alert = this.driver.switchTo().alert();
            String alertText = alert.getText();
            if (this.acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }

            var3 = alertText;
        } finally {
            this.acceptNextAlert = true;
        }

        return var3;
    }

    protected void openPageWithTimeOut(By by, String url) throws InterruptedException {
        this.driver.get(url);
        int second = 0;

        while(true) {
            if (second >= 60) {
                AssertJUnit.fail("timeout");
            }

            try {
                if (this.driver.findElement(by).isDisplayed()) {
                    return;
                }
            } catch (Exception var5) {
                ;
            }

            Thread.sleep(1000L);
            ++second;
        }
    }

    protected WebElement findWebElementInListByText(List<WebElement> webElementList, String findingText) throws Exception {
        Iterator var3 = webElementList.iterator();

        WebElement webElement;
        do {
            if (!var3.hasNext()) {
                throw new Exception("Не найден элемент по тексту " + findingText);
            }

            webElement = (WebElement)var3.next();
        } while(!webElement.getText().equals(findingText));

        return webElement;
    }

    protected List<WebElement> waitForNotEmptyWebElementList(By by) throws Exception {
        return this.waitConditionWebElementList(by, 2, 10, 1);
    }

    protected List<WebElement> waitConditionWebElementList(By by, int timeOut, int step, int expectedWebElementCount) throws Exception {
        this.LOG.info("Проверяем, что в коллекции есть элементы");
        new ArrayList();

        for(int i = 0; i < step; ++i) {
            List<WebElement> webElementList = this.driver.findElements(by);
            if (webElementList.size() == expectedWebElementCount) {
                this.LOG.info(String.format("В коллекции:%d элементов, успех!", webElementList.size()));
                return webElementList;
            }

            this.LOG.debug(String.format("В коллекции:%d элементов, ждем", webElementList.size()));
            this.sleepTest(timeOut);
        }

        throw new Exception("Не дождались элементов коллекции");
    }

    protected List<WebElement> waitGreaterThenWebElementList(By by, int timeOut, int step, int greaterThenCount) throws Exception {
        this.LOG.info("Проверяем, что в коллекции есть элементы");
        new ArrayList();

        for(int i = 0; i < step; ++i) {
            List<WebElement> webElementList = this.driver.findElements(by);
            if (webElementList.size() > greaterThenCount) {
                this.LOG.info(String.format("В коллекции:%d элементов, успех!", webElementList.size()));
                return webElementList;
            }

            this.LOG.debug(String.format("В коллекции:%d элементов, ждем", webElementList.size()));
            this.sleepTest(timeOut);
        }

        throw new Exception("Не дождались элементов коллекции");
    }

    protected List<WebElement> waitLessThenWebElementList(By by, int timeOut, int step, int lessThencount) throws Exception {
        this.LOG.info("Проверяем, что в коллекции есть элементы");
        new ArrayList();

        for(int i = 0; i < step; ++i) {
            List<WebElement> webElementList = this.driver.findElements(by);
            if (webElementList.size() < lessThencount) {
                this.LOG.info(String.format("В коллекции:%d элементов, успех!", webElementList.size()));
                return webElementList;
            }

            this.LOG.debug(String.format("В коллекции:%d элементов, ждем", webElementList.size()));
            this.sleepTest(timeOut);
        }

        throw new Exception("Не дождались элементов коллекции");
    }

    protected WebElement waitFor(By by) throws Exception {
        return this.waitFor(by, 2, 10);
    }

    protected WebElement waitFor(By by, int sleepSecond, int step) throws Exception {
        this.LOG.info("Ищем элемент: " + by.toString());
        int i = 0;

        while(i < step) {
            try {
                WebElement element = this.driver.findElement(by);
                return element;
            } catch (Exception var7) {
                this.LOG.error(String.format("Элемент не доступен по причине: %s", var7.getCause()));
                this.sleepTest(sleepSecond);
                ++i;
            }
        }

        throw new Exception("Элемент не стал доступен в отведенное время");
    }

    protected void clickAfterWait(By by) throws Exception {
        this.clickAfterWait(by, 2, 10);
    }

    protected void clickAfterWait(By by, int sleepSecond, int step) throws Exception {
        this.LOG.info("Ищем элемент: " + by.toString());
        int i = 0;

        while(i < step) {
            try {
                WebElement element = this.driver.findElement(by);
                element.click();
                return;
            } catch (WebDriverException var7) {
                this.LOG.error(String.format("Элемент не доступен по причине: %s", var7.getCause()));
                this.sleepTest(sleepSecond);
                ++i;
            }
        }

        throw new Exception("Элемент не стал доступен в отведенное время");
    }

    protected void sleepTest(int second) {
        this.LOG.info("SleepTest: Приостанавливаем тест на " + second + " c.");

        try {
            Thread.sleep((long)(second * 1000));
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }

    protected boolean waitUntil(Function expectedCondition, int iterations) {
        int i = 0;

        while(i < iterations - 1) {
            try {
                this.wait.until(expectedCondition);
                return true;
            } catch (Exception var5) {
                ++i;
            }
        }

        this.wait.until(expectedCondition);
        return true;
    }

    protected void delay() {
        this.LOG.info("delay: Приостанавливаем тест на " + this.timeOut + " мс.");

        try {
            Thread.sleep((long)this.timeOut);
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }

    public void delay(int ms) {
        this.LOG.info("delay: Приостанавливаем тест на " + ms + " мc.");

        try {
            Thread.sleep((long)ms);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }
}
