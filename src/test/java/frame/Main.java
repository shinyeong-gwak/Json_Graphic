package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Zoom Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JPanel panel = new JPanel(new BorderLayout()) {
                private double scale = 1.0; // 초기 축척 값

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    int panelWidth = getWidth();
                    int panelHeight = getHeight();

                    int dotSize = 10;
                    int dotX = (panelWidth - dotSize) / 2;
                    int dotY = (panelHeight - dotSize) / 2;

                    int scaledWidth = (int) (dotSize * scale);
                    int scaledHeight = (int) (dotSize * scale);
                    int scaledX = dotX - (scaledWidth - dotSize) / 2; // 점의 중심 유지
                    int scaledY = dotY - (scaledHeight - dotSize) / 2;

                    g.setColor(Color.RED);
                    g.fillOval(scaledX, scaledY, scaledWidth, scaledHeight);
                }

                public void zoomIn() {
                    scale *= 1.2; // 확대 비율
                    revalidate();
                    repaint();
                }

                public void zoomOut() {
                    scale /= 1.2; // 축소 비율
                    revalidate();
                    repaint();
                }
            };

            panel.setPreferredSize(new Dimension(400, 300));

//            JButton zoomInButton = new JButton("Zoom In");
//            zoomInButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    ((Main.MainPanel) panel).zoomIn();
//                }
//            });
//
//            JButton zoomOutButton = new JButton("Zoom Out");
//            zoomOutButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    ((Main.MainPanel) panel).zoomOut();
//                }
//            });

            JPanel buttonPanel = new JPanel();
            //buttonPanel.add(zoomInButton);
            //buttonPanel.add(zoomOutButton);

            panel.add(buttonPanel, BorderLayout.NORTH);
            scrollPane.setViewportView(panel);

            frame.getContentPane().add(scrollPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}