package ru.timofey.NauJava.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginLogoutSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @BeforeEach
    void setUp() {
        System.setProperty("java.net.useSystemProxies", "false");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    /**
     * Сценарий: успешный вход в приложение.
     * <p>
     * Шаги:
     * 1. Пользователь переходит на страницу входа (/login).
     * 2. Вводит корректные учётные данные (admin/admin).
     * 3. Нажимает кнопку «Войти».
     * 4. Проверяет, что произошла успешная авторизация и
     *    отображается домашняя страница (URL больше не содержит /login).
     */
    @Test
    @Order(1)
    void loginWithValidCredentials_ShouldRedirectToHomePage() {
        driver.get(baseUrl() + "/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "Должна отображаться страница входа (/login)");

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameInput.sendKeys(ADMIN_USERNAME);
        passwordInput.sendKeys(ADMIN_PASSWORD);
        submitButton.click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")
        ));

        String currentUrl = driver.getCurrentUrl();
        assertFalse(currentUrl.contains("/login"),
                "После успешного входа URL не должен содержать /login. Текущий URL: " + currentUrl);
    }

    /**
     * Сценарий: выход из приложения.
     * <p>
     * Шаги:
     * 1. Пользователь авторизуется (admin/admin).
     * 2. Переходит на страницу подтверждения выхода (/logout).
     * 3. Нажимает кнопку «Выйти».
     * 4. Проверяет перенаправление на страницу входа с параметром ?logout
     *    и наличие сообщения «Вы успешно вышли из системы».
     */
    @Test
    @Order(2)
    void logout_WhenAuthenticated_ShouldRedirectToLoginPageWithLogoutMessage() {
        driver.get(baseUrl() + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        driver.findElement(By.name("username")).sendKeys(ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(ADMIN_PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")
        ));

        driver.get(baseUrl() + "/logout-page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));

        WebElement logoutButton = driver.findElement(By.cssSelector("button[type='submit']"));
        assertEquals("Выйти", logoutButton.getText(),
                "Кнопка выхода должна называться «Выйти»");

        logoutButton.click();

        wait.until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/login"),
                "После выхода должно быть перенаправление на /login. Текущий URL: " + currentUrl);
        assertTrue(currentUrl.contains("logout"),
                "URL должен содержать параметр logout. Текущий URL: " + currentUrl);

        WebElement logoutMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logout"))
        );
        assertTrue(logoutMessage.isDisplayed(),
                "Должно отображаться сообщение об успешном выходе");
        assertTrue(logoutMessage.getText().contains("вышли"),
                "Сообщение должно содержать текст о выходе. Текст: " + logoutMessage.getText());
    }
}
