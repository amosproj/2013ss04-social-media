package com.amos.project4.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.amos.project4.models.Client;
import com.amos.project4.models.ClientDAO;
import com.amos.project4.models.FacebookData;
import com.amos.project4.models.FacebookDataDAO;
import com.amos.project4.socialMedia.facebook.FacebookDataType;

public class FacebookDataController extends AbstractController {

	private Client selected_client;
	private FacebookDataDAO facebook_DAO;
	private List<FacebookData> facebookDatas;
	
	
	public FacebookDataController() {
		super();
		this.facebook_DAO = FacebookDataDAO.getInstance();
		List<Client> clients = ClientDAO.getInstance().getAllClients();
		if(clients != null && !clients.isEmpty()) setSelected_client(clients.get(0));
	}

	public void updateInternally(Object arg, Observable o) {
		if(o!= null && arg.getClass().equals(Client.class)){
			Client c = (Client) arg;
			this.setSelected_client(c);
		}
	}

	public void setSelected_client(Client selected_client) {
		this.selected_client = selected_client;
		facebookDatas = selected_client.getFacebook_datas();
	}

	public FacebookDataDAO getFacebook_DAO() {
		return facebook_DAO;
	}

	public List<FacebookData> getFacebookDatas(FacebookDataType type) {
		ArrayList<FacebookData> rslts = new ArrayList<FacebookData>();
		for (FacebookData facebookData : this.selected_client.getFacebook_datas()) {
			if(facebookData.getType().equalsIgnoreCase(type.toString())){
				rslts.add(facebookData);
			}
		}
		return rslts;
	}
}
