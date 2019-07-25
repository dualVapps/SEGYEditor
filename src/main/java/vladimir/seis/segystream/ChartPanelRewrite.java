package main.java.vladimir.seis.segystream;

//TODO Not nessecarily
/*
 *  Переписанный класс Панель Графики, в котором обрабатываются
 * события нажатия мышки и вычисляется положение щелчка
 *  приблизительно в координатах трасса, время
 *
 *
 * */


import com.sun.org.apache.bcel.internal.generic.IADD;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.annotations.CategoryPointerAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PlotEntity;
import org.jfree.chart.entity.XYAnnotationEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import main.java.vladimir.seis.ChartExecutor;
import main.java.vladimir.seis.Settings;


import main.java.vladimir.seis.Settings_singleton;
import main.java.vladimir.seis.mainGui;
import main.java.vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;

import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;


//public class ChartPanelRewrite extends ChartPanel{
public class ChartPanelRewrite extends ChartPanel implements ChartMouseListener {


    static final double KOEF_OF_TRANSITION = 0.11;
    static final int NUMBER_OF_CHANELS = 48;
    private double traceLenght;
    private int mouseXCoordinate;
    private int mouseYCoordinate;
    private boolean isHasSubcharts = false;
    private ChartExecutor chartExecutor;
    private Settings_singleton settings_singleton;

    public void setChartExecutor(ChartExecutor chartExecutor) {
        this.chartExecutor = chartExecutor;
    }

    public boolean isHasSubcharts() {
        return isHasSubcharts;
    }

    public void setHasSubcharts(boolean hasSubcharts) {
        isHasSubcharts = hasSubcharts;
    }

    public int getMouseXCoordinate() {
        return mouseXCoordinate;
    }

    public int getMouseYCoordinate() {
        return mouseYCoordinate;
    }


    public ChartPanelRewrite(JFreeChart chart, Settings_singleton settings_singleton) {
        super(chart);
        this.addChartMouseListener(this);
        this.settings_singleton = settings_singleton;
    }

    public ChartPanelRewrite(JFreeChart chart, double traceLenght, Settings_singleton settings_singleton) {

        super(chart);
        this.traceLenght = traceLenght;
        this.addChartMouseListener(this);
        this.settings_singleton = settings_singleton;
    }


    @Override
    public void mousePressed(MouseEvent e) {


//        returnDomainAxisValue(e.getY(), e.getX()); //TODO Stop here


    }

//    @Override
////    public void chartMouseClicked(ChartMouseEvent event) {
////        System.out.println("chartMouseClicked + " + event.getEntity());
////
////    }
////
////    @Override
////    public void chartMouseMoved(ChartMouseEvent event) {
////
////    }
////
////
////    public int returnDomainAxisValue(double mouseXCoordinate, double mouseYCoordinate) {
//////        double ratio = traceLenght*mouseYCoordinate-mouseYCoordinate*KOEF_OF_TRANSITION/2;
////        System.out.println("mouseYcoordinate" + mouseYCoordinate);
////        System.out.println("mouseXcoordinate" + mouseXCoordinate);
//////        System.out.println("ratio*mouseYCoordinate" + ratio*mouseYCoordinate);
//////        System.out.println("ratio*mouseYCoordinate" + ratio*mouseYCoordinate);
//////        Work with right axis
//////        System.out.println("Insets" + this.getChart().getCategoryPlot().getAxisOffset().getTop());
//////        System.out.println("Insets" + this.getChart().getCategoryPlot().getAxisOffset().getBottom());
//////        System.out.println("Insets" + this.getChart().getCategoryPlot().getAxisOffset().getLeft());
//////        System.out.println("Insets" + this.getChart().getCategoryPlot().getAxisOffset().getRight());
//////        System.out.println("!Insets top" + this.getChart().getCategoryPlot().getInsets().calculateBottomInset(this.getHeight()));
//////        System.out.println("Insets bottom" + this.getChart().getCategoryPlot().getInsets().getBottom());
//////        System.out.println(this.getChart().getCategoryPlot().getInsets().getLeft());
//////        System.out.println(this.getChart().getCategoryPlot().getInsets().getRight());
//////        System.out.println("Top " + this.getChart().getPadding().getTop());
//////        System.out.println("Bottom " + this.getChart().getPadding().getBottom());
//////        System.out.println("Left " + this.getChart().getPadding().getLeft());
//////        System.out.println("Right " + this.getChart().getPadding().getRight());
////
////
////        System.out.println(this.getSize().getWidth() / NUMBER_OF_CHANELS);
////
////        if (isHasSubcharts) {
////            double oneChanelWidth = this.getSize().getWidth() / NUMBER_OF_CHANELS + 1;
////            int channelNumber = (int) ((mouseYCoordinate - oneChanelWidth) / oneChanelWidth);
////            System.out.println("" + channelNumber);
////
////            // Work with down axis
////
////            System.out.println("------------------------------------------------------------------");
////            System.out.println(traceLenght);
////            System.out.println(this.getSize().getHeight() * (1 - KOEF_OF_TRANSITION) / (traceLenght * 1000));
////            double oneSampleHeight = this.getSize().getHeight() * (1 - KOEF_OF_TRANSITION) / (traceLenght * 1000);
////            System.out.println((int) Math.round((mouseXCoordinate - oneSampleHeight / 2) / oneSampleHeight) - 800);
////            int nearlySampleNumber = (int) Math.round((mouseXCoordinate - oneSampleHeight / 2) / oneSampleHeight) - 800;
////            System.out.println("trace = " + channelNumber + ": " + "sampleNumber" + nearlySampleNumber);
////            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////
////        }
//////        return (int)(ratio*mouseYCoordinate);
////        return (int) (0);
////    }


