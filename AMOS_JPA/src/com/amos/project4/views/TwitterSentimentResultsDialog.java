/*
 *
 * This file is part of the Datev and Social Media project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.amos.project4.views;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.models.ClassifierDAO;
import com.amos.project4.models.ClassifierData;
import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.sentimentAnalysis.DefaultSentimentClassifier;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.test.PieChart;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;

public class TwitterSentimentResultsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField textField;
	private JProgressBar progressBar;
	private JLabel lblTotalvalue;
	private JLabel lblPositivevalue;
	private JLabel lblNegativevalue;
	private JLabel lblNeutralvalue;
	private ClientsController controller;
	private JLabel lblLasttrainingdata;
	private PieChart chartPanel;

	/**
	 * Create the application.
	 * @param frame2 
	 * @param c_list 
	 * @param user 
	 */
	TwitterSentimentResultsDialog(ClientsController controller) {
		super();
		this.controller = controller;
		init();
		initialize();
	}

	private void init() {
		setTitle("AMOS Project 4 - Twitter Sentioments Analysis");

		setSize(650, 450);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);

		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JButton btnAnalyse = new JButton("Analyse");
		btnAnalyse.addActionListener(new AnalyseAction());
		getContentPane().add(btnAnalyse);
		
		JButton btnClose = new JButton("Close");
		springLayout.putConstraint(SpringLayout.WEST, btnClose, 0, SpringLayout.WEST, btnAnalyse);
		btnClose.addActionListener(new CloseAction());
		springLayout.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnClose);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnAnalyse);
		springLayout.putConstraint(SpringLayout.NORTH, btnAnalyse, 0, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextField txtpnPleaseEnterYour = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, txtpnPleaseEnterYour, -10, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAnalyse, 0, SpringLayout.EAST, txtpnPleaseEnterYour);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, txtpnPleaseEnterYour);
		springLayout.putConstraint(SpringLayout.NORTH, txtpnPleaseEnterYour, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtpnPleaseEnterYour, 10, SpringLayout.WEST, getContentPane());
		txtpnPleaseEnterYour.setEditable(false);
		txtpnPleaseEnterYour.setText("Please enter your chosen Twitter Sentiment term");
		getContentPane().add(txtpnPleaseEnterYour);
		
		JPanel resultWrapperpanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, resultWrapperpanel, 6, SpringLayout.SOUTH, btnAnalyse);
		springLayout.putConstraint(SpringLayout.WEST, resultWrapperpanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, resultWrapperpanel, -6, SpringLayout.NORTH, btnClose);
		springLayout.putConstraint(SpringLayout.EAST, resultWrapperpanel, 632, SpringLayout.WEST, getContentPane());
		getContentPane().add(resultWrapperpanel);
		resultWrapperpanel.setLayout(new GridLayout(0, 2, 6, 6));
		
		chartPanel = new PieChart("Twitter Sentiment",0,0,0);
		resultWrapperpanel.add(chartPanel);		
		
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(new TitledBorder(null, "Total Data Volume", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		resultWrapperpanel.add(detailsPanel);
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[] {10, 0};
		gbl_detailsPanel.rowHeights = new int[] {0, 0, 0, 0, 0};
		gbl_detailsPanel.columnWeights = new double[]{0.0, 0.0};
		gbl_detailsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		JLabel lblTotal = new JLabel("Total:");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.WEST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 0;
		gbc_lblTotal.gridy = 0;
		detailsPanel.add(lblTotal, gbc_lblTotal);
		
		lblTotalvalue = new JLabel("0");
		GridBagConstraints gbc_lblTotalvalue = new GridBagConstraints();
		gbc_lblTotalvalue.anchor = GridBagConstraints.WEST;
		gbc_lblTotalvalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblTotalvalue.gridx = 1;
		gbc_lblTotalvalue.gridy = 0;
		detailsPanel.add(lblTotalvalue, gbc_lblTotalvalue);
		
		JLabel lblPositive = new JLabel("Positive:");
		GridBagConstraints gbc_lblPositive = new GridBagConstraints();
		gbc_lblPositive.anchor = GridBagConstraints.WEST;
		gbc_lblPositive.insets = new Insets(0, 0, 5, 5);
		gbc_lblPositive.gridx = 0;
		gbc_lblPositive.gridy = 1;
		detailsPanel.add(lblPositive, gbc_lblPositive);
		
		lblPositivevalue = new JLabel("0");
		GridBagConstraints gbc_lblPositivevalue = new GridBagConstraints();
		gbc_lblPositivevalue.anchor = GridBagConstraints.WEST;
		gbc_lblPositivevalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblPositivevalue.gridx = 1;
		gbc_lblPositivevalue.gridy = 1;
		detailsPanel.add(lblPositivevalue, gbc_lblPositivevalue);
		
		JLabel lblNegative = new JLabel("Negative:");
		GridBagConstraints gbc_lblNegative = new GridBagConstraints();
		gbc_lblNegative.anchor = GridBagConstraints.WEST;
		gbc_lblNegative.insets = new Insets(0, 0, 5, 5);
		gbc_lblNegative.gridx = 0;
		gbc_lblNegative.gridy = 2;
		detailsPanel.add(lblNegative, gbc_lblNegative);
		
		lblNegativevalue = new JLabel("0");
		GridBagConstraints gbc_lblNegativevalue = new GridBagConstraints();
		gbc_lblNegativevalue.anchor = GridBagConstraints.WEST;
		gbc_lblNegativevalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblNegativevalue.gridx = 1;
		gbc_lblNegativevalue.gridy = 2;
		detailsPanel.add(lblNegativevalue, gbc_lblNegativevalue);
		
		JLabel lblNeutral = new JLabel("Neutral:");
		GridBagConstraints gbc_lblNeutral = new GridBagConstraints();
		gbc_lblNeutral.anchor = GridBagConstraints.WEST;
		gbc_lblNeutral.insets = new Insets(0, 0, 5, 5);
		gbc_lblNeutral.gridx = 0;
		gbc_lblNeutral.gridy = 3;
		detailsPanel.add(lblNeutral, gbc_lblNeutral);
		
		lblNeutralvalue = new JLabel("0");
		GridBagConstraints gbc_lblNeutralvalue = new GridBagConstraints();
		gbc_lblNeutralvalue.anchor = GridBagConstraints.WEST;
		gbc_lblNeutralvalue.insets = new Insets(0, 0, 5, 0);
		gbc_lblNeutralvalue.gridx = 1;
		gbc_lblNeutralvalue.gridy = 3;
		detailsPanel.add(lblNeutralvalue, gbc_lblNeutralvalue);
		
		JLabel lblLastTraining = new JLabel("Last Training:");
		GridBagConstraints gbc_lblLastTraining = new GridBagConstraints();
		gbc_lblLastTraining.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastTraining.gridx = 0;
		gbc_lblLastTraining.gridy = 4;
		detailsPanel.add(lblLastTraining, gbc_lblLastTraining);
		
		lblLasttrainingdata = new JLabel("now()");
		GridBagConstraints gbc_lblLasttrainingdata = new GridBagConstraints();
		gbc_lblLasttrainingdata.anchor = GridBagConstraints.WEST;
		gbc_lblLasttrainingdata.insets = new Insets(0, 0, 5, 0);
		gbc_lblLasttrainingdata.gridx = 1;
		gbc_lblLasttrainingdata.gridy = 4;
		detailsPanel.add(lblLasttrainingdata, gbc_lblLasttrainingdata);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 0, SpringLayout.NORTH, btnClose);
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, progressBar, 0, SpringLayout.SOUTH, btnClose);
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -6, SpringLayout.WEST, btnClose);
		getContentPane().add(progressBar);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new SpringLayout());
	}
	
	private class CloseAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {			
			dispose();
		}
	}
	
	private class AnalyseAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			AnalisisTask worker = new AnalisisTask();
			worker.execute();
		}
	}
	
	private class AnalisisTask extends SwingWorker<String, Integer>{
		private TwitterDataDAO t_dao;
		private ClassifierDAO c_dao;
		private DefaultSentimentClassifier classifier;
		
		private int total;
		private int pos;
		private int neg;
		private int neu;
		private String lastTrainingDate;
		
		@Override
		protected String doInBackground() throws Exception {
			publish(1);
			// Init the DAO
			t_dao = TwitterDataDAO.getInstance();
			c_dao = ClassifierDAO.getInstance();
			// get the clients from the database
			List<Client> clients = controller.getAllClients();
			
			// Retrieve the trained Classifier from the Databse
			List<ClassifierData> classifiers = c_dao.getAllClassifierDatas();
			if(classifiers == null || classifiers.isEmpty()) return null;
			
			lastTrainingDate = classifiers.get(0).getLastModified();
			
			// Create new File with the readed data
			File f = File.createTempFile("AMOSClassifier", ".model");
			FileOutputStream output = new FileOutputStream(f);
			output.write(classifiers.get(0).getClassifier());
			output.flush();
			output.close();
			publish(5);
			// initialise the Classifier
			try {
				classifier = DefaultSentimentClassifier.getInstance(f);
				
				int c_count = clients.size();
				int i = 0;
				publish(10);
				for(Client c: clients){
					List<TwitterData> tweets =c.getTwitterDatasByType(TwitterDataType.TWEETS);
					for(TwitterData tweet : tweets){
						total++;
						String category = classifier.evaluatetext(tweet.getDataString());
						if(category.equalsIgnoreCase("pos")){
							pos++;
						}else if(category.equalsIgnoreCase("neg")){
							neg++;
						}else{
							neu++;
						}
					}
					publish((int) ((i++ * 85) / c_count));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				publish(100);
				return null;
			}
			
			publish(98);
			
			return null;
		}
		
		@Override
		protected void process(List<Integer> chunks) {
			if(chunks == null || chunks.isEmpty()) return;
			if(chunks.get(0) > 0) progressBar.setVisible(true);
			getLblLasttrainingdata().setText("" +lastTrainingDate);
			progressBar.setValue(chunks.get(0));
			super.process(chunks);
		}
		
		@Override
		protected void done() {
			progressBar.setValue(100);
			
			getLblTotalvalue().setText("" + total);
			getLblPositivevalue().setText(total != 0 ? "" + pos + " (" + (pos * 100 / total) + "%)": "");
			getLblNegativevalue().setText(total != 0 ? "" + neg + " (" + (neg * 100 / total) + "%)": "");
			getLblNeutralvalue().setText(total != 0 ? "" + neu + " (" + (neu * 100 / total) + "%)": "");
			getLblLasttrainingdata().setText(total != 0 ? "" + lastTrainingDate: "");
			
			if(pos > 0)  chartPanel.getResult().setValue("Pos", pos);
			if(neg > 0)  chartPanel.getResult().setValue("Neg", neg);
			if(neu <= 0)  
				chartPanel.getResult().remove("Neu");
			else 
				chartPanel.getResult().setValue("Neu", neu);
			
			progressBar.setVisible(false);
			setCursor(null);
			Toolkit.getDefaultToolkit().beep();
		}
				
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JLabel getLblTotalvalue() {
		return lblTotalvalue;
	}
	public JLabel getLblPositivevalue() {
		return lblPositivevalue;
	}
	public JLabel getLblNegativevalue() {
		return lblNegativevalue;
	}
	public JLabel getLblNeutralvalue() {
		return lblNeutralvalue;
	}
	public JLabel getLblLasttrainingdata() {
		return lblLasttrainingdata;
	}
}
