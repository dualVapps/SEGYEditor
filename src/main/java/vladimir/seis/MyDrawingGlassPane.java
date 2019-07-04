package main.java.vladimir.seis;

import org.jfree.chart.ChartMouseEvent;
import main.java.vladimir.seis.segystream.ChartPanelRewrite;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyDrawingGlassPane extends JComponent implements MouseInputListener {
    Point point;
    JPanel buttonJPanel;
    JPanel tempJPanel;
    JScrollPane jScrollPane;
    ChartPanelRewrite tempChartPanelRewrite;
    ArrayList<Point> muteLaw = new ArrayList<>();
    String[] JL_PointsLabelsString = new String[6];
    mainController mainController;

    public int getPointsCount() {
        return pointsCount;
    }

    public ArrayList<Point> getMuteLaw() {
        return muteLaw;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    int pointsCount = -1;  //OverInfo to checking do not uses

    public void setButtonJPanel(JPanel buttonJPanel) {
        this.buttonJPanel = buttonJPanel;
    }

    public void setTempJPanel(JPanel tempJPanel) {
        this.tempJPanel = tempJPanel;
    }

    protected void paintComponent(Graphics g) {

        if (muteLaw.size()>0) {
            g.setColor(Color.red);
            g.setPaintMode();
            for (int i = 0; i < muteLaw.size(); i++) {
                g.drawOval(muteLaw.get(i).x - 5, muteLaw.get(i).y - 5, 10, 10);
                JL_PointsLabelsString[i] = "" + muteLaw.get(i).x + "  ::  " + muteLaw.get(i).y;
            }


            if (muteLaw.size() > 1) {
                for (int i = 1; i < muteLaw.size(); i++) {
                    g.drawLine(muteLaw.get(i - 1).x, muteLaw.get(i - 1).y, muteLaw.get(i).x, muteLaw.get(i).y);

                }

            }
        }

//        if (point != null) {
//            g.setColor(Color.red);
//            g.setPaintMode();
//            g.drawOval(point.x - 5, point.y - 5, 10, 10);
//
//
//            if (pointsCount > 1) {
//                for (int i = 1; i < muteLaw.size(); i++) {
//                    g.drawLine(muteLaw.get(pointsCount - 1).x, muteLaw.get(pointsCount - 1).y, muteLaw.get(pointsCount).x, muteLaw.get(pointsCount).y);
//                }
//            }
//
//        }
    }

    public void setPoint(Point p) {
        point = p;
    }


    public MyDrawingGlassPane(mainController mainController){
        this.mainController = mainController;
        init();
    }

    public void drawingOnMouseReleased(MouseEvent e) {

        point = e.getPoint();


        if (muteLaw.size() <= 5) {

            //TODO Check if in window


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
            tempChartPanelRewrite.chartMouseClicked(new ChartMouseEvent(tempChartPanelRewrite.getChart(), e, tempChartPanelRewrite.getEntityForPoint(e.getX(), e.getY())));
            System.out.println(tempJPanel.getComponentAt(point).toString());
            System.out.println(" Checking if aright x--- "  + point.x);
            System.out.println(" Checking if aright y--- "  + point.y);

            if (muteLaw.size() == 0) {   //Checking if point aright from previous
                muteLaw.add(point);
            }

            else if (muteLaw.get(muteLaw.size()-1).x > point.x) {
                muteLaw.add(point);
            }


            repaint();
            updateUIMuteLawLabels();

        }

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
        drawingOnMouseReleased(e);
        System.out.println("GlassPaneMouse Clicked");
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

    private void init () {
        {
           addMouseListener(this);
            addMouseMotionListener(this);


        }
    }

    private void updateUIMuteLawLabels() {
        for (int i = 0; i < muteLaw.size(); i++) {
            JL_PointsLabelsString[i] = Integer.toString(muteLaw.get(i).x) + "  ::  " + Integer.toString(muteLaw.get(i).y);
        }


        mainController.defineJLabelText(
                JL_PointsLabelsString[0] != null ? JL_PointsLabelsString[0] : "",
                JL_PointsLabelsString[1] != null ? JL_PointsLabelsString[1] : "",
                JL_PointsLabelsString[2] != null ? JL_PointsLabelsString[2] : "",
                JL_PointsLabelsString[3] != null ? JL_PointsLabelsString[3] : "",
                JL_PointsLabelsString[4] != null ? JL_PointsLabelsString[4] : "",
                JL_PointsLabelsString[5] != null ? JL_PointsLabelsString[5] : ""
        );
    }
}
