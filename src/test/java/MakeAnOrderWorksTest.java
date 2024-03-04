import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import page_object.MainPageLocators;
import page_object.OrderPagesLocators;
import static org.hamcrest.CoreMatchers.containsString;
import static urls.WebsiteUrl.WEB_URL;

@RunWith(Parameterized.class)
public class MakeAnOrderWorksTest {
    WebDriver driver;
    String[] orderData;

    public MakeAnOrderWorksTest(String metro, String name, String surname, String adress, String phone, String comment,
                                String colour, String leaseDuration, String orderDate) {
        this.orderData = new String[]{metro, name, surname, adress, phone, comment, colour, leaseDuration, orderDate};
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                {"Игорь", "Овсянкин", "Наметкина 5 корп. 1", "89251831546", "109",
                        "Оставьте у двери", "'чёрный жемчуг'", "трое суток", "03.03.2024"},
                {"Николай", "Злобин", "Испанские кварталы 10 корп. 3", "89265823070", "22",
                        "Привезите как можно скорее", "'серая безысходность'", "четверо суток", "07.03.2024"}
        };
    }

    @Before
    public void setUp() {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Test
    public void orderFlowTestOrderButton1() {
        // строка для сравнения в assert
        String assertExpectedText = "Заказ оформлен";
        // инициализируем драйвер адресом страницы
        driver.get(WEB_URL);
        // создаем экземпляр главной страницы
        MainPageLocators homePageLocators = new MainPageLocators(driver);
        // создаем экземпляр класса объектов страниц заказа
        OrderPagesLocators orderPagesObjects = new OrderPagesLocators(driver);
        // ждем окончания загрузки страницы
        homePageLocators.waitForLoadPageData();
        // закрываем форму согласия с куки
        homePageLocators.clickCookieAgreeButton();
        // нажимаем кнопку "Заказать" в правом верхнем углу главной страницы
        homePageLocators.clickOrderTopButton();
        // ждем загрузку страницы
        orderPagesObjects.getHeaderText();
        // заполняем поля заказа тестовыми данными
        orderPagesObjects.fillDataInInputFields(orderData);
        // нажимаем кнопку Заказать для завершения оформления заказа
        orderPagesObjects.clickOrderButton();
        // нажимаем кнопку Да формы "Хотите оформить заказ?"
        orderPagesObjects.clickYesOrderButton();
        // проводим проверку: ищем текст, который ожидаем получить в тексте формы подтверждения оформления заказа
        MatcherAssert.assertThat(orderPagesObjects.getOrderConfirmFormHeaderText(), containsString(assertExpectedText));

    }

    @After
    public void teardown() {
        // Зарываем браузер
        driver.quit();
    }

}