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

package com.amos.project4.views;

import java.util.Date;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.models.Client;

/**
 * Basic table Model for the Client Table in the GUI
 * 
 * @author Jupiter BAKAKEU
 * 
 */
public class ClientTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ClientsController controller;

	public ClientTableModel(ClientsController controller) {
		this.controller = controller;
	}

	public ClientsController getController() {
		return controller;
	}

	public void setController(ClientsController controller) {
		this.controller = controller;
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
		case 5:
			return String.class;
		case 6:
			return String.class;
		case 7:
			return char.class;
		default:
			return String.class;
		}

	}

	@Override
	public int getColumnCount() {
		return 8;
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
		case 5:
			return "Place";
		case 6:
			return "ZipCode";
		case 7:
			return "Gender";
		default:
			return "Unknown";
		}
	}

	@Override
	public int getRowCount() {
		return ((ClientsController) (this.getController())).getAllClients()
				.size();
	}

	@Override
	public Object getValueAt(int i, int j) {
		switch (j) {
		case 0:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getID();
		case 1:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getName();
		case 2:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getFirstname();
		case 3:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getBirthdate();
		case 4:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getMail();
		case 5:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getPlace();
		case 6:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getZipCode();
		case 7:
			return ((ClientsController) (this.getController())).getAllClients()
					.get(i).getGender();
		default:
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}


	public Client getClientAt(int i) {
		return ((ClientsController) (this.getController())).getAllClients()
				.get(i);
	}
}
