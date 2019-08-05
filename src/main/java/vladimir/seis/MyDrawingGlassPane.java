package main.java.vladimir.seis;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.jfree.chart.ChartMouseEvent;
import main.java.vladimir.seis.segystream.ChartPanelRewrite;
import org.jfree.chart.entity.CategoryItemEntity;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyDrawingGlassPane extends JComponent implements MouseInputListener {

    Point point,pointOnScreen;
    JPanel buttonJPanel;
    JPanel tempJPanel;
    JScrollPane jScrollPane;
    ChartPanelRewrite tempChartPanelRewrite;
    ArrayList<Point> muteLaw = new ArrayList<>();
    mainController mainController;
    JButton pickButton;
    JButton clearButton;
    int pickButtonXmin, pickButtonXmax,pickButtonYmin,pickButtonYmax,
            clearButtonXmin,clearButtonXmax,clearButtonYmin,clearButtonYmax;



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
            }


            if (muteLaw.size() > 1) {
                for (int i = 1; i < muteLaw.size(); i++) {
                    g.drawLine(muteLaw.get(i - 1).x, muteLaw.get(i - 1).y, muteLaw.get(i).x, muteLaw.get(i).y);

                }

            }
//            if (mainGui.getSettings_singl().ge)
        }

        if (mainGui.getSettings_singl().getFullTrimLaw().size()>1) {
            g.setColor(Color.green);
            g.setPaintMode();
            for (int i = 0; i < mainGui.getSettings_singl().getFullTrimLaw().size(); i++) {
                g.drawOval(
                        (int)mainGui.getSettings_singl().getFullTrimLaw().get(i).getX()-3,
                        (int)mainGui.getSettings_singl().getFullTrimLaw().get(i).getY()-3,
                        6,
                        6);
            }
         }

        if (mainGui.getSettings_singl().getFullTrimShifted().size()>1) {
            g.setColor(Color.BLUE);
            g.setPaintMode();
            for (int i = 0; i < mainGui.getSettings_singl().getFullTrimShifted().size(); i++) {
                g.drawOval(
                        (int)mainGui.getSettings_singl().getFullTrimShifted().get(i).getX()-3,
                        (int)mainGui.getSettings_singl().getFullTrimShifted().get(i).getY()-3,
                        6,
                        6);
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
//        init(); //Execute in main GUI
    }

    public void drawingOnMouseReleased(MouseEvent e) {

        point = e.getPoint();

//        System.out.println("Size of Mute Law before comparising " + muteLaw.size());
        if (point.getX()<440) { //checking for disable picking 440: 100 + 150 + addtrace width
            pickButtonXmin = pickButton.getLocationOnScreen().x;
            pickButtonXmax = pickButton.getLocationOnScreen().x + pickButton.getWidth();
            pickButtonYmin = pickButton.getLocationOnScreen().y;
            pickButtonYmax = pickButton.getLocationOnScreen().y + pickButton.getHeight();
            clearButtonXmin = clearButton.getLocationOnScreen().x;
            clearButtonXmax = clearButton.getLocationOnScreen().x + clearButton.getWidth();
            clearButtonYmin = clearButton.getLocationOnScreen().y;
            clearButtonYmax = clearButton.getLocationOnScreen().y + clearButton.getHeight();

            pointOnScreen = e.getLocationOnScreen();



            if (pointOnScreen.x>pickButtonXmin
                &&pointOnScreen.x<=pickButtonXmax
                &&pointOnScreen.y>pickButtonYmin
                &&pointOnScreen.y<=pickButtonYmax) {
//                System.out.println("****************Pick Button Pressed");
                 mainGui.pickingDisablerGui();             }

            if (pointOnScreen.x>clearButtonXmin
                    &&pointOnScreen.x<=clearButtonXmax
                    &&pointOnScreen.y>clearButtonYmin
                    &&pointOnScreen.y<=clearButtonYmax) {
//                System.out.println("****************Clear Button Pressed");
                clearMuteLaw();
            }





        }
        else if (muteLaw.size() <= 5) {





//                System.out.println("~~~~~~~ Point ~~~~~~~" + point.toString());


//                Object tJPanel = tempJPanel.getComponentAt(point);          //Fix variable bug NullPointerException. Bug not fixed
                Object tJPanel = tempJPanel.getComponentAt(point.x-284, point.y); //284 - difference between glassPane and tempJPanel

//                System.out.println("Object tJPanel" + tJPanel.toString());

                if (tJPanel instanceof ChartPanelRewrite) {  //Trying to check getting class
                    tempChartPanelRewrite = (ChartPanelRewrite) tJPanel;
//                    System.out.println("ChartPanelRewrite=getComponent" + tempJPanel.getComponentAt(point).toString());
//                    System.out.println("ChartPanelRewrite=getComponent" + tempJPanel.getComponentAt(point.x-284,point.y).toString());// 284 - difference between glassPane and tempJPanel
                }


//            tempChartPanelRewrite = (ChartPanelRewrite) tempJPanel.getComponentAt(point);

//                System.out.println("TempChartPanelRewrite" + tempChartPanelRewrite.toString());

                //Checking and fix entity

                boolean isItemEntity = false;
                int shiftedX = e.getX()-440,shiftedY = e.getY();
                int shift = 1;
                while (!isItemEntity) {

                    if (tempChartPanelRewrite.getEntityForPoint(shiftedX,shiftedY) instanceof CategoryItemEntity) {
                        isItemEntity = true;

                    }

                    else if (tempChartPanelRewrite.getEntityForPoint(shiftedX+shift,shiftedY) instanceof CategoryItemEntity) {
                        isItemEntity = true;
                        shiftedX = shiftedX+shift;


                    }

                    else if (tempChartPanelRewrite.getEntityForPoint(shiftedX-shift,shiftedY) instanceof CategoryItemEntity) {
                        isItemEntity = true;
                        shiftedX = shiftedX-shift;

                    }

                    else {
                        shift++;

                    }

                    if (shift>16) break;


                }



                //Wrong definition of dartaset number.
                tempChartPanelRewrite.chartMouseClicked(new ChartMouseEvent(tempChartPanelRewrite.getChart(), e, tempChartPanelRewrite.getEntityForPoint(shiftedX, e.getY())));




                if (muteLaw.size() == 0) {   //Checking if point aright from previous
                    muteLaw.add(point);
//                    System.out.println("!!!! First point added");
                }
//                else if ((muteLaw.get(muteLaw.size() - 1).x < point.x)) {
//                    if (mainGui.getSettings_singl().getTrimLaw().get(mainGui.getSettings_singl().getTrimLaw().size()-1).getDatasetValue()
//                            != mainGui.getSettings_singl().getTrimLaw().get(mainGui.getSettings_singl().getTrimLaw().size()-2).getDatasetValue())
                else if (mainGui.getSettings_singl().getTrimLaw().size()>
                    muteLaw.size())
                    {
                        muteLaw.add(point);
//                        System.out.println("!!! Second+ point added");
                    }
                }



                updateUIMuteLawLabels();
                repaint();

//                debugMuteLawOutput(); //TODO Only for debug




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
//        System.out.println("GlassPaneMouse Clicked");
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

    public void init () {
        {
           addMouseListener(this);
           addMouseMotionListener(this);

//            System.out.println("Size of Mute Law after comparising " + muteLaw.size());

            //TODO Check if in window


//        System.out.println(this.getRootPane().getContentPane().getComponentAt(point).getComponentAt(point).toString());
//            this.getRootPane().getContentPane().getComponents();

            Component[] component = this.getRootPane().getContentPane().getComponents(); //TODO worst solution
            buttonJPanel = (JPanel) component[1];

            jScrollPane = (JScrollPane) component[2];
            component = buttonJPanel.getComponents();


            if (component[3] instanceof JButton) {pickButton = (JButton) component[3];}
            if (component[12] instanceof JButton) {clearButton = (JButton) component[12];}


            tempJPanel = (JPanel) jScrollPane.getViewport().getView();




        }
    }

    private void updateUIMuteLawLabels() {
        for (int i = 0; i < mainGui.getSettings_singl().getTrimLaw().size(); i++) {
//            mainController.defineJLabelText(""+muteLaw.get(i).getX() + " :: " + muteLaw.get(i).getY(),i);
            mainController.defineJLabelText(
                    mainGui.getSettings_singl().getTrimLaw().get(i).getDatasetValue()
                            + " :: "
                            + mainGui.getSettings_singl().getTrimLaw().get(i).getSampleValue(),
                    i);



        }



//        mainController.defineJLabelText(
//                JL_PointsLabelsString[0] != null ? JL_PointsLabelsString[0] : "",
//                JL_PointsLabelsString[1] != null ? JL_PointsLabelsString[1] : "",
//                JL_PointsLabelsString[2] != null ? JL_PointsLabelsString[2] : "",
//                JL_PointsLabelsString[3] != null ? JL_PointsLabelsString[3] : "",
//                JL_PointsLabelsString[4] != null ? JL_PointsLabelsString[4] : "",
//                JL_PointsLabelsString[5] != null ? JL_PointsLabelsString[5] : ""
//        );
    }

    private void clearMuteLaw() {
        for (int i = 0; i < muteLaw.size(); i++) {
            mainController.defineJLabelText(null, i);}
        muteLaw.clear();
//        System.out.println("singleton --- " + mainGui.getSettings_singl().toString());
        mainGui.getSettings_singl().zeroedTrimLaw();
        repaint();


    }

    public void zeroedMuteLaw(){
        muteLaw.clear();
//        System.out.println("--aaa" + muteLaw.size());
        repaint();
    }

//    public void debugMuteLawOutput () {
//        for (int i = 0; i < mainGui.getSettings_singl().getTrimLaw().size(); i++) {
//            System.out.println();
//            System.out.print("GlassPane getDatasetValue   " + mainGui.getSettings_singl().getTrimLaw().get(i).getDatasetValue());
//            System.out.print(" getSampleValue   " + mainGui.getSettings_singl().getTrimLaw().get(i).getSampleValue());
//            System.out.print(" getDataValue   " + mainGui.getSettings_singl().getTrimLaw().get(i).getDataValue());
//            System.out.print(" getX   " + mainGui.getSettings_singl().getTrimLaw().get(i).getX());
//            System.out.print(" getY   " + mainGui.getSettings_singl().getTrimLaw().get(i).getY());
//            System.out.println();
//        }
//    }


}
