package node;

import Json.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedHashMap;
import java.util.Map;


public class Node_AP extends Node {
    Network network;

    public Shape circle;

    public Node_AP(Network _network) {
        network = _network;
    }

    @Override
    public void drawNode(Graphics2D g, double scale) {

        int x = (int) (network.AP.location[0] * scale);
        int y = (int) (network.AP.location[1] * scale);
        //System.out.printf("[circle] %d, %d",x,y);
        circle = new Ellipse2D.Double(x,y,5*scale,5*scale);
        if (focusbit) {
            g.drawOval(x,y, (int) (5*scale+1),(int) (5*scale+1));
            focusbit = false;
        }
        g.fill(circle);
    }

    @Override
    public Map<String,String> getData() {
        Map<String,String> apdata = new LinkedHashMap<>();
        apdata.putIfAbsent("ssid",network.ssid);
        apdata.putIfAbsent("Wifi standard",network.standard);
        apdata.putIfAbsent("location", String.format("[ %.1f, %.1f ]",network.AP.location[0],network.AP.location[1]));
        int i = 1;
        for (Link l : network.AP.links) {
            apdata.putIfAbsent("ipv4 Address", l.Ipv4Address);
            apdata.putIfAbsent("ipv4 Subnet", l.Ipv4Mask);
            apdata.putIfAbsent(String.format("band [%d]",i++), l.band);
        }
        for (APApplication app : network.AP_applications) {
            apdata.putIfAbsent(String.format("%s - %s : interval" ,app.band,app.protocol),String.valueOf(app.interval));
            apdata.putIfAbsent(String.format("%s - %s : type" ,app.band,app.protocol),app.destType);
            apdata.putIfAbsent(String.format("%s - %s : AC" ,app.band,app.protocol), String.valueOf(app.AC));
            apdata.putIfAbsent(String.format("%s - %s : Packet Size" ,app.band,app.protocol), String.valueOf(app.packetSize));
            //random 구현 이후 관련 매핑 추가

        }

        return apdata;

    }


}
