package frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.*;

public class ObjectFieldDisplayer {
    private static JFrame frame;
    private static JPanel fieldPanel; // JPanel을 전역 변수로 선언

    public static void main(String[] args) {
        // 테스트용 객체 생성
        Person person = new Person("John", 30);

        // 기존 프레임 생성 및 버튼 추가
        frame = new JFrame("객체 필드 표시기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JButton displayButton = new JButton("필드 표시하기");
        frame.add(displayButton, BorderLayout.SOUTH);

        // JPanel을 초기화하고 프레임에 추가
        fieldPanel = new JPanel();
        frame.add(fieldPanel, BorderLayout.CENTER);

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayObjectFields(person);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    public static void displayObjectFields(Object obj) {
        if (obj == null) {
            System.out.println("객체가 null입니다.");
            return;
        }

        // JPanel을 초기화
        fieldPanel.removeAll();
        fieldPanel.setLayout(new GridBagLayout());

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(obj);

                JLabel label = new JLabel(fieldName);
                JTextField textField = new JTextField(value.toString());
                textField.setEditable(false);
                textField.setPreferredSize(new Dimension(150, textField.getPreferredSize().height)); // 너비 고정

                gbc.gridy++;
                fieldPanel.add(label, gbc);

                gbc.gridx = 1;
                fieldPanel.add(textField, gbc);

                gbc.gridx = 0;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        frame.revalidate(); // 레이아웃 갱신
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
