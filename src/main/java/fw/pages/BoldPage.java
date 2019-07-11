package fw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class BoldPage extends Page {

    public BoldPage(WebDriver driver) {
        super(driver);
    }


    /**
     * Метод  проверяет контерйнер с формой для создания новой доски
     */
    // "Открываем страницу:
    public void selectFormNewBold() {
        LOG.info("Проверяем форму для создания новой доски");
        By.cssSelector("div[class='form-container']");
    }

    /**
     * Метод добавляет заголовок на новой доске
     */
    // "Открываем страницу:
    public void inputHeadlineNewBold(String textHeadline) {
        LOG.info("Находим поле для ввода заголовка на новой доске");
        WebElement headline = driver.findElement(By.cssSelector("input[placeholder='Добавить заголовок доски']"));
        headline.click();
        LOG.info("Вводим в найденное поле следующий заголовок: " + textHeadline);
        headline.sendKeys(textHeadline);
    }


    /**
     * Метод выбирает тип новой доски из списка
     */
    // "Открываем страницу:
    public void selectTypeNewBold(String textTypeBold) {
        LOG.info("Находим список типа новой доски и выбираем из списка: " + textTypeBold);
        findByXpath("*//button[text()='" + textTypeBold + "']").click();
    }


    /**
     * Метод выбирает тему новой доски из списка
     */
    public void selectChapterNewBold() {
        LOG.info("Находим список тем новой доски ");
        findByCss("ul li button span[class='icon-sm icon-overflow-menu-horizontal']").click();
        LOG.info("Проверяет, что на экране появилась форма выбора фона доски");
        findByCss("div[class='pop-over is-shown']");
    }



        /**
         * Метод находит ссылку Подробнее у Фотографии и нажмаем на нее
         */
        public void selectLinkMorePhotoNewBold() {
            LOG.info("Находим ссылку Подробнее у Фотографии и нажмаем на нее");
            List<WebElement> linksMore = driver.findElements(By.xpath("*//span[text()='Подробнее']"));
            linksMore.get(0).click();
            findByCss("div[class='pop-over is-shown']");
        }


        /**
         * Метод находит список Фотографий и выбираем рандомное фото
         */
        public Integer findRandListPhotosNewBold() {
            LOG.info("Находим список Фотографий и выбираем одно рандомное фото");
            List<WebElement> photosList = driver.findElements(By.cssSelector("ul[class='background-grid'] li"));
            Random rand = new Random();
            int randElement = rand.nextInt(photosList.size());
            return randElement;
        }


    /**
     * Метод находит список всех Досок пользователя и сохраняет их в сортированный список
     */
    public List<String> findListBolds() {
        LOG.info("Находим список всех Досок пользователя:");
        List<WebElement> boldsList = driver.findElements(By.cssSelector("div[class='board-tile-details-name']"));
        LOG.info("Вытаскиваем атрибут с названием доски");
        List<String> boldsNames =  new ArrayList<>();
        for(WebElement element : boldsList){
           String boldName = element.getAttribute("title");
            LOG.info("находим атрибут с названием доски, и записываем название в список: " + boldName);
            boldsNames.add(boldName);
            }
        LOG.info("Сортируем полученный список");
            Collections.sort(boldsNames);
            return boldsNames;
    }



        /**
         * Метод указывает выбранное фото и запоминает его атрибут стиль
         */
        public String findStylePhotosNewBold(int numPhoto) {
            LOG.info("Находим атрибут style у выбранного фото и сохраняем значение этого поля");
            List<WebElement> photosList = driver.findElements(By.cssSelector("ul[class='background-grid'] li div[role='button']"));
            WebElement selectPhoto = photosList.get(numPhoto);
            String stylePhoto = selectPhoto.getAttribute("style");
            selectPhoto.click();
            return stylePhoto;
        }


    /**
     * Метод находит ссылку Подробнее у Фотографии и нажмаем на нее
     */
    public void selectCloseFormPhotoNewBold() {
        LOG.info("Закрываем форму после выбора фото");
        findByCss("a[class='pop-over-header-close-btn icon-sm icon-close']").click();
    }


    /**
     * Метод проверяет, что к новой доске применилась выбранная  тема
     */
   public String  checkChapterNewBold() {
        LOG.info("Закрываем форму после выбора фото");
        String chapterNewBold =findByCss("div[class='form-container'] div").getAttribute("style");
        return chapterNewBold;
    }



    /**
     * Метод нажимает кнопку Создать доску
     */
    // "Открываем страницу:
    public void selectButtonCreateNewBold() {
        LOG.info("Находим кнопку Создать доску и нажимаем ее: ");
        findByXpath("*//button//span[text()='Создать доску']").click();
    }

    /**
     * Метод  проверяет, что новая доска создалась
     */
    public void selectCheckCreateNewBold(String text) throws Exception {
        LOG.info("Находим на сранице наименование созданной доски");
        waitFor(By.xpath("*//span[text()='"+text+"']"),2,10).click();
    }



    /**
     * Метод находит Доску по имени
     */
    public void findBoldByName(String textName)  {
        LOG.info("Находим Доску  по имени, последнему успешно созданной  у пользователя");
        findByCss("div[title='" + textName + "']").click();
    }

    /**
     * Метод находит заголовок доски
     */
    public void findBoldByNameHeader(String textName) throws Exception {
        LOG.info("Проверяем, что успешно загрузилась страница доски");
        waitFor(By.xpath("*//span[text()='"+textName+"']"),2,10).click();
    }

    /**
     * Метод открывает меню на доске
     */
    public void selectLinkMenu() throws Exception {
        LOG.info("Находим ссылку меню на доске");
        waitFor(By.xpath("*//a//span[text()='Меню']"), 2, 10).click();
    }

    /**
     * Метод находит меню на доске
     */
    public void findBoldMenu() throws Exception {
        LOG.info("Находим меню на доске");
        waitFor(By.cssSelector("div[class='board-menu js-fill-board-menu']"), 2, 10);
    }



    /**
     * Метод находит ссылку на описание Доски
     */
    public void selectBoldDescripton() throws Exception {
        LOG.info("Находим в меню ссылку О Доске, Добавьте описание для доски");
        findByCss("a[class='board-menu-navigation-item-link js-about-this-board']").click();
    }


    /**
     * Метод создает описание
     */
    public void createBoldDescripton( String textDescription ) throws Exception {
        LOG.info("Находим в меню Добавьте описание для доски");
        findByCss("a[class='description-fake-text-area hide-on-edit js-edit-desc js-hide-with-draft large']").click();
        waitFor(By.cssSelector("div[class='editable editing']"), 2, 10);
        LOG.info("Находим поле для ввода описания");
        WebElement description = driver.findElement(By.cssSelector("textarea[class='field field-autosave js-description-draft description board-description']"));
       // description.click();
        LOG.info("Вводим текст описания");
        description.sendKeys(textDescription);
    }




    /**
     * Метод нажимает кнопку сохранить описание
     */
    public void selectSaveBoldDescripton() throws Exception {
        LOG.info("Находим меню на доске");
        findByCss("input[class='primary confirm mod-submit-edit js-save-edit']").click();
    }





    // МЕТОДЫ ДЛЯ РАБОТЫ С МЕНЮ НА ДОСКЕ
    //*******************************************************************************************************************

    /**
     * Метод закрывает меню
     */
    public void selectCloseMenuBold(String textOperation)  {
        LOG.info("Закрываем меню на доске");
        findByCss("a[title='"+textOperation+"']").click();
    }


    /**
     * Метод открывает меню на доске
     */
    public void selectLinksMenu(String text)  {
        LOG.info("Находим ссылку меню на доске");
        findByXpath("*//ul[@class='board-menu-navigation']//li//a[contains(text(), '"+text+"')]").click();
    }


    /**
     * Метод открывает меню на доске
     */
    public void selectTypeBackRoundBold(String textType) throws Exception {
        for ( int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим ссылку меню на доске " + textType);
            try {
                findByXpath("*//div[@class='title'][text()='"+textType+"']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось нажать выбранную ссылку: " + e.getCause());
                continue;
            }
            return;
        }
       throw new Exception("Не удалось нажать на ссылку: " + textType);
    }




    /**
     * Метод находит список Фотографий
     */
    public void findRandListPhotosMenuBold() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим список Фотографий");
            try {
                findByCss("div[class='board-backgrounds-photos']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти список элементов: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти список элементов");
    }


    /**
     * Метод находит список Цветов
     */
    public void findRandListColorsMenuBold() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим список Цветов");
            try {
                findByCss("div[class='board-menu-content-frame']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти список элементов: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти список элементов");
    }






     public void selectRandPhotoInListMenuBold() {
        LOG.info("Выбираем в списке Фотографий рандомное фото");
        List<WebElement> photosList = driver.findElements(By.cssSelector("div[class='board-background-select']"));
        Random rand = new Random();
        int randElement = rand.nextInt(photosList.size());
        WebElement selectPhoto = photosList.get(randElement);
        LOG.info("Устанавливаем выбранное фото на фон");
        selectPhoto.click();
    }




    public void selectRandColorInListMenuBold() {
        LOG.info("Выбираем в списке Цветов рандомный цвет");
        List<WebElement> photosList = driver.findElements(By.cssSelector("div[class='board-backgrounds-section-tile']"));
        Random rand = new Random();
        int randElement = rand.nextInt(photosList.size());
        WebElement selectPhoto = photosList.get(randElement);
        LOG.info("Устанавливаем выбранный цвет на фон");
        selectPhoto.click();
    }



    /**
     * Метод находит заголовок
     */
    public void selectAbouteBold(String text) {
        LOG.info("Находим в меню заголовок: " + text );
        By.xpath("*//h3[text()='" + text + "']");
    }

    /**
     * Метод находит заголовок
     */
    public void selectHeaderTwoBold(String text) {
        LOG.info("Находим в меню заголовок: " + text );
        By.xpath("*//h2[text()='" + text + "']");
    }


    /**
     * Метод находит сообщение
     */
    public void selectMessageInBold(String text) {
        LOG.info("Находим в меню сообщение: " + text );
        By.xpath("*//span[text()='" + text + "']");
    }

    /**
     * Метод находит текст
     */
    public void selectTextInBold(String text) {
        LOG.info("Находим в меню текст: " + text );
        By.xpath("*//a[text()='" + text + "']");
    }

    /**
     * Метод проверяет информацию об авторе: UserNameAll
     */
    public void selectFindAllUserName(String textAllUserName) {
        LOG.info("Находим  и проверяет информацию об авторе: UserNameAll ->" + textAllUserName );
        By.xpath("**//div[@class='mini-profile']//a[text()='"+textAllUserName+"']']");
    }


    /**
     * Метод проверяет информацию об авторе: UserName
     */
    public void selectFindUserName(String textUserName) {
        LOG.info("Находим  и проверяет информацию об авторе: UserName ->" + textUserName );
        By.xpath("**//div[@class='mini-profile']//p[text()='"+textUserName+"']']");
    }




    /**
     * Метод проверяет информацию об авторе: ссылка изменить профиль
     */
    public void selectFindlinkEditProfile(String text) {
        LOG.info("Находим  и проверяет информацию об авторе: ссылка-> " + text );
        By.xpath("**//div[@class='mini-profile']//a[text()='"+text+"']']");
    }



    /**
     * Метод находит кнопку Изменить описание о доске
     */
    public void selectFindButtomEditDescription(String text) {
        LOG.info("Находим кнопку Изменить описание о доске" );
        By.xpath("**//div[@class='window-module']//a[text()='"+text+"']']");
    }



    /**
     * Метод находит  описание о доске
     */
    public void selectFindBoldDescription(String text) {
        LOG.info("Находим  описание о доске" );
        By.xpath("**//div[@class='u-gutter']//p[text()='"+text+"']']");
    }



    // МЕТОДЫ ДЛЯ РАБОТЫ С СО СПИСКОМ
    // *******************************************************************************************************************


    /**
     * Метод создает новый список на доске
     */
    public void selectAddListInBold() {
        LOG.info("Находим элемент на доске для создания нового списка");
        findByCss("a[class='open-add-list js-open-add-list'] span[class='placeholder']").click();
        LOG.info("Проверяем, что поле для ввода наименования списка стало активным");
        findByCss("a[class='open-add-list js-open-add-list'][tabindex='-1']");
        }



    /**
     * Метод указывает название нового списка на доске
     */
    public void selectinputNameListInBold(String textName) {
        LOG.info("Вводим название нового списка");
        WebElement nameList = driver.findElement(By.cssSelector("input[class='list-name-input'][name='name']"));
        nameList.sendKeys(textName);
    }

    /**
     * Метод нажимает кнопку Сохранить Список
     */
    public void selectButtonSaveListInBold() {
        LOG.info("Находим и нажимаем кнопку 'Добавить список'");
        findByCss("input[class='primary mod-list-add-button js-save-edit'][value='Добавить список']").click();
    }


    /**
     * Метод находит созданный список на Доске
     */
    public void selectListInBoldByName(String textNameList) {
        LOG.info("Находим  на доске список: " + textNameList);
        findByXpath("*//div[@class='list js-list-content']//h2[text()='"+textNameList+"']");
    }


    // МЕТОДЫ ДЛЯ РАБОТЫ С КАРТОЧКАМИ
    // *******************************************************************************************************************


    /**
     * Метод находим индекс необходимого списка по его имени
     */
    public int findIndexListByName(String textName) {
        LOG.info("Находим все списки на доске пользователя:");
        List<WebElement> lists = driver.findElements(By.cssSelector("div[class='list js-list-content'] h2"));
        LOG.info("Вытаскиваем атрибут с названием списка");
        int index = 0;
        for (WebElement element : lists) {
            String libfst = element.getAttribute("innerText");
            LOG.info("Иищем нужный список: " + textName);
            if (libfst.equals(textName)) {
                return index;
            } else {
                index++;
            }

        }
        LOG.info("Возвращаем значение индекса  листа: " + textName + "индекс равен: " + index);
        return index;
    }



    /**
     * Метод проверяет количество созданных карт у списка на доске
     */
    public int selectCountCardInListBold() {
        LOG.info("Находим все карточки в списке:");
        List<WebElement> listCards = driver.findElements(By.cssSelector("a[class='list-card js-member-droppable ui-droppable']"));
        int Count = (listCards.size());
       return Count;
    }


    /**
     * Метод находит карточку по имени и открывает ее
     */
    public void selectCardInListByName(String cardName) throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим карточку в списке по имени : "+ cardName + " и открываем ее");
            try {
                findByXpath("*//a//span[text()='"+cardName+"']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти карточку в списке: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти карточку в списке");
    }



    /**
     * Метод находит форму с описанием  карточки
     */
    public void selectCardForm() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим форму с описанием карточки");
            try {
                findByCss("div[class='window-wrapper js-tab-parent']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти форму с описанием карточки: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти форму с описанием карточки");
    }







    /**
     * Метод нажимает ссылку Добавить карточку у списка
     */
    public void selectAddCardInListBold(int index) {
        LOG.info("Находим все ссылки Добавить карточку:");
        List<WebElement> LinksAddCard = driver.findElements(By.xpath("*//a//span[text()='Добавить карточку']"));
        LOG.info("Выбираем ссылку для создания карточки по индексу листа, найденного на предыдущем шаге: " + index);
        WebElement addCard = LinksAddCard.get(index);
        LOG.info("Нажимаем на найденную ссылку и проверяем, что открылась форма для создания карточки");
        addCard.click();
        findByCss("div[class='list js-list-content'] div[class='card-composer']");
    }


    /**
     * Метод нажимает ссылку Добавить еще одну  карточку у списка
     */
    public void selectAddAnotherCardInListBold(int index) {
        LOG.info("Находим все ссылки Добавить карточку:");
        List<WebElement> LinksAddCard = driver.findElements(By.xpath("*//a//span[text()='Добавить еще одну карточку']"));
        LOG.info("Выбираем ссылку для создания карточки по индексу листа, найденного на предыдущем шаге: " + index);
        WebElement addCard = LinksAddCard.get(index);
        LOG.info("Нажимаем на найденную ссылку и проверяем, что открылась форма для создания карточки");
        addCard.click();
        findByCss("div[class='list js-list-content'] div[class='list-card js-composer']");
    }


    /**
     * Метод вводит заголовок карточки
     */
    public void selectAddCardInListBold(String textNameCard) {
        LOG.info("Находим поле для ввода заголовка карточки и вводим текст: " + textNameCard);
        WebElement heardCard = findByCss("div[class='list js-list-content'] div[class='card-composer'] textarea");
        heardCard.click();
        heardCard.sendKeys(textNameCard);
    }


    /**
     * Метод сохранет карточку
     */
    public void selectButtonSaveCardInListBold() {
        LOG.info("Находим кнопку Сохранить карточку и нажимаем на нее ");
        findByCss("input[value='Добавить карточку']").click();
    }


    /**
     * Метод находит созданную карту
     */
    public void selectfindCardInListBold(String textCard) {
        LOG.info("Находим созданную карту");
        findByXpath("*//a//span[text()='"+textCard+"']");
    }



    /**
     * Метод отправляет карточку в архив
     */
    public void selectButtonArchiveCard() {
        LOG.info("Отправляем авбранную карточку в архив");
        findByCss("div span[class='icon-sm icon-archive']").click();
    }




    /**
     * Метод Нажимает кнопку Удалить Карточку
     */
    public void selectButtonDeleteCardOnForm() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим кнопку Удалить");
            try {
                findByCss("a[class='button-link js-delete-card negate']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти кнопку Удалить карточку: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти кнопку Удалить карточку");
    }


    /**
     * Метод Нажимает кнопку Удалить Карточку
     */
    public void selectConfirmationDeleteCardOnForm() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Подтверждаем удаление карточки");
            try {
                findByCss("div[class='pop-over is-shown'] input[value='Удалить']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось подтвержить удаление: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось подтвержить удаление");
    }


    /**
     * Метод закрывает форму карточки
     */
    public void selectButtonCloseFormCard() {
        LOG.info("закрываем форму карточки");
        findByCss("div a[class='icon-lg icon-close dialog-close-button js-close-window']").click();
    }


    /**
     * Метод проверяет отсутствие карточки в листе
     */
    public void selectIsCardInListPresent(String textCard) throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(2);

            try {
                LOG.info("пытаемся найти заархивированную карточку: " + textCard);
                findByXpath("*//a//span[text()='" + textCard + "'])");
                throw new Exception("Некорректное поведение. Элемент найден на странице");

            } catch (org.openqa.selenium.NoSuchElementException e) {
                LOG.info("Карточка  удалилась");
                i++;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            }
        LOG.info("Поведение корректное. Элемент не появился на странице в отведенное время");

        }




    /**
     * Метод вводит описание карточки
     */
    public void selectAddDescriptionCard(String textDescriptoin) {
        LOG.info("Находим поле для ввода описания карточки и вводим текст: " + textDescriptoin);
        WebElement descriptoinCard = findByCss("div[class='editable editing'] textarea");
        descriptoinCard.click();
        descriptoinCard.sendKeys(textDescriptoin);
    }


    /**
     * Метод сохраняет описание карточки
     */
    public void selectButtonSaveDescriptionCard() {
        LOG.info("Находим кнопку для Сохранить описания карточки и нажимаем на нее");
        findByCss("div[class='editable editing'] input[value='Сохранить']").click();
    }


    /**
     * Метод возвращает добавленное описание карточки
     */
    public String selectCheckDescriptionCard() {
        LOG.info("Находим добавленное описание: ");
        String textDescripton = findByCss("div[class='current markeddown hide-on-edit js-desc js-show-with-desc'] p").getText();
        return textDescripton;
    }




    /**
     * Метод вводит Комментарий карточки
     */
    public void selectAddCommentCard(String textComment) {
        LOG.info("Находим поле для ввода комментария карточки и вводим текст: " + textComment);
        WebElement descriptoinCard = findByCss("div[class='comment-box'] textarea[class='comment-box-input js-new-comment-input']");
        descriptoinCard.click();
        descriptoinCard.sendKeys(textComment);
    }


    /**
     * Метод сохраняет комментарий карточки
     */
    public void selectButtonSaveCommentCard() {
        LOG.info("Находим кнопку для Сохранить комментария карточки и нажимаем на нее");
        findByCss("div[class='comment-controls u-clearfix'] input[value='Сохранить']").click();
    }




    /**
     * Метод возвращает добавленный комментарий карточки
     */
    public String selectCheckCommentCard() {
        LOG.info("Находим добавленное комментарий: ");
        String textDescripton = findByCss("div[class='current-comment js-friendly-links js-open-card'] p").getText();
        return textDescripton;
    }

    /**
     * Метод  открывает форму для добавления метки на карточку
     */
    public void selectAddLabelInCard() throws Exception {
        LOG.info("Находим кнопку Добавить:Метки и нажимаем на нее");
        findByCss("div[class='window-sidebar'] a[title='Метки']").click();
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим форму для создания и выбора Меток");
            try {
                findByXpath("*//div[@class='pop-over is-shown']//span[text()='Метки']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось подтвержить загрузку форму Метки: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось подтвержить загрузку форму Метки");
    }



    /**
     * Метод  выбирает метки на карточку по цвету
     */
    public void findLabelByColor(String colorLabel) {
        LOG.info("Находим метку по цвету: " + colorLabel);
        findByCss("ul[class='edit-labels-pop-over js-labels-list'] li span[data-color='"+colorLabel+"']").click();
        }


    /**
     * Метод  закрывает форму для создания и выбора метки на карточку
     */
    public void selectButtonCloseLabel() {
        LOG.info("находим конпку закрыть фору Метки и нажимает на нее");
        findByCss("div[class='pop-over is-shown'] a[class='pop-over-header-close-btn icon-sm icon-close']").click();
    }


    /**
     * Метод проверяет заголовок метки на карточке
     */
    public void selectCheckLabelCard() {
        LOG.info("находим  заголовок метки:");
        findByXpath("*//h3[@class='card-detail-item-header'][text()='Метки']");
    }




    /**
     * Метод  открывает форму для добавления Участника на карточку
     */
    public void selectAddMemberInCard() throws Exception {
        LOG.info("Находим кнопку Добавить:Участники и нажимаем на нее");
        findByCss("div[class='window-sidebar'] a[title='Участники']").click();
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим форму для создания и выбора Участников");
            try {
                findByXpath("*//div[@class='pop-over is-shown']//span[text()='Участники']");
            } catch (TimeoutException e) {
                LOG.info("Не удалось подтвержить загрузку форму Участники: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось подтвержить загрузку форму Участники");
    }



    /**
     * Метод вводит электронный адрес Участника в поле
     */
    public void selectInputNameMemberCard(String textMember) {
        LOG.info("Находим поле для ввода элекронного адреса участника карточки и вводим текст: " + textMember);
        WebElement memberCard = findByCss("input[class='js-search-mem js-autofocus'][placeholder='Поиск участников']");
        memberCard.click();
        memberCard.sendKeys(textMember);
    }

    /**
     * Метод нажимаем кнопку отправить запрос Участнику
     */
    public void selectButtonPutMemberCard() throws Exception {
        for (int i = 0; i < 5; i++) {
            sleepTest(4);
            LOG.info("Находим кнопку Отправить и нажимаем на нее");
            try {
                findByCss("input[class='wide primary js-send-email-invite'][value='Отправить']").click();
            } catch (TimeoutException e) {
                LOG.info("Не удалось найти кнопку Отправить: " + e.getCause());
                continue;
            }
            return;
        }
        throw new Exception("Не удалось найти кнопку Отправить");


    }

    /**
     * Метод проверяет заголовок Участники на карточке
     */
    public void selectCheckMemberCard() {
        LOG.info("находим  заголовок  Участники:");
        findByXpath("*//h3[@class='card-detail-item-header mod-no-top-margin'][text()='Участники']");
    }


}