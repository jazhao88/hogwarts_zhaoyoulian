package TestCase;

import Model.Member;
import Page.WeWorkPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class MemberTest {

    @Test
    public void addMember(){

        //数据
        String time= String.valueOf(System.currentTimeMillis());
        String name="user_" + time;
        String acount = "account_" + time;
        String phone = time.substring(0,11);
        HashMap<String,Object>data = null;

        WeWorkPage weWorkPage = new WeWorkPage();

        //步骤：登录->进入添加成员页->添加成员->获取成员名称
        String result = weWorkPage.startWeb()
                .startWeb()
                .login()
                .toContactPage()
                .addMember(name, acount, phone, data)
                .searchName(name)
                .getMemberName(name );

        //断言
        assertThat(result, equalTo(name));

        //退出浏览器
        weWorkPage.tearDown();
    }

    @ParameterizedTest()
    @MethodSource("testDateMemberFromYaml")
    @Test
    public void addMemberWithDataProvider(Member member){

        WeWorkPage weWorkPage = new WeWorkPage();

        //步骤：登录->进入添加成员页->添加成员->获取成员名称
        String result = weWorkPage.startWeb()
                .startWeb()
                .login()
                .toContactPage()
                .addMemberWithDataProvider(member)
                .searchName(member.getName())
                .getMemberName(member.getName() );

        //断言
        assertThat(result, equalTo(member.getName()));

        //退出浏览器
        weWorkPage.tearDown();
    }

    /**
     * 添加成员的数据
     * @return
     */
    static List<Member> testDateMemberFromYaml(){

        ObjectMapper objectMapper;
        try {
            objectMapper = new ObjectMapper(new YAMLFactory());
            List<Member> members= objectMapper.readValue(
                    new File("D:\\学习\\hogwarts\\gitdoc\\AutoTest\\src\\test\\resources\\member.yaml"),
                    new TypeReference<List<Member>>(){});
//            List<Member> members = objectMapper.readValue(MemberTest.class.getResourceAsStream("/member.yaml"), new TypeReference<List<Member>>() {});
            return members;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
