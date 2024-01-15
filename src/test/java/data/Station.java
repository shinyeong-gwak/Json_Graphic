package data;

import Json.Application;
import com.google.gson.JsonElement;

public class Station {
    public int stationNumber;
    public String ssid;
    public String band;
    public JsonElement location;
    //public boolean randomLocation;
    public Application application;

    public int getStationNumber() {
        return stationNumber;
    }

    public Class<?> stateLoacation() {
        if(location.isJsonArray()) {
            return double[].class;
        } else if (location.isJsonPrimitive()) {
            return String.class;
        } else {
            System.out.println("이 객체는 아닐텐데...");
            throw new NullPointerException();
        }
    }


}
