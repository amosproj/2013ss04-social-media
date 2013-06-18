package com.amos.project4.views.facebook;

import java.util.List;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.utils.AMOSUtils;
import com.amos.project4.views.DefaultAccountSettingPanel;

public class FacebookAccountSetting extends DefaultAccountSettingPanel {
	private static final long serialVersionUID = 1L;

	public FacebookAccountSetting(ClientsController controller,MediaSearchController media_controller) {
		super(controller, media_controller);
		this.setProfileURL("http://www.facebook.com");
		this.updateButtonTitle("Facebook");
		this.updateButtonIcon(AMOSUtils.makeIcon(AMOSUtils.FACEBOOK_SMALL_LOGO_URL, 16, 16));
	}
	@Override
	protected void updatePanel(Client arg) {
		List<FacebookData> datas = arg.getFacebookDatasByType(FacebookDataType.UID);
		this.disableLink();
		if(datas != null && !datas.isEmpty()){
			String data = datas.get(0).getDataString();
			this.setProfileURL("http://www.facebook.com/"+ data);
			this.eneableLink();
		}		
	}
	
}
