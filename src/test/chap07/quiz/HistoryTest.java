package test.chap07.quiz;

import main.chap07.quiz.FakeHistoryRepository;
import main.chap07.quiz.History;
import main.chap07.quiz.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryTest {
    private HistoryRepository fakeHistoryRepository;

    @BeforeEach
    void setUp(){
     fakeHistoryRepository = new FakeHistoryRepository();
    }
    @DisplayName("fake : 이력 찾기")
    @Test
    void testMethodName(){
        // given
        History history= new History(1,"hello", LocalDateTime.of(2024,5,28,1,14));
        fakeHistoryRepository.save(history);

        // when
        History result = fakeHistoryRepository.findbyId(1);


        //then
        assertEquals("hello",result.getHistoryText());
        assertEquals(1,result.getId());
        assertEquals(LocalDateTime.of(2024,5,28,1,14),result.getInsertTime());


    }




}
