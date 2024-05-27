package main.chap07.quiz;

public interface HistoryRepository {
    void save(History history);
    History findbyId(int id);
}
