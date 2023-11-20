package frame;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
    static boolean buttonCreated = false;
    static JPanel buttonZone;

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
        add(new JLabel("-AP-"), constraintsNet);
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

        change = new JButton("change NetSetting");
        change.addActionListener(e -> changeSetting(apPanel));
        save = new JButton("save Json");
        addThis = new JButton("add this Station");

        constraintsNet.gridwidth=1;
        constraintsNet.gridy++;
        buttonZone.add(addThis,constraintsNet);constraintsNet.gridy++;
        addThis.addActionListener(e -> SaveTheseNodeInData(apPanel));
        buttonZone.add(change,constraintsNet);constraintsNet.gridx++;
        buttonZone.add(save,constraintsNet);
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
                obj = ((Node_Station) obj).getObject();
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
                    textField.setEditable(true);

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

        if(obj instanceof Station sta) {
            staPanels.add(selected);
            staSet.add(selected, sta.ssid + sta.stationNumber); // card에서 이름은 ssid+넘버로 구별
            constraintsNet.gridy =4;
            constraintsNet.gridx=0; constraintsNet.gridwidth=3;
            add(staSet, constraintsNet);
            card.show(staSet, sta.ssid+sta.stationNumber);
            staSet.revalidate();
            viewPageNum.setText(String.format("%d/%d",sta.stationNumber, (MainFrame.coordPanel.focusList.size()-1)));
            buttonZone.revalidate();
        }
        if(!buttonCreated) {
            ButtonPage();
            buttonCreated = true;
        }
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
                    textField.setEditable(true);
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
        val.setEditable(true);

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
            current = getCurrentStaPage();
            viewPageNum = new JLabel(String.format("%d/%d", current, MainFrame.coordPanel.focusList.isEmpty() ? 0 : (MainFrame.coordPanel.focusList.size() - 1)));
        }

        return current;
    }
    //1번만 호출됨
    public void ButtonPage() {
        constraintsNet.gridwidth=3;
        GridBagConstraints btc = new GridBagConstraints();
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
                    //add(viewPageNum);
                } catch (NullPointerException ignored){};
                add(prev);
                add(next);
            }
        }, btc);

        buttonZone.revalidate();
        revalidate();

    }

    public void SaveTheseNodeInData(JPanel nodePanel) {
        // CoordPanel.focusList를 순회하면서 Node_AP를 찾고 필드를 업데이트합니다.
        for (Node node : MainFrame.coordPanel.focusList) {
            if (node instanceof Node_AP) {
                Node_AP nodeAP = (Node_AP) node;
                Object networkObject = nodeAP.getObject(); // Node_AP의 'network' 필드 값 가져오기

                // nodePanel 내부의 컴포넌트를 확인하고 라벨과 텍스트 필드를 처리합니다.
                for (Component component : nodePanel.getComponents()) {
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        String labelText = label.getText().trim(); // 라벨의 텍스트 가져오기

                        if (networkObject != null) {
                            Field[] fields = networkObject.getClass().getFields();
                            for (Field field : fields) {
                                String fieldName = field.getName();

                                // labelText와 public 필드 이름을 비교하여 일치하는 경우 JTextField의 값을 업데이트합니다.
                                if (labelText.equals(fieldName)) {
                                    for (Component innerComponent : nodePanel.getComponents()) {
                                        if (innerComponent instanceof JTextField) {
                                            JTextField textField = (JTextField) innerComponent;
                                            textField.setEditable(true);

                                            // JTextField의 값을 업데이트합니다.
                                            updateNetworkField(networkObject, fieldName, textField.getText());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        MainFrame.coordPanel.focusList.stream().forEach({
//                node -> {
//                    if(node instanceof Node_AP ap)
//                        System.out.println(ap.network.toString());
//                }
//        } );
    }

    // networkObject의 필드를 업데이트하는 메서드
    private void updateNetworkField(Object networkObject, String fieldName, String value) {
        try {
            Field field = networkObject.getClass().getField(fieldName);

            // 필드의 데이터 유형에 따라 적절한 변환을 수행하고 값을 설정합니다.
            if (field.getType() == String.class) {
                field.set(networkObject, value);
            } else if (field.getType() == Integer.class || field.getType() == int.class) {
                int intValue = Integer.parseInt(value);
                field.set(networkObject, intValue);
            } else if (field.getType() == Double.class || field.getType() == double.class) {
                double doubleValue = Double.parseDouble(value);
                field.set(networkObject, doubleValue);
            }
            // 필요한 경우 더 많은 데이터 유형을 처리합니다.

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void updateObjectFromUI(Object obj, JPanel parent) {
        // 패널 내의 모든 컴포넌트를 검사
        for (Component component : parent.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String labelText = label.getText().trim();

                // JLabel의 텍스트를 기반으로 필드를 찾음
                Field field = findFieldInObject(obj, labelText);

                if (field != null) {
                    for (Component innerComponent : parent.getComponents()) {
                        if (innerComponent instanceof JTextField) {
                            JTextField textField = (JTextField) innerComponent;
                            textField.setEditable(true);
                            // JTextField의 값을 해당 필드에 설정
                            setFieldInObject(obj, field, textField.getText());
                        }
                    }
                }
            }
        }
    }

    private Field findFieldInObject(Object obj, String fieldName) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null; // 해당 필드를 찾지 못한 경우
    }

    private void setFieldInObject(Object obj, Field field, String value) {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();

        try {
            if (fieldType == String.class) {
                field.set(obj, value);
            } else if (fieldType == Integer.class || fieldType == int.class) {
                int intValue = Integer.parseInt(value);
                field.set(obj, intValue);
            } else if (fieldType == Double.class || fieldType == double.class) {
                double doubleValue = Double.parseDouble(value);
                field.set(obj, doubleValue);
            }
            // 필요한 경우 다른 데이터 유형을 처리합니다.
        } catch (IllegalAccessException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

}