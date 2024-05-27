package test.chap07.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import main.chap07.quiz.TimeBasedGreeting;
import main.chap07.quiz.Times;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class TimeBasedGreetingTest {
    private Times mockTimes = Mockito.mock(Times.class);
    private final TimeBasedGreeting timeBasedGreeting = new TimeBasedGreeting();

    @BeforeEach
    void setUp(){
     timeBasedGreeting.setTimes(mockTimes);
    }

    @Test
    void testMethodName(){
        // given
    given(mockTimes.now()).willReturn(LocalDateTime.of(2024,5,28,13,00));

        // when
    String result = timeBasedGreeting.getGreeting();
        //then
    assertEquals("Good Afternoon",result);
    }



}
