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

import java.util.Observable;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.amos.project4.controllers.ClientsController;

public class ClientTable extends JTable implements AbstractControlledView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9143258101609845500L;
	private ClientTableModel model;
	private SelectedClientViewModel selectedClient;

	public ClientTable(ClientsController controller) {
		super();
		this.model = new ClientTableModel(controller);
		this.setModel(model);
		// Initialise and register the Model
		selectedClient = new SelectedClientViewModel();
		controller.addModel(selectedClient);
		this.setAutoCreateRowSorter(true);
		this.getSelectionModel().addListSelectionListener(
				new ClientSelectionListener());
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (o.getClass().equals(SearchParameters.class))
			this.model.fireTableDataChanged();
	}

	public ClientTableModel getModel() {
		return model;
	}

	private class ClientSelectionListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(getSelectedRows() != null && getSelectedRows().length > 0){
				selectedClient.setSelectedClient(getModel().getClientAt(getSelectedRows()[0]));
			}
		}
	}
}