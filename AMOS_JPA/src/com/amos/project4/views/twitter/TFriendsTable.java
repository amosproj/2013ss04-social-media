package com.amos.project4.views.twitter;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.amos.project4.models.TwitterData;
import com.amos.project4.views.ClientTableModel;

public class TFriendsTable extends JTable {
	
	private TFriendsTableModel model;	
	public TFriendsTable(List<TwitterData> datas) {
		super();
		this.model = new TFriendsTableModel(datas);
		setModel(model);
		// Custommize the Header
	}
}
