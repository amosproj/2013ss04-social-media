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
import javax.swing.border.LineBorder;
import java.awt.Color;

public class TwitterSentimentResultsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField textField;
	private JProgressBar progressBar;
	private JLabel lblTotalvalue;
	private JLabel lblPositivevalue;
	private JLabel lblNegativevalue;
	private JLabel lblNeutralvalue;
	private JLabel lblLasttrainingdata;
	private ClientsController controller;

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
		setTitle("AMOS Project 4 - Notification Settings");

		setSize(650, 400);
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
		resultWrapperpanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel chartPanel = new JPanel();
		resultWrapperpanel.add(chartPanel);
		
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		resultWrapperpanel.add(detailsPanel);
		GridBagLayout gbl_detailsPanel = new GridBagLayout();
		gbl_detailsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_detailsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_detailsPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_detailsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		detailsPanel.setLayout(gbl_detailsPanel);
		
		JLabel lblTotalDataVolume = new JLabel("Total Data Volume");
		lblTotalDataVolume.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblTotalDataVolume = new GridBagConstraints();
		gbc_lblTotalDataVolume.fill = GridBagConstraints.VERTICAL;
		gbc_lblTotalDataVolume.insets = new Insets(0, 0, 5, 0);
		gbc_lblTotalDataVolume.anchor = GridBagConstraints.WEST;
		gbc_lblTotalDataVolume.ipady = 10;
		gbc_lblTotalDataVolume.ipadx = 6;
		gbc_lblTotalDataVolume.gridwidth = 2;
		gbc_lblTotalDataVolume.gridx = 1;
		gbc_lblTotalDataVolume.gridy = 0;
		detailsPanel.add(lblTotalDataVolume, gbc_lblTotalDataVolume);
		
		JLabel lblTotal = new JLabel("Total:");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.anchor = GridBagConstraints.WEST;
		gbc_lblTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotal.gridx = 1;
		gbc_lblTotal.gridy = 1;
		detailsPanel.add(lblTotal, gbc_lblTotal);
		
		lblTotalvalue = new JLabel("TotalValue");
		GridBagConstraints gbc_lblTotalvalue = new GridBagConstraints();
		gbc_lblTotalvalue.anchor = GridBagConstraints.WEST;
		gbc_lblTotalvalue.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalvalue.gridx = 2;
		gbc_lblTotalvalue.gridy = 1;
		detailsPanel.add(lblTotalvalue, gbc_lblTotalvalue);
		
		JLabel lblPositive = new JLabel("Positive:");
		GridBagConstraints gbc_lblPositive = new GridBagConstraints();
		gbc_lblPositive.anchor = GridBagConstraints.WEST;
		gbc_lblPositive.insets = new Insets(0, 0, 5, 5);
		gbc_lblPositive.gridx = 1;
		gbc_lblPositive.gridy = 2;
		detailsPanel.add(lblPositive, gbc_lblPositive);
		
		lblPositivevalue = new JLabel("PositiveValue");
		GridBagConstraints gbc_lblPositivevalue = new GridBagConstraints();
		gbc_lblPositivevalue.anchor = GridBagConstraints.WEST;
		gbc_lblPositivevalue.insets = new Insets(0, 0, 5, 5);
		gbc_lblPositivevalue.gridx = 2;
		gbc_lblPositivevalue.gridy = 2;
		detailsPanel.add(lblPositivevalue, gbc_lblPositivevalue);
		
		JLabel lblNegative = new JLabel("Negative:");
		GridBagConstraints gbc_lblNegative = new GridBagConstraints();
		gbc_lblNegative.anchor = GridBagConstraints.WEST;
		gbc_lblNegative.insets = new Insets(0, 0, 5, 5);
		gbc_lblNegative.gridx = 1;
		gbc_lblNegative.gridy = 3;
		detailsPanel.add(lblNegative, gbc_lblNegative);
		
		lblNegativevalue = new JLabel("NegativeValue");
		GridBagConstraints gbc_lblNegativevalue = new GridBagConstraints();
		gbc_lblNegativevalue.anchor = GridBagConstraints.WEST;
		gbc_lblNegativevalue.insets = new Insets(0, 0, 5, 5);
		gbc_lblNegativevalue.gridx = 2;
		gbc_lblNegativevalue.gridy = 3;
		detailsPanel.add(lblNegativevalue, gbc_lblNegativevalue);
		
		JLabel lblNeutral = new JLabel("Neutral:");
		GridBagConstraints gbc_lblNeutral = new GridBagConstraints();
		gbc_lblNeutral.anchor = GridBagConstraints.WEST;
		gbc_lblNeutral.insets = new Insets(0, 0, 5, 5);
		gbc_lblNeutral.gridx = 1;
		gbc_lblNeutral.gridy = 4;
		detailsPanel.add(lblNeutral, gbc_lblNeutral);
		
		lblNeutralvalue = new JLabel("NeutralValue");
		GridBagConstraints gbc_lblNeutralvalue = new GridBagConstraints();
		gbc_lblNeutralvalue.anchor = GridBagConstraints.WEST;
		gbc_lblNeutralvalue.insets = new Insets(0, 0, 5, 5);
		gbc_lblNeutralvalue.gridx = 2;
		gbc_lblNeutralvalue.gridy = 4;
		detailsPanel.add(lblNeutralvalue, gbc_lblNeutralvalue);
		
		JLabel lblLastTraining = new JLabel("Last Training:");
		GridBagConstraints gbc_lblLastTraining = new GridBagConstraints();
		gbc_lblLastTraining.insets = new Insets(0, 0, 0, 5);
		gbc_lblLastTraining.gridx = 1;
		gbc_lblLastTraining.gridy = 5;
		detailsPanel.add(lblLastTraining, gbc_lblLastTraining);
		
		lblLasttrainingdata = new JLabel("lastTrainingData");
		GridBagConstraints gbc_lblLasttrainingdata = new GridBagConstraints();
		gbc_lblLasttrainingdata.anchor = GridBagConstraints.WEST;
		gbc_lblLasttrainingdata.insets = new Insets(0, 0, 0, 5);
		gbc_lblLasttrainingdata.gridx = 2;
		gbc_lblLasttrainingdata.gridy = 5;
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
		
		private long total;
		private long pos;
		private long neg;
		private long neu;
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
