package TestCase;

import Model.Tag;
import Page.WeWorkPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class TagTest {


    @Test
    public void addTag(){
        String time= String.valueOf(System.currentTimeMillis());
        String name = "tag" + time;
        String owner = "所有管理员";

        WeWorkPage weWorkPage = new WeWorkPage();

        String result = weWorkPage.startWeb()
                .login()
                .toContactPage()
                .addTag(name, owner)
                .getTagName(name);
        //断言
        assertThat(result, equalTo(name));

        //退出浏览器
        weWorkPage.tearDown();
    }

    @ParameterizedTest
    @MethodSource("testDateTagFromYaml")
    @Test
    public void addDepartmentWithDataProvider(Tag tag){

        WeWorkPage weWorkPage = new WeWorkPage();

        String result = weWorkPage.startWeb()
                .login()
                .toContactPage()
                .addTag(tag)
                .getTagName(tag.getName());
        //断言
        assertThat(result, equalTo(tag.getName()));

        //退出浏览器
        weWorkPage.tearDown();
    }

    /**
     * 添加部门的数据
     * @return
     */
    static List<Tag> testDateTagFromYaml(){

        ObjectMapper objectMapper;
        try {
            objectMapper = new ObjectMapper(new YAMLFactory());
            List<Tag> tags= objectMapper.readValue(
                    new File("D:\\学习\\hogwarts\\gitdoc\\AutoTest\\src\\test\\resources\\tag.yaml"),
                    new TypeReference<List<Tag>>(){});
            return tags;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
