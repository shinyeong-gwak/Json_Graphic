package frame;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Json.Network;
import Json.Station;

import node.*;

public class InfoPanel extends JPanel {
    JPanel apSet;
    String showingAp;
    JPanel apPanel;
    CardLayout card;
    JPanel staSet;
    String showingSta;

    List<JPanel> staPanels = new ArrayList<>();
    static JButton save;
    static JButton change;
    static JLabel viewPageNum = new JLabel();
    static JButton prev = new JButton("<");;
    static JButton next = new JButton(">");;
    JButton addThis;
    GridBagConstraints constraintsNet, constraintsTable;

    static JPanel buttonZone;
    GridBagConstraints btc;

    public InfoPanel() {
        setPreferredSize(new Dimension(440, 800));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createDashedBorder(Color.gray));

        //apSet = new JPanel(new CardLayout());

        apPanel = new JPanel(new GridBagLayout() );
        //카드로 바꾸기 - 누르면 해당 카드에 해당하는 sta 카드 페이지와 ap 포커스
        staSet = new JPanel(card = new CardLayout());
        
        //**staPanel = new JPanel(new GridBagLayout());
        constraintsNet = new GridBagConstraints();
        constraintsTable = new GridBagConstraints();
        constraintsNet.gridy=0; constraintsTable.gridy=0;
        constraintsNet.gridx=0; constraintsTable.gridx=0;
        constraintsNet.fill = GridBagConstraints.HORIZONTAL; constraintsTable.fill = GridBagConstraints.HORIZONTAL; // 컴포넌트가 셀을 채우도록 설정
        constraintsNet.anchor = GridBagConstraints.NORTHWEST;
        constraintsNet.gridwidth=3;
        constraintsNet.weightx = 1.0; constraintsTable.weightx = 1.0; // 첫 번째 행의 셀 넓이 비율
        constraintsNet.weighty = 0.01;
        //sta들 모아보기 기능 추가중
        //**staSet.add(staPanel);

        add(new JLabel(""), constraintsNet);
        add(new JLabel("-AP-") {{setBorder(BorderFactory.createDashedBorder(Color.green));}}, constraintsNet);
        constraintsNet.gridy++;//1
        constraintsNet.weighty = 0.3;
        add(apPanel, constraintsNet);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        constraintsNet.gridy++;//2
        constraintsNet.weighty = 0.1;
        add(separator, constraintsNet);
        constraintsNet.gridy++;//3

        add(new JLabel("-Station-"), constraintsNet);
        //constraintsNet.gridy++;//4
        constraintsNet.weighty = 0.4;
        constraintsNet.anchor =GridBagConstraints.SOUTHWEST;

        constraintsNet.gridx=0;
        add(staSet,constraintsNet);

        buttonZone = new JPanel(new GridBagLayout());
        constraintsNet.gridy = 5;
        add(buttonZone,constraintsNet);

        change = new JButton("change NetSet");
        change.addActionListener(e -> changeSetting(apPanel));
        save = new JButton("save Json");
        addThis = new JButton("add this Station");

        constraintsNet.gridwidth=1;
        constraintsNet.gridy++;
        add(addThis,constraintsNet);
        constraintsNet.gridy++;
        add(change,constraintsNet);
        constraintsNet.gridx++;
        add(save,constraintsNet);
        setVisible(true);

    }

    private void changeSetting(JPanel apPanel) {

    }

    public void viewNewNetwork() {

    }

    //    private Map<String,String> cngToMap() {
