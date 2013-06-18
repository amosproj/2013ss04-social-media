package com.amos.project4.views.twitter;

import java.util.List;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.utils.AMOSUtils;
import com.amos.project4.views.DefaultAccountSettingPanel;

public class TwitterAccountSetting extends DefaultAccountSettingPanel {

	private static final long serialVersionUID = 1L;

	public TwitterAccountSetting(ClientsController controller,MediaSearchController media_controller) {
		super(controller,media_controller);
		this.setProfileURL("https://www.twitter.com");
		this.updateButtonTitle("Twitter");
		this.updateButtonIcon(AMOSUtils.makeIcon(AMOSUtils.TWITTER_TOO_SMALL_LOGO_URL, 16, 16));
	}
	@Override
	protected void updatePanel(Client arg) {
		List<TwitterData> datas = arg.getTwitterDatasByType(TwitterDataType.TWITTER_NAME);
		this.disableLink();
		if(datas != null && !datas.isEmpty()){
			String data = datas.get(0).getDataString();
			this.setProfileURL("https://www.twitter.com/"+data);
			this.eneableLink();
		}		
	}

}
