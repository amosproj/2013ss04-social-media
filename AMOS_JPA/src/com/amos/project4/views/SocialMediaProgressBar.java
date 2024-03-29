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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import org.xml.sax.SAXException;

import com.amos.project4.controllers.SocialMediaScanController;
import com.amos.project4.models.Client;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaProgressBar extends JDialog implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private SocialMediaScanTask task;
	private JPanel buttonPane;
	private JLabel title_lbl;
	
	private SocialMediaScanController controller;
	private SocialMediaScanModel model;
	private List<Client> clients;
	private User user;
	
	private boolean canceled;

	public SocialMediaProgressBar(SocialMediaScanController controller,
			SocialMediaScanModel model, List<Client> clients, User user, JFrame frame) {
		super();
		this.controller = controller;
		this.model = model;
		this.clients = clients;
		this.user = user;
		init();
		setLocationRelativeTo(frame);
		//start();
	}

	
	private void init(){
		this.setTitle("Social Media Scan process");
		setSize(500, 150);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		{
			title_lbl = new JLabel("Scaning Social Media Data. Please wait ...");
			springLayout.putConstraint(SpringLayout.EAST, title_lbl, -10,SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, title_lbl, 10,SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.NORTH, title_lbl, 20, SpringLayout.NORTH, getContentPane());			
			getContentPane().add(title_lbl);
			
			progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			springLayout.putConstraint(SpringLayout.EAST, progressBar, -10,SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, progressBar, 10,SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.NORTH, progressBar, 10, SpringLayout.SOUTH, title_lbl);
			getContentPane().add(progressBar);
			
			
			
		}
		{
			buttonPane = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH	, buttonPane, -6,SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST	, buttonPane, 6,SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST	, buttonPane, -6,SpringLayout.EAST, getContentPane());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelAction());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
		}
	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void start(){
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		task = new SocialMediaScanTask();
		task.addPropertyChangeListener(this);
		title_lbl.setText("Start Scanning !");
		canceled = false;
		task.execute();
	}
	
	public void stop(){
		if(task != null){
			canceled = true;
			task.done();
		}
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}
	
	private class CancelAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			stop();
			dispose();		
		}		
	}
	
	public synchronized void makeMessage(String msg, int severity){
		switch (severity) {
		case 0:
			title_lbl.setText(msg);
			title_lbl.setForeground(Color.BLACK);
			break;
		case 1:
			//JOptionPane.showMessageDialog(this, msg, "Social Media Scan Error", JOptionPane.ERROR);
			title_lbl.setText(msg);
			System.err.println(msg);
			title_lbl.setForeground(Color.RED);
			break;
		default:
			//JOptionPane.showMessageDialog(this, msg, "Social Media Scan Error", JOptionPane.ERROR);
			title_lbl.setText(msg);
			System.err.println(msg);
			title_lbl.setForeground(Color.RED);
			break;
		}
	}
	
	private class SocialMediaScanTask extends SwingWorker<Void, Void> {
		
	    @Override
	    public Void doInBackground() {
	    	List<FacebookDataType> f_types = model.getF_selectedDataTypes();
	    	List<TwitterDataType> t_types = model.getT_selectedDataTypes();
	    	List<XingDataType> x_types = model.getX_selectedDataTypes();
	    	List<LinkedInDataType> l_types = model.getL_selectedDataTypes();
	    	
	    	final int F_COUNT = (f_types != null)?f_types.size():0;
	    	final int T_COUNT = (t_types != null)?t_types.size():0;
	    	final int X_COUNT = (x_types != null)?x_types.size():0;
	    	final int L_COUNT = (l_types != null)?l_types.size():0;
	    	
	    	final int C_COUNT = clients.size();
	    	final int TOTAL  = (2 * (F_COUNT + T_COUNT + X_COUNT + L_COUNT) * C_COUNT);
	    	double step = 0;
	    	if(TOTAL == 0 || user == null){
	    		makeMessage("Nothing to scan !",1);
	    		return null;
	    	}
	    	
	    	
	    	// Import Social Media Data
    		makeMessage("Scanning social medias ...",0);
	    	setProgress(0);
	    	if(canceled) return null;
	    		    	
	    	// Import facebook Datas
	    	boolean f_init = false;
	    	if(F_COUNT > 0){
	    		makeMessage("Scanning facebook ...",0);
	    		f_init = controller.getF_retriever().init(user);
	    		if(!f_init){
		    		makeMessage( "Unable to make a connection with facebook. \nPlease verify your settings in the General Setting menu",1);
	    		}
	    	}
	    	if(canceled) return null;		    	
	    	for (int j = 0; j < clients.size() && F_COUNT > 0 && f_init; j++) {
	    		Client client = clients.get(j);    			
	    		String facebook_id = null;
	    		try{
	    			facebook_id = controller.getF_retriever().importFacebookIDofUser(user, client);
	    		}catch (Exception e) {
						e.printStackTrace();
				}
	    		if(facebook_id == null || facebook_id.isEmpty()){
	    			step = step + 2 * F_COUNT;
	    			makeMessage("Unable to get Facebook ID of Client : "+client.getFirstname() + " " + client.getName() +"...",1);
	    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    			if(canceled) return null;
	    			continue;
	    		}
	    		
	    		// Scan relatives Datas
	    		for (int i = 0; i < f_types.size(); i++) {
	    			try{
	    				
	    				makeMessage("Deleting Facebook " + f_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getF_retriever().deleteUserFacebookData(client,f_types.get(i));
			    		step += 1;			    		
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));    			
			    		if(canceled) return null;
			    		
		    			makeMessage("Scanning Facebook " + f_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
		    			controller.getF_retriever().importFacebookData(user, client, f_types.get(i),facebook_id);
		    			step += 1;
		    			
		    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));    			
		    			if(canceled) return null;
	    			}catch (Exception e) {
	    	    		e.printStackTrace();
	    	    		makeMessage("An Error occurs while scanning facebook: "+client.getFirstname() + " " + client.getName() , 1);
	    	    		continue;
	    			}
				}
	    	}
	    	
	    	setProgress((int)Math.min(100 * (step/ TOTAL), 100));    			
	    	if(canceled) return null;
	    	
	    	// Import Twitter Datas
	    	boolean t_init = false;
	    	if(T_COUNT > 0){
	    		title_lbl.setText("Scanning Twitter ...");
	    		t_init = controller.getT_retriever().init(user);
		    	if(!t_init){
		    		makeMessage("Unable to make a connection with twitter. \nPlease verify your settings in the General Setting menu",1);
	    		}
	    	}
	    	if(canceled) return null;
	    	for (int j = 0; j < clients.size() && T_COUNT > 0 && t_init; j++) {
	    		Client client = clients.get(j);    			
	    		String twitter_id = null;
	    		try{
	    			twitter_id = controller.getT_retriever().importTwitterIDofUser(user, client);
	    		}catch (Exception e) {
						e.printStackTrace();
				}
	    		if(twitter_id == null || twitter_id.isEmpty()){
	    			step = step + 2 * T_COUNT;
	    			makeMessage("Unable to get Twitter ID of Client : "+client.getFirstname() + " " + client.getName() +"...",1);
	    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    			if(canceled) return null;
	    			continue;
	    		}
	 
	    		// Scan relatives Datas		    		
	    		for (int i = 0; i < t_types.size(); i++) {
	    			try{
		    			makeMessage("Deleting Twitter " + t_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getT_retriever().deleteUserTwitterData(client,t_types.get(i));
			    		step += 1;
		    		
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));			    		
			    		if(canceled) return null;
			    		
		    			makeMessage("Scanning Twitter " + t_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getT_retriever().importTwitterData(user, client, t_types.get(i),twitter_id);
			    		step += 1;
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));
			    		if(canceled) return null;
			    		
	    			}catch (Exception e) {
			    		e.printStackTrace();
			    		makeMessage("An Error occurs while scanning Twitter data:"+client.getFirstname() + " " + client.getName() , 1);
			    		continue;
					}
				}
	    	}
	    	setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    	
	    	// Import Xing Datas
	    	boolean x_init = false;
	    	if(X_COUNT > 0){
		    	title_lbl.setText("Scanning Xing ...");
		    	x_init = controller.getX_retriever().init(user);
		    	if(!x_init){
		    		makeMessage("Unable to make a connection with Xing. \nPlease verify your settings in the General Setting menu",1);
	    		}
	    	}
	    	if(canceled) return null;
	    	for (int j = 0; j < clients.size() && X_COUNT > 0 && x_init; j++) {
	    		Client client = clients.get(j);
	    		String xing_id = null;
	    		try{
	    			xing_id = controller.getX_retriever().importXingIDofUser(user, client);
	    		}catch (Exception e) {
						e.printStackTrace();
				}
	    		if(xing_id == null || xing_id.isEmpty()){
	    			step = step + 2*X_COUNT;
	    			makeMessage("Unable to get Xing ID of Client : "+client.getFirstname() + " " + client.getName() +"...",1);
	    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    			if(canceled) return null;
	    			continue;
	    		}
	 
	    		// Scan relatives Datas		    		
	    		for (int i = 0; i < x_types.size(); i++) {
	    			try{
		    			makeMessage("Deleting Xing " + x_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getX_retriever().deleteUserXingData(client,x_types.get(i));
			    		step += 1;
		    		
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));			    		
			    		if(canceled) return null;
			    		
		    			makeMessage("Scanning Xing " + x_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getX_retriever().importXingData(user, client, x_types.get(i),xing_id);
			    		step += 1;
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));
			    		if(canceled) return null;
			    		
	    			}catch (Exception e) {
			    		e.printStackTrace();
			    		makeMessage("An Error occurs while scanning Xing data:"+client.getFirstname() + " " + client.getName() , 1);
			    		continue;
					}
				}
	    	}
	    	
	    	setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    	if(canceled) return null;
	    	
	    	// Import Linked Datas
	    	boolean l_init = false;
	    	if(L_COUNT > 0){
		    	title_lbl.setText("Scanning LinkedIn ...");
		    	l_init = controller.getL_retriever().init(user);
		    	if(!l_init){
		    		makeMessage("Unable to make a connection with LinkedIn. \nPlease verify your settings in the General Setting menu",1);
	    		}
	    	}
	    	for (int j = 0; j < clients.size() && L_COUNT > 0 && l_init; j++) {
	    		Client client = clients.get(j);
	    		
	    		String linkedIn_id = null;
				try {
					linkedIn_id = controller.getL_retriever().getLinkedInIDofUser(user, client);
				} catch (SAXException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		if(linkedIn_id == null || linkedIn_id.isEmpty()){
	    			step+=  2*L_COUNT;
	    			makeMessage("Unable to get LinkedIn ID of Client : "+client.getFirstname() + " " + client.getName() +"...",1);
	    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
	    			if(canceled) return null;
	    			continue;
	    		}
	 
	    		// Scan relatives Datas		    		
	    		for (int i = 0; i < l_types.size(); i++) {
	    			try{
		    			makeMessage("Deleting LinkedIn " + l_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getL_retriever().deleteUserLinkedInData(client,l_types.get(i));
			    		step += 1;
		    		
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));			    		
			    		if(canceled) return null;
			    		
		    			makeMessage("Scanning LinkedIn " + l_types.get(i).toString() + " : "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getL_retriever().importLinkedInData(user, client, l_types.get(i),linkedIn_id);
			    		step += 1;
			    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));
			    		if(canceled) return null;
			    		
	    			}catch (Exception e) {
			    		e.printStackTrace();
			    		makeMessage("An Error occurs while scanning Linked data:"+client.getFirstname() + " " + client.getName() , 1);
			    		continue;
					}
				}
	    	}
	    	
	    	return null;	    	
	    }

	    @Override
	    public void done() {
	      Toolkit.getDefaultToolkit().beep();
	      title_lbl.setText("Done !");
	      setCursor(null); // turn off the wait cursor
	      dispose();
	    }
	}
}
