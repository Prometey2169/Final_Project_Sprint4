package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static urls.WebsiteUrl.WEB_URL;

public class OrderPagesLocators {
    WebDriver driver;
    public OrderPagesLocators(WebDriver driver) {
        this.driver = driver;
        driver.get(WEB_URL);
    }
    // локаторы 1 страницы оформления заказа
// локатор для заголовка страницы
    private final By orderPage1HeaderLabel = By.xpath(".//div[text()='Для кого самокат']");
    // Поля ввода: Имя, Фамилия, Адрес, Телефон
    private final By inputFields = By.xpath(".//*[@placeholder = '* Имя' or @placeholder = " +
            "'* Фамилия' or @placeholder = '* Адрес: куда привезти заказ'" +
            " or @placeholder = '* Телефон: на него позвонит курьер']");

    // выпадающий список станций метро
    private final By selectSearchInput = By.className("select-search__input");

    // кнопка "Далее"
    private final By continueButton = By.xpath(".//button[text()='Далее']");

    // 2 страница оформления заказа

    // Поле "Когда привезти самокат"
    private final By deliveryDateField = By.xpath(".//*[@placeholder='* Когда привезти самокат']");

    // Поле "Срок аренды"
    private final By rentalPeriodField = By.className("Dropdown-placeholder");

    // Поле "Комментарии для курьера"
    private  final By commentForCourierField = By.xpath(".//*[@placeholder = 'Комментарий для курьера']");

    // Кнопка "Заказать"
    private final By orderButton = By.xpath(".//button[text()='Заказать']");

    // Кнопка "Нет" в форме "Хотите оформить заказ?"
    private final By noOrderButton = By.xpath(".//*[text()='Нет']");

    // Кнопка "Да" в форме "Хотите оформить заказ?"
    private final By yesOrderButton = By.xpath(".//*[text()='Да']");

    // Кнопка "Посмотреть статус"
    private final By seeOrderStateButton = By.xpath(".//*[text()='Посмотреть статус']");

    //
    private final By orderConfirmFormHeader = By.className("Order_ModalHeader__3FDaJ");

    public void getHeaderText() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(orderPage1HeaderLabel)).getText();
    }

    public void fillDataInInputFields(String[] Data) {
        int i = 0;
        String[] orderData = Data;
        By scooterColourChoiceField = By.xpath(".//*[text() = '"+orderData[7]+"']");

        // заполнение оставшихся полей ввода для первой страницы
        List<WebElement> inputElements = driver.findElements(inputFields);
        for (WebElement inputElement : inputElements) {
            inputElement.sendKeys(orderData[i]);
            i++;
        }
        //заполнение поля "Метро"
        driver.findElement(selectSearchInput).click();
        driver.findElement(By.xpath(".//button[@value = '" + orderData[i] + "']")).click();
        i++;

        //клик на кнопку "Далее" и ожидание загрузки страницы
        driver.findElement(continueButton).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(rentalPeriodField));

        // заполнение поля "Комментарий для курьера"
        driver.findElement(commentForCourierField).sendKeys(orderData[i]);
        i++;

        driver.findElement(By.xpath(".//*[text() = "+orderData[i]+"]")).click();
        i++;
        driver.findElement(rentalPeriodField).click();
        // выбор цвета самоката
        driver.findElement(scooterColourChoiceField).click();
        i++;
        // выбор даты доставки
        driver.findElement(deliveryDateField).sendKeys(orderData[i]);

    }
    // клик на кнопку "Заказать" и ожидание загрузки формы "Хотите оформить заказ?"
    public void clickOrderButton() {

        driver.findElements(orderButton).get(1).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(noOrderButton));
    }

    public void clickYesOrderButton(){
        driver.findElement(yesOrderButton).click();
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(seeOrderStateButton));
    }
    //текст формы "Заказ оформлен"
    public String getOrderConfirmFormHeaderText(){
        return  driver.findElement(orderConfirmFormHeader).getText();
    }

    public String getOrderPage1HeaderText(){
        return  driver.findElement(orderPage1HeaderLabel).getText();
    }
    public boolean isMessageAfterInputWrong(int fieldNumber, String wrongInputText, String wrongInputMessage){
        if (fieldNumber != 4) {
            driver.findElements(inputFields).get(fieldNumber).sendKeys(wrongInputText);

        }
        driver.findElement(continueButton).click();
        return driver.findElement(By.xpath(".//div[text()= "+wrongInputMessage+"]")).isDisplayed();
    }

}

