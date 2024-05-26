package main.chap07;

import main.chap07.Exception.DupIdException;
import main.chap07.Exception.WeakPassWordException;

public class UserRegister {
    private WeakPasswordChecker passwordChecker;
    private UserRepository userRepository;

    public UserRegister(WeakPasswordChecker passwordChecker, UserRepository userRepo) {
        this.passwordChecker = passwordChecker;
        this.userRepository = userRepo;
    }

    public void register(String id, String pw, String email) {
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPassWordException();
        }

        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }
        userRepository.save(new User(id,pw,email));
    }
}
