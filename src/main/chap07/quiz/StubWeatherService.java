package main.chap07.quiz;

public class StubWeatherService extends WeatherService {
    private String location;
    private String weather;

    public void setLocation(String location){
        this.location = location;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }

    @Override
    public String getWeather(String location) {
        return weather;
    }
}
