package Page;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WeWorkPage extends BasePage {

    /**
     * 打开浏览器
     * @return
     */
    public WeWorkPage startWeb(){
        System.setProperty("webdriver.chrome.driver","D:\\devTool\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 登录系统
     * @return 返回主页
     */
    public MainPage login(){

        webDriver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx?from=myhome");
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            TypeReference<List<HashMap<String, Object>>> typeReference = new TypeReference<List<HashMap<String,Object>>>(){};
            List<HashMap<String,Object>>cookies = objectMapper.readValue(new File("src/test/resources/cookies.yaml"), typeReference);
            cookies.forEach(cookie->{
                webDriver.manage().addCookie(new Cookie(cookie.get("name").toString(), cookie.get("value").toString()));
            });
            webDriver.navigate().refresh();
            return new MainPage(webDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存cookie
     */
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

    public void tearDown(){
        webDriver.quit();
    }


    /**
     * 退出系统
     * @return
     */
    public boolean logout(){
        return true;
    }
}
