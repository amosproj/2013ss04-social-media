package com.amos.project4.views;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;

import com.amos.project4.controllers.SocialMediaScanController;
import com.amos.project4.models.Client;
import com.amos.project4.models.User;
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
			title_lbl = new JLabel("Scanning Social Media Data. Please wait ...");
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
			springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, -6,SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 6,SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, -6,SpringLayout.EAST, getContentPane());
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
		title_lbl.setText("Start Scanning!");
		task.execute();
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
			task.done();
			dispose();		
		}		
	}
	
	public synchronized void makeMessage(String msg, int severity){
		switch (severity) {
		case 0:
			title_lbl.setText(msg);
			break;
		case 1:
			JOptionPane.showMessageDialog(this, msg, "Social Media Scan Error", JOptionPane.ERROR);
			break;
		default:
			JOptionPane.showMessageDialog(this, msg, "Social Media Scan Error", JOptionPane.ERROR);
			break;
		}
	}
	
	private class SocialMediaScanTask extends SwingWorker<Void, Void> {
		
	    @Override
	    public Void doInBackground() {
	    	List<FacebookDataType> f_types = model.getF_selectedDataTypes();
	    	List<TwitterDataType> t_types = model.getT_selectedDataTypes();
	    	if(f_types == null || t_types == null || user == null){
	    		makeMessage("Nothing to scan!",1);
	    		return null;
	    	}
	    	
	    	final int F_COUNT = f_types.size();
	    	final int T_COUNT = t_types.size();
	    	final int C_COUNT = clients.size();
	    	final int TOTAL  = ((F_COUNT + T_COUNT) * C_COUNT) + 2 * C_COUNT;
	    	double step = 0;
	    	if(TOTAL == 0 || user == null){
	    		makeMessage("Nothing to scan!",1);
	    		return null;
	    	}
	    	
	    	
		    	// Import Social Media Data
	    		makeMessage("Scanning social media ...",0);
		    	setProgress(0);
		    	// Import facebook Datas
		    	makeMessage("Scanning Facebook ...",0);
		    	if(!controller.getF_retriever().init(user)){
		    		makeMessage( "Unable to make a connection with Facebook. \nPlease verify your settings in the General Setting menu.",1);
	    		}		    	
		    	for (int j = 0; j < clients.size(); j++) {
		    		Client client = clients.get(j);
		    		
		    		// delete the clients relatives data
		    		if( f_types.size()>0){
		    			makeMessage("Deleting Facebook: "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getF_retriever().deleteUserFacebookData(client);
			    		step += 1;
		    		}
	    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
		    		
		    		// Scan relatives Datas
		    		try{
			    		for (int i = 0; i < f_types.size(); i++) {
			    			makeMessage("Scanning Facebook: "+client.getFirstname() + " " + client.getName() +"...",0);
			    			controller.getF_retriever().importFacebookData(user, client, f_types.get(i));
			    			step += 1;
			    			setProgress((int)Math.min(100 * (step/ TOTAL), 100));
						}
		    		}catch (Exception e) {
	    	    		e.printStackTrace();
	    	    		makeMessage("An Error occured while scanning Facebook: "+client.getFirstname() + " " + client.getName() , 1);
	    	    		return null;
	    			}
		    	}
		    	
		    	setProgress((int)Math.min(100 * (step/ TOTAL), 100));
		    	
		    	// Import Twitter Datas
		    	title_lbl.setText("Scanning Twitter ...");
		    	if(!controller.getT_retriever().init(user)){
		    		makeMessage("Unable to make a connection with Twitter. \nPlease verify your settings in the General Setting menu.",1);
	    		}
		    	for (int j = 0; j < clients.size(); j++) {
		    		Client client = clients.get(j);		    		
		    		
		    		// delete the clients relatives data
		    		if(t_types.size() > 0){
		    			makeMessage("Deleting Twitter: "+client.getFirstname() + " " + client.getName() +"...",0);
			    		controller.getT_retriever().deleteUserTwitterData(client);
			    		step += 1;
		    		}
		    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));
		    		
		    		// Scan relatives Datas
		    		try{
			    		for (int i = 0; i < t_types.size(); i++) {
			    			makeMessage("Scanning Twitter: "+client.getFirstname() + " " + client.getName() +"...",0);
				    		controller.getT_retriever().importTwitterData(user, client, t_types.get(i));
				    		step += 1;
				    		setProgress((int)Math.min(100 * (step/ TOTAL), 100));
						}
		    		}catch (Exception e) {
			    		e.printStackTrace();
			    		makeMessage("An Error occured while scanning Twitter data:"+client.getFirstname() + " " + client.getName() , 1);
			    		return null;
					}
		    	}
		    	setProgress(100);
		    	return null;	    	
	    }

	    @Override
	    public void done() {
	      Toolkit.getDefaultToolkit().beep();
	      title_lbl.setText("Done!");
	      setCursor(null); // turn off the wait cursor
	      dispose();
	    }
	}
}
