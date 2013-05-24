package com.amos.project4.views.twitter;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.amos.project4.models.TwitterData;
import com.amos.project4.views.ClientTableModel;

public class TweetsTable extends JTable {
	
	private TweetsTableModel model;
	
	public TweetsTable(List<TwitterData> datas) {
		super();
		this.model = new TweetsTableModel(datas);
		setModel(model);
		// Custommize the Header
		

	}
}
