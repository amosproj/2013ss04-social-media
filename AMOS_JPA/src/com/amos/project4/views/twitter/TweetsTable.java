package com.amos.project4.views.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.views.AbstractControlledView;

public class TweetsTable extends JTable implements AbstractControlledView{

	private static final long serialVersionUID = 1L;
	private TweetsTableModel model;
	
	public TweetsTable() {
		super();
		this.model = new TweetsTableModel(new ArrayList<TwitterData>());
		setModel(model);
		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;		
			// Get the last Tweets
			List<TwitterData> tmp_name = c.getTwitterDataByType(TwitterDataType.TWEETS);			
			if(tmp_name != null && !tmp_name.isEmpty()){
				this.setModel((TableModel) new TweetsTableModel(tmp_name));
			}else{
				this.setModel((TableModel) new TweetsTableModel(new ArrayList<TwitterData>()));
			}
			getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
		}
		
	}
}