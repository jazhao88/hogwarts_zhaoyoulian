package Page;

import Model.Member;
import Model.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver webDriver) {
        super();
    }

    /**
     * 添加成员
     * @param name
     * @param acount
     * @param phone
     * @param data
     */
    public ContactPage addMember(String name, String acount, String phone, HashMap<String,Object>data){
        clickUtil(By.linkText("添加成员"),By.name("username"));
        sendKeys(By.name("username"), name);
        sendKeys(By.name("acctid"), acount);
        sendKeys(By.name("mobile"), phone);
        click(By.linkText("保存"));
        return this;
    }

    /**
     * 添加成员
     * @param member
     */
    public ContactPage addMemberWithDataProvider(Member member){
        clickUtil(By.linkText("添加成员"),By.name("username"));
        sendKeys(By.name("username"), member.getName());
        sendKeys(By.name("acctid"), member.getAccount());
        sendKeys(By.name("mobile"), member.getMobile());
        click(By.linkText("保存"));
        return this;
    }


    /**
     * 添加部门
     * @param name
     * @param parent
     * @return
     */
    public ContactPage addDepartment(String name, String parent){
        click(By.linkText("添加"));
        clickUtil(By.linkText("添加部门"),By.name("name"));
        sendKeys(By.name("name"), name);
        click(By.linkText("选择所属部门"));
        click(By.linkText(parent));
        webDriver.findElement(By.tagName("form")).findElement(By.linkText(parent)).click();
        click(By.linkText("确定"));
        return this;
    }

    /**
     * 添加标签
     * @param name
     * @param owner
     * @return
     */
    public ContactPage addTag(String name, String owner){
        click(By.linkText("标签"));
        clickUtil(By.linkText("添加"),By.name("name"));
        sendKeys(By.name("name"), name);
        click(By.cssSelector(".js_share_range_name"));
        webDriver.findElement(By.tagName("form")).findElement(By.linkText(owner)).click();
        click(By.linkText("确定"));
        return this;
    }
    /**
     * 添加标签
     * @param tag
     * @return
     */
    public ContactPage addTag(Tag tag){
        click(By.linkText("标签"));
        clickUtil(By.linkText("添加"),By.name("name"));
        sendKeys(By.name("name"), tag.getName());
        click(By.cssSelector(".js_share_range_name"));
        webDriver.findElement(By.tagName("form")).findElement(By.linkText(tag.getOwner())).click();
        click(By.linkText("确定"));
        return this;
    }


    /**
     * 按名称搜索部门名称
     * @param name
     * @return
     */
    public ContactPage searchName(String name){
        sendKeysUtil(By.cssSelector("qui_inputText ww_inputText ww_searchInput_text js_search_in"),
                name,
                By.cssSelector(".member_display_cover_detail_name"));
        return this;
    }

    /**
     * 获取成员名称
     * @param name
     * @return 成员名称
     */
    public String getMemberName(String name){
        return getText(By.cssSelector(".member_display_cover_detail_name"));
    }

    /**
     * 获取部门名称
     * @param name
     * @return 部门名称
     */
    public String getDepartmentName(String name){
        return getText(By.id("party_name"));
    }

    /**
     * 获取TAG名称
     * @param name
     * @return tag名称
     */
    public String getTagName(String name){
        return getText(By.cssSelector(".ww_commonCntHead_title_inner_text"));
    }
}
