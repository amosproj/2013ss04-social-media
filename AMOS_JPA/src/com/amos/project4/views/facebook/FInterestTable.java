package com.amos.project4.views.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.views.AbstractControlledView;

public class FInterestTable extends JTable implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private FInterestTableModel model;
	
	public FInterestTable() {
		super();
		this.model = new FInterestTableModel(new ArrayList<FacebookData>());
		setModel(model);		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			List<FacebookData>  tmp_educations =  c.getFacebookDatasByType(FacebookDataType.INTERESTS);			
			if(tmp_educations != null && !tmp_educations.isEmpty()){
				this.setModel((TableModel) new FInterestTableModel(tmp_educations));
			}else{
				this.setModel((TableModel) new FInterestTableModel(new ArrayList<FacebookData>()));
			}			
		}		
	}
	
	class FInterestTableModel extends AbstractTableModel{

		private static final long serialVersionUID = 1L;
		private List<FacebookData> data = new ArrayList<FacebookData>();

		public List<FacebookData> getData() {
			return data;
		}

		public void setDatas(List<FacebookData> data) {
			this.data = data;
		}

		public FInterestTableModel(List<FacebookData> data) {
			super();
			this.data = data;
		}
		
		@Override
		public String getColumnName(int i) {
			switch (i) {
			case 0:
				return "Interest";
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
			return 1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String row_data = data.get(rowIndex).getDataString();
			return row_data;
		}
		
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}	

	}
	
}
