package test.chap07;

import main.chap07.Exception.WeakPassWordException;
import main.chap07.UserRegister;
import main.chap07.fake.MemoryUserRepository;
import main.chap07.spy.EmailNotifier;
import main.chap07.stub.WeakPasswordChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserRegisterMockTest {
    /**
     * 기대한 대로 상호작용하는지 행위를 검증한다.
     * 기대한 대로 동작하지 않으면 exception을 발생할 수 있다.
     * 모의 객체는 Stub이자 Spy도 된다.
     */
    private UserRegister userRegister;
    @Mock
    private WeakPasswordChecker mockPasswordChecker;
    private final MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @Mock
    private EmailNotifier mockEmailNotifier;

    @BeforeEach
    void setUp(){
        userRegister = new UserRegister(mockPasswordChecker,memoryUserRepository,mockEmailNotifier);

    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPwd(){
        given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);
        assertThrows(
                WeakPassWordException.class
                ,()-> { userRegister.register("id","pw","email");
        });

        assertThrows(
                WeakPassWordException.class
                ,()-> { userRegister.register("id","pwt","email");
                });

    }

    @DisplayName("회원 가입 시 암호 검사 수행")
    @Test
    void checkPwd(){
       userRegister.register("id","pw","email");
       then(mockPasswordChecker)
               .should()
               .checkPasswordWeak(anyString());
    }

    @DisplayName("메일 전송 시 인자 값 구하기")
    @Test
    void sendMail(){
        userRegister.register("id","pw","email@email.com");

        ArgumentCaptor<String> captor =ArgumentCaptor.forClass(String.class);
        then(mockEmailNotifier)
                .should()
                .sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertEquals("email@email.com",realEmail);
    }









}