    @Override
    public void chartMouseClicked(ChartMouseEvent event) {

        ChartEntity eventEntity = event.getEntity();

        if (eventEntity != null) {

            System.out.println("11" + event.getTrigger().toString());
            System.out.println("12" + event.getTrigger().getY());
            System.out.println("12" + event.getTrigger().getY());
            System.out.println("13" + event.getEntity().toString());
        }

        if (eventEntity != null) {

            if (eventEntity instanceof PlotEntity) {


//        PlotEntity plE = (PlotEntity) event.getEntity();
//        System.out.println("3" + plE.getPlot().getInsets().toString());
//        System.out.println("4" + plE.getPlot().getDatasetGroup());
//        System.out.println("5" + plE.getPlot().toString());
//        System.out.println("6" + plE.getPlot().getDatasetGroup());
            }

            if (eventEntity instanceof CategoryItemEntity) {
                CategoryItemEntity caE = (CategoryItemEntity) event.getEntity();
                System.out.println("Dataset " + caE.getDataset().toString());
                DefaultCategoryDatasetRewrite sCDR = (DefaultCategoryDatasetRewrite) caE.getDataset();
                System.out.println("******Dataset number " + sCDR.getNumberDataset());
                System.out.println("******Column key " + caE.getColumnKey().toString());

                System.out.println("Raw key " + caE.getRowKey().toString());
                System.out.println("Column Index   " + caE.getDataset().getRowIndex(caE.getRowKey()));
                System.out.println("*******Value  " + caE.getDataset().getValue(caE.getRowKey(), caE.getColumnKey()));


                TrimLawSingleValue trimLawSingleValue = new TrimLawSingleValue(
                        event.getTrigger().getX(),
                        event.getTrigger().getY(),
                        sCDR.getNumberDataset(),
                        Integer.parseInt(caE.getColumnKey().toString()),
                        (double) caE.getDataset().getValue(caE.getRowKey(), caE.getColumnKey()));




//            settings_singleton.addValueToTrimLaw(trimLawSingleValue); //TODO something wrong here
                System.out.println("Annotation begin");
//            CategoryPointerAnnotation cpa = new CategoryPointerAnnotation("First", caE.getColumnKey(),event.getTrigger().getY(),0.9);

                System.out.println("Annotation and");


//            CategoryLineAnnotation  xYLineAnnotation = new CategoryLineAnnotation(oldxPoint, oldyPoint, newxPoint, newyPoint, new BasicStroke(1.0f), Color.blue);
//               chartExecutor.drewCircleInBackground(event.getTrigger().getX(), event.getTrigger().getY()); //Trying make drawing circles first attempt (not use)
                this.revalidate();
                this.repaint();


            }

        }
        eventEntity = null;



    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {

    }

//    public void setSettings_singleton(Settings_singleton settings_singleton) {
//        this.settings_singleton = settings_singleton;
//    }
}
