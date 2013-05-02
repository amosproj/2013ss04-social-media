/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit rating application.
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

package com.amos.project4.views;

import java.util.Date;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.amos.project4.controllers.ClientController;
import com.amos.project4.models.Client;

/**
 * Basic table Model for the Client Table in the GUI
 * @author Jupiter BAKAKEU
 *
 */
public class ClientTableModel extends AbstractControlledView implements TableModel  {	
	
	
	public ClientTableModel(ClientController controller) {
		super(controller);
	}

	
	@Override
	public Class<?> getColumnClass(int i) {
		switch (i) {
		case 0:
			return Long.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return Date.class;
		case 4:
			return String.class;
		default:
			return String.class;
		}

	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int i) {
		switch (i) {
		case 0:
			return "ID";
		case 1:
			return "Name";
		case 2:
			return "Firstname";
		case 3:
			return "Birthdate";
		case 4:
			return "E-Mail";
		default:
			return "Unknown";
		}
	}

	@Override
	public int getRowCount() {		
		return ((ClientController)(this.getController())).getAllClients().size();
	}

	@Override
	public Object getValueAt(int i, int j) {
		switch (j) {
		case 0:
			return ((ClientController)(this.getController())).getAllClients().get(i).getID();
		case 1:
			return ((ClientController)(this.getController())).getAllClients().get(i).getName();
		case 2:
			return ((ClientController)(this.getController())).getAllClients().get(i).getFirstname();
		case 3:
			return ((ClientController)(this.getController())).getAllClients().get(i).getBirthdate();
		case 4:
			return ((ClientController)(this.getController())).getAllClients().get(i).getMail();
		default:
			return "";
		}
	}
	
	@Override
	public void setValueAt(Object val, int i, int j) {
		
		/*
		Client old_client = ((ClientController)(this.getController())).getAllClients().get(i);
		
		switch (j) {
		case 0:
			firePropertyChange("ID", old_client.getID(), val);
			break;
		case 1:
			firePropertyChange("Name", old_client.getName(), val);
			break;
		case 2:
			firePropertyChange("ID", old_client.getID(), val);
			break;
		case 3:
			firePropertyChange("ID", old_client.getID(), val);
			break;
		case 4:
			firePropertyChange("ID", old_client.getID(), val);
			break;
		default:
			return ;
		}
		
		*/
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public void modelChanged() {
		
		
	}

}