//        Map<String,String> map = new LinkedHashMap<>();
//        for()
//        String key = .getText();
//        String value = valueField.getText();
//
//        if (!key.isEmpty() && !value.isEmpty()) {
//            dataMap.put(key, value);
//            keyField.setText("");
//            valueField.setText("");
//            System.out.println("Data added to map: Key = " + key + ", Value = " + value);
//        } else {
//            System.out.println("Both Key and Value are required.");
//        }
//        return
//    }
    //최초 호출 시
    public void setInfo(Object obj) {
        JPanel selected = null;

        if (obj == null) {
            System.out.println("객체가 null입니다.");
            return;
        }

        if (obj instanceof Network || obj instanceof Node_AP) {
            selected = apPanel;
            selected.removeAll();
            selected.revalidate();
            if(obj instanceof Node_AP)
                obj = ((Node_AP) obj).network;
        } else if (obj instanceof Station || obj instanceof Node_Station) {
            selected = new JPanel(new GridBagLayout());
            if(obj instanceof Node_Station)
                obj = ((Node_Station) obj).station;
        }
        // JPanel을 초기화

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        constraintsTable.anchor = GridBagConstraints.WEST;

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(obj);
                if (value instanceof List<?>) {
                    // List 객체인 경우
                    List<?> list = (List<?>) value;
                    int i =0;
                    for (Object listItem : list) {
                        if (listItem != null) {
                            String msg = String.format("[%d]",++i);
                            setInfo(listItem,selected,msg); // 재귀 호출
                        }
                    }
                } else if (value.getClass().isArray()) {

                    constraintsTable.gridy++;

                    JLabel label = new JLabel("  "+fieldName);
                    selected.add(label, constraintsTable);
                    int length = java.lang.reflect.Array.getLength(value);
                    JPanel cooP = new JPanel(new GridBagLayout());
                    GridBagConstraints tmp = new GridBagConstraints();
                    tmp.weightx=1;
                    for (int i = 0; i < length; i++) {
                        Object arrayItem = java.lang.reflect.Array.get(value, i);

                        if (arrayItem != null) {
                            JTextField loc = displayArrayElement(fieldName,tmp, arrayItem, i,cooP);
                            cooP.add(loc,tmp);
                            //cs.gridx++;
                        }
                    }
                    constraintsTable.gridx++;
                    selected.add(cooP, constraintsTable);
                    constraintsTable.gridy+=2;
                    constraintsTable.gridx--;
                } else if (value.toString().startsWith("Json.")) {
                    // 내부 클래스인 경우
                    constraintsTable.gridy++;
                    String msg = "   " + fieldName + ".";
                    setInfo(value,selected,msg);
                    // 재귀 호출
                } else {
                    JLabel label = new JLabel("  "+fieldName);
                    JTextField textField = new JTextField(value.toString());
                    textField.setEditable(false);

                    constraintsTable.gridy++;
                    selected.add(label, constraintsTable);

                    constraintsTable.gridx = 1;
                    selected.add(textField, constraintsTable);

                    constraintsTable.gridx = 0;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(obj instanceof Station) {
            staPanels.add(selected);
            staSet.add(selected,String.valueOf(((Station) obj).stationNumber));
            constraintsNet.gridy =4;
            constraintsNet.gridx=0; constraintsNet.gridwidth=3;
            add(staSet, constraintsNet);
            card.show(staSet, String.valueOf(((Station) obj).stationNumber));
            staSet.revalidate();
            viewPageNum.setText(String.format("%d/%d",(((Station) obj).stationNumber), (MainFrame.coordPanel.focusList.size()-1)));
        }
        ButtonPage();
        revalidate(); // 레이아웃 갱신
    }
    //원하는 패널에 재귀 호출 시
    public void setInfo(Object obj,JPanel parent,String msg) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        constraintsTable.anchor = GridBagConstraints.WEST;

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(obj);
                if (value instanceof List<?>) {
                    // List 객체인 경우
                    List<?> list = (List<?>) value;
                    int i=0;
                    for (Object listItem : list) {
                        if (listItem != null) {
                            msg += String.format("[%d]",++i);
                            setInfo(listItem,parent,msg);
                        }
                    }
                } else if (value.getClass().isArray()) {

                    constraintsTable.gridy++;

                    JLabel label = new JLabel("  "+fieldName);
                    parent.add(label, constraintsTable);

                    JPanel cooP = new JPanel(new GridBagLayout());
                    GridBagConstraints tmp = new GridBagConstraints();
                    tmp.weightx=1;
                    int length = java.lang.reflect.Array.getLength(value);
                    for (int i = 0; i < length; i++) {
                        Object arrayItem = java.lang.reflect.Array.get(value, i);

                        if (arrayItem != null) {
                            JTextField loc = displayArrayElement(fieldName,tmp, arrayItem, i,cooP);
                            cooP.add(loc,tmp);
                            //cs.gridx++;
                        }
                    }
                    constraintsTable.gridx++;
                    parent.add(cooP, constraintsTable);
                    constraintsTable.gridy+=2;
                    constraintsTable.gridx--;
                } else if (value.toString().startsWith("Json.")) {
                    // 내부 클래스인 경우
                    constraintsTable.gridy++;
                    msg += "   " + fieldName + ".";
                    setInfo(value,parent,msg);
                    // 재귀 호출
                } else {
                    JLabel label = new JLabel("  "+fieldName);
                    JTextField textField = new JTextField(value.toString());
                    textField.setEditable(false);
                    textField.setPreferredSize(new Dimension(150, textField.getPreferredSize().height)); // 너비 고정

                    constraintsTable.gridy++;
                    parent.add(label, constraintsTable);

                    constraintsTable.gridx = 1;
                    parent.add(textField, constraintsTable);

                    constraintsTable.gridx = 0;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        revalidate(); // 레이아웃 갱신

    }
    private JTextField displayArrayElement(String fieldName,GridBagConstraints gbc, Object arrayItem, int index, JPanel field) {
        JTextField val = new JTextField(arrayItem.toString(),3);

        JLabel text;

        switch(index) {
            case 0:
                text = new JLabel("x:");
                break;
            case 1:
                text = new JLabel("y:");
                break;
            case 2:
                text = new JLabel("z:");
                break;
            default:
                text = null;
        }
        if(text !=null){
            gbc.gridx++;
            field.add(text,gbc);
            gbc.gridx++;

        }
        return val;
    }
    private int getCurrentStaPage() {
        int current=0;
        if(MainFrame.coordPanel.focusNode != null && MainFrame.coordPanel.focusNode.getClass() == Node_Station.class){
            Node_Station focusNode = (Node_Station) (MainFrame.coordPanel.focusNode);
            current = focusNode.station.stationNumber;
            viewPageNum = new JLabel(String.format("%d/%d", current, MainFrame.coordPanel.focusList.isEmpty() ? 0 : (MainFrame.coordPanel.focusList.size() - 1)));
        }

        return current;
    }
    //자주 호출되므로
    public void ButtonPage() {
        constraintsNet.gridwidth=3;
        btc = new GridBagConstraints();
        prev.addActionListener(e -> {
            card.previous(staSet);

        });
        next.addActionListener(e -> {
            card.next(staSet);
        });
        btc.gridwidth=2;
        buttonZone.add( new JPanel() {
            {
                try {
                    add(viewPageNum);
                } catch (NullPointerException ignored){};
                add(prev);
                add(next);
            }
        }, btc);

        buttonZone.revalidate();
        revalidate();

    }
//    public void setInfo(Node node){
//        JPanel selected;
//        GridBagConstraints sc;
//
//        //save.addActionListener(e -> node.setData(cngToMap()));
//
//        if (node instanceof Node_AP) {
//            selected = apPanel;
//            sc = cs;
//        } else {
//            selected = staPanel;
//            sc = cs;
//        }
//        selected.removeAll();
//        selected.revalidate();
//        for (Map.Entry<String, String> entry : node.getData().entrySet()) {
//            //System.out.println(c.gridx+ c.gridy);
//            sc.weightx = 0.01;
//            sc.weighty = 0.01;
//            if(entry.getValue() != null) {
//                sc.gridy++; sc.gridx--;
//                selected.add(new JLabel(entry.getKey()) {{setBorder(BorderFactory.createDashedBorder(Color.green));}}, sc);
//                sc.gridx++;
//                sc.weightx = 0.2;
//                selected.add(new JTextField(entry.getValue()), sc);
//
//            }
//        }


}