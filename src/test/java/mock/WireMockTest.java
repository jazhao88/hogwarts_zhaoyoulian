package mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockTest {
    @Test
    void mock() throws IOException {
        //服务器启动
        WireMockServer wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(8089)
                        .extensions(new ResponseTemplateTransformer(true))); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();

        // 客户端配置
        configureFor(8089);

        //stuff
        stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")));

        //从模板中读取
        //输入的访问地址：http://127.0.0.1:8089/a/b/c?id=1
        stubFor(get(urlPathEqualTo("/a/b/c")).withQueryParam("id",new ContainsPattern("1"))
                .willReturn(aResponse()
                        .withHeader("token", "1")
                        .withBody("files/{request.query.id}.png")));

        //输入的访问地址：http://127.0.0.1:8089/abc?site=ceshiren.com
        stubFor(get(anyUrl())
                .withQueryParam("site", new ContainsPattern("ceshiren.com"))
                .willReturn(aResponse()
                        .withBody("site={request.query.site}")));

        //Proxying
        //输入的访问地址：http://127.0.0.1:8089/ceshiren/
        stubFor(get(urlMatching("/ceshiren/.*"))
                .willReturn(aResponse().proxiedFrom("https://ceshiren.com/")));

        //输入的访问地址：http://127.0.0.1:8089/s?wd=mp3
        stubFor(get(urlMatching("/s\\?.*"))
                .willReturn(aResponse().proxiedFrom("https://baidu.com/")));

        System.in.read();

        WireMock.reset();

// Finish doing stuff

        wireMockServer.stop();
    }
}
