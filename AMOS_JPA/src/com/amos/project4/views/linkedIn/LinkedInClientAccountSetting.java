package com.amos.project4.views.linkedIn;

import java.util.List;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.models.Client;
import com.amos.project4.models.LinkedInData;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.views.DefaultAccountSettingPanel;

public class LinkedInClientAccountSetting extends DefaultAccountSettingPanel {

	private static final long serialVersionUID = 1L;
	public LinkedInClientAccountSetting(ClientsController controller,MediaSearchController media_controller) {
		super(controller,media_controller);
		this.setProfileURL("http://www.linkedin.com");
		this.updateButtonTitle("LinkedIn");
	}
	
	@Override
	protected void updatePanel(Client arg) {
		List<LinkedInData> datas = arg.getLinkedInDatasByType(LinkedInDataType.PROFILE_URL);
		List<LinkedInData> datas_id = arg.getLinkedInDatasByType(LinkedInDataType.ID);
		this.disableLink();
		if(datas != null && !datas.isEmpty()){
			String data = datas.get(0).getDataString();
			this.setProfileURL(data);
			this.eneableLink();
		}else if(datas_id != null && !datas_id.isEmpty()){
			String id = datas_id.get(0).getDataString();
			this.setProfileURL("http://www.linkedin.com/profile/id=" + id);
			this.eneableLink();
		}
	}
}
