package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static frame.MainFrame.jsonPath;

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
        try {
            // 실행할 명령어 및 작업 디렉토리 설정
            String command = "~/ns-allinone-3.40/ns-3.40/ns3 run \"wifi-mlms --config="+jsonPath.getText()+"\"";
            String workingDirectory = "";

            // 프로세스 빌더 생성
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            //processBuilder.directory(new java.io.File(workingDirectory));

            // 프로세스 실행
            Process process = processBuilder.start();

            // 프로세스의 출력을 읽어오기 위한 InputStream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int exitCode = process.waitFor();
            // 프로세스의 출력을 읽어옴
            String line;
            StringBuilder output= new StringBuilder();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line);
                // 여기에서 필요한 작업을 수행
            }

            // 프로세스가 끝날 때까지 대기

            System.out.println("프로세스 종료 코드: " + exitCode);

            // 리소스 정리
            reader.close();
            inputStream.close();

            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "오류";
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
