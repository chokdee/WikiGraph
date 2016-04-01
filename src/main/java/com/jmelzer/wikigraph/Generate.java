package com.jmelzer.wikigraph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 */
public class Generate extends ApplicationFrame {
    public static final String NAME = "WALTHER";

    public static void main(String[] args) {
        try {
            final Generate demo = new Generate("Wiki Graph");
            demo.pack();
            RefineryUtilities.centerFrameOnScreen(demo);
            demo.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Generate(String s) throws IOException {
        super(s);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        ChartUtilities.saveChartAsPNG(new File("C:\\ws\\WikiGraph\\results\\" + NAME + "-WRL.png"), chart, 1600, 1000);
        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(800, 570));
        setContentPane(chartPanel);
    }


    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private XYDataset createDataset() throws IOException {

        ReadDataFromHtml reader = new ReadDataFromHtml();

        TimeSeries series = reader.read(FileUtil.readFile(NAME + ".htm"));
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);

        return dataset;

    }


    /**
     * Creates a chart.
     *
     * @param dataset the data for the chart.
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Platzierungen",      // chart title
                "Jahr",                      // x axis label
                "Position",                      // y axis label
                dataset,                  // data
                false,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);


        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);

//        plot.setOutlineVisible(false);


        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(0, Color.BLACK);

        plot.setRenderer(renderer);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM.yyyy"));

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setTickUnit(new NumberTickUnit(50));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        rangeAxis.setLowerBound(1.);

        rangeAxis.setInverted(true);
//        rangeAxis.setRange(new Range(0.9f, 50.f));
//        rangeAxis.setAutoRange(true);

        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}
