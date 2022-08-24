package com.eden.selenium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class SeleniumApplicationTests {

  private static ChromeOptions options;

  @Data
  @AllArgsConstructor
  public class ResultVO {
    private String title;
    private boolean isPass;
    private String message;
  }

  static List<ResultVO> results = new ArrayList<>();

  @BeforeAll
  static void setup() {
    System.setProperty("webdriver.chrome.driver", "E:\\Development\\utils\\chromedriver\\chromedriver.exe");
    options = new ChromeOptions();
    options.addArguments("--disable-popup-blocking"); //팝업안띄움
    options.addArguments("headless");  //브라우저 안띄움
    options.addArguments("--disable-gpu"); //gpu 비활성화
    options.addArguments("--blink-settings=imagesEnabled=false"); //이미지 다운 안받음
  }

  @DisplayName("MOS 관리시스템 로그인")
  @Test
  @Order(1)
  void login() {
    String url = "";
    WebDriver driver = new ChromeDriver(options);
    driver.get(url);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    log.info("[title] {}", driver.getTitle());
    WebElement zloginId = driver.findElement(By.name("zloginId"));
    WebElement zpasswd = driver.findElement(By.name("zpasswd"));
    zloginId.sendKeys("");
    zpasswd.sendKeys("");
    driver.findElement(By.className("btn_login")).click();

    Alert alert = driver.switchTo().alert();
    String alertText = alert.getText();
    log.info("aler >>> {}", alertText);

    WebElement el = driver.findElement(By.id("snbUl"));
    log.info("web >>>>>> {}", el.getText());
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    results.add(new ResultVO("MOS 관리시스템 로그인", !Objects.isNull(el), "message"));

  }

  @DisplayName("MOS 관리시스템 로그인 실패")
  @Test
  @Order(2)
  void failLogin() {
    String url = "";
    WebDriver driver = new ChromeDriver(options);
    driver.get(url);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    log.info("[title] {}", driver.getTitle());
    WebElement zloginId = driver.findElement(By.name("zloginId"));
    WebElement zpasswd = driver.findElement(By.name("zpasswd"));
    zloginId.sendKeys("");
    zpasswd.sendKeys("");
    try {
      driver.findElement(By.className("btn_login")).click();
    } catch (Exception e) {
      log.info("Exception >> {}", e.getMessage());
    }


    Alert alert = driver.switchTo().alert();
    String alertText = alert.getText();
    log.info("aler >>> {}", alertText);

    WebElement result = driver.findElement(By.id("snbUl"));
    log.info("result >>> {}", result);
    results.add(new ResultVO(getClass().getName(), Objects.isNull(result), "message"));
    try {
      assertThat(result).isNotNull();
    }
    catch (Exception expected) {
      log.info("exception >> , P{", expected.getMessage());
    }


  }

  @AfterEach
  void afterEach() {
    log.info("afterEach >>>>>>>>> {}", results);
  }

  @AfterAll
  static void afterAll() {
    log.info("test end =============== {}", results);
  }
}
