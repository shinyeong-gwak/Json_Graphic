package frame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        JPanel panel = new JPanel();
//        JTextField field = new JTextField("여기 입력",20);
//        field.setEditable(true);
//        panel.add(field);
//        frame.add(panel);
//
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

        try {
            // 실행할 명령어 및 작업 디렉토리 설정
            String command = "cat ~/Desktop/scenario-1.json";  // your_file.txt에는 읽고자 하는 파일의 경로가 들어가야 합니다.

            // 프로세스 빌더 생성
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            // 프로세스 실행
            Process process = processBuilder.start();

            // 프로세스의 출력을 읽어오기 위한 InputStream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // 프로세스의 출력을 읽어옴
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                // 여기에서 필요한 작업을 수행
            }

            // 프로세스가 끝날 때까지 대기
            int exitCode = process.waitFor();
            System.out.println("프로세스 종료 코드: " + exitCode);

            // 리소스 정리
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            // IOException이 발생하면 에러 메시지 출력
            e.printStackTrace();
        } catch (InterruptedException e) {
            // InterruptedException이 발생하면 에러 메시지 출력
            e.printStackTrace();
        }
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Zoom Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            JScrollPane scrollPane = new JScrollPane();
//
//            JPanel panel = new JPanel(new BorderLayout()) {
//                private double scale = 1.0; // 초기 축척 값
//
//                @Override
//                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
//
//                    int panelWidth = getWidth();
//                    int panelHeight = getHeight();
//
//                    int dotSize = 10;
//                    int dotX = (panelWidth - dotSize) / 2;
//                    int dotY = (panelHeight - dotSize) / 2;
//
//                    int scaledWidth = (int) (dotSize * scale);
//                    int scaledHeight = (int) (dotSize * scale);
//                    int scaledX = dotX - (scaledWidth - dotSize) / 2; // 점의 중심 유지
//                    int scaledY = dotY - (scaledHeight - dotSize) / 2;
//
//                    g.setColor(Color.RED);
//                    g.fillOval(scaledX, scaledY, scaledWidth, scaledHeight);
//                }
//
//                public void zoomIn() {
//                    scale *= 1.2; // 확대 비율
//                    revalidate();
//                    repaint();
//                }
//
//                public void zoomOut() {
//                    scale /= 1.2; // 축소 비율
//                    revalidate();
//                    repaint();
//                }
//            };
//            panel.setPreferredSize(new Dimension(400, 300));
//
////            JButton zoomInButton = new JButton("Zoom In");
////            zoomInButton.addActionListener(new ActionListener() {
////                @Override
////                public void actionPerformed(ActionEvent e) {
////                    ((Main.MainPanel) panel).zoomIn();
////                }
////            });
////
////            JButton zoomOutButton = new JButton("Zoom Out");
////            zoomOutButton.addActionListener(new ActionListener() {
////                @Override
////                public void actionPerformed(ActionEvent e) {
////                    ((Main.MainPanel) panel).zoomOut();
////                }
////            });
//            JLabel[] labels = {new JLabel("1"),new JLabel("2")};
//            JList<JLabel> jlist = new JList<>(labels);
//            jlist.setLocation(20,60);
//            jlist.setSize(new Dimension(30,30));
//            jlist.addListSelectionListener(e -> System.out.printf("누른거 : %s\n",jlist.getSelectedValue().getText()));
//            frame.add(jlist);
//            JPanel buttonPanel = new JPanel();
//            //buttonPanel.add(zoomInButton);
//            //buttonPanel.add(zoomOutButton);
//
//            panel.add(buttonPanel, BorderLayout.NORTH);
//            scrollPane.setViewportView(panel);
//
//            frame.getContentPane().add(scrollPane);
//            frame.pack();
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });


    }
}