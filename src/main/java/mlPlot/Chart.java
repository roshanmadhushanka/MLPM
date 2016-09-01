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

    public void plot2SeriesChart(ArrayList<Double> series1, String series1Name, ArrayList<Double> series2, String series2Name){
        //Series are not in equal sizes
        if(series1.size() != series2.size()){
            Logger.log(MESSAGE_TYPE.WARNING, "Incompatible sized series", this.getClass().getName(), "plot2SeriesChart");
            return;
        }

        //Initialize Data Set
        XYSeries xySeries1 = new XYSeries(series1Name);
        XYSeries xySeries2 = new XYSeries(series2Name);

        //Generate Data Set
        for(int i=0; i<series1.size(); i++){
            xySeries1.add((double)i, series1.get(i));
            xySeries2.add((double)i, series2.get(i));
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
}
