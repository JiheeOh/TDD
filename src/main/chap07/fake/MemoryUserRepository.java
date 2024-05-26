package main.chap07.fake;

import main.chap07.User;
import main.chap07.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    /**
     * 제품에는 적합하지 않지만, 실제 동작하는 구현을 제공한다.
     * DB 대신에 메모리를 이용해서 구현하는 것이 예다.
     */
    private Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(),user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}
