package vladimir.seis;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.BooleanList;
import org.jfree.util.ShapeUtilities;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class MyLineAndFillRenderer extends LineAndShapeRenderer {

    /** @deprecated */
    private Boolean linesVisible;
    private BooleanList seriesLinesVisible;
    private boolean baseLinesVisible;
    /** @deprecated */
    private Boolean shapesVisible;
    private BooleanList seriesShapesVisible;
    private boolean baseShapesVisible;
    /** @deprecated */
    private Boolean shapesFilled;
    private BooleanList seriesShapesFilled;
    private boolean baseShapesFilled;
    private boolean useFillPaint;
    private boolean drawOutlines;
    private boolean useOutlinePaint;
    private boolean useSeriesOffset;
    private double itemMargin;

    public MyLineAndFillRenderer() {
        this(true,true);
    }

    public MyLineAndFillRenderer(boolean lines, boolean shapes) {
        super(lines, shapes);
        this.linesVisible = null;
        this.seriesLinesVisible = new BooleanList();
        this.baseLinesVisible = lines;
        this.shapesVisible = null;
        this.seriesShapesVisible = new BooleanList();
        this.baseShapesVisible = shapes;
        this.shapesFilled = null;
        this.seriesShapesFilled = new BooleanList();
        this.baseShapesFilled = true;
        this.useFillPaint = false;
        this.drawOutlines = true;
        this.useOutlinePaint = false;
        this.useSeriesOffset = false;
        this.itemMargin = 0.0D;

    }

    @Override
    public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
//        super.drawItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column, pass);
        //TODO replace shape rendered code in super class source to render fill with lines from x1, y1 to 0, 0, if value is positive
            if (this.getItemVisible(row, column)) {
                if (this.getItemLineVisible(row, column) || this.getItemShapeVisible(row, column)) {
                    Number v = dataset.getValue(row, column);
                    if (v != null) {
                        int visibleRow = state.getVisibleSeriesIndex(row);
                        if (visibleRow >= 0) {
                            int visibleRowCount = state.getVisibleSeriesCount();
                            PlotOrientation orientation = plot.getOrientation();
                            double x1;
                            if (this.useSeriesOffset) {
                                x1 = domainAxis.getCategorySeriesMiddle(column, dataset.getColumnCount(), visibleRow, visibleRowCount, this.itemMargin, dataArea, plot.getDomainAxisEdge());
                            } else {
                                x1 = domainAxis.getCategoryMiddle(column, this.getColumnCount(), dataArea, plot.getDomainAxisEdge());
                            }

                            double value = v.doubleValue();
                            double y1 = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
                            if (pass == 0 && this.getItemLineVisible(row, column) && column != 0) {
                                Number previousValue = dataset.getValue(row, column - 1);
                                if (previousValue != null) {
                                    double previous = previousValue.doubleValue();
                                    double x0;
                                    if (this.useSeriesOffset) {
                                        x0 = domainAxis.getCategorySeriesMiddle(column - 1, dataset.getColumnCount(), visibleRow, visibleRowCount, this.itemMargin, dataArea, plot.getDomainAxisEdge());
                                    } else {
                                        x0 = domainAxis.getCategoryMiddle(column - 1, this.getColumnCount(), dataArea, plot.getDomainAxisEdge());
                                    }

                                    double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());
                                    Line2D line = null;
                                    if (orientation == PlotOrientation.HORIZONTAL) {
                                        line = new Line2D.Double(y0, x0, y1, x1);
                                    } else if (orientation == PlotOrientation.VERTICAL) {
                                        line = new Line2D.Double(x0, y0, x1, y1);
                                    }

                                    g2.setPaint(this.getItemPaint(row, column));
                                    g2.setStroke(this.getItemStroke(row, column));
                                    g2.draw(line);
                                }
                            }

                            if (pass == 1) {
                                Shape shape = this.getItemShape(row, column);
                                if (orientation == PlotOrientation.HORIZONTAL) {
                                    shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
                                } else if (orientation == PlotOrientation.VERTICAL) {
                                    shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
                                }

                                if (this.getItemShapeVisible(row, column)) {
                                    if (this.getItemShapeFilled(row, column)) {
                                        if (this.useFillPaint) {
                                            g2.setPaint(this.getItemFillPaint(row, column));
                                        } else {
                                            g2.setPaint(this.getItemPaint(row, column));
                                        }

//                                        g2.fill(shape); // Need deleting
                                    }

                                    if (this.drawOutlines) {
                                        if (this.useOutlinePaint) {
                                            g2.setPaint(this.getItemOutlinePaint(row, column));
                                        } else {
                                            g2.setPaint(this.getItemPaint(row, column));
                                        }

                                        g2.setStroke(this.getItemOutlineStroke(row, column));

                                        //Realisation of drawing line from current value horizontal to axis point
                                        // How to estimate value?

                                        Line2D line = null;
//                                        if ((row < 5)&&(column <5)) {
//                                            System.out.println("-----Data---");
//                                            System.out.println("row " + row);
//                                            System.out.println("column " + column);
//                                            System.out.println("value --> " + dataset.getValue(row,column));}
//                                            //dataset.getValue(row,column);}
//                                        //Replace "0" with axis coordinate
                                        double yZero = rangeAxis.valueToJava2D(0.0d, dataArea, plot.getRangeAxisEdge());
                                        if (dataset.getValue(row,column).doubleValue() > 0) {
                                            if (orientation == PlotOrientation.HORIZONTAL) {
                                                line = new Line2D.Double(yZero, x1, y1, x1);

                                            } else if (orientation == PlotOrientation.VERTICAL) {
                                                line = new Line2D.Double(0, y1, x1, y1);

                                            }
                                            g2.draw(line);
                                        }
                                    }
                                }

                                if (this.isItemLabelVisible(row, column)) {
                                    if (orientation == PlotOrientation.HORIZONTAL) {
                                        this.drawItemLabel(g2, orientation, dataset, row, column, y1, x1, value < 0.0D);
                                    } else if (orientation == PlotOrientation.VERTICAL) {
                                        this.drawItemLabel(g2, orientation, dataset, row, column, x1, y1, value < 0.0D);
                                    }
                                }

                                int datasetIndex = plot.indexOf(dataset);
                                this.updateCrosshairValues(state.getCrosshairState(), dataset.getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, x1, y1, orientation);
                                EntityCollection entities = state.getEntityCollection();
                                if (entities != null) {
                                    this.addItemEntity(entities, dataset, row, column, shape);
                                }
                            }

                        }
                    }
                }
            }
        }


    }

