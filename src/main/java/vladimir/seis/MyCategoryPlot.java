package main.java.vladimir.seis;

// TODO Applying of this class is question.....


import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MyCategoryPlot extends CategoryPlot implements MouseListener {


    public MyCategoryPlot() {
        super();
    }

    public MyCategoryPlot(CategoryDataset dataset, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryItemRenderer renderer) {
        super(dataset, domainAxis, rangeAxis, renderer);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("X   :" +e.getX());
        System.out.println("Y   :" +e.getY());


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
        public void handleClick(int x, int y, PlotRenderingInfo info) {
//            super.handleClick(x, y, info);

        }





}