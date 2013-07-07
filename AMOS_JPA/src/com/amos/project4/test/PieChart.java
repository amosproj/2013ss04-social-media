package com.amos.project4.test;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.LayoutFocusTraversalPolicy;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChart extends JPanel {

	private static final long serialVersionUID = 1L;
	private int pos;
	private int neg;
	private int neu;
	private DefaultPieDataset result;

	public PieChart(String chartTitle, int pos, int neg, int neu) {
		this.pos = pos;
		this.neg = neg;
		this.neu = neu;

		// This will create the dataset
		PieDataset dataset = createDataset();
		// based on the dataset we create the chart
		JFreeChart chart = createChart(dataset, chartTitle);
		
		// we put the chart into a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setLayout(new BorderLayout(0, 0));
		add(chartPanel,BorderLayout.CENTER);
	}

	/**
	 * Creates a sample dataset
	 */

	private PieDataset createDataset() {
		result = new DefaultPieDataset();
		result.setValue("Pos", pos);
		result.setValue("Neg", neg);
		result.setValue("Neu", neu);
		return result;

	}

	public DefaultPieDataset getResult() {
		return result;
	}

	public void setResult(DefaultPieDataset result) {
		this.result = result;
	}

	/**
	 * Creates a chart
	 */

	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setNoDataMessage("No data available");
		//plot.setForegroundAlpha(0.5f);
		plot.setInteriorGap(0.0);
		plot.setExplodePercent(0, 0.30);
		
		return chart;
	}
}