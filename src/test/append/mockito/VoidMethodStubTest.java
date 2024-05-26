package test.append.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class VoidMethodStubTest {
    @DisplayName("리턴타입이 void인 메서드에서 exception 발생")
    @Test
    void voidMethodThrowTest(){
        List<String> mockList = mock(List.class);
        // willThrow 를 먼저 선언한다.
        // given에서는 모의 객체의 메서드 실행이 아닌 모의객체를 전달받아 모의 객체 자신을 리턴한다.
        // 이때 exception을 발생할 메서드를 호출
        willThrow(UnsupportedOperationException.class)
                .given(mockList)
                .clear();

        assertThrows(UnsupportedOperationException.class,()-> mockList.clear());

    }



}
