package mlPlot;

import log.Logger;
import log.MESSAGE_TYPE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by wso2123 on 9/1/16.
 */
public class Chart extends ApplicationFrame {
    private String chartTitle;
    private String xAxisTitle;
    private String yAxisTitle;

    public Chart(String applicationTitle, String chartTitle, String xAxisTitle, String yAxisTitle) {
        super(applicationTitle);
        this.chartTitle = chartTitle;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
    }

    public void plot2SeriesChart(ArrayList<Double> xAxisValues1, ArrayList<Double> yAxisValues1, String series1Name, ArrayList<Double> xAxisValues2, ArrayList<Double> yAxisValues2, String series2Name){
        //Series are not in equal sizes
        if((xAxisValues1.size() != yAxisValues1.size() || (xAxisValues2.size() != yAxisValues2.size()))){
            Logger.log(MESSAGE_TYPE.WARNING, "Incompatible sized series", this.getClass().getName(), "plot2SeriesChart");
            return;
        }

        //Initialize Data Set
        XYSeries xySeries1 = new XYSeries(series1Name);
        XYSeries xySeries2 = new XYSeries(series2Name);

        //Generate Data Set
        for(int i=0; i<xAxisValues1.size(); i++){
            xySeries1.add(xAxisValues1.get(i), yAxisValues1.get(i));
        }
        for(int i=0; i<xAxisValues2.size(); i++){
            xySeries2.add(xAxisValues2.get(i), yAxisValues2.get(i));
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xySeries1);
        xySeriesCollection.addSeries(xySeries2);

        //Create Chart
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                xAxisTitle ,
                yAxisTitle ,
                xySeriesCollection ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );

        this.pack( );
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
    }

    public void plotLineChart(ArrayList<Double> xAxisValues, ArrayList<Double> yAxisValues, String seriesName){
        //X Axis values and Y Axis values are not equal
        if(xAxisValues.size() != yAxisValues.size()){
            Logger.log(MESSAGE_TYPE.WARNING, "X axis vlaues and Y axis values are not equal", this.getClass().getName(), "plotLineChart");
            return;
        }

        //Initialize Data Set
        XYSeries xySeries = new XYSeries(seriesName);

        //Generate Data Set
        for(int i=0; i<xAxisValues.size(); i++){
            xySeries.add(xAxisValues.get(i), yAxisValues.get(i));
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xySeries);

        //Create Chart
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                xAxisTitle ,
                yAxisTitle ,
                xySeriesCollection ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );

        this.pack( );
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
    }
}
