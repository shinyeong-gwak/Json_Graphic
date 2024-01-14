package data;

import Json.Network;
import Json.Region;
import Json.Station;

import java.util.ArrayList;
import java.util.HashMap;


//객체의 키는 이름, 값은 객체
public class JsonData {
    public long simulationTime;
    public int seeds;
    public HashMap<String,Region> regionsMap;
    public HashMap<String,CustomAC> CustomAC;
    public HashMap<String,Network> networks;
    public HashMap<String,Station> stations;
}
