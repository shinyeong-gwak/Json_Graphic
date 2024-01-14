package node;

import Json.*;
import com.google.gson.JsonObject;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedHashMap;
import java.util.Map;

import static Json.JsonParser.getLocationValue;

public class Node_Station extends Node {
    public Station station;

    public Node_Station(Station _station) {
        station = _station;
    }

    @Override
    public void drawNode(Graphics2D g, double scale, double oX, double oY) {
        if(station.stateLoacation() == double[].class) {
            double[] location = (double[]) getLocationValue(station.location);
            x = (int) (location[0] * scale + oX);
            y = (int) (location[1] * scale + oY);

            circle = new Ellipse2D.Double(x, y, (5 * scale + 10), (5 * scale + 10));
            if (focusbit) {
                g.drawOval(x, y, (int) (5 * scale + 1), (int) (5 * scale + 1));
                focusbit = false;
            }
            g.fillOval(x, y, (int) (5 * scale + 1), (int) (5 * scale + 1));
        } else {
            isRandomLoc = true;
        }
    }


    public JsonObject setData(Map<String,Object> map) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue().toString());
        }
        return jsonObject;
    }
    @Override
    public Map<String, String> getData() throws NullPointerException{
        Map<String,String> stadata = new LinkedHashMap<>();
        stadata.putIfAbsent("ssid",station.ssid);
        if(station.stateLoacation() == double[].class) {
            stadata.putIfAbsent("location", String.valueOf(station.location.getAsJsonArray()));
        } else {
            stadata.putIfAbsent("location", station.location.getAsString());
        }
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
