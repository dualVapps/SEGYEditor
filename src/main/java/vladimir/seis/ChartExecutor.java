package vladimir.seis;

/*
*   Контроллер в моделе (модель данных - отображение - котроллер)
*   Управляет все действиями над отображение графиков
*
* */


import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleInsets;
import vladimir.seis.segystream.CategoryAxisSkipLabels;
import vladimir.seis.segystream.CategoryPlotRewrite;

import vladimir.seis.segystream.ChartPanelRewrite;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import vladimir.seis.segystream.DefaultCategoryDatasetRewrite;
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
    private int TRACES_NUMBER;
    private ChartPanelRewrite[] chartPanel;
    private DefaultCategoryDatasetRewrite[] categoryDatasets;
    private CategoryPlotRewrite[] categoryPlots;
    private double scaleFactor = 1.0;
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
        chartPanel[1] = new ChartPanelRewrite(chartData, 12.288, settings_singleton); //traceLength dont use
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

//        for (int i = 0; i < mainGui.getMainController().segyTempTracesDataForDisplaying[index].getData().length; i++) {   //TODO Display a 1/8 of trace
//            dataset.addValue(mainGui.getMainController().segyTempTracesDataForDisplaying[index].getData()[i], series1, Integer.toString(i));
//            System.out.println("Samples in graphics :" +mainGui.getMainController().segyTempTracesDataForDisplaying[index].getData().length );
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


        CategoryAxis domainAxis = new CategoryAxisSkipLabels(100);
        domainAxis.setVisible(true); //trying to disable domain (down) axis labels on additional traces
//        domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN,18));
        domainAxis.setTickLabelsVisible(true);
        domainAxis.setTickLabelInsets(new RectangleInsets(30,30,30,30));



        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        CategoryDataset datasetTemp = new DefaultCategoryDataset();
        ((DefaultCategoryDataset) datasetTemp).addValue(0.0, "", "");
        LineAndShapeRenderer subplotRenderer = new MyLineAndFillRenderer(true, true); //TODO change fill
        subplotRenderer.setSeriesPaint(0, new Color(0x000000));

        for (int i = 0; i < 6; i++) {

            NumberAxis rangeAxis1 = new NumberAxis();
            rangeAxis1.setVisible(true); //disable range axis labels
            rangeAxis1.setTickLabelsVisible(false);


//            xyPlots[i] = new XYPlot(categoryDatasets[i], null, rangeAxisX, subplotRenderer);
            categoryPlots[i] = new CategoryPlotRewrite(datasetTemp, null, rangeAxis1, subplotRenderer, i);
            categoryPlots[i].getRangeAxis().setLabelFont (new Font("Arial",Font.PLAIN,10));
            categoryPlots[i].getRangeAxis().setLabel(" ");
            plot.add(categoryPlots[i]);

        }

        plot.setGap(0.0);

        plot.setOrientation(PlotOrientation.HORIZONTAL);

        JFreeChart chart = new JFreeChart(
                null, null, plot, false);

        chart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF));


        return chart;


    }

    // Создание графика основный трасс данных
