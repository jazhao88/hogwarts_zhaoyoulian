package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public static WebDriver webDriver;

    /**
     * 点击对象
     * @param by
     * @return
     */
    public void click(By by){
        try {
            webDriver.findElement(by).click();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean clickUtil(By by, By next){
        try {
            webDriver.findElement(by).click();
            WebElement foo = new WebDriverWait(webDriver, 10)
                    .until(driver -> driver.findElement(next));
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return clickUtil(by, next);
        }
    }

    public boolean click2(By by){
        try {
            webDriver.findElement(by).click();

        }catch (Exception e){
            e.printStackTrace();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return click2(by);
        }
        return true;
    }

    /**
     * 输入文本
     * @param by
     * @param name
     * @return
     */
    public void sendKeys(By by, String name){
        try {
            webDriver.findElement(by).sendKeys(name);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendKeysUtil(By by, String name, By next){
        try {
            webDriver.findElement(by).sendKeys(name);
            WebElement foo = new WebDriverWait(webDriver, 10)
                    .until(driver -> driver.findElement(next));

        }catch (Exception e){
            e.printStackTrace();
            //重试一次
            webDriver.findElement(by).clear();
            webDriver.findElement(by).sendKeys(name);
        }
    }

    public String getText(By by){
        try {
            WebElement foo = new WebDriverWait(webDriver, 10)
                    .until(driver -> driver.findElement(by));
            return foo.getText();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
