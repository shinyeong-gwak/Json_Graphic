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
        class NorthPanel extends JPanel {
            public NorthPanel() {
                setLayout(new GridBagLayout());

                add(new JLabel("json 경로 : "));
                add(jsonPath);
                for (int i = 0; i < 2; i++) {
                    add(new JLabel());
                }

                JButton sco = new JButton("x2");
                sco.addActionListener(e -> {
                    coordPanel.setScale();
                    coordPanel.repaint();
                });
                add(sco);

                // You can add any additional components here
            }
        }
        class SouthPanel extends JPanel {
            public SouthPanel() {
                setLayout(new GridLayout(1, 7));

                JButton path = new JButton("Path");
                JLabel dirLabel = new JLabel();
                path.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 디렉토리 선택 모드 설정
                        int returnValue = fileChooser.showOpenDialog(null); // 경로 선택 창 열기

                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File selectedDirectory = fileChooser.getSelectedFile();
                            String dirPath = selectedDirectory.getAbsolutePath();
                            dirLabel.setText(dirPath);

                            //JOptionPane.showMessageDialog(null, "Selected Directory Path: " + dirPath);
                        }
                    }});
                JButton run = new JButton("run");
                run.addActionListener(e -> {
                    setNRun();
                });
                add(new JLabel());
                add(new JLabel());
                add(new JLabel());
                add(new JLabel("report 저장 위치 : "));
                add(dirLabel);
                add(path);
                add(run);

                // You can add any additional components here
            }
        }
        jsonPath = new JLabel();

        JMenuBar menuBar = new JMenuBar();
        FileMenu fileMenu = new FileMenu("File");
        EditMenu editMenu = new EditMenu("Edit");
        ViewMenu viewMenu = new ViewMenu("View");
        contentPane = new JPanel(new BorderLayout());

        coordPanel = new CoordPanel();
        infoPanel = new InfoPanel();
        northPanel = new NorthPanel();
        southPanel = new SouthPanel();

        setFocusableWindowState(false);
        setLocationRelativeTo(null);

        contentPane.add(coordPanel, BorderLayout.EAST);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        contentPane.add(infoPanel, BorderLayout.WEST);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);


        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(northPanel, BorderLayout.NORTH);

        add(contentPane);
        setJMenuBar(menuBar);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setVisible(true);

    }

    //public static JLabel setFileName() {

    //}

    private void setNRun() {
    }

    private static JsonData jsonData; // 추 후 여러 개로 변환 가능성
    public static JPanel contentPane;
    public static CoordPanel coordPanel;
    public static JPanel northPanel;
    public static JPanel southPanel;
    public static InfoPanel infoPanel;
    public static JLabel jsonPath;
    public static JLabel reportPath;
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
        // Add view menu items here
    }
}