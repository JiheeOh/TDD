package main.chap07.quiz;

import java.util.ArrayList;
import java.util.List;

public class FakeHistoryRepository implements HistoryRepository{
    private List<History> histories = new ArrayList<>();
    @Override
    public void save(History history) {
        histories.add(history);

    }

    @Override
    public History findbyId(int id) {
        List<History> historyList = histories.stream().filter(x->x.getId() ==id).toList();
        return historyList.get(0);
    }
}
