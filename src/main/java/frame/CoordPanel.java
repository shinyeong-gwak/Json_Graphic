package frame;

import node.*;
//import sun.awt.PlatformGraphicsInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class CoordPanel extends JPanel implements MouseListener{//, MouseWheelListener {
    public boolean isRepaintMeth = false;

    public List<Node> focusList = new ArrayList<>();



    // 확대/축소할 배율
    private static double scale = 1.0;
    private static final int GRID_SIZE = 50 * (int)(scale); // 격자 크기
    // 현재 좌표의 위치
    private int offsetX = 0;
    private int offsetY = 0;
    // 마우스 포인터의 이전 위치
    private int lastX;
    private int lastY;
    // 마우스 포인터의 현재 위치
    //private int currentX = (int) getMousePosition().getX();
    //private int currentY = (int) getMousePosition().getY();
    // 좌표를 드래그하고 있는지 여부
    private boolean dragging = false;

    public void setScale() {
        if (this.scale == 1.0)
            this.scale = 2.0;
        else
            this.scale = 1.0;
    }

    public CoordPanel() {
        setPreferredSize(new Dimension(500,800));
        setBackground(Color.LIGHT_GRAY);
        JTextArea coordText = new JTextArea("This is the coordinate panel.");
        coordText.setEditable(false);
        add(coordText, BorderLayout.CENTER);
        addMouseListener(this);

        // 버튼 1 (우측 하단)
        JButton zoomin = new JButton("+");
        add(zoomin, BorderLayout.PAGE_END);

        // 버튼 2 (우측 하단)
        JButton zoomout = new JButton("-");
        add(zoomout, BorderLayout.PAGE_END);
        //addMouseWheelListener(this);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 배율과 이동량을 적용하여 좌표를 그리기
        g2d.scale(scale, scale);
        g2d.translate(offsetX, offsetY);

        // 그리기 영역 초기화
        g2d.clearRect(0, 0, 5000, 8000);


        g.setColor(Color.GRAY);
        for (int x = 0; x < 5000; x += GRID_SIZE) {
            g.drawLine(x, 0, x, 8000);
        }
        for (int y = 0; y < 8000; y += GRID_SIZE) {
            g.drawLine(0, y, 5000, y);
        }


        // AP 좌표에 대해 빨간색 점 그리기
        if(isRepaintMeth) {
            g2d.setColor(new Color(255, 0, 0, 64));
            for (Node_AP ap : MainFrame.nodeAPList ) {
                if(focusList != null && focusList.contains((Node) ap))
                    ap.focusbit = true;
                ap.drawNode(g2d,scale);
            }

            g2d.setColor(new Color(0, 0, 255, 64));
            for (Node_Station station : MainFrame.nodeStaList) {
                if(focusList != null && focusList.contains((Node) station))
                    station.focusbit = true;
                station.drawNode(g2d,scale);

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 클릭된 좌표
        focusList.clear();
        double x = e.getX() / scale;
        double y = e.getY() / scale;
        //System.out.printf("[clicked] %.1f , %.1f\n",x,y);

        for (Node_AP ap : MainFrame.nodeAPList) {
            if (ap.circle.contains(x, y)) {
                System.out.println("Clicked circle in list 1");
                // 클릭한 원에 대한 처리 로직 추가
                focusList = MainFrame.findSameNet(ap.getData().get("ssid"));
                try {
                    MainFrame.infoPanel.setInfo(ap);

                }catch(NullPointerException ne) {
                    System.out.println(ne.getMessage());
                }
                break;
            }
        }

        // 어레이리스트 2의 원 객체 확인
        for (Node_Station station : MainFrame.nodeStaList) {
            if (station.circle.contains(x, y)) {
                System.out.println("Clicked circle in list 2");
                // 클릭한 원에 대한 처리 로직 추가
                focusList = MainFrame.findSameNet(station.getData().get("ssid"));
                try {
                    MainFrame.infoPanel.setInfo(station);
                }catch(NullPointerException ne) {
                    System.out.println(ne.getMessage());
                }
                break;
            }
        }
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // 마우스를 눌렀을 때, 드래그 시작
//        dragging = true;
//        lastX = e.getX();
//        lastY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 마우스 버튼을 놓았을 때, 드래그 종료
//        dragging = false;
//        offsetX += (currentX - lastX);
//        offsetY += (currentY - lastY);
//        lastX = currentX;
//        lastY = currentY;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*@Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // 마우스 휠을 움직일 때, 배율 조정
        int notches = e.getWheelRotation();
        if (notches < 0) {
            // 휠을 위로 움직일 때, 확대
            scale *= 1.1;
            System.out.println("확대");
        } else {
            // 휠을 아래로 움직일 때, 축소
            if(scale > 1.0)
                scale /= 1.1;
        }
        // 배율이 변경될 때마다 다시 그리기
        repaint();
    }*/
}