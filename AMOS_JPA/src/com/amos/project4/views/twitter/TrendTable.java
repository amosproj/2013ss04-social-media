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

public class TrendTable extends JTable implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private TrendTableModel model;
	
	public TrendTable() {
		super();
		this.model = new TrendTableModel(new ArrayList<TwitterData>());
		setModel(model);
		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
		getTableHeader().getColumnModel().getColumn(1).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(1).setMinWidth(200);
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			// Get the last Tweets
			List<TwitterData>  tmp_trebds = c.getTwitterDataByType(TwitterDataType.TRENDS);			
			if(tmp_trebds != null && !tmp_trebds.isEmpty()){
				this.setModel((TableModel) new TrendTableModel(tmp_trebds));
			}else{
				this.setModel((TableModel) new TrendTableModel(new ArrayList<TwitterData>()));
			}			
			getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
			getTableHeader().getColumnModel().getColumn(1).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(1).setMinWidth(200);
		}		
	}
	
}
