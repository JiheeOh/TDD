package test.chap09;

import com.github.tomakehurst.wiremock.WireMockServer;
import main.chap09.CardNumberValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardNumberValidatorTest {

    //WireMock을 사용하면 특정 api에 대해 stub을 설정할 수 있다.

    private WireMockServer wireMockServer;

    @BeforeEach
    void setUp(){
        wireMockServer = new WireMockServer(options().port(8089));
        wireMockServer.start();
    }

    @AfterEach
    void tearDown(){
        wireMockServer.stop();
    }

    @Test
    void valid(){
        wireMockServer.stubFor(post(urlEqualTo("/card"))
                .withRequestBody(equalTo("1234567890"))
                .willReturn(aResponse().withHeader("Content-Type","text/plain")
                        .withBody("ok")));

        CardNumberValidator validator = new CardNumberValidator("http://localhost:8089");
        String validity = validator.validate("1234567890");
        assertEquals("VALID",validity);
    }

    @Test
    void timeout(){
        wireMockServer.stubFor(post(urlEqualTo("/card"))
                .willReturn(aResponse().withFixedDelay(5000))
        );

        CardNumberValidator validator=new CardNumberValidator("http://localhost:8089");
        String validity = validator.validate("1234567890");
        assertEquals("TIMEOUT",validity);
    }




}
