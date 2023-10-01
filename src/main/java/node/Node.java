package node;

import Json.Network;
import Json.Station;

import java.awt.*;
import java.util.Map;

//바운딩 박스 구현 필요!!!!!!!!!!!1


public abstract class Node {
    public int x;
    public int y;
    public boolean focusbit = false;
    public Shape circle;
    abstract void drawNode(Graphics2D g, double scale, double oX, double oY);
    public abstract Map<String,String> getData();
    //public abstract void setData(Map<String,String> map);
    //객체가 ap일지, sta일지 확인하는 메서드
    public abstract Object getObject();

    public String toString() {
        if (this.getData().get("AP") != null) {
            return String.format("%s | ap",this.getData().get("ssid"));
        } else {
            return String.format("%s | sta", this.getData().get("ssid"));
        }
    }


}
