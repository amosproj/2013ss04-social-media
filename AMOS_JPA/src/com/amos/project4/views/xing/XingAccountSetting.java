package com.amos.project4.views.xing;

import java.util.List;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.models.Client;
import com.amos.project4.models.XingData;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.utils.AMOSUtils;
import com.amos.project4.views.DefaultAccountSettingPanel;

public class XingAccountSetting extends DefaultAccountSettingPanel {

	private static final long serialVersionUID = 1L;

	public XingAccountSetting(ClientsController controller,MediaSearchController media_controller) {
		super(controller,media_controller);
		this.setProfileURL("https://www.xing.com");
		this.updateButtonTitle("Xing");
		this.updateButtonIcon(AMOSUtils.makeIcon(AMOSUtils.XING_LOGO_URL, 27, 16));
	}
	@Override
	protected void updatePanel(Client arg) {
		List<XingData> datas = arg.getXingDatasByType(XingDataType.PERMALINK);
		this.disableLink();
		if(datas != null && !datas.isEmpty()){
			String data = datas.get(0).getDataString();
			this.setProfileURL(data);
			this.eneableLink();
		}		
	}

}
