package main.chap07;

public class StubWeakPasswordChecker implements WeakPasswordChecker {
    /**
     * Stub
     * 구현을 단순한 것으로 대체한다.
     * 테스트에 맞게 단순히 원하는 동작을 수행한다.
     */

    private boolean weak;

    public void setWeak(boolean b) {
        this.weak = weak;
    }

    @Override
    public boolean checkPasswordWeak(String pw) {
        return !weak;
    }
}
