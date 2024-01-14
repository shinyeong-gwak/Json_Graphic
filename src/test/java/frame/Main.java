package frame;

import com.google.gson.JsonArray;
import Menu.myMenuBar;
import panels.CoordPanel;
import panels.InfoPanel;
import panels.NorthPanel;
import panels.SouthPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;


public class Main extends JPanel{
    JPanel northPanel;
    JPanel southPanel;
    JPanel infoPanel;
    JPanel coordPanel;
    static JMenuBar menuBar;


    static Dimension frameDim;
    Optional<JsonArray> jsonOptional;


    public Main() {
        setLayout(new BorderLayout());

        //영역 나누기

        Dimension scroll2Dim = new Dimension(frameDim.width/2,frameDim.height-100);
        JScrollPane scroll1 = new JScrollPane(infoPanel) {{setPreferredSize(scroll2Dim);}};
        JScrollPane scroll2 = new JScrollPane(coordPanel) {{setPreferredSize(scroll2Dim);}};
        //scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        //각 영역에 들어갈 패널 생성하기
        northPanel = new NorthPanel();
        southPanel = new SouthPanel();
        infoPanel = new InfoPanel();
        coordPanel = new CoordPanel(this,scroll2Dim);

        add(scroll1, BorderLayout.WEST);
        add(scroll2, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frameDim = new Dimension(1000,800);

        JPanel main;
        frame.add(main = new Main());
        frame.setJMenuBar(menuBar = new myMenuBar(main));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameDim);
        frame.setVisible(true);
    }

    public static boolean isInitialized;

}