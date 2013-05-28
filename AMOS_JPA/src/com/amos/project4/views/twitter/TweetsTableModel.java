package com.amos.project4.views.twitter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.amos.project4.models.TwitterData;

public class TweetsTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<TwitterData> data = new ArrayList<TwitterData>();

	public List<TwitterData> getData() {
		return data;
	}

	public void setDatas(List<TwitterData> data) {
		this.data = data;
	}

	public TweetsTableModel(List<TwitterData> data) {
		super();
		this.data = data;
	}
	
	@Override
	public String getColumnName(int i) {
		switch (i) {
		case 0:
			return "Date";
		case 1:
			return "Text";
		default:
			return super.getColumnName(i);
		}
	}
	
	@Override
	public Class<?> getColumnClass(int i) {
		return String.class;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String row_data = data.get(rowIndex).getDataString();
		if(row_data.contains("#")){
			String rslt = (row_data.split("#"))[columnIndex];
			return rslt;
		}else{
			return row_data;
		}
	}
	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}	

}
