package main.java.vladimir.seis;

import org.jfree.chart.ChartMouseEvent;
import main.java.vladimir.seis.segystream.ChartPanelRewrite;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MyDrawingGlassPane extends JComponent implements MouseInputListener {
    Point point;
    JPanel buttonJPanel;
    JPanel tempJPanel;
    JScrollPane jScrollPane;
    ChartPanelRewrite tempChartPanelRewrite;

    public void setButtonJPanel(JPanel buttonJPanel) {
        this.buttonJPanel = buttonJPanel;
    }

    public void setTempJPanel(JPanel tempJPanel) {
        this.tempJPanel = tempJPanel;
    }

    protected void paintComponent(Graphics g) {
        if (point != null) {
            g.setColor(Color.red);
            g.setPaintMode();
            g.drawOval(point.x - 5, point.y - 5, 10, 10);

        }
    }

    public void setPoint(Point p) {
        point = p;
    }


    public MyDrawingGlassPane() {

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void drawingOnMouseReleased(MouseEvent e) {

        point = e.getPoint();
        repaint();
//        System.out.println(this.getRootPane().getContentPane().getComponentAt(point).getComponentAt(point).toString());
        this.getRootPane().getContentPane().getComponents();
//        System.out.println(tempJPanel.toString());
//        System.out.println(buttonJPanel.toString());

        Component[] component = this.getRootPane().getContentPane().getComponents(); //TODO worst solution

        buttonJPanel = (JPanel) component[1];
        jScrollPane = (JScrollPane) component[2];

        component = buttonJPanel.getComponents();

        JButton pickButton = (JButton) component[4];

        System.out.println(jScrollPane.getViewport().getView().toString());

        tempJPanel = (JPanel) jScrollPane.getViewport().getView();

        tempChartPanelRewrite = (ChartPanelRewrite) tempJPanel.getComponentAt(point);
        tempChartPanelRewrite.chartMouseClicked(new ChartMouseEvent(tempChartPanelRewrite.getChart(),e,tempChartPanelRewrite.getEntityForPoint(e.getX(),e.getY())));
        System.out.println(tempJPanel.getComponentAt(point).toString());

//        for(int i=0; i<component.length; i++)
//        {
//            System.out.println(component[i].toString());
//            if (component[i] instanceof JPanel)
//            {
//                JPanel temp = (JPanel) component[i];
//                System.out.println("components + : " + i + "  " + temp.toString());
//
//
//            }
//
//            else if (component[i] instanceof JScrollPane)
//            {
//                JScrollPane temp = (JScrollPane) component[i];
//                System.out.println("components + : " + i + "  " + temp.toString());
//            }
//            else {
//
//            }
//
//
//        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
            drawingOnMouseReleased(e);
        System.out.println("GlassPaneMouse Clicked");
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
