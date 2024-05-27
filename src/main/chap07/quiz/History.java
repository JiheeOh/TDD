package main.chap07.quiz;

import java.time.LocalDateTime;

public class History {
    int id;
    String historyText;
    LocalDateTime insertTime;

    public History(int id, String historyText, LocalDateTime insertTime) {
        this.id = id;
        this.historyText = historyText;
        this.insertTime = insertTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistoryText() {
        return historyText;
    }

    public void setHistoryText(String historyText) {
        this.historyText = historyText;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }
}
