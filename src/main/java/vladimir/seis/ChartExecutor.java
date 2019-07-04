package main.java.vladimir.seis;

/*
*   Контроллер в моделе (модель данных - отображение - котроллер)
*   Управляет все действиями над отображение графиков
*
* */


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;
import main.java.vladimir.seis.segystream.CategoryPlotRewrite;

import main.java.vladimir.seis.segystream.ChartPanelRewrite;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import main.java.vladimir.seis.segystream.DefaultCategoryDatasetRewrite;
import main.java.vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;

import javax.swing.*;
//    import org.jfree.chart.renderer.ItemLabelPosition;
//    import org.jfree.chart.renderer.LineAndShapeRenderer;
//    import org.jfree.data.CategoryDataset;
//    import org.jfree.data.DefaultCategoryDataset;
//    import org.jfree.ui.ApplicationFrame;
//    import org.jfree.ui.RefineryUtilities;


public class ChartExecutor {

    //    private ChartPanelRewrite[] chartPanel;
    /* Инициализация основных обьектов
    *   Определение класса типа синглетон с двойной проверкой для хранения настроек
    *  */
    private ChartPanelRewrite[] chartPanel;
    private DefaultCategoryDatasetRewrite[] categoryDatasets;
    private CategoryPlotRewrite[] categoryPlots;
    private double scaleFactor = 0.5;
    private ArrayList<Point> currentMuteLaw;
    JFreeChart chartAdd, chartData;
    Settings_singleton settings_singleton;


    // Метод для установки мастаба (для установки одинакового масштаба для все трасс)
    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public ChartPanelRewrite[] getChartPanel() {
        return chartPanel;
    }

    public ChartExecutor(String title) {



        /* Конструктор класса выполнения графопостроений
        * Реврайт классы - переписанные со стандартных
        * с дополнительным функционалом
        *  */
//        super(title);
//        chartPanel = new ChartPanel[54];
//        categoryDatasets = new DefaultCategoryDataset[54];
        chartPanel = new ChartPanelRewrite[2];
        categoryDatasets = new DefaultCategoryDatasetRewrite[54];
        for (int i = 0; i < categoryDatasets.length; i++) {
            categoryDatasets[i] = new DefaultCategoryDatasetRewrite(i);
        }
//        categoryPlots = new CategoryPlot[54];
        categoryPlots = new CategoryPlotRewrite[54];



//            dataset = createDataset(j); TODO replace
        chartAdd = createAddChart(null);
        chartData = createDateChart(null);
//            categoryDatasets[j] = new DefaultCategoryDataset();
//            chart = createChart(categoryDatasets[j],j);
//            chartPanel[0] = new ChartPanelRewrite(chartAdd);
//            chartPanel[1] = new ChartPanelRewrite(chartData);

        chartPanel[0] = new ChartPanelRewrite(chartAdd, settings_singleton);
        chartPanel[1] = new ChartPanelRewrite(chartData, 12.288,settings_singleton);
        chartPanel[1].setChartExecutor(this);


        // Изменение размеров составных частей окна (не работает!)
        chartPanel[0].setPreferredSize((new java.awt.Dimension(97, 270)));
        chartPanel[1].setPreferredSize((new java.awt.Dimension(778, 270)));
//            System.out.println("1   1  + "+chartPanel[1].getMouseXCoordinate());
//            System.out.println("2   2  + "+chartPanel[1].getX());
//            System.out.println("3   3  + "+chartPanel[1].getHeight());

    }

    public void setCurrentMuteLaw(ArrayList<Point> currentMuteLaw) {
        this.currentMuteLaw = currentMuteLaw;
    }



    private CategoryDataset createDataset(int index) { //Не исспользуется????
        // row keys...
        String series1 = "First";
//        String series2 = "Second";
//        String series3 = "Third";
        // column keys...
        String type1 = "Type 1";
//        String type2 = "Type 2";
//        String type3 = "Type 3";
//        String type4 = "Type 4";
//        String type5 = "Type 5";
//        String type6 = "Type 6";
//        String type7 = "Type 7";
//        String type8 = "Type 8";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // create the dataset...
        dataset.addValue(1.0, series1, type1);
//        dataset.addValue(4.0, series1, type2);
//        dataset.addValue(3.0, series1, type3);
//        dataset.addValue(5.0, series1, type4);
//        dataset.addValue(5.0, series1, type5);
//        dataset.addValue(7.0, series1, type6);
//        dataset.addValue(7.0, series1, type7);
//        dataset.addValue(8.0, series1, type8);
//        dataset.addValue(5.0, series2, type1);
//        dataset.addValue(7.0, series2, type2);
//        dataset.addValue(6.0, series2, type3);
//        dataset.addValue(8.0, series2, type4);
//        dataset.addValue(4.0, series2, type5);
//        dataset.addValue(4.0, series2, type6);
//        dataset.addValue(2.0, series2, type7);
//        dataset.addValue(1.0, series2, type8);

//        for (int i = 0; i < mainGui.getMainController().segyTempTracesData[index].getData().length; i++) {   //TODO Display a 1/8 of trace
//            dataset.addValue(mainGui.getMainController().segyTempTracesData[index].getData()[i], series1, Integer.toString(i));
//            System.out.println("Samples in graphics :" +mainGui.getMainController().segyTempTracesData[index].getData().length );
//        }

//        dataset.addValue(4.0, series3, type1);
//        dataset.addValue(3.0, series3, type2);
//        dataset.addValue(2.0, series3, type3);
//        dataset.addValue(3.0, series3, type4);
//        dataset.addValue(6.0, series3, type5);
//        dataset.addValue(3.0, series3, type6);
//        dataset.addValue(4.0, series3, type7);
//        dataset.addValue(3.0, series3, type8);
        return dataset;

    }

