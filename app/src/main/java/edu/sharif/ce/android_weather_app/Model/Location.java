package edu.sharif.ce.android_weather_app.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Location {
    private double longitude;
    private double latitude;
    public static boolean isThereCity=true;

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Location findCoordinate(String cityName){
        String API_KEY = "e9eef37fb1d6417d9d14ae392d82ffb6";
        String encodeCityName = null;
        try {
            encodeCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String urlString = "https://api.opencagedata.com/geocode/v1/json?q="+encodeCityName+"&key="+API_KEY;
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
            return finding(result.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Location finding(String str) {
        int index = str.indexOf("bounds");
        int startIndexLat = str.indexOf("lat", index);
        int startIndexLng = str.indexOf("lng", index);
        if(startIndexLat==-1){
            isThereCity=false;
            return null;
        }
        int i = startIndexLat+5;
        String lat="";
        while (str.charAt(i) != ',') {
            lat+=str.charAt(i);
            i++;
        }
        String lng = "";
        i = startIndexLng+5;
        while (str.charAt(i) != '}') {
            lng+=str.charAt(i);
            i++;
        }
        Location location = new Location(Double.parseDouble(lng) , Double.parseDouble(lat));
        return location;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
