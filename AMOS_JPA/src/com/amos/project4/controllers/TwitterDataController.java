package com.amos.project4.controllers;

import java.util.List;
import java.util.Observable;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;

public class TwitterDataController extends AbstractController {

	private Client selected_client;
	private TwitterDataDAO twitter_DAO;
	private List<TwitterData> twitterDatas;
	
	
	public TwitterDataController() {
		super();
		this.twitter_DAO = TwitterDataDAO.getInstance();
		List<Client> clients = ClientDAO.getInstance().getAllClients();
		if(clients != null && !clients.isEmpty()) this.selected_client = clients.get(0);
	}

	public void updateInternally(Object arg, Observable o) {
		if(o!= null && arg.getClass().equals(Client.class)){
			Client c = (Client) arg;
			this.setSelected_client(c);
		}
	}

	public void setSelected_client(Client selected_client) {
		this.selected_client = selected_client;
	}

	public TwitterDataDAO getTwitter_DAO() {
		return twitter_DAO;
	}

	public List<TwitterData> getTwitterDatas() {
		return twitterDatas;
	}

}
