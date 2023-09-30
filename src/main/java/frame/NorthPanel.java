package frame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static frame.MainFrame.coordPanel;

public class NorthPanel extends JPanel {
    public NorthPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;

        add(new JLabel("json 경로 : "),c);
        c.gridx = 2;c.weightx = 1;
        add(MainFrame.jsonPath,c);
        c.gridx++; c.weightx = 0.01;
        MainFrame.viewRaw = new JButton("view");
        MainFrame.viewRaw.addActionListener(e -> {
            if(MainFrame.jsonPath.getText().endsWith(".json")) {
                JFrame dataFrame = new JFrame();
                JPanel dataPanel = new JPanel(new BorderLayout());
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(MainFrame.jsonPath.getText()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                } catch (IOException ignored) {
                }
                JTextArea area = new JTextArea(content.toString()) {{setSize(900,800);}};
                JScrollPane scrollPane = new JScrollPane(area);
                dataPanel.add(scrollPane);
                dataFrame.add(dataPanel,BorderLayout.CENTER);

                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(1000, 800);
                dataFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No json File");
            }
        });
        add(MainFrame.viewRaw,c);
        c.gridx++; c.weightx = 0.6;
        add(new JLabel(" "),c);
        c.gridx = 6; c.weightx = 0.1;
        JButton sco1 = new JButton("-");
        JButton sco2 = new JButton("+");
        sco1.addActionListener(e -> {
            coordPanel.subScale();
            coordPanel.revalidate();
            coordPanel.repaint();
        });
        sco2.addActionListener(e -> {
            coordPanel.addScale();
            coordPanel.revalidate();
            coordPanel.repaint();
        });
        add(sco1,c);
        c.gridx++;
        add(sco2,c);
        // You can add any additional components here
    }
}
