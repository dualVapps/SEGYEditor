package vladimir.seis.segystream;

import javafx.scene.text.Text;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryTick;
import org.jfree.text.TextBlock;
import org.jfree.text.TextFragment;
import org.jfree.text.TextLine;
import org.jfree.ui.RectangleEdge;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class CategoryAxisSkipLabels extends CategoryAxis {

    /**
     * The number of ticks to label.
     */
    private final int labeledTicks;

    /**
     * Construct an axis without a label.
     * @param skipTicks show how many ticks skip
     */
    public CategoryAxisSkipLabels(int skipTicks) {
        this.labeledTicks = skipTicks;
    }

    /**
     * Construct and axis with a label.
     * @param skipTicks show how many ticks skip
     * @param label the axis label
     */
    public CategoryAxisSkipLabels(int skipTicks, String label) {
        super(label);
        this.labeledTicks = skipTicks;
    }

//    @Override
//    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
//        return super.refreshTicks(g2, state, dataArea, edge);
//    }

    @Override
    @SuppressWarnings("unchecked")
    public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
        List<CategoryTick> standardTicks = super.refreshTicks(g2, state, dataArea, edge);
        if (standardTicks.isEmpty()) {
            return standardTicks;
        }
       int tickEvery = labeledTicks; //standardTicks.size() / labeledTicks;

//        if (tickEvery < 1) {
//            return standardTicks;
//        }

        //Replace a few labels with blank ones
        List<CategoryTick> fixedTicks = new ArrayList<CategoryTick>(standardTicks.size());

//        //Skip the first tick so your 45degree labels don't fall of the edge
        CategoryTick tick;// = standardTicks.get(0);



//        fixedTicks.add(new CategoryTick(tick.getCategory(), new TextBlock(), tick
//                .getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));

        for (int i = 0; i < standardTicks.size(); i++) {
            tick = standardTicks.get(i);

            if ((i % tickEvery == 0)||(i==standardTicks.size()-1)) {
                TextBlock tBlock;
                tBlock = tick.getLabel();

                int label = -1;
                if (tBlock.getLastLine()!=null) {

                     label = Integer.parseInt(tBlock.getLastLine().getFirstTextFragment().getText());

                }

                float descrete = 0.001f; //TODO Change to value from readed file

                tBlock = new TextBlock();

                if (label !=-1) {
                    float  labelF = label * descrete;
                    labelF = Math.round(labelF *1000);
                    labelF = labelF/1000;
                    String newTimelabel =  String.format("%.3f", labelF);
                    newTimelabel = newTimelabel  + " sec";
                    tBlock.addLine(new TextLine(newTimelabel));
                }

                fixedTicks.add(new CategoryTick(tick.getCategory(), tBlock, tick
                        .getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));
            }
            else {
                fixedTicks.add(new CategoryTick(tick.getCategory(), new TextBlock(), tick
                        .getLabelAnchor(), tick.getRotationAnchor(), tick.getAngle()));
            }
        }

        return fixedTicks;
    }

}
