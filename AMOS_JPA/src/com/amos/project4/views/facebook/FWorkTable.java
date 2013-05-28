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

public class FWorkTable extends JTable implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private FFWorkTableModel model;
	
	public FWorkTable() {
		super();
		this.model = new FFWorkTableModel(new ArrayList<FacebookData>());
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
			List<FacebookData>  tmp_works =  c.getFacebookDatasByType( FacebookDataType.WORK);			
			if(tmp_works != null && !tmp_works.isEmpty()){
				this.setModel((TableModel) new FFWorkTableModel(tmp_works));
			}else{
				this.setModel((TableModel) new FFWorkTableModel(new ArrayList<FacebookData>()));
			}			
			getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
			getTableHeader().getColumnModel().getColumn(1).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(1).setMinWidth(200);
		}		
	}
	
	class FFWorkTableModel extends AbstractTableModel{

		private static final long serialVersionUID = 1L;
		private List<FacebookData> data = new ArrayList<FacebookData>();

		public List<FacebookData> getData() {
			return data;
		}

		public void setDatas(List<FacebookData> data) {
			this.data = data;
		}

		public FFWorkTableModel(List<FacebookData> data) {
			super();
			this.data = data;
		}
		
		@Override
		public String getColumnName(int i) {
			switch (i) {
			case 0:
				return "Position";
			case 1:
				return "Employer";
			case 2:
				return "Description";
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
			return 3;
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
}
