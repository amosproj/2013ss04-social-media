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
package com.amos.project4.views.facebook;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.views.AbstractControlledView;

public class FacebookPicturePanel extends JPanel implements AbstractControlledView {

	private static final long serialVersionUID = 1L;
	Image img ;
	Image default_img ;
	public FacebookPicturePanel() {
		super();
		
		setBorder(new LineBorder(new Color(0, 0, 0)));		
		
		try {
			java.net.URL url = new URL("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");//this.getClass().getResource("/com/amos/project4/images/no_images.jpg");
			// Image imgFondo = javax.imageio.ImageIO.read(new java.io.File(url.getPath()));
			 Image imgFondo = javax.imageio.ImageIO.read(url);
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
			List<FacebookData> urls =  c.getFacebookDatasByType(FacebookDataType.PROFILE_PICTURE);
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
