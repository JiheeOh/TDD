package test.append.mockito;

import main.append.mockito.Game;
import main.append.mockito.GameLevel;
import main.append.mockito.GameNumGen;
import main.chap07.UserRegister;
import main.chap07.fake.UserRepository;
import main.chap07.spy.EmailNotifier;
import main.chap07.stub.WeakPasswordChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

// @Mock 필드에 자동으로 모의 객체 생성
@ExtendWith(MockitoExtension.class)
public class GameGenMockTest {

    private UserRegister userRegister;
    @Mock
    private WeakPasswordChecker mockWeakNotifier;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private EmailNotifier mockEmailNotifier;

    @Mock
    private GameNumGen genMock;

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockWeakNotifier, mockUserRepository, mockEmailNotifier);
    }

    @DisplayName("Stub : 게임레벨 easy-> 123 리턴 ")
    @Test
    void mockTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        // genMock의 generate 메서드가 실행되면 "123"을 리턴하도록 설정 : Stub
        given(genMock.generate(GameLevel.EASY)).willReturn("123");

        String num = genMock.generate(GameLevel.EASY);
        assertEquals("123", num);
    }

    @DisplayName("Stub : null 값일 경우 exception 처리 ")
    @Test
    void mockThrowTest() {
        // given
        given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        assertThrows(
                IllegalArgumentException.class, () -> genMock.generate(null)
        );
    }

    @DisplayName("인자 매칭 처리 : 모든 레벨에 임의 값을 리턴받음")
    @Test
    void anyMatchTest() {
        given(genMock.generate(any())).willReturn("456");

        String num = genMock.generate(GameLevel.EASY);
        assertEquals("456", num);

        String num2 = genMock.generate(GameLevel.NORMAL);
        assertEquals("456", num2);
    }


    @DisplayName("인자 매칭 처리 : 임의의 값과 일치하는 인자와 정확하게 일치하는 인자를 함께 사용하고 싶을 때  ")
    @Test
    void mixAnyAndEq() {
        List<String> mockList = mock(List.class);

        given(mockList.set(anyInt(), eq("123"))).willReturn("456");
        String old = mockList.set(5, "123");
        assertEquals("456", old);

    }

    @DisplayName("행위 검증 : 실제로 모의 객체가 불렸는지 확인 ")
    @Test
    void init1() {
        Game game = new Game(genMock);
        game.init(GameLevel.EASY);

        then(genMock).should().generate(GameLevel.EASY);
    }

    @DisplayName("행위 검증 : 실제로 모의 객체가 한번만 아무 인자로 불렸는지 확인 ")
    @Test
    void init2() {
        Game game = new Game(genMock);
        game.init(GameLevel.EASY);

        then(genMock).should(only()).generate(any());
    }

    @DisplayName("인자 캡쳐 (사용한 인자 검증): 가입하면 메일을 전송함")
    @Test
    void sendMail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertEquals("email@email.com", realEmail);
    }


}
