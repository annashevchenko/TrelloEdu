package fw.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Метод находит и нажимает ссылку Войти
     */
    //
    public void entry() {
        LOG.info("Находим и нажимаем ссылку Войти");
        WebElement entry = findByCss("a[href='/login']");
        entry.click();
    }

    public void authorization(String login, String pass) {
        LOG.info("Вводим логин: " + login);
        WebElement loginInput = findByCss("input[id = 'user']");
        loginInput.sendKeys(login);
        LOG.info("Вводим пароль: " + pass);
        WebElement passInput = findByCss("input[id = 'password']");
        passInput.sendKeys(pass);
        LOG.info("Выполняем вход");
        findByCss("input[id = 'login']").click();
    }

}
