/*
 *
 * This file is part of the Datev and Social Media project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.amos.project4.views.twitter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.amos.project4.models.TwitterData;

public class TrendTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private List<TwitterData> datas = new ArrayList<TwitterData>();

	public List<TwitterData> getDatas() {
		return datas;
	}

	public void setDatas(List<TwitterData> datas) {
		this.datas = datas;
	}

	public TrendTableModel(List<TwitterData> datas) {
		super();
		this.datas = datas;
	}
	
	@Override
	public String getColumnName(int i) {
		switch (i) {
		case 0:
			return "Name";
		case 1:
			return "Country";
		case 2:
			return "URL";
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
		return 3;
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
