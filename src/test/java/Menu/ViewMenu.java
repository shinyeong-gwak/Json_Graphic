package Menu;

import Json.CustomAC;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewMenu extends JMenu{
    public ViewMenu(String title) {
        super(title);
        JMenuItem customMenuItem = new CustomMenuItem("CustomAC");
        add(customMenuItem);
    }
    private class CustomMenuItem extends JMenuItem implements ActionListener {

        public CustomMenuItem(String title) {
            super(title);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(MainFrame.isSetJson) {
                ArrayList<CustomAC> customACS =  MainFrame.getJsonData().CustomAC;

                JFrame dataFrame = new JFrame();
                JPanel dataPanel = new JPanel(new GridLayout(7,1));
                for(CustomAC ac : customACS) {
                    JLabel name = new JLabel("name : " + ac.name);
                    JLabel refAC = new JLabel("refAC : " + ac.refAC);
                    JLabel cwmin = new JLabel("CWmin : " + ac.CWmin);
                    JLabel cwmax = new JLabel("CWmax : " + ac.CWmax);
                    JLabel aifsn = new JLabel("AIFSN : " + ac.AIFSN);
                    JLabel txop = new JLabel("MaxTXOP_ms : " + ac.MaxTXOP_ms);
                    dataPanel.add(name);
                    dataPanel.add(refAC);
                    dataPanel.add(cwmin);
                    dataPanel.add(cwmax);
                    dataPanel.add(aifsn);
                    dataPanel.add(txop);

                }
                dataFrame.add(dataPanel);

                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(300, 180*customACS.size());
                dataFrame.setVisible(true);
            }
        }
    }
}
