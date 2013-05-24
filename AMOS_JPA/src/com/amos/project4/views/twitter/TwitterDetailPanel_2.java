package com.amos.project4.views.twitter;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import com.amos.project4.controllers.TwitterDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.TwitterDataDAO;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.views.AbstractControlledView;
import com.amos.project4.views.PicturePanel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TwitterDetailPanel_2 extends JPanel implements AbstractControlledView {
	
	private static final long serialVersionUID = 1L;
	private JTextField ID_textField;
	private JTextField twitterName_textField;
	private JTable tweets_table;
	
	private TwitterDataController controller;
	private JTable friends_table;
	private JTable trends_table;
	private JPanel Picture_panel;
	
	public TwitterDetailPanel_2(TwitterDataController controller) {
		init();
		this.controller = controller;
	}

	
	private void init() {
		setLayout(new GridLayout(4, 0, 0, 4));
		
		JPanel accountDetails_Panel = new JPanel();
		add(accountDetails_Panel);
		SpringLayout sl_accountDetails_Panel = new SpringLayout();
		accountDetails_Panel.setLayout(sl_accountDetails_Panel);
		
		Picture_panel = new JPanel();
		Picture_panel.setMinimumSize(new Dimension(100,100));
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, Picture_panel, -6, SpringLayout.SOUTH, accountDetails_Panel);
		Picture_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, Picture_panel, 10, SpringLayout.NORTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, Picture_panel, 10, SpringLayout.WEST, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, Picture_panel, 160, SpringLayout.WEST, accountDetails_Panel);
		accountDetails_Panel.add(Picture_panel);
		
		JLabel lblId = new JLabel("ID :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblId, 10, SpringLayout.NORTH, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblId, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblId);
		
		JLabel lblTwitterName = new JLabel("Twitter  name :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblTwitterName, 12, SpringLayout.SOUTH, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblTwitterName, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblTwitterName);
		
		ID_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, ID_textField, 6, SpringLayout.EAST, lblTwitterName);
		ID_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, ID_textField, 0, SpringLayout.SOUTH, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, ID_textField, -44, SpringLayout.EAST, accountDetails_Panel);
		accountDetails_Panel.add(ID_textField);
		ID_textField.setColumns(10);
		
		twitterName_textField = new JTextField();
		twitterName_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, twitterName_textField, 0, SpringLayout.WEST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, twitterName_textField, 0, SpringLayout.SOUTH, lblTwitterName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, twitterName_textField, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(twitterName_textField);
		twitterName_textField.setColumns(10);
		
		JPanel tweets_Panel = new JPanel();
		tweets_Panel.setBorder(new TitledBorder(null, "Tweets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(tweets_Panel);
		SpringLayout sl_tweets_Panel = new SpringLayout();
		tweets_Panel.setLayout(sl_tweets_Panel);
		
		JScrollPane tweets_scrollPane = new JScrollPane();
		sl_tweets_Panel.putConstraint(SpringLayout.NORTH, tweets_scrollPane, 0, SpringLayout.NORTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.WEST, tweets_scrollPane, 0, SpringLayout.WEST, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.SOUTH, tweets_scrollPane, -0, SpringLayout.SOUTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.EAST, tweets_scrollPane, -0, SpringLayout.EAST, tweets_Panel);
		tweets_Panel.add(tweets_scrollPane);
		
		tweets_table = new JTable();	
		tweets_scrollPane.add(tweets_table);
		tweets_scrollPane.setViewportView(tweets_table);
		
		JPanel Friends_Panels = new JPanel();
		Friends_Panels.setBorder(new TitledBorder(null, "Friends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(Friends_Panels);
		SpringLayout sl_Friends_Panels = new SpringLayout();
		Friends_Panels.setLayout(sl_Friends_Panels);
		
		JScrollPane friends_scrollPane = new JScrollPane();
		sl_Friends_Panels.putConstraint(SpringLayout.NORTH, friends_scrollPane, 0, SpringLayout.NORTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.WEST, friends_scrollPane, 0, SpringLayout.WEST, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.SOUTH, friends_scrollPane, -0, SpringLayout.SOUTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.EAST, friends_scrollPane, -0, SpringLayout.EAST, Friends_Panels);
		Friends_Panels.add(friends_scrollPane);
		
		friends_table = new JTable();
		friends_scrollPane.setViewportView(friends_table);
		
		JPanel Trends_Panel = new JPanel();
		Trends_Panel.setBorder(new TitledBorder(null, "Trends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(Trends_Panel);
		SpringLayout sl_Trends_Panel = new SpringLayout();
		Trends_Panel.setLayout(sl_Trends_Panel);
		
		JScrollPane trends_scrollPane = new JScrollPane();
		sl_Trends_Panel.putConstraint(SpringLayout.NORTH, trends_scrollPane, 0, SpringLayout.NORTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.WEST, trends_scrollPane, 0, SpringLayout.WEST, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.SOUTH, trends_scrollPane, -0, SpringLayout.SOUTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.EAST, trends_scrollPane, -0, SpringLayout.EAST, Trends_Panel);
		Trends_Panel.add(trends_scrollPane);
		
		trends_table = new JTable();
		trends_scrollPane.setViewportView(trends_table);
		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			controller.setSelected_client(c);
			TwitterDataDAO dao = controller.getTwitter_DAO();
			List<TwitterData> datas = dao.getAllTwitterDataOfClient(c.getID());
			
			// Get the ID from Database
			List<TwitterData> tmp = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.ID);
			if(tmp != null && !tmp.isEmpty()){
				this.ID_textField.setText(tmp.get(0).getDataString());
			}else{
				this.ID_textField.setText("");
			}
			
			// Get the user Name
			tmp = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TWITTER_NAME);
			if(tmp != null && !tmp.isEmpty()){
				this.twitterName_textField.setText(tmp.get(0).getDataString());
			}else{
				this.twitterName_textField.setText("");
			}
			
			// Get the last Tweets
			List<TwitterData> tmp_name = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TWEETS);			
			if(tmp_name != null && !tmp_name.isEmpty()){
				this.tweets_table.setModel((TableModel) new TweetsTableModel(tmp_name));
			}else{
				this.tweets_table.setModel((TableModel) new TweetsTableModel(new ArrayList<TwitterData>()));
			}
			//this.tweets_table.getColumnModel().getColumn(0).setPreferredWidth(200);
			
			// Get the last Tweets
			List<TwitterData> tmp_friends = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.FRIENDS);			
			if(tmp_friends != null && !tmp_friends.isEmpty()){
				this.friends_table.setModel((TableModel) new TFriendsTableModel(tmp_friends));
			}else{
				this.friends_table.setModel((TableModel) new TweetsTableModel(new ArrayList<TwitterData>()));
			}
			//this.friends_table.getColumnModel().getColumn(0).setPreferredWidth(100);
			
			// Get the last Tweets
			List<TwitterData>  tmp_trebds = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TRENDS);			
			if(tmp_trebds != null && !tmp_trebds.isEmpty()){
				this.trends_table.setModel((TableModel) new TrendTableModel(tmp_trebds));
			}else{
				this.trends_table.setModel((TableModel) new TweetsTableModel(new ArrayList<TwitterData>()));
			}
			
			// Set the image
			List<TwitterData> urls = dao.getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.USER_PICTURE);
			try{
				if(urls != null && !urls.isEmpty()){				
					repaintProfileImage(urls.get(0).getDataString());
				}else{				
					BufferedImage image = javax.imageio.ImageIO.read(new java.io.File("img/no_image.jpg"));
					this.Picture_panel.getGraphics().drawImage(image, 0, 0, null);				
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void  repaintProfileImage(String url) throws IOException {
		if(url != null && !url.isEmpty()){	
			java.net.URL imgURL = new URL(url);
	        Image imgFondo = javax.imageio.ImageIO.read(imgURL);
            this.Picture_panel.getGraphics().drawImage(imgFondo.getScaledInstance(this.Picture_panel.getWidth(), this.Picture_panel.getHeight(), Image.SCALE_DEFAULT), 0, 0, null);	
		}
	}
	
}
