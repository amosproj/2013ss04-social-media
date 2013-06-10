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
package com.amos.project4.views.linkedIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.amos.project4.models.Client;
import com.amos.project4.models.LinkedInData;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.views.AbstractControlledView;

public class PhonesTable extends JTable implements AbstractControlledView{

	private static final long serialVersionUID = 1L;
	private PhonesTableModel model;
	
	public PhonesTable() {
		super();
		this.model = new PhonesTableModel(new ArrayList<LinkedInData>());
		setModel(model);
		getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
		getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;		
			// Get the last Phoness
			List<LinkedInData> tmp_name = c.getLinkedInDatasByType(LinkedInDataType.PHONES_NUMBER);			
			if(tmp_name != null && !tmp_name.isEmpty()){
				this.setModel((TableModel) new PhonesTableModel(tmp_name));
			}else{
				this.setModel((TableModel) new PhonesTableModel(new ArrayList<LinkedInData>()));
			}
			getTableHeader().getColumnModel().getColumn(0).setMaxWidth(300);
			getTableHeader().getColumnModel().getColumn(0).setMinWidth(200);
		}
		
	}
}
