package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static frame.MainFrame.jsonPath;
import static frame.MainFrame.output;

public class SouthPanel extends JPanel {
    private static JTextArea resultTextArea;

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
            }
        });
        JButton run = new JButton("run");
        run.addActionListener(e -> {
            // GUI를 업데이트하기 위한 Swing 쓰레드
            SwingUtilities.invokeLater(() -> showResultInNewWindow());

            // WSL 명령어 실행
            new Thread(() -> setNRun()).start();
        });
        c.gridy = 1;
        c.gridx = 1;
        add(new JLabel("report 저장 위치 : "), c);
        c.weightx = 3;
        c.gridx++;
        add(dirLabel, c);
        c.weightx = 0.3;
        c.gridx = 5;
        add(path, c);
        c.gridx++;
        add(run, c);
        // You can add any additional components here
    }

    private void setNRun() {
        Process process = null;
        try {
            // 실행할 명령어 및 작업 디렉토리 설정
            String command = "\"wifi-mlms --config=" + jsonPath.getText() + "\"";
            String workingDirectory = "/home/mlms/ns-allinone-3.40/ns-3.40/";

            String userHome = System.getProperty("user.home");
            System.out.println("User Home Directory: " + userHome);

            // ProcessBuilder를 사용하여 WSL 명령어 실행
            ProcessBuilder processBuilder = new ProcessBuilder("./ns3" , "run",command);
            processBuilder.directory(new File(workingDirectory));
            process = processBuilder.start();

            // 프로세스의 출력 스트림을 읽어오기 위한 InputStream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // 쓰레드를 사용하여 결과를 실시간으로 처리
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // 결과를 GUI에 업데이트
                        if (resultTextArea != null) {
                            String finalLine = line;
                            SwingUtilities.invokeLater(() -> updateResult(finalLine));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

        // 결과를 실시간으로 업데이트하는 메서드
    private static void updateResult (String line){
        // JTextArea에 결과를 추가
        resultTextArea.append(line + "\n");
    }
    private static void showResultInNewWindow() {
        SwingUtilities.invokeLater(() -> {
            JFrame newFrame = new JFrame("Command Result");
            JPanel newPanel = new JPanel(new BorderLayout());

            resultTextArea = new JTextArea("hi") {{
                setSize(900, 800);
            }};
            resultTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            newPanel.add(scrollPane, BorderLayout.CENTER);

            newFrame.add(newPanel);

            newFrame.setLocation(150, 100);

            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setSize(new Dimension(900, 800));

            newFrame.setVisible(true);
        });
    }

}
