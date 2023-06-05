package frame;

import Json.*;
import node.*;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame{
    public MainFrame() {

        JMenuBar menuBar = new JMenuBar();
        FileMenu fileMenu = new FileMenu("File");
        EditMenu editMenu = new EditMenu("Edit");
        ViewMenu viewMenu = new ViewMenu("View");
        contentPane = new JPanel(new BorderLayout());

        scrollCood = new ScrollPane();
        coordPanel = new CoordPanel();
        scrollPane = new ScrollPane();
        infoPanel = new InfoPanel();

        JButton sco = new JButton("2배");

        setFocusableWindowState(false);
        setLocationRelativeTo(null);

        sco.addActionListener(e -> {coordPanel.setScale(); coordPanel.repaint();});
        contentPane.add(sco,BorderLayout.NORTH);

        scrollCood.setPreferredSize(new Dimension(500,800));
        scrollCood.add(coordPanel);
        contentPane.add(scrollCood, BorderLayout.EAST);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        scrollPane.setPreferredSize(new Dimension(480,800));
        scrollPane.add(infoPanel);
        contentPane.add(scrollPane, BorderLayout.WEST);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);

        add(contentPane);
        setJMenuBar(menuBar);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setVisible(true);

    }
    private static JsonData jsonData; // 추 후 여러 개로 변환 가능성
    public static JPanel contentPane;
    public static CoordPanel coordPanel;
    public static ScrollPane scrollPane;
    public static ScrollPane scrollCood;
    public static InfoPanel infoPanel;
    public static List<Node_AP> nodeAPList;
    public static List<Node_Station> nodeStaList;

    public static void setJsonData(JsonData jsonData) {
        MainFrame.jsonData = jsonData;
        nodeAPList = new ArrayList<>();
        // node_AP 객체를 리스트에 추가
        for (Network network : MainFrame.getJsonData().networks) {
            nodeAPList.add(new Node_AP(network));
        }
        nodeStaList = new ArrayList<>();
        for (Station station : MainFrame.getJsonData().stations) {
            nodeStaList.add(new Node_Station(station));
        }

        if(coordPanel!=null) {
            coordPanel.isRepaintMeth = true;
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
        // Add view menu items here
    }
}