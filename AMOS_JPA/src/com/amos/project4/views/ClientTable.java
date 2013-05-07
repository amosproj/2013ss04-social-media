/*
 * 
 * This file is part of the software project "Social Media and Datev".
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

import java.beans.PropertyChangeEvent;

import javax.swing.JTable;

import com.amos.project4.controllers.ClientController;

public class ClientTable  extends JTable implements  AbstractControlledView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143258101609845500L;
	ClientTableModel model;
	
	public ClientTable(ClientController controller) {
		super();
		this.model = new ClientTableModel(controller);
		this.setModel(model);
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		this.model.fireTableDataChanged();
	}
}
