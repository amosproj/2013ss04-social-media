package com.amos.project4.views;

import com.amos.project4.models.Client;

public class SelectedClientViewModel extends AbstractViewModel{

	Client selectedClient;

	public Client getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient) {
		Client old = this.getSelectedClient();
		this.selectedClient = selectedClient;
		this.setChanged();
		this.fireChange("SELECTED CLIENT", old, this.getSelectedClient());
	}
	
	
}
