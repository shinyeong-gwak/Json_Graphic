package node;

import Json.*;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedHashMap;
import java.util.Map;

public class Node_Station extends Node {
    public Station station;

    public Node_Station(Station _station) {
        station = _station;
    }

    @Override
    public void drawNode(Graphics2D g, double scale, double oX, double oY) {
        x = (int) (station.location[0] * scale + oX);
        y = (int) (station.location[1] * scale + oY);

        circle = new Ellipse2D.Double(x,y,(5*scale+10),(5*scale+10));
        if (focusbit) {
            g.drawOval(x,y,(int) (5*scale+1),(int) (5*scale+1));
            focusbit = false;
        }
        g.fillOval(x,y,(int) (5*scale+1),(int) (5*scale+1));
    }

    @Override
    public Map<String, String> getData(){
        Map<String,String> stadata = new LinkedHashMap<>();
        stadata.putIfAbsent("ssid",station.ssid);
        stadata.putIfAbsent("location", String.format("[ %.1f, %.1f ]",station.location[0],station.location[1]));
        stadata.putIfAbsent("links",station.band);

        stadata.putIfAbsent("protocol",station.application.protocol);
        stadata.putIfAbsent("dst PortNumber", String.valueOf(station.application.destPort));
        stadata.putIfAbsent("dst Type",station.application.destType);
        stadata.putIfAbsent("protocol",station.application.protocol);
        stadata.putIfAbsent("AC", String.valueOf(station.application.AC));
        stadata.putIfAbsent("Interval", String.valueOf(station.application.interval));
        stadata.putIfAbsent("Packet size", String.valueOf(station.application.packetSize));
        return stadata;
    }

    @Override
    public Object getObject() {
        return this.station;
    }


}
