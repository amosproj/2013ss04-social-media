package com.amos.project4.views;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.amos.project4.controllers.SocialMediaScanController;
import com.amos.project4.models.Client;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaScanTask extends SwingWorker<Void, Void> {
	
	private SocialMediaScanController controller;
	private SocialMediaScanModel model;
	private List<Client> clients;
	private User user;
	
    @Override
    public Void doInBackground() {
    	List<FacebookDataType> f_types = model.getF_selectedDataTypes();
    	List<TwitterDataType> t_types = model.getT_selectedDataTypes();
    	int t_count = f_types.size() + t_types.size();
    	int c_count = clients.size();
    	int total_count = t_count * c_count;
    	if(t_count == 0 || c_count == 0 || this.user == null){
    		JOptionPane.showMessageDialog(null, "Nothing to scan!", "Social Media Scan Error", JOptionPane.ERROR);
    		return null;
    	}
    	
    	try{
	    	// Import Social Media Data
	    	setProgress(0);
	    	// Import facebook Datas
	    	for (int j = 0; j < clients.size(); j++) {
	    		Client client = clients.get(j);
	    		if(!this.controller.getF_retriever().init(user)){
	    			JOptionPane.showMessageDialog(null, "Unable to make a connection with Facebook. \nPlease verify your settings in the General Setting menu.",
	    					"Social Media Scan Error", JOptionPane.ERROR);
	    		}
	    		
	    		// delete the clients relatives data
	    		this.controller.getF_retriever().deleteUserFacebookData(client);
	    		setProgress(1);
	    		
	    		// Scan relatives Datas
	    		for (int i = 0; i < f_types.size(); i++) {
	    			this.controller.getF_retriever().importFacebookData(user, client, f_types.get(i));
	    			setProgress((100 * (j*c_count + i))/(total_count));
				}    		
	    	}
	    	
	    	int middle = 100 * c_count * f_types.size() / total_count;
	    	setProgress(middle);
	    	
	    	// Import Twitter Datas
	    	for (int j = 0; j < clients.size(); j++) {
	    		Client client = clients.get(j);
	    		if(!this.controller.getT_retriever().init(user)){
	    			JOptionPane.showMessageDialog(null, "Unable to make a connection with Twitter. \nPlease verify your settings in the General Setting menu.",
	    					"Social Media Scan Error", JOptionPane.ERROR);
	    		}
	    		
	    		// delete the clients relatives data
	    		this.controller.getT_retriever().deleteUserTwitterData(client);
	    		setProgress(middle + 1);
	    		
	    		// Scan relatives Datas
	    		for (int i = 0; i < f_types.size(); i++) {
	    			this.controller.getT_retriever().importTwitterData(user, client, t_types.get(i));
	    			setProgress((100 * (j*c_count + i + f_types.size()))/(total_count));
				}    		
	    	}
	    	return null;
    	}catch (Exception e) {
    		e.printStackTrace();
    		
    		JOptionPane.showMessageDialog(null, "An Error occured while scanning the Social Media sites !\nPlease try again later.", "Social Media Scan Error", JOptionPane.ERROR);
    		return null;
		}
    }

    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
      
    }
  }