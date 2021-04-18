package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    public MainPage(WebDriver webDriver) {
        super();
    }

    /**
     * 进入添加成员页
     * @return
     */
    public ContactPage toMemberAddPage(){
        click(By.linkText("添加成员"));
        return new ContactPage(webDriver);
    }

    /**
     * 进入通讯录页面
     * @return
     */
    public ContactPage toContactPage(){
        clickUtil(By.linkText("通讯录"),By.linkText("添加"));
        return new ContactPage(webDriver);
    }
}
