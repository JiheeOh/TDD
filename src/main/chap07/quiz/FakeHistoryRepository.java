package main.chap07.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeHistoryRepository implements HistoryRepository{
    private Map<Integer,History> histories = new HashMap<>();
    @Override
    public void save(History history) {
        histories.put(history.getId(), history);

    }

    @Override
    public History findbyId(int id) {
        return histories.get(id);
    }
}
