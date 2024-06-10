package test.chap10;

import main.chap07.UserRegister;
import main.chap07.fake.UserRepository;
import main.chap07.spy.EmailNotifier;
import main.chap07.stub.WeakPasswordChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ExTest {
    private UserRegister userRegister;
    @Mock
    private WeakPasswordChecker mockPasswordChecker;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailNotifier emailNotifier;

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker, userRepository, emailNotifier);
    }

    @DisplayName("회원 가입시 암호 검사 수행함")
    @Test
    void checkPassword() {
        userRegister.register("id", "pw", "email");

        BDDMockito.then(mockPasswordChecker).should().checkPasswordWeak(BDDMockito.anyString());

    }

   


}
