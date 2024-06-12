package vladimir.seis.segystream;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

public class CategoryPlotRewrite extends CategoryPlot{

    private int posNumber = -1;

    public CategoryPlotRewrite(int posNumber) {
        this.posNumber = posNumber;

    }

    public int getPosNumber() {
        return posNumber;
    }

    public CategoryPlotRewrite(CategoryDataset dataset, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryItemRenderer renderer, int posNumber) {
        super(dataset, domainAxis, rangeAxis, renderer);
        this.posNumber = posNumber;
    }



}
