package Menu;

import Json.JsonData;
import Json.JsonParser;
import frame.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileMenu extends JMenuItem{
    JPanel basePanel;
    public FileMenu(String title,JPanel basePanel) {
        super(title);
        this.basePanel = basePanel;
        JMenuItem open = new OpenMenuItem("Open");
        JMenuItem exit = new ExitMenuItem("Exit");

        add(open);
        add(exit);
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

                    JsonData jsonData = JsonParser.parse(selectedFile);

                    //MainFrame.simTime.setText(MainFrame.simTime.getText() + jsonData.simulationTime);
                    //MainFrame.jsonPath.setText(filePath);


                } else {
                    JOptionPane.showMessageDialog(FileMenu.this, "Selected file is not a JSON file", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

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
