package frame;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

import node.*;

public class InfoPanel extends JPanel {
    GridBagConstraints constraints;

    public InfoPanel() {
        setPreferredSize(new Dimension(460, 800));
        setLayout(new GridBagLayout());

// 컴포넌트 추가 및 제약 조건 설정
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH; // 컴포넌트가 셀을 채우도록 설정
        constraints.weightx = 1.0; // 첫 번째 행의 셀 넓이 비율
        setVisible(true);

    }

    public void setInfo(Node node){
        removeAll();
        if (node instanceof Node_AP) {
            constraints.anchor = GridBagConstraints.NORTHWEST;
        }
        if (node instanceof Node_Station) {
            constraints.anchor = GridBagConstraints.SOUTHWEST;
        }
        for (Map.Entry<String, String> entry : node.getData().entrySet()) {
            constraints.gridy++;
            System.out.println(constraints.gridx+constraints.gridy);
            constraints.weightx = 0.05;
            if(entry.getValue() != null) {
                add(new JLabel(entry.getKey()), constraints);
                constraints.weightx = 0.2;
                add(new JTextField(entry.getValue()), constraints);
            }
        }
        revalidate();
        repaint();

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        constraints.gridy++;
        add(separator, constraints);
        constraints.gridy++;
    }
}