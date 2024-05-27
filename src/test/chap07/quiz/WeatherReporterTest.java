package test.chap07.quiz;

import main.chap07.quiz.StubWeatherService;
import main.chap07.quiz.WeatherReporter;
import main.chap07.quiz.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class WeatherReporterTest {
    private StubWeatherService stubWeatherService;

    @BeforeEach
    void setUp(){
     stubWeatherService = new StubWeatherService();
    }
    @DisplayName("stub 사용")
    @Test
    void stubMethod(){
        stubWeatherService.setLocation("Korea");
        stubWeatherService.setWeather("Sunny");

        WeatherReporter weatherReporter = new WeatherReporter(stubWeatherService);
        String result = weatherReporter.getWeatherReport("Korea");
        assertEquals(result,"The weather in".concat("Korea").concat("is").concat("Sunny"));
    }

    @DisplayName("mock 사용")
    @Test
    void mockMethod(){
        WeatherService mockWeatherService = mock(WeatherService.class);

        given(mockWeatherService.getWeather("Korea")).willReturn("Sunny");

        WeatherReporter weatherReporter = new WeatherReporter(mockWeatherService);
        String result = weatherReporter.getWeatherReport("Korea");
        assertEquals(result,"The weather in".concat("Korea").concat("is").concat("Sunny"));
    }
}
