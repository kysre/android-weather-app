package edu.sharif.ce.android_weather_app.Model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Weather {
    private double temperature;
    private double feelsLike;
    private double windSpeed;
    private MainWeather weather;
    private double latitude;
    private double longitude;
    public static ArrayList<Weather> fullWeek;

    public static void start(double longitude, double latitude) {
        String[] details = getDetail(latitude, longitude);
        fullWeek = arrangeData(details);
    }


    public static ArrayList<Weather> arrangeData(String[] data) {
        ArrayList<Weather> answer = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            Weather weather = new Weather();
            weather.setTemperature(Weather.getTemp(data[i]));
            weather.setFeelsLike(Weather.getFeelsLike(data[i]));
            weather.setWindSpeed(Weather.getSpeed(data[i]));
            weather.setWeather(weather.getWeather());
        }
        return answer;
    }

    private static MainWeather getMainWeather(String str){
        int index = str.indexOf("main");
        String answer = "";
        int i = index + 6;
        while (str.charAt(i)!='"'){
            answer+=str.charAt(i);
            i++;
        }
        return MainWeather.valueOf(answer);
    }


    private static double getSpeed(String str) {
        int index = str.indexOf("wind_speed");
        String stringAns = "";
        int i = index + 12;
        while (str.charAt(i) != ',') {
            stringAns += str.charAt(i);
            i++;
        }
        return Double.parseDouble(stringAns);
    }

    private static double getFeelsLike(String str) {
        int index = str.indexOf("day", str.indexOf("day") + 8);
        String stringAns = str.substring(index + 5, index + 10);
        return Double.parseDouble(stringAns) - 273.15;
    }

    private static double getTemp(String str) {
        int forMin = str.indexOf("min");
        int forMax = str.indexOf("max");
        String minTemp = str.substring(forMin + 5, forMin + 10);
        String maxTemp = str.substring(forMax + 5, forMax + 10);
        double realMaxTemp = Double.parseDouble(maxTemp);
        double realMinTemp = Double.parseDouble(minTemp);
        return (realMaxTemp + realMinTemp) / 2 - 273.15;
    }

    public static String[] getDetail(double latitude, double longitude) {
        String API_KEY = "afca478c6d573aea22fc3deaaa3f4d0a";
        String urlString = "https://api.openweathermap.org/data/2.5/onecall?lat="
                + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) +
                "&exclude=hourly&appid=0265754e85cd9098b60a309ef15b99a4";
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String allData = result.toString();
            String[] allDays = allData.split("sunrise");
            return allDays;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Weather> getFullWeek() {
        return fullWeek;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public MainWeather getWeather() {
        return weather;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setTemperature(double temperature) {
         this.temperature = temperature;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWeather(MainWeather weather) {
        this.weather = weather;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
