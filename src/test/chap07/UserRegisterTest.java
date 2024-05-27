package test.chap07;


import main.chap07.Exception.DupIdException;
import main.chap07.User;
import main.chap07.fake.MemoryUserRepository;
import main.chap07.spy.SpyEmailNotifier;
import main.chap07.stub.StubWeakPasswordChecker;
import main.chap07.UserRegister;
import main.chap07.Exception.WeakPassWordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {
    private UserRegister userRegister;
    private final StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
    private final MemoryUserRepository fakeRepo = new MemoryUserRepository();

    private final SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(stubWeakPasswordChecker, fakeRepo, spyEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패 : Stub 사용 ")
    @Test
    void weakPwd() {
        // 암호가 약하다고 응답하도록 설정
        stubWeakPasswordChecker.setWeak(true);
        assertThrows(WeakPassWordException.class, () -> {
            userRegister.register("id", "pw", "email");
        });
    }

    @DisplayName("동일 ID를 가진 회원 존재 : Fake 사용")
    @Test
    void dupId() {
        // 이미 같은 ID가 존재하는 상황 만들기
        fakeRepo.save(new User("id", "pw1", "email@email.com"));

        assertThrows(DupIdException.class, () -> {
            userRegister.register("id", "pw2", "email");
        });
    }

    @DisplayName("같은 ID가 없으면 가입 성공함 : Fake 사용")
    @Test
    void regiDiffId() {
        userRegister.register("id", "pw", "email");

        User savedUser = fakeRepo.findById("id");
        assertEquals("id", savedUser.getId());
        assertEquals("email", savedUser.getEmail());
    }

    @DisplayName("가입하면 이메일 발송여부를 확인 : Spy 사용")
    @Test
    void checkEmail() {
        userRegister.register("id", "pw", "email@email.com");
        assertTrue(spyEmailNotifier.isCalled());
        assertEquals("email@email.com", spyEmailNotifier.getEmail());

    }


}
