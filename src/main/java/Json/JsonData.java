package Json;

import java.util.ArrayList;

enum Qos_AC {
    BE,BK,VO,VI,CsT
}
public class JsonData {
    public long simulationTime;
    public ArrayList<Region> regions;
    public ArrayList<CustomAC> CustomAC;
    public ArrayList<Network> networks;
    public ArrayList<Station> stations;
    //access category 추가 필요


}

