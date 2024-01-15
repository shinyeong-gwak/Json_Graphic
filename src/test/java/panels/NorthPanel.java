package panels;

import frame.MainFrame;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static frame.Main.isInitialized;
import static frame.MainFrame.coordPanel;
import static frame.MainFrame.simTime;

public class NorthPanel extends JPanel {
    public JTextField viewJsonPath = new JTextField();
    JButton viewRawFile;
    public JLabel viewSimulationTime;

    public NorthPanel(JPanel basePanel) {
        setLayout(new GridBagLayout());

        //layout관리...
        GridBagConstraints c = new GridBagConstraints();

        //open한 json파일이 어디 경로에 있는지 보는 곳
        c.gridy = 1;c.gridx = 1;c.fill = GridBagConstraints.BOTH;
        add(new JLabel("json 경로 : "),c);

        c.gridx = 2;c.weightx = 1;
        viewJsonPath.setEditable(false);
        add(viewJsonPath,c);

        c.gridx++; c.weightx = 0.01;
        viewRawFile = new JButton("view");
        //vew버튼 누르면 새 창 띄워서 json 파일 내용 띄워주기.
        viewRawFile.addActionListener(new newRawJsonViewer(basePanel));
        add(viewRawFile,c);

        c.gridy=2; c.gridx = 1;//아랫줄에 시뮬 시간
        viewSimulationTime = new JLabel("Simulation Time : ");
        if(isInitialized){ simTime.setText(simTime.getText()+MainFrame.getJsonData().simulationTime); }
        add(viewSimulationTime, c);

        c.gridy=1;c.gridx=3; c.weightx = 0.6;
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


    }

    private class newRawJsonViewer implements ActionListener {
        JPanel basePanel;
        public newRawJsonViewer(JPanel basePanel) {
            this.basePanel = basePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(viewJsonPath.getText().endsWith(".json")) {
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
                JOptionPane.showMessageDialog(basePanel, "No json File");
            }
        }
    }
}