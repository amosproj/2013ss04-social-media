package com.amos.project4.views.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.amos.project4.controllers.TwitterDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.views.AbstractControlledView;

public class TFriendsTable extends JTable implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private TFriendsTableModel model;
	private TwitterDataController controller;
	
	public TFriendsTable(TwitterDataController controller) {
		super();
		this.model = new TFriendsTableModel(new ArrayList<TwitterData>());
		setModel(model);
		this.controller = controller;
		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
		
	}
	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			TwitterDataDAO dao = controller.getTwitter_DAO();
			
			List<TwitterData> tmp_friends = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.FRIENDS);			
			if(tmp_friends != null && !tmp_friends.isEmpty()){
				this.setModel((TableModel) new TFriendsTableModel(tmp_friends));
			}else{
				this.setModel((TableModel) new TFriendsTableModel(new ArrayList<TwitterData>()));
			}			
		}
		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
	}
}
