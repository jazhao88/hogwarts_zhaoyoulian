package TestCase;

import Model.Department;
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


public class DepartmentTest {


    @Test
    public void addDepartment(){
        String time= String.valueOf(System.currentTimeMillis());
        String name = "dep" + time;
        String parant = "LotusAutoTest";

        WeWorkPage weWorkPage = new WeWorkPage();

        String result = weWorkPage.startWeb()
                .login()
                .toContactPage()
                .addDepartment(name, parant)
                .searchName(name)
                .getDepartmentName(name);
        //断言
        assertThat(result, equalTo(name));

        //退出浏览器
        weWorkPage.tearDown();
    }

    @ParameterizedTest
    @MethodSource("testDateDepartmentFromYaml")
    @Test
    public void addDepartmentWithDataProvider(Department department){

        WeWorkPage weWorkPage = new WeWorkPage();
        String result = weWorkPage.startWeb()
                .startWeb()
                .login()
                .toContactPage()
                .addDepartment(department.getName(), department.getParent())
                .getDepartmentName(department.getName());
        //断言
        assertThat(result, equalTo(department.getName()));

        //退出浏览器
        weWorkPage.tearDown();
    }

    /**
     * 添加部门的数据
     * @return
     */
    static List<Department> testDateDepartmentFromYaml(){

        ObjectMapper objectMapper;
        try {
            objectMapper = new ObjectMapper(new YAMLFactory());
            List<Department> departments= objectMapper.readValue(
                    new File("D:\\学习\\hogwarts\\gitdoc\\AutoTest\\src\\test\\resources\\department.yaml"),
                    new TypeReference<List<Department>>(){});
//            List<Member> members = objectMapper.readValue(MemberTest.class.getResourceAsStream("/member.yaml"), new TypeReference<List<Member>>() {});
            return departments;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
