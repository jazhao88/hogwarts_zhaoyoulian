import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WechartTest {
    static WebDriver webDriver;

    @BeforeAll
    public static void init(){
        System.setProperty("webdriver.chrome.driver","D:\\devTool\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void teardown(){
        webDriver.quit();
    }

    @Test
    public void saveCookie(){
        webDriver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx?from=myhome");
        try {
            Thread.sleep(10000);

            Set<Cookie> cookies = webDriver.manage().getCookies();
            webDriver.navigate().refresh();
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            objectMapper.writeValue(new File("src/test/resources/cookies.yaml"), cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wechartLogin(){
        webDriver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx?from=myhome");
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            TypeReference<List<HashMap<String, Object>>> typeReference = new TypeReference<List<HashMap<String,Object>>>(){};
            List<HashMap<String,Object>>cookies = objectMapper.readValue(new File("src/test/resources/cookies.yaml"), typeReference);
            cookies.forEach(cookie->{
                webDriver.manage().addCookie(new Cookie(cookie.get("name").toString(), cookie.get("value").toString()));
            });
            webDriver.navigate().refresh();

            webDriver.findElement(By.id("menu_contacts")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