//    TODO Rewrite to using only own classes
    private JFreeChart createDateChart(CategoryDataset dataset) {

        // create the chart...
        CategoryAxis domainAxis = new CategoryAxisSkipLabels(10);// reinitialising domain for toward appearance changing
        domainAxis.setVisible(false);//

        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
//        CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(); //with labels? using empty constructor


        CategoryDataset datasetTemp = new DefaultCategoryDataset();
        ((DefaultCategoryDataset) datasetTemp).addValue(0.0, "", "");


        LineAndShapeRenderer subplotRenderer = new MyLineAndFillRenderer(true, true);
        subplotRenderer.setSeriesPaint(0, new Color(0x000000));

        for (int i = 6; i < categoryPlots.length; i++) {
            NumberAxis rangeAxis1 = new NumberAxis();

            rangeAxis1.setVisible(true); //disable range axis labels
            rangeAxis1.setTickLabelsVisible(false);

            categoryPlots[i] = new CategoryPlotRewrite(datasetTemp, null, rangeAxis1, subplotRenderer, i);
            categoryPlots[i].getRangeAxis().setLabelFont (new Font("Arial",Font.PLAIN,10));
            categoryPlots[i].getRangeAxis().setLabel(" ");
            plot.add(categoryPlots[i]);

        }
        plot.setGap(0.0);

        plot.setOrientation(PlotOrientation.HORIZONTAL);
        JFreeChart chart = new JFreeChart(
                null, null, plot, false);

        chart.setBackgroundPaint(new Color(255, 255, 255));
//        chart.setBackgroundImageAlpha(0.1f);
        return chart;
    }

    // Заполнение графиков данными
    public void updateWithDataset(int index, int FILE_SEQ_NUM){  //TODO 4 executing need change to one ???? Somewhere hire java.lang.IllegalArgumentException: Invalid category index:
        String series1 = "First";

        for (int i = 0; i < mainGui.getMainController().segyTempTracesDataForDisplaying[index].getData().length; i++) {   //TODO Change to display a 1/8 of trace
            categoryDatasets[index].addValue(mainGui.getMainController().segyTempTracesDataForDisplaying[index+FILE_SEQ_NUM*54].getData()[i], series1, Integer.toString(i));

        }







        if (!chartPanel[1].isHasSubcharts()) {
            chartPanel[1].setHasSubcharts(true);
        }

//        for (int i = 0; i < 48; i++) {
//            System.out.println(":: Dataset number -  " + ((DefaultCategoryDatasetRewrite)((CategoryPlot)((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot())
//                    .getSubplots().get(i)).getDataset()).getNumberDataset());
//
//        }


    }

    //TODO Change scale to 0.8 of current, Needs changes if 4*54 traces

    public void redefineDatasets(int FILE_SEQ_NUM) {
        for (int i = 0; i < 6; i++) { //TODO Number of addition traces graphs
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[0].getChart().getPlot()).getSubplots().get(i)).setDataset(categoryDatasets[i]);

        }
        for (int i = 0; i < 48; i++) { //TODO Number of data traces graphs
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).setDataset(categoryDatasets[i + 6]);

        }

        for (int i = 0; i < 54; i++) {//TODO Number of addition+data traces graphs
//            System.out.println("cur trace index -> " + (i+FILE_SEQ_NUM*54));
//            System.out.println(Integer.toString(mainGui.getMainController().segyTempTraces[i+FILE_SEQ_NUM*54].getTraceNumberWithinOrigFieldRecord()));
            categoryPlots[i].getRangeAxis().setLabel(Integer.toString(mainGui.getMainController().segyTempTraces[i+FILE_SEQ_NUM*54].getTraceSequenceNumberWithinSegyFile()));

        }


    }

    public void setSameScale() {


        for (int i = 0; i < 48; i++) {
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).getRangeAxis()
                    .setRange(mainGui.getSettings_singl().getInitialFileScaleRange().getLowerBound() * 1 * scaleFactor,
                            mainGui.getSettings_singl().getInitialFileScaleRange().getLowerBound() * -1 * scaleFactor); //Maybe change to upper bound value


            //            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).setRangeAxis(
//                    ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(0)).getRangeAxis()
//
//            );

//            System.out.println("Range values" + ((ValueAxis)((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].
//                    getChart().getPlot()).getSubplots().get(i)).getRangeAxis()).getRange());

        }
    }

//    Не исспользуеться попытка реализации 1-го варианта пикирования (выбор к исспользованию glasspanel)
//    public void drewCircleInBackground(int x, int y) {
//
//        BufferedImage bf = new BufferedImage(chartPanel[1].getWidth(), chartPanel[1].getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2D = bf.createGraphics();
//        g2D.drawOval(x-10,y-10,20,20);
//        chartData.setBackgroundImage(bf);
//        System.out.println("Affords to drew Something");
//    }

    public void setInitialSameScale() {

        ValueAxis tempValueAxis0 = ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(12)).getRangeAxis();
        ValueAxis tempValueAxis1 = ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(36)).getRangeAxis();
        Range tempRange0 = tempValueAxis0.getRange();
        Range tempRange1 = tempValueAxis1.getRange();
        Range dataRange = DatasetUtilities.findRangeBounds(  ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(12)).getDataset()  );
        Range dataRange1 = DatasetUtilities.findRangeBounds(  ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(36)).getDataset()  );
//        ((CombinedDomainCategoryPlot)chartPanel[1].getChart().getPlot()).getDataRange(axis);




        mainGui.getSettings_singl().setInitialFileScaleRange(
                new Range((dataRange.getLowerBound() + dataRange1.getLowerBound()) / 2,
                        (-1 * (dataRange.getLowerBound() + dataRange1.getLowerBound())) / 2));
        mainGui.getSettings_singl().setCurrentFileScaleRange(
                new Range((dataRange.getLowerBound() + dataRange1.getLowerBound()) / 2,
                          (-1 * (dataRange.getLowerBound() + dataRange1.getLowerBound())) / 2));
//        System.out.println(" Init Scale 0000000000000000 -- " + mainGui.getSettings_singl().getInitialFileScaleRange().toString());
//        System.out.println(" Curr Scale 0000000000000000 -- " + mainGui.getSettings_singl().getInitialFileScaleRange().toString());
//        System.out.println(" Temp1 Range 111 -- " + dataRange);
//        System.out.println(" Temp1 Range 111 -- " + dataRange1);


    }

    public void setSettings_singleton(Settings_singleton settings_singleton) {
        this.settings_singleton = settings_singleton;
    }

    public void resetPlotsRange() {
        for (int i = 0; i < 48; i++) {
            ((CategoryPlot) ((CombinedDomainCategoryPlot) chartPanel[1].getChart().getPlot()).getSubplots().get(i)).getRangeAxis()
                    .setRange(mainGui.getSettings_singl().getInitialFileScaleRange());
        }

        mainGui.getSettings_singl().resetFileScales();
    }
}


