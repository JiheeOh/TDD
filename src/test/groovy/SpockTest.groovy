import main.chap07.Exception.WeakPassWordException
import main.chap07.UserRegister
import main.chap07.fake.MemoryUserRepository
import main.chap07.spy.EmailNotifier
import main.chap07.stub.WeakPasswordChecker
import spock.lang.Specification

class SpockTest extends Specification {

    def "약한 암호면 가입 실패"(){

        /**
         * Given ('given:'을 사용해도 된다.)
         * 항상 다른 블록보다 먼저 사용해야 하며 setup: 키워드를 생략해도
         * 피처 메서드 처음부터 다른 블록이 나오기 전까지의 코드를 암묵적인 setup: 블록으로 인식한다.
         */
        setup :
        def mockPasswordChecker = Mock(WeakPasswordChecker.class)
        def memoryUserRepository = new MemoryUserRepository()
        def mockEmailNotifier = Mock(EmailNotifier.class)

        def userRegister = new UserRegister(mockPasswordChecker,memoryUserRepository,mockEmailNotifier)

        /**
         * 테스트 하고 싶은 코드를 실행
         */
        when:
        userRegister.register("id","pw","email")

        /**
         * 예외확인, 조건 확인 ,인터렉션 (mocking)을 할 수 있다.
         * 특정 메서드가 몇 번 호출됐는지 확인 가능
         * 이 블록에 작성한 코드 한줄 한줄이 모두 assert 문이다.
         */
        then:
        mockPasswordChecker.checkPasswordWeak("pw") >> true
        thrown(WeakPassWordException.class)
    }


    def "더하기"() {
        expect:
        Math.max(a,b)==c
//  maven에서는 안됨.. 이상하다 gradle에서는 잘된다.
//        where :
//        a | b | c
//        1 | 3| 3
    }


}
