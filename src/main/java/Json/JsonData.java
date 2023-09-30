package Json;

import java.util.ArrayList;
import java.util.List;

enum Qos_AC {
    BE,BK,VO,VI,CsT
}
public class JsonData {
    public long simulationTime;
    public ArrayList<Network> networks;
    public ArrayList<Station> stations;
    //access category 추가 필요


}

