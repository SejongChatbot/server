package com.sejongmate.user.application.util;

import com.sejongmate.common.BaseException;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.json.simple.parser.*;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_CRAWLING;
import static com.sejongmate.common.BaseResponseStatus.INVALID_USER_INFO;

@Log4j2
public class WebDriverUtil {
    private WebDriver driver;
    private String num;
    private String password;
    private static final String url = "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp?rtUrl=blackboard.sejong.ac.kr";

    public WebDriverUtil(String num, String password) {
        this.num = num;
        this.password = password;

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);
        options.addArguments("--remote-allow-origins=*");


        this.driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public JSONObject getUserInfoObj() throws BaseException {
        try{
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

            driver.findElement(By.id("id")).click();
            driver.findElement(By.id("id")).sendKeys(num);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();

            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            String userId = driver.findElement(By.id("sidebar-user-name"))
                    .getAttribute("class").split(" ")[2].split("user")[1];

            log.info("회원가입 유저 아이디 불러오기 성공. user id = " + userId);

            JSONParser jsonParser = new JSONParser();

            driver.get("https://blackboard.sejong.ac.kr/learn/api/v1/users/" + userId);
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            String res = driver.findElement(By.tagName("pre")).getText();

            log.info("회원가입 유저 정보 불러오기 성공");

            Object obj = jsonParser.parse(res);
            JSONObject jsonObj = (JSONObject) obj;

            quit();
            return jsonObj;

        } catch(UnhandledAlertException e) {
            log.error(INVALID_USER_INFO + " " + e.toString());
            quit();
            throw new BaseException(INVALID_USER_INFO);
        } catch(Exception e) {
            log.error(INVALID_USER_CRAWLING + " " + e.toString());
            quit();
            throw new BaseException(INVALID_USER_CRAWLING);
        }
    }

    private void quit(){
        driver.close();
        driver.quit();
    }
}
