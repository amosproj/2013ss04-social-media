package com.amos.project4.views.twitter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.amos.project4.controllers.TwitterDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.views.AbstractControlledView;

public class TwitterPicturePanel extends JPanel implements AbstractControlledView {

	private static final long serialVersionUID = 1L;
	Image img ;
	Image default_img ;
	public TwitterPicturePanel(TwitterDataController controller) {
		super();
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		URL url = this.getClass().getResource("/com/amos/project4/images/no_images.jpg");
		try {
			 Image imgFondo = javax.imageio.ImageIO.read(new java.io.File(url.getPath()));
			 default_img = imgFondo.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		};
		
	}
	
	public void  repaintProfileImage(String url) throws IOException {
		if(url != null && !url.isEmpty()){	
			java.net.URL imgURL = new URL(url);
	        Image imgFondo = javax.imageio.ImageIO.read(imgURL);
	        img = imgFondo.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);	
		}else{
			img = null;
			default_img = default_img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);	
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(img != null){
			g.drawImage(img, 0, 0, null);
		}else{
			g.drawImage(default_img, 0, 0, null);
		}
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			List<TwitterData> urls = c.getTwitterDataByType(TwitterDataType.USER_PICTURE);
			try{
				if(urls != null && !urls.isEmpty()){				
					repaintProfileImage(urls.get(0).getDataString());
				}else{
					repaintProfileImage(null);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		repaint();
		
	}
	
}