    // Создание графика вспомогательных трасс
    private JFreeChart createAddChart(CategoryDataset DefaultCategoryDataset) {


        // create the chart...


        CategoryAxis domainAxis = new CategoryAxis();
        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        CategoryDataset datasetTemp = new DefaultCategoryDataset();
        ((DefaultCategoryDataset) datasetTemp).addValue(0.0, "", "");
        LineAndShapeRenderer subplotRenderer = new LineAndShapeRenderer(true, false); //TODO change shapes
        subplotRenderer.setSeriesPaint(0, new Color(0x000000));

        for (int i = 0; i < 6; i++) {

            NumberAxis rangeAxis1 = new NumberAxis();


//            xyPlots[i] = new XYPlot(categoryDatasets[i], null, rangeAxisX, subplotRenderer);
            categoryPlots[i] = new CategoryPlotRewrite(datasetTemp, null, rangeAxis1, subplotRenderer, i);
            plot.add(categoryPlots[i]);

        }


        plot.setGap(0.0);

        plot.setOrientation(PlotOrientation.HORIZONTAL);

        JFreeChart chart = new JFreeChart(
                null, null, plot, false);

        chart.setBackgroundPaint(new Color(0xDD, 0xDD, 0xFF));


        return chart;


    }

    // Создание графика основный трасс данных
//    TODO Rewrite to using only own classes
    private JFreeChart createDateChart(CategoryDataset dataset) {

        // create the chart...


        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();


        CategoryDataset datasetTemp = new DefaultCategoryDataset();
        ((DefaultCategoryDataset) datasetTemp).addValue(0.0, "", "");


        LineAndShapeRenderer subplotRenderer = new LineAndShapeRenderer(true, false);
        subplotRenderer.setSeriesPaint(0, new Color(0x000000));

        for (int i = 6; i < categoryPlots.length; i++) {
            NumberAxis rangeAxis1 = new NumberAxis();

            categoryPlots[i] = new CategoryPlotRewrite(datasetTemp, null, rangeAxis1, subplotRenderer,i);
            plot.add(categoryPlots[i]);
        }
        plot.setGap(0.0);

        plot.setOrientation(PlotOrientation.HORIZONTAL);
        JFreeChart chart = new JFreeChart(
                null, null, plot, false);

        chart.setBackgroundPaint(new Color(249, 255, 253));
        chart.setBackgroundImageAlpha(0.1f);
        return chart;
    }

    // Заполнение графиков данными
    public void updateWithDataset(int index) {

        String series1 = "First";
        for (int i = 0; i < mainGui.getMainController().segyTempTracesData[index].getData().length; i++) {   //TODO Change to display a 1/8 of trace
            categoryDatasets[index].addValue(mainGui.getMainController().segyTempTracesData[index].getData()[i], series1, Integer.toString(i));
        }
        for (int i = 0; i < 6; i++) {
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[0].getChart().getPlot()).getSubplots().get(i)).setDataset(categoryDatasets[i]);
        }
        for (int i = 0; i < 48; i++) {
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).setDataset(categoryDatasets[i + 6]);
        }


       if (!chartPanel[1].isHasSubcharts()){
           chartPanel[1].setHasSubcharts(true);
       }


    }

    //TODO Change scale to 0.8 of current

    public void setSameScale() {

        ValueAxis tempValueAxis =  ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(0)).getRangeAxis();
        Range tempRange =  tempValueAxis.getRange();

        for (int i = 0; i < 48; i++) {
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).getRangeAxis()
                    .setRange(tempRange.getLowerBound()*1*scaleFactor, tempRange.getLowerBound()*-1 * scaleFactor);




            //            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).setRangeAxis(
//                    ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(0)).getRangeAxis()
//
//            );

//            System.out.println("Range values" + ((ValueAxis)((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].
//                    getChart().getPlot()).getSubplots().get(i)).getRangeAxis()).getRange());

        }
    }

//    Не исспользуеться попытка реализации 1-го варианта пикирования (выбор к исспользованию glasspanel)
    public void drewCircleInBackground(int x, int y) {

        BufferedImage bf = new BufferedImage(chartPanel[1].getWidth(), chartPanel[1].getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = bf.createGraphics();
        g2D.drawOval(x-10,y-10,20,20);
        chartData.setBackgroundImage(bf);
        System.out.println("Affords to drew Something");
    }

    public void setSettings_singleton(Settings_singleton settings_singleton) {
        this.settings_singleton = settings_singleton;
    }
}


