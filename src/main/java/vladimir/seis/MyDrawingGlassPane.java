package vladimir.seis;

import org.jfree.chart.ChartMouseEvent;
import vladimir.seis.segystream.ChartPanelRewrite;
import org.jfree.chart.entity.CategoryItemEntity;
import vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyDrawingGlassPane extends JComponent implements MouseInputListener {

    private boolean isPickingEnable = true;
    private Point point, pointOnScreen;
    private JPanel buttonJPanel;
    private JPanel tempJPanel;
    private JScrollPane jScrollPane;
    private ChartPanelRewrite tempChartPanelRewrite;
    private ArrayList<Point> muteLaw = new ArrayList<>();
    private mainController mainController;
    private JButton pickButton;
    private JButton clearButton;
    private BasicArrowButton bUp, bDown;
    private JButton scalePlus, scaleMinus, scaleZero;
    private JSpinner reelSpiner;
    private int pickButtonXmin, pickButtonXmax, pickButtonYmin, pickButtonYmax,
            clearButtonXmin, clearButtonXmax, clearButtonYmin, clearButtonYmax,
            scaleMinusXmin, scaleMinusXmax, scaleMinusYmin, scaleMinusYmax,
            scalePlusXmin, scalePlusXmax, scalePlusYmin, scalePlusYmax,
            scaleZeroXmin, scaleZeroXmax, scaleZeroYmin, scaleZeroYmax,
            bUpXmin, bUpXmax, bUpYmin, bUpYmax,
            bDownXmin, bDownXmax, bDownYmin, bDownYmax;

    private int mousePosX = 0, mousePosY = 0;

    public MyDrawingGlassPane(mainController mainController,
                              BasicArrowButton bUp,
                              BasicArrowButton bDown,
                              JSpinner reelSpiner
                               ) {
        this.mainController = mainController;
        this.bUp = bUp;
        this.bDown = bDown;
        this.reelSpiner = reelSpiner;
//        init(); //Execute in main GUI
    }

    public boolean isPickingEnable() {
        return isPickingEnable;
    }

    public void setPickingEnable(boolean pickingEnable) {
        isPickingEnable = pickingEnable;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public ArrayList<Point> getMuteLaw() {
        return muteLaw;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    private int pointsCount = -1;  //OverInfo to checking do not uses

    public void setButtonJPanel(JPanel buttonJPanel) {
        this.buttonJPanel = buttonJPanel;
    }

    public void setTempJPanel(JPanel tempJPanel) {
        this.tempJPanel = tempJPanel;
    }

    protected void paintComponent(Graphics g) {
        if (mousePosX != 0 & mousePosY != 0) {

            if (muteLaw.size() > 0) {
                //            g.setColor(Color.YELLOW);
//            g.setPaintMode();
//            g.drawLine((int) muteLaw.get(muteLaw.size()-1).getX(),
//                    (int) muteLaw.get(muteLaw.size()-1).getY(),
//                    (int) (MouseInfo.getPointerInfo().getLocation().getX()-this.getLocationOnScreen().getX()+20),
//                    (int) (MouseInfo.getPointerInfo().getLocation().getY()- this.getLocationOnScreen().getY()+20));

                g.setColor(new Color(Color.DARK_GRAY.getRed(), Color.DARK_GRAY.getGreen(), Color.DARK_GRAY.getBlue(), 100));

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setStroke(new BasicStroke(5));
                g2.drawLine((int) muteLaw.get(muteLaw.size() - 1).getX(),
                        (int) muteLaw.get(muteLaw.size() - 1).getY(),
                        (int) mousePosX,
                        (int) mousePosY);

            }


        }


        if (muteLaw.size() > 0) {


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

        if (mainGui.getSettings_singl().getFullTrimLaw().size() > 1) {
            g.setColor(Color.green);
            g.setPaintMode();
            for (int i = 0; i < mainGui.getSettings_singl().getFullTrimLaw().size(); i++) {
               if (mainGui.getSettings_singl().getFullTrimLaw().get(i).getReelNumber()==mainGui.getSettings_singl().getCfgCurrentFileSeqNumber()) {
                   g.drawOval(
                           (int) mainGui.getSettings_singl().getFullTrimLaw().get(i).getX() - 3,
                           (int) mainGui.getSettings_singl().getFullTrimLaw().get(i).getY() - 3,
                           6,
                           6);
               }

            }
        }

        if (mainGui.getSettings_singl().getFullTrimShifted().size() > 1) {
            g.setColor(Color.BLUE);
            g.setPaintMode();
            for (int i = 0; i < mainGui.getSettings_singl().getFullTrimShifted().size(); i++) {
                if (mainGui.getSettings_singl().getFullTrimLaw().get(i).getReelNumber()==mainGui.getSettings_singl().getCfgCurrentFileSeqNumber()) {

                    g.drawOval(
                            (int) mainGui.getSettings_singl().getFullTrimShifted().get(i).getX() - 3,
                            (int) mainGui.getSettings_singl().getFullTrimShifted().get(i).getY() - 3,
                            6,
                            6);
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


    private void drawingOnMouseReleased(MouseEvent e) {

        point = e.getPoint();

//        System.out.println("X= " + point.getX());
//        System.out.println("Y= " + point.getX());

//        System.out.println("Size of Mute Law before comparising " + muteLaw.size());
        if (point.getX() < 440) { //checking for disable picking 440: 100 + 150 + addtrace width
            pickButtonXmin = pickButton.getLocationOnScreen().x;
            pickButtonXmax = pickButton.getLocationOnScreen().x + pickButton.getWidth();
            pickButtonYmin = pickButton.getLocationOnScreen().y;
            pickButtonYmax = pickButton.getLocationOnScreen().y + pickButton.getHeight();

            clearButtonXmin = clearButton.getLocationOnScreen().x;
            clearButtonXmax = clearButton.getLocationOnScreen().x + clearButton.getWidth();
            clearButtonYmin = clearButton.getLocationOnScreen().y;
            clearButtonYmax = clearButton.getLocationOnScreen().y + clearButton.getHeight();

            scaleMinusXmin = scaleMinus.getLocationOnScreen().x;
            scaleMinusXmax = scaleMinus.getLocationOnScreen().x + scaleMinus.getWidth();
            scaleMinusYmin = scaleMinus.getLocationOnScreen().y;
            scaleMinusYmax = scaleMinus.getLocationOnScreen().y + scaleMinus.getHeight();

            scalePlusXmin = scalePlus.getLocationOnScreen().x;
            scalePlusXmax = scalePlus.getLocationOnScreen().x + scalePlus.getWidth();
            scalePlusYmin = scalePlus.getLocationOnScreen().y;
            scalePlusYmax = scalePlus.getLocationOnScreen().y + scalePlus.getHeight();

            scaleZeroXmin = scaleZero.getLocationOnScreen().x;
            scaleZeroXmax = scaleZero.getLocationOnScreen().x + scaleZero.getWidth();
            scaleZeroYmin = scaleZero.getLocationOnScreen().y;
            scaleZeroYmax = scaleZero.getLocationOnScreen().y + scaleZero.getHeight();

            if (reelSpiner.isVisible()) {
                bUpXmin = bUp.getLocationOnScreen().x;
                bUpXmax = bUp.getLocationOnScreen().x + bUp.getWidth();
                bUpYmin = bUp.getLocationOnScreen().y;
                bUpYmax = bUp.getLocationOnScreen().y + bUp.getHeight();

                bDownXmin = bDown.getLocationOnScreen().x;
                bDownXmax = bDown.getLocationOnScreen().x + bDown.getWidth();
                bDownYmin = bDown.getLocationOnScreen().y;
                bDownYmax = bDown.getLocationOnScreen().y + bDown.getHeight();
            }
            pointOnScreen = e.getLocationOnScreen();


            if (pointOnScreen.x > pickButtonXmin  //Pick button pressed
                    && pointOnScreen.x <= pickButtonXmax
                    && pointOnScreen.y > pickButtonYmin
                    && pointOnScreen.y <= pickButtonYmax) {
//                System.out.println("****************Pick Button Pressed");
                mainGui.pickingDisablerGui();
            }

            if (pointOnScreen.x > clearButtonXmin //Pick button pressed
                    && pointOnScreen.x <= clearButtonXmax
                    && pointOnScreen.y > clearButtonYmin
                    && pointOnScreen.y <= clearButtonYmax) {
//                System.out.println("****************Clear Button Pressed");
                clearMuteLaw();
                mainGui.getSettings_singl().initTrimLawDescr(mainGui.getSettings_singl().getCfgFilesNumber());
                checkPickingStatus();
            }

            if (pointOnScreen.x > scaleMinusXmin //ScaleMinus button pressed
                    && pointOnScreen.x <= scaleMinusXmax
                    && pointOnScreen.y > scaleMinusYmin
                    && pointOnScreen.y <= scaleMinusYmax) {

                scaleMinus.doClick();
            }

            if (pointOnScreen.x > scalePlusXmin //Pick button pressed
                    && pointOnScreen.x <= scalePlusXmax
                    && pointOnScreen.y > scalePlusYmin
                    && pointOnScreen.y <= scalePlusYmax) {

                scalePlus.doClick();
            }


            if (pointOnScreen.x > scaleZeroXmin //Pick button pressed
                    && pointOnScreen.x <= scaleZeroXmax
                    && pointOnScreen.y > scaleZeroYmin
                    && pointOnScreen.y <= scaleZeroYmax) {

                scaleZero.doClick();
            }

            if (reelSpiner.isVisible()) {
                if (pointOnScreen.x > bUpXmin //Pick button pressed
                        && pointOnScreen.x <= bUpXmax
                        && pointOnScreen.y > bUpYmin
                        && pointOnScreen.y <= bUpYmax) {

                    bUp.doClick();
                }

                if (pointOnScreen.x > bDownXmin //Pick button pressed
                        && pointOnScreen.x <= bDownXmax
                        && pointOnScreen.y > bDownYmin
                        && pointOnScreen.y <= bDownYmax) {

                    bDown.doClick();
                }
            }

        }
//        else if (muteLaw.size() <= 5) {
        else if (isPickingEnable) { {


//                System.out.println("~~~~~~~ Point ~~~~~~~" + point.toString());


//                Object tJPanel = tempJPanel.getComponentAt(point);          //Fix variable bug NullPointerException. Bug not fixed
            Object tJPanel = tempJPanel.getComponentAt(point.x - 284, point.y); //284 - difference between glassPane and tempJPanel

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
            int shiftedX = e.getX() - 440, shiftedY = e.getY();
            int shift = 1;
            while (!isItemEntity) {

                if (tempChartPanelRewrite.getEntityForPoint(shiftedX, shiftedY) instanceof CategoryItemEntity) {
                    isItemEntity = true;

                } else if (tempChartPanelRewrite.getEntityForPoint(shiftedX + shift, shiftedY) instanceof CategoryItemEntity) {
                    isItemEntity = true;
                    shiftedX = shiftedX + shift;


                } else if (tempChartPanelRewrite.getEntityForPoint(shiftedX - shift, shiftedY) instanceof CategoryItemEntity) {
                    isItemEntity = true;
                    shiftedX = shiftedX - shift;

                } else {
                    shift++;
//                    System.out.println(shift);

                }
                if (shift > 6) break;


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
            else if (mainGui.getSettings_singl().getTrimLaw().size() >
                    muteLaw.size()) {
                muteLaw.add(point);
//                        System.out.println("!!! Second+ point added");
            }
        } }


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

    public void checkPickingStatus() {
        if (muteLaw.size()==0) setPickingEnable(true);
        else setPickingEnable(false);
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
        if ((muteLaw.size() > 0) && isPickingEnable) {
            if (e.getX() > muteLaw.get(muteLaw.size() - 1).getX()) {
                this.mousePosX = e.getX();
                this.mousePosY = e.getY();
            } else {
                this.mousePosX = 0;
                this.mousePosY = 0;

            }

            this.repaint(); //TODO Change to triangle


        }
    }

    public void init() {
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


            if (component[3] instanceof JButton) {
                pickButton = (JButton) component[3];
            }
            if (component[11] instanceof JButton) {
                clearButton = (JButton) component[11];
            }

            //Scale buttons
            if (component[7] instanceof JButton) {
                scalePlus = (JButton) component[7];
            }
            if (component[10] instanceof JButton) {
                scaleMinus = (JButton) component[10];
            }
            if (component[8] instanceof JButton) {
                scaleZero = (JButton) component[8];
            }

            if (component[19] instanceof JSpinner) {
                reelSpiner = (JSpinner) component[19];
            }

//            System.out.println(component.length);
//            for (int i = 0; i < component.length; i++) {
//                System.out.println(component[i].getClass().getSimpleName()+ " " + component[i].getName() );
//                if (component[i] instanceof JButton) System.out.println(i +  ((JButton)component[i]).getText() );
//                if (component[i] instanceof JSpinner) System.out.println(i +  ((JSpinner)component[i]).getUIClassID() );
//            }



            tempJPanel = (JPanel) jScrollPane.getViewport().getView();


        }
    }

    private void updateUIMuteLawLabels() {
        if (mainGui.getSettings_singl().getTrimLaw().size() <= 6) {


            for (int i = 0; i < mainGui.getSettings_singl().getTrimLaw().size(); i++) {
//            mainController.defineJLabelText(""+muteLaw.get(i).getX() + " :: " + muteLaw.get(i).getY(),i);
                mainController.defineJLabelText(
                        mainGui.getSettings_singl().getTrimLaw().get(i).getDatasetValue()
                                + " :: "
                                + mainGui.getSettings_singl().getTrimLaw().get(i).getSampleValue(),
                        i);
            }

        } else {
            for (int i = 0; i < 6; i++) {
//            mainController.defineJLabelText(""+muteLaw.get(i).getX() + " :: " + muteLaw.get(i).getY(),i);
                mainController.defineJLabelText(
                        mainGui.getSettings_singl().getTrimLaw().get(i + mainGui.getSettings_singl().getTrimLaw().size() - 6).getDatasetValue()
                                + " :: "
                                + mainGui.getSettings_singl().getTrimLaw().get(i + mainGui.getSettings_singl().getTrimLaw().size() - 6).getSampleValue(),
                        i);
            }
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
            mainController.defineJLabelText(null, i);
        }
        muteLaw.clear();
//        System.out.println("singleton --- " + mainGui.getSettings_singl().toString());
        mainGui.getSettings_singl().zeroedTrimLaw();
        mainGui.getSettings_singl().zeroedFullTrimLaw();
        mainGui.getSettings_singl().zeroedFullTrimLawShifted();
        repaint();


    }

    public void zeroedMuteLaw() {
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

    public void checkAndAddLawEnd() {
        if (muteLaw.size() > 0) {
            mainGui.getSettings_singl().saveTrimLawEndsDescr();
        }
    }

    public void reloadTrimLaw() {
        zeroedMuteLaw();
        for (TrimLawSingleValue singleValue : mainGui.getSettings_singl().getTrimLaw()) {
            if (singleValue.getReelNumber() == mainGui.getSettings_singl().getCfgCurrentFileSeqNumber()) {
                muteLaw.add(new Point((int) singleValue.getX(), (int) singleValue.getY()));
            }
        }
    }
}
