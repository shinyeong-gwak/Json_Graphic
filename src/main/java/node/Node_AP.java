package node;

import Json.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Node_AP extends Node {
    public Network network;

    public Node_AP(Network _network) {
        network = _network;
    }

    @Override
    public void drawNode(Graphics2D g, double scale, double oX, double oY) {

        x = (int) (network.AP.location[0] * scale + oX);
        y = (int) (network.AP.location[1] * scale + oY);
        //System.out.printf("[circle] %d, %d",x,y);
        circle = new Ellipse2D.Double(x,y,5*scale+10,5*scale+10);
        if (focusbit) {
            g.drawOval(x,y, (int) (5*scale),(int) (5*scale));
            focusbit = false;
        }
        g.fillOval(x,y,(int) (5*scale), (int) (5*scale));
        g.setColor(Color.pink);
        int bigCircleRadius = (int) (5 * scale + 100);
        Ellipse2D bigCircle = new Ellipse2D.Double(x - bigCircleRadius / 2, y - bigCircleRadius / 2, bigCircleRadius, bigCircleRadius);
        g.draw(bigCircle);
    }

    //@Override
    public void setData(Map<String,String> map) {
        map.forEach((k,v) -> {
            Pattern pattern = Pattern.compile("\\[\\d+\\]");
            Matcher matcher = pattern.matcher(k);

            if (k.equals("ssid")) {
                network.ssid = v;
            } else if (k.equals("Wifi standard")) {
                network.standard = v;
            } else if (k.equals("location")) {
                String tmp = v.replaceAll("[\\[\\],]", "");
                network.AP.location = Arrays.stream(tmp.split(",")).mapToDouble(s -> Double.parseDouble(s)).toArray();
            } else {
                List<Link> newLinks = new ArrayList<>();
                while(matcher.find()) {
                    if(k.contains("ipv4 Address")) {

                    } else if(k.contains("ipv4 Subnet")) {

                    } else if(k.contains("band")) {

                    }
                    network.AP.links = newLinks;
                }
            }
        });
    }

    @Override
    public Map<String,String> getData() {
        Map<String,String> apdata = new LinkedHashMap<>();
        apdata.putIfAbsent("ssid",network.ssid);
        apdata.putIfAbsent("Wifi standard",network.standard);
        apdata.putIfAbsent("location", String.format("[ %.1f, %.1f ]",network.AP.location[0],network.AP.location[1]));
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


}
