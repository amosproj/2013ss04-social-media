package com.amos.project4.views.twitter;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import com.amos.project4.models.TwitterData;
import com.amos.project4.views.ClientTableModel;

public class TrendTable extends JTable {
	
	private TrendTableModel model;
	
	public TrendTable(List<TwitterData> datas) {
		super();
		this.model = new TrendTableModel(datas);
		setModel(model);
		// Custommize the Header
		getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(200);
		getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(200);
		

	}
}
