package panels;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

import static frame.Main.isInitialized;

public class CoordPanel extends JPanel implements MouseInputListener {
    private Dimension panelDim;
    private JPanel mainPanel;

    private static  double scale = 1.0;
    private static int GRID_SIZE = 50 * (int)(scale);

    private double offsetX = (panelDim.width/2.0) /scale;
    private double offsetY = (panelDim.height/2.0-50) /scale;

    private double mouseX; private double mouseY;

    public CoordPanel(JPanel panel,Dimension dimension) {
        this.mainPanel = panel;
        this.panelDim = dimension;

        setPreferredSize(panelDim);
        setBackground(Color.LIGHT_GRAY);

        //마우스를 통한 그래프 관리
        addMouseListener(this);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(scale, scale);
        g2d.clearRect(0, 0, 5000, 8000);

        drawGrid(g);

        if(isInitialized) {
            g2d.setColor(new Color(255, 0, 0, 64));

        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(new Color(230,230,230));
        for (int x = 0; x < 5000; x += GRID_SIZE) {
            g.drawString(String.valueOf(x-offsetX),x,10);
            g.drawLine(x, 0, x, 8000);
        }
        for (int y = 0; y < 8000; y += GRID_SIZE) {
            g.drawString(String.valueOf(y-offsetY),0,y);
            g.drawLine(0, y, 5000, y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}