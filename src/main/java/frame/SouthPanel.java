package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SouthPanel extends JPanel {
    public SouthPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

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
            MainFrame.output = setNRun();
            showResultInNewWindow(MainFrame.output);
        });
        c.gridy =1;
        c.gridx = 1;
        add(new JLabel("report 저장 위치 : "),c);
        c.weightx = 3;
        c.gridx ++;
        add(dirLabel,c);
        c.weightx = 0.3;
        c.gridx = 5;
        add(path,c);
        c.gridx++;
        add(run,c);

        // You can add any additional components here
    }
    private String setNRun() {

        if (MainFrame.jsonPath.getText().isEmpty()) {
            System.out.println("error!");
        }
        try {
            Process process = new ProcessBuilder("bash", "-c", Command.CMD.get())
                    .directory(null) // 상위 경로에서 실행하도록 설정
                    .start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return output.toString();
            } else {
                return "Error executing command.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Exception occurred: " + e.getMessage();
        }

    }
    private void showResultInNewWindow(String result) {
        JFrame newFrame = new JFrame("Command Result");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setPreferredSize(new Dimension(800,600));

        JTextArea resultTextArea = new JTextArea(result);
        resultTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        newFrame.add(scrollPane);

        newFrame.pack();
        newFrame.setVisible(true);
    }
}
