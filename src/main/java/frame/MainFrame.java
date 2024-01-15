package frame;

import Json.*;
import node.*;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame{
    public MainFrame() {

        jsonPath = new JTextField();

        menuBar = new JMenuBar();
        FileMenu fileMenu = new FileMenu("File");
        EditMenu editMenu = new EditMenu("Edit");
        ViewMenu viewMenu = new ViewMenu("View");
        contentPane = new JPanel(new BorderLayout());

        infoPanel = new InfoPanel();
        JScrollPane scrollPane = new JScrollPane(infoPanel) {{setPreferredSize(new Dimension(480,3000));}};
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        coordPanel = new CoordPanel();
        northPanel = new NorthPanel();
        southPanel = new SouthPanel();



        JScrollPane coordScroll = new JScrollPane(coordPanel) {{setPreferredSize(new Dimension(500,700));}};
        contentPane.add(coordScroll, BorderLayout.EAST);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        contentPane.add(scrollPane, BorderLayout.WEST);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);


        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(northPanel, BorderLayout.NORTH);


        add(contentPane);
        setJMenuBar(menuBar);

        //테스트용 코드 완성 시 없애기!!!!!
//        String filePath = "";
//        File selectedFile = new File(filePath);
//        JsonData jsonData = JsonParser.parse(selectedFile);
//        MainFrame.setJsonData(jsonData);
//        MainFrame.jsonPath.setText(filePath);
        //테스트 완료 시 해제

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == Frame.MAXIMIZED_BOTH) {
                    Dimension newSize = new Dimension(getWidth()-infoPanel.getWidth()-40, getHeight());
                    coordScroll.setPreferredSize(newSize);
                    coordScroll.revalidate();
                } else if (e.getNewState() == Frame.NORMAL) {
                    Dimension newSize = new Dimension(getWidth()/2-40, getHeight());
                    coordScroll.setPreferredSize(newSize);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 100);
        frameDim = new Dimension(1000,800);
        setSize(frameDim);
        setVisible(true);

    }

    //public static JLabel setFileName() {

    //}

    public static Dimension frameDim;
    private static JsonData jsonData; // 추 후 여러 개로 변환 가능성
    public static JsonData newJson;
    public static JPanel contentPane;
    public static JMenuBar menuBar;
    public static CoordPanel coordPanel;
    public static JPanel northPanel;
    public static JPanel southPanel;
    public static InfoPanel infoPanel;
    public static JTextField jsonPath;
    public static JButton viewRaw;
    public static JLabel simTime;
    public static JLabel reportPath;
    public static String output;
    public static List<Node_AP> nodeAPList;
    public static List<Node_Station> nodeStaList;
    public static boolean isSetJson = false;

    public static void setJsonData(JsonData jsonData) {
        MainFrame.jsonData = jsonData;
        nodeAPList = new ArrayList<>();
        // node_AP 객체를 리스트에 추가
        for (Network network : getJsonData().networks) {
            nodeAPList.add(new Node_AP(network));
        }
        nodeStaList = new ArrayList<>();
        for (Station station : getJsonData().stations) {
            nodeStaList.add(new Node_Station(station));
        }

        if(coordPanel!=null) {
            coordPanel.isRepaintMeth = true;
            isSetJson = true;
            coordPanel.repaint();
        }

    }

    public static JsonData getJsonData() {
        return jsonData;
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    public static List<Node> findSameNet(String ssid) {
        List<Node> result = new ArrayList<>();
        for(Node n : nodeAPList)
            if(n.getData().get("ssid").equals(ssid))
                result.add(n);
        for(Node n : nodeStaList)
            if(n.getData().get("ssid").equals(ssid))
                result.add(n);
        return result;

    }
}

class FileMenu extends JMenu {

    public FileMenu(String title) {
        super(title);
        JMenuItem openMenuItem = new OpenMenuItem("Open");
        JMenuItem saveMenuItem = new SaveMenuItem("Save");
        JMenuItem exitMenuItem = new ExitMenuItem("Exit");

        add(openMenuItem);
        add(saveMenuItem);
        add(exitMenuItem);
    }

    private class OpenMenuItem extends JMenuItem implements ActionListener {

        public OpenMenuItem(String title) {
            super(title);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(FileMenu.this);

            if (result == JFileChooser.APPROVE_OPTION) {


                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();


                if (filePath.endsWith(".json")) {
                    // Open JSON file logic here
                    // Parse the JSON file using JsonParser
                    JsonData jsonData = JsonParser.parse(selectedFile);
                    // Set the parsed data in MainFrame
                    MainFrame.setJsonData(jsonData);
                    MainFrame.simTime.setText(MainFrame.simTime.getText() + jsonData.simulationTime);
                    MainFrame.jsonPath.setText(filePath);


                } else {
                    JOptionPane.showMessageDialog(FileMenu.this, "Selected file is not a JSON file", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }

    private class SaveMenuItem extends JMenuItem implements ActionListener {
        public SaveMenuItem(String title) {
            super(title);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Save file logic here
        }
    }

    private class ExitMenuItem extends JMenuItem implements ActionListener {
        public ExitMenuItem(String title) {
            super(title);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


}

class EditMenu extends JMenu {
    public EditMenu(String title) {
        super(title);
        // Add edit menu items here
    }
}

class ViewMenu extends JMenu {
    public ViewMenu(String title) {
        super(title);
        JMenuItem customMenuItem = new CustomMenuItem("CustomAC");
        add(customMenuItem);
        // Add view menu items here
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