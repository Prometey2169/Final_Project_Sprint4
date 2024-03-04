import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import page_object.MainPageLocators;

import static org.junit.Assert.assertEquals;
import static urls.WebsiteUrl.WEB_URL;

@RunWith(Parameterized.class)
public class AccordionListTest {
            WebDriver driver;

            //переменная для хранения номера пункта в выпадающем списке
            private final int listItemNumber;
            //переменная для хранения текста пункта в выпадающем списке
            private final String listItemText;

            public AccordionListTest(int listItemNumber, String listItemText) {
                this.listItemNumber = listItemNumber;
                this.listItemText = listItemText;
            }

            // тестовые данные
            @Parameterized.Parameters
            public static Object[][] accordionListTestData() {
                return new Object[][]{
                        {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                        {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                        {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                        {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                        {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                        {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                        {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                        {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
                };
            }

        @Before
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//         driver = new FirefoxDriver();
        }

            @Test
            public void checkTextAccordionListFAQHomepage() {
                // инициализируем драйвер адресом страницы
                driver.get(WEB_URL);
                // создаем экземпляр главной страницы
                MainPageLocators homePageLocators = new MainPageLocators(driver);
                // ждем окончания загрузки страницы
                homePageLocators.waitForLoadPageData();
                // закрываем форму согласия с куки
                homePageLocators.clickCookieAgreeButton();
                // прокручиваем страницу до тестируемого списка
                homePageLocators.scrollPageTillAccordionList();
                // проводим проверку: сравниваем полученный от элемента такст с тестовыми данными
                assertEquals("Неверный текст сообщения", listItemText, homePageLocators.accordionItemGetText(listItemNumber));

            }

            @After
            public void teardown() {
                // Зарываем браузер
                driver.quit();
            }

        }


