package node;

import java.awt.*;
import java.util.Map;

public abstract class Node {
    public boolean focusbit = false;
    abstract void drawNode(Graphics2D g, double scale);
    public abstract Map<String,String> getData();


}
