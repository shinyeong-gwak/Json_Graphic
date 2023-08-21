package frame;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;

import node.*;

public class InfoPanel extends JPanel {
    JPanel apPanel;
    JPanel staSet;
    JPanel staPanel;
    JButton save;
    JLabel pageNum;
    JButton prev;
    JButton next;
    GridBagConstraints ca,cs;
    CardLayout card;

    public InfoPanel() {
        setPreferredSize(new Dimension(460, 800));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createDashedBorder(Color.gray));

        apPanel = new JPanel(new GridBagLayout());
        //카드로 바꾸기 - 누르면 해당 카드에 해당하는 sta 카드 페이지와 ap 포커스
        staPanel = new JPanel(new GridBagLayout());
        staSet = new JPanel(card = new CardLayout());

        save = new JButton("save Json");

//        GridBagConstraints c1 = new GridBagConstraints();
//        c1.insets = new Insets(0,0,0,0);
//        c1.gridx = 1;
//        c1.gridy = 1;
//        c1.anchor = GridBagConstraints.WEST;
//        c1.fill = GridBagConstraints.CENTER;

// 컴포넌트 추가 및 제약 조건 설정
        ca = new GridBagConstraints();
        cs = new GridBagConstraints();
        ca.gridy=0; cs.gridy=0;
        ca.gridx=1; cs.gridx=1;
        ca.fill = GridBagConstraints.HORIZONTAL; cs.fill = GridBagConstraints.HORIZONTAL; // 컴포넌트가 셀을 채우도록 설정
        ca.anchor = GridBagConstraints.NORTHWEST;
        ca.weightx = 1.0; cs.weightx = 1.0; // 첫 번째 행의 셀 넓이 비율
        ca.weighty = 0.01;
        //sta들 모아보기 기능 추가중
        staSet.add(staPanel);
        //시험
        JPanel temp = new JPanel();
        temp.add(new JLabel("test"));
        staSet.add(temp);

        add(new JLabel(""),ca);
        add(new JLabel("-AP-") {{setBorder(BorderFactory.createDashedBorder(Color.green));}},ca);
        ca.gridy++;
        ca.weighty = 1.0;
        apPanel.setBorder(BorderFactory.createDashedBorder(Color.red));
        add(apPanel,ca);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        ca.gridy++;
        ca.weighty = 0.01;
        add(separator,ca);
        ca.gridy++;
        add(new JLabel("-Station-"),ca);
        ca.gridy++;
        ca.weighty = 1.0;
        add(staSet,ca);
        ca.weighty = 0.01;
        ca.gridy++;
        ca.anchor =GridBagConstraints.SOUTHWEST;

        pageNum = new JLabel("1/5");
        prev = new JButton("<");
        prev.addActionListener(e -> card.previous(staSet));
        next = new JButton(">");
        next.addActionListener(e -> card.next(staSet));

        add(new JPanel() {{
            add(pageNum);
            add(prev);
            add(next);
        }
        },ca);
        ca.gridy++;
        add(save,ca);

        ca.gridx--;
        setVisible(true);

    }


    public void setInfo(Node node){
        JPanel selected;
        GridBagConstraints sc;

        if (node instanceof Node_AP) {
            selected = apPanel;
            sc = cs;
        } else {
            selected = staPanel;
            sc = cs;
        }
        selected.removeAll();
        selected.revalidate();
        for (Map.Entry<String, String> entry : node.getData().entrySet()) {
            //System.out.println(c.gridx+ c.gridy);
            sc.weightx = 0.01;
            sc.weighty = 0.01;
            if(entry.getValue() != null) {
                sc.gridy++; sc.gridx--;
                selected.add(new JLabel(entry.getKey()) {{setBorder(BorderFactory.createDashedBorder(Color.green));}}, sc);
                sc.gridx++;
                sc.weightx = 0.2;
                selected.add(new JTextField(entry.getValue()), sc);

            }
        }

    }
}