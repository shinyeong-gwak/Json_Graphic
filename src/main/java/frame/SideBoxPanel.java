package frame;

import javax.swing.*;

public class SideBoxPanel extends JPanel {
    JList nodeNames;
    public SideBoxPanel(int x, int y, int w, int h) {
        nodeNames = new JList();

        add(nodeNames);
        MainFrame.coordPanel.add(this);
    }

}
