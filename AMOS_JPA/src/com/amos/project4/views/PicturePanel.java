package com.amos.project4.views;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.JPanel;

public class PicturePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String url;
	public PicturePanel(String url) {
		super();
		this.url = url;
		this.repaint();
	}
	@Override
	public void paintComponent(Graphics grafica) {
		try{
				java.net.URL imgURL = new URL("");
	            Image imgFondo = javax.imageio.ImageIO.read(imgURL);
	            grafica.drawImage(imgFondo, 0, 0, null);
		}catch(Exception e){
			e.printStackTrace();
		}				
	}
	
}
