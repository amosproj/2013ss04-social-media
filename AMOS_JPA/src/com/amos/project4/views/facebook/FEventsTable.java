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

public class FEventsTable extends JTable implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private FEventsTableModel model;
	
	public FEventsTable() {
		super();
		this.model = new FEventsTableModel(new ArrayList<FacebookData>());
		setModel(model);
//		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
//		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
//		getTableHeader().getColumnModel().getColumn(1).setMaxWidth(300);
//		getTableHeader().getColumnModel().getColumn(1).setMinWidth(200);
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			List<FacebookData>  tmp_events =  c.getFacebookDatasByType(FacebookDataType.EVENTS);			
			if(tmp_events != null && !tmp_events.isEmpty()){
				this.setModel((TableModel) new FEventsTableModel(tmp_events));
			}else{
				this.setModel((TableModel) new FEventsTableModel(new ArrayList<FacebookData>()));
			}			
//			getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
//			getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
//			getTableHeader().getColumnModel().getColumn(1).setMaxWidth(300);
//			getTableHeader().getColumnModel().getColumn(1).setMinWidth(200);
		}		
	}
	
	class FEventsTableModel extends AbstractTableModel{

		private static final long serialVersionUID = 1L;
		private List<FacebookData> datas = new ArrayList<FacebookData>();

		public List<FacebookData> getDatas() {
			return datas;
		}

		public void setDatas(List<FacebookData> datas) {
			this.datas = datas;
		}

		public FEventsTableModel(List<FacebookData> datas) {
			super();
			this.datas = datas;
		}
		
		@Override
		public String getColumnName(int i) {
			switch (i) {
			case 0:
				return "ID";
			case 1:
				return "Event name";
			case 2:
				return "Start time";
			case 3:
				return "End time";
			case 4:
				return "Location";
			case 5:
				return "Owner";
			case 6:
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
			return datas.size();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String row_data = datas.get(rowIndex).getDataString();
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
