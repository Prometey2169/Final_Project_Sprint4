package page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static urls.WebsiteUrl.WEB_URL;


public class MainPageLocators {
    WebDriver driver;
    public MainPageLocators(WebDriver driver) {
        this.driver = driver;
        driver.get(WEB_URL);
    }

    // локаторы страницы
    // Кнопка "Принять куки"
    private final By buttonCookie = By.className("App_CookieButton__3cvqF");

    //Кнопка "Заказать" (верхняя)
    private final By orderTopButton = By.className("Button_Button__ra12g");

    //Кнопка "Заказать" (нижняя)
    private final By orderBottomButton = By.className("Home_FinishButton__1_cWm");

    //Блок "Вопросы о важном"
    private final By accordionListFAQMainPage = By.className("Home_FAQ__3uVm4");

    // методы страницы

    //ожидание загрузки страницы
    public void waitForLoadPageData() {
        new WebDriverWait(driver, 5)
                .until(driver -> (driver.findElement(orderTopButton).getText() != null));
    }

    //нажатие кнопки согласия с куки
    public void clickCookieAgreeButton() {
        driver.findElement(buttonCookie).click();
    }

    //прокрутка страницы до блока "Вопросы о важном"
    public void scrollPageTillAccordionList() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(accordionListFAQMainPage));
    }

    // прокрутка страницы до кнопки "Заказать" (нижняя)
    public void scrollPageTillOrderBottomButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(orderBottomButton));
    }

    // Получение текста в блоке "Вопросы о важном"
    public String accordionItemGetText(int listItemNumber){
        driver.findElement(By.id("accordion__heading-"+listItemNumber)).click();
        return driver.findElement(By.xpath(".//*[@id='accordion__panel-"+listItemNumber+"']/p")).getText();

    }

    // нажатие на кнопку "Заказать" (верхняя)
    public void clickOrderTopButton() {
        driver.findElement(orderTopButton).click();
    }

    // нажатие на кнопку "Заказать" (нижняя)
    public void clickOrderBottomButton() {
        driver.findElement(orderBottomButton).click();
    }
}

