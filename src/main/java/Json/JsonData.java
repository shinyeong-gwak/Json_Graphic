package Json;

import java.util.List;

enum Qos_AC {
    BE,BK,VO,VI,CsT
}
public class JsonData {
    public long simulationTime;
    public List<Network> networks;
    public List<Station> stations;
    //access category 추가 필요

}

