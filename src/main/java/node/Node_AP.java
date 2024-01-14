package node;

import Json.*;
import com.google.gson.JsonObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

import static Json.JsonParser.getLocationValue;


public class Node_AP extends Node {

    public Network network;

    public Node_AP(Network _network) {
        network = _network;
    }

    @Override
    public void drawNode(Graphics2D g, double scale, double oX, double oY) {
        if(network.AP.stateLoacation() == double[].class) {
            double[] location = (double[]) getLocationValue(network.AP.location);
            x = (int) (location[0] * scale + oX);
            y = (int) (location[1] * scale + oY);
            //System.out.printf("[circle] %d, %d",x,y);
            circle = new Ellipse2D.Double(x, y, 5 * scale + 10, 5 * scale + 10);
            if (focusbit) {
                g.drawOval(x, y, (int) (5 * scale), (int) (5 * scale));
                focusbit = false;
            }
            g.fillOval(x, y, (int) (5 * scale), (int) (5 * scale));
            g.setColor(Color.pink);
            int bigCircleRadius = (int) (5 * scale + 100);
            Ellipse2D bigCircle = new Ellipse2D.Double(x - bigCircleRadius / 2, y - bigCircleRadius / 2, bigCircleRadius, bigCircleRadius);
            g.draw(bigCircle);

        } else {
            isRandomLoc = true;
        }
    }

    //@Override
    public JsonObject setData(Map<String,Object> map) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue().toString());
        }
        return jsonObject;
    }

    @Override
    public Map<String,String> getData() {
        Map<String,String> apdata = new LinkedHashMap<>();
        apdata.putIfAbsent("ssid",network.ssid);
        apdata.putIfAbsent("Wifi standard",network.standard);
        if(network.AP.stateLoacation() == double[].class) {
            apdata.putIfAbsent("location", String.valueOf(network.AP.location.getAsJsonArray()));
        } else {
            apdata.putIfAbsent("location", network.AP.location.getAsString());
        }
        int i = 1;
        for (Link l : network.AP.links) {
            apdata.putIfAbsent(String.format("ipv4 Address [%d]",i), l.Ipv4Address);
            apdata.putIfAbsent(String.format("ipv4 Subnet [%d]",i), l.Ipv4Mask);
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

    @Override
    public Object getObject() {
        return this.network;
    }


}
