package main.chap07.quiz;

import java.time.LocalDateTime;

public class TimeBasedGreeting {
    private Times times = new Times();

    public void setTimes(Times times){
        this.times=times;
    }
    public String getGreeting(){
        LocalDateTime now = times.now();
        if(now.getHour() <12){
            return "Good Morning";
        }else if(now.getHour()<18){
            return "Good Afternoon";
        }else {
            return "Good Evening";
        }
    }
}
