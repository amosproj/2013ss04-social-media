package com.amos.project4.test;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TestImages {
	
	public static void main(String[] args) throws IOException {
		TestImages test = new TestImages();
		URL url = test.getClass().getResource("/com/amos/project4/images/no_images.jpg");
		System.out.println(url.getPath());
		Image image = javax.imageio.ImageIO.read(new java.io.File(url.getPath()));
		Image scaled = image.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		System.out.println(image.getHeight(null) + " " + image.getWidth(null));
		System.out.println(scaled.getHeight(null) + " " + scaled.getWidth(null));
		
    }

}
