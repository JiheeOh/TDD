package main.chap07.fake;

import main.chap07.User;

public interface UserRepository {
    void save(User user);

    User findById(String id);
}
