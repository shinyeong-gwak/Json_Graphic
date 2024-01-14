package frame;

import Json.Network;
import Json.Station;
import node.Node_AP;
import node.Node_Station;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*
card 2개 위아래로 셋
위의 < > 버튼
아래의 < > 버튼
카드의 패널은 각각 hashmap에 저장되어 있음. - key에 패널 이름

 */
public class tmpInfoPanel extends  JPanel {
    static CardLayout card;
    GridBagConstraints constraintsNet, constraintsTable;


    JPanel apSet;
    String showingAP;
    // 이름 형식 : network-1
    HashMap<String,JPanel> apPanels = new HashMap<>();
    static JButton prevAp = new JButton("<");
    static JButton nextAp = new JButton(">");


    JPanel staSet;
    String showingSta;
    // 이름 형식 : network-2-1
    HashMap<String,JPanel> staPanels = new HashMap<>();
    static JButton prevSta = new JButton("<");
    static JButton nextSta = new JButton(">");

    static JPanel buttonZone;
    static JButton change; // 2번 change network setting 으로 객체(a,s)를 찾아 수정, ap별로 모아 임시저장
    static JButton save; // 3번 save json 전체 모인 ap, station 객체를
    static JButton addThis; // 1번 지금 보이는 station 객체를 임시 저장

    //visible영역
    static JPanel topPanel = new JPanel(card);
    static JPanel bottomPanel = new JPanel(card);

    public tmpInfoPanel() {
        setPreferredSize(new Dimension(440, 800));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createDashedBorder(Color.gray));

        //잘은 모르지만 레이아웃 설정

        constraintsNet = new GridBagConstraints();
        constraintsTable = new GridBagConstraints();
        constraintsTable.insets = new Insets(10,10,10,10);
        constraintsNet.gridy=0; constraintsTable.gridy=0;
        constraintsNet.gridx=0; constraintsTable.gridx=0;
        constraintsNet.fill = GridBagConstraints.HORIZONTAL; constraintsTable.fill = GridBagConstraints.HORIZONTAL; // 컴포넌트가 셀을 채우도록 설정
        constraintsNet.anchor = GridBagConstraints.NORTHWEST;
        constraintsNet.gridwidth=3;
        constraintsNet.weightx = 1.0; constraintsTable.weightx = 1.0; // 첫 번째 행의 셀 넓이 비율
        constraintsNet.weighty = 0.01;

        //ap영역 만들기
        cardPageTurner(topPanel,prevAp= new JButton(),nextAp = new JButton());
        constraintsNet.gridy++;
        add(new JLabel("-AP-"),constraintsNet);
        constraintsNet.gridy++;//1
        constraintsNet.weighty = 0.3;
        add(topPanel, constraintsNet);

        //구분선 그리기
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        constraintsNet.gridy++;//2
        constraintsNet.weighty = 0.1;
        add(separator, constraintsNet);
        constraintsNet.gridy++;//3

        //sta영역 만들기
        add(new JLabel("-Station-"), constraintsNet);
        constraintsNet.weighty = 0.4;
        constraintsNet.anchor =GridBagConstraints.SOUTHWEST;
        constraintsNet.gridx=0;
        add(bottomPanel,constraintsNet);
        constraintsNet.gridy++;//4
        cardPageTurner(topPanel,prevAp= new JButton(),nextAp = new JButton());


        //각 종 버튼 영역 만들기
        buttonZone = new JPanel(new GridBagLayout());
        constraintsNet.gridy++;//
        add(buttonZone,constraintsNet);
        change = new JButton("change Network Setting");
        save = new JButton("save Json");
        addThis = new JButton("add Station in this AP");

        constraintsNet.gridwidth=1;
        constraintsNet.gridy++;
        buttonZone.add(addThis,constraintsNet);constraintsNet.gridy++;
        buttonZone.add(change,constraintsNet);constraintsNet.gridx++;
        buttonZone.add(save,constraintsNet);

        setVisible(true);


    }

    public static void testMode(JPanel container) {
        if (container != null) {
            Border border = BorderFactory.createLineBorder(Color.RED);
            container.setBorder(border);
        }
    }

    public void setInfo(Object obj ) {
        JPanel selected = null;

        if (obj == null) {
            System.out.println("객체가 null입니다.");
            return;
        }

        if (obj instanceof Network || obj instanceof Node_AP) {
            selected = topPanel;
            selected.removeAll();
            selected.revalidate();
            if(obj instanceof Node_AP)
                obj = ((Node_AP) obj).network;
        } else if (obj instanceof Station || obj instanceof Node_Station) {
            selected = bottomPanel;
            if(obj instanceof Node_Station)
                obj = ((Node_Station) obj).getObject();
        }

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
            staPanels.put(sta.ssid+"-"+sta.stationNumber,selected);
            staSet.add(selected, sta.ssid + sta.stationNumber); // card에서 이름은 ssid+넘버로 구별
            constraintsNet.gridy =4;
            constraintsNet.gridx=0; constraintsNet.gridwidth=3;
            add(staSet, constraintsNet);
            card.show(staSet, sta.ssid+sta.stationNumber);
            staSet.revalidate();
            //viewPageNum.setText(String.format("%d/%d",sta.stationNumber, (MainFrame.coordPanel.focusList.size()-1)));!!!!!!!!!!
            buttonZone.revalidate();
        }

    }

    private void setInfo(Object obj, JPanel parent, String msg) {
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
    public void cardPageTurner(JPanel panel,JButton prev, JButton next) {
        //constraintsNet.gridwidth=3;
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
                    //add(viewPageNum);!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                } catch (NullPointerException ignored){};
                add(prev);
                add(next);
            }
        }, btc);

        buttonZone.revalidate();
        revalidate();

    }
    //칸나눠 뿌리기
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

    //test
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new tmpInfoPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,800));
        frame.setVisible(true);


    }




}
