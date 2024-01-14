package frame;

import Json.Region;
import node.*;
//import sun.awt.PlatformGraphicsInfo;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CoordPanel extends JPanel implements MouseInputListener {
    public boolean isRepaintMeth = false;

    JList<Node> sameLocListPanel;
    public Node focusNode = null;
    public List<Node> focusList = new ArrayList<>();



    // 확대/축소할 배율
    private static double scale = 1.0;
    private static final int GRID_SIZE = 50 * (int)(scale); // 격자 크기
    // 현재 좌표의 위치
    private double offsetX = 250 / scale;
    private double offsetY = 300 / scale;

    private double mouseX;
    private double mouseY;

    private boolean dragging = false;

    public void subScale() {
        if(scale > 0.4) {
            this.scale -= 0.2;
            setPreferredSize(new Dimension((int) (500*scale),(int) (800*scale)));
        }
    }
    public void addScale() {
        if(scale < 2.6) {
            this.scale += 0.2;
            setPreferredSize(new Dimension((int) (500*scale),(int) (800*scale)));
        }
    }

    public CoordPanel() {
        setPreferredSize(new Dimension(MainFrame.getFrames().length-MainFrame.infoPanel.getWidth(),800));
        setBackground(Color.LIGHT_GRAY);

        addMouseListener(this);

        //addMouseWheelListener(this);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 배율과 이동량을 적용하여 좌표를 그리기
        g2d.scale(scale, scale);

        // 그리기 영역 초기화
        g2d.clearRect(0, 0, 5000, 8000);


        g.setColor(new Color(230,230,230));
        for (int x = 0; x < 5000; x += GRID_SIZE) {
            g.drawString(String.valueOf(x-250),x,10);
            g.drawLine(x, 0, x, 8000);
        }
        for (int y = 0; y < 8000; y += GRID_SIZE) {
            g.drawString(String.valueOf(y-300),0,y);
            g.drawLine(0, y, 5000, y);
        }


        // AP 좌표에 대해 빨간색 점 그리기
        if(isRepaintMeth) {
            int numRandomNode = 0;

            g2d.setColor(new Color(255, 0, 0, 64));
            for (Node_AP ap : MainFrame.nodeAPList ) {
                if(focusList != null && focusList.contains((Node) ap))
                    ap.focusbit = true;
                ap.drawNode(g2d,scale,offsetX,offsetY);
                if(ap.isRandomLoc)
                    numRandomNode += 1;
            }

            g2d.setColor(new Color(0, 0, 255, 64));
            for (Node_Station station : MainFrame.nodeStaList) {
                if(focusList != null && focusList.contains((Node) station))
                    station.focusbit = true;
                station.drawNode(g2d,scale,offsetX,offsetY);
                if(station.isRandomLoc)
                    numRandomNode += 1;

            }

            //랜덤 부분 처리하기 위해...
            if(MainFrame.getJsonData().regions != null) {
                List<Region> regions = MainFrame.getJsonData().regions;

                for(Region r : regions) {
                    String regionName = r.name;
                    setSquare(g,regionName,numRandomNode, r.xMin,r.xMax,r.yMin,r.yMax);
                }
            }
        }

        highlight(g,mouseX,mouseY,5*scale+3);
    }

    private void setSquare(Graphics g,String regionName,int numRand, int minX, int maxX, int minY, int maxY) {
        g.setColor(Color.green);
        minX = (int) (minX*scale + offsetX - 3*scale);
        maxX = (int) (maxX*scale + offsetX + 3*scale);
        minY = (int) (minY*scale + offsetY - 3*scale);
        maxY = (int) (maxY*scale + offsetY + 3*scale);
        g.drawLine(minX,minY,maxX,minY);
        g.drawLine(maxX,minY,maxX,maxY);
        g.drawLine(maxX,maxY,minX,maxY);
        g.drawLine(minX,maxY,minX,minY);
        g.drawString(regionName,minX,maxY+10);
        g.drawString("Number Of Random : "+numRand,minX,maxY+20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 클릭된 좌표
        focusList.clear();
        mouseX = e.getX() / scale;
        mouseY = e.getY() / scale;
        //System.out.printf("[clicked] %.1f , %.1f\n",x,y);
        Vector<Node> sameCoordCircles = new Vector<>();

        for (Node_AP ap : MainFrame.nodeAPList) {
            if (ap.circle.contains(mouseX, mouseY) && !sameCoordCircles.contains(ap)) {
                sameCoordCircles.add(ap);
            }
            if (ap.circle.contains(mouseX, mouseY)) {
                // 클릭한 원에 대한 처리 로직 추가
                focusNode = ap;
                focusList = MainFrame.findSameNet(ap.getData().get("ssid"));

                break;
            }
        }


        // 어레이리스트 2의 원 객체 확인
        for (Node_Station station : MainFrame.nodeStaList) {
            if(station.isRandomLoc)
                break;


            if (station.circle.contains(mouseX, mouseY) && !sameCoordCircles.contains(station)) {
                sameCoordCircles.add(station);
            }
            if (station.circle.contains(mouseX, mouseY)) {
                // 클릭한 원에 대한 처리 로직 추가
                focusNode = station;
                focusList = MainFrame.findSameNet(station.getData().get("ssid"));
//                try {
//                    MainFrame.infoPanel.setInfo(station);
//                    repaint();
//                }catch(NullPointerException ne) {
//                    System.out.println(ne.getMessage());
//                }
                break;
            }
        }
        if(focusNode != null) {
            InfoPanel info = MainFrame.infoPanel;
            for(Node n : focusList) {
                info.setInfo(n);

            }
            if(focusNode.getClass() == Node_Station.class) {
                info.card.show(info.staSet, ((Node_Station)focusNode).station.ssid+((Node_Station)focusNode).station.stationNumber);
            }
            repaint();
        } else {
            System.out.println("why null?");
        }

        if(!sameCoordCircles.isEmpty() && sameCoordCircles.size() != 1){
            if(sameLocListPanel != null && sameLocListPanel.isVisible()) {
                try {
                    sameLocListPanel.setVisible(false);
                    sameLocListPanel = null;
                } catch (NullPointerException ignored) {}
                repaint();
            }
            else
                createSideBox(sameCoordCircles,mouseX,mouseY);
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
//        double x = e.getX() / scale;
//        double y = e.getY() / scale;
//        System.out.printf("mouse Entered! %.1f,%.1f \n",x,y);
//        List<Node> sameCoodCircles = new ArrayList<>();
//        if(MainFrame.nodeAPList != null){
//            for (Node_Station node : MainFrame.nodeStaList)
//                if (node.circle.contains(x, y) && !sameCoodCircles.contains(node)) {
//                    System.out.println("there is a station circle!");
//                    sameCoodCircles.add(node);
//                }
//        }
//
//        if(MainFrame.nodeStaList != null) {
//            for (Node_AP node : MainFrame.nodeAPList)
//                if (node.circle.contains(x, y) && !sameCoodCircles.contains(node)) {
//                    System.out.println("there is a ap circle!");
//                    sameCoodCircles.add(node);
//                }
//        }
//        if(!sameCoodCircles.isEmpty() && sameCoodCircles.size() != 1){
//            System.out.println("find!");
//            createSideBox(sameCoodCircles,x,y);
//        }

    }
    public static void highlight(Graphics g, double x, double y, double size) {
        Graphics2D g2d = (Graphics2D) g;
        Color color = new Color(225,255,50);
        g2d.setColor(color);
        double diameter = size;
        double radius = diameter / 2;
        double centerX = x - radius;
        double centerY = y - radius;
        g2d.drawOval((int)centerX, (int)centerY, (int)diameter, (int)diameter);
    }

    private void createSideBox(Vector<Node> nodes, double x, double y) {
//        sameLocListPanel = new JList<>(nodes);
//
//        sameLocListPanel.addListSelectionListener(e -> {
//            focusList.clear();
//            focusList = MainFrame.findSameNet(sameLocListPanel.getSelectedValue().getData().get("ssid"));
//            MainFrame.infoPanel.setInfo(sameLocListPanel.getSelectedValue());
//            sameLocListPanel.setVisible(false);
//            repaint();
//        });
//
//        sameLocListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        sameLocListPanel.setBackground(new Color(230,230,230,0));
//        sameLocListPanel.setFont(new Font("Calibri",Font.BOLD,5));
//        sameLocListPanel.setSize(new Dimension(150,10*nodes.size()));
//        sameLocListPanel.setLocation((int) (x+10.0),(int) y);
//
//        add(sameLocListPanel);
//
//        repaint();
    }


    @Override
    public void mouseExited(MouseEvent e) {
        if(sameLocListPanel !=null)
            sameLocListPanel.removeAll();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
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