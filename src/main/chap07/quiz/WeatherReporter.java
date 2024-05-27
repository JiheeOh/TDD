package main.chap07.quiz;

public class WeatherReporter {
    private WeatherService weatherService;


    public WeatherReporter(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    public String getWeatherReport(String location){
        return "The weather in".concat(location).concat("is").concat(weatherService.getWeather(location));
    }
}
