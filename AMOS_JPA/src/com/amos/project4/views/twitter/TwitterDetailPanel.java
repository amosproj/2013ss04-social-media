package com.amos.project4.views.twitter;

import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.TwitterDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.TwitterData;
import com.amos.project4.socialMedia.twitter.TwitterDataType;
import com.amos.project4.views.AbstractControlledView;

public class TwitterDetailPanel extends JPanel implements AbstractControlledView {
	
	private static final long serialVersionUID = 1L;
	private JTextField ID_textField;
	private JTextField twitterName_textField;
	private TwitterPicturePanel Picture_panel;
	
	private TwitterDataController controller;
	private ClientsController c_controller;
	private TweetsTable tweets_table;
	private TFriendsTable friends_table;
	private TrendTable trends_table;
	
	public TwitterDetailPanel(ClientsController c_controller) {
		super();
		this.c_controller = c_controller;
		this.c_controller.addView(this);
		init();		
	}

	private void init() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel accountDetails_Panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, accountDetails_Panel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, accountDetails_Panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, accountDetails_Panel, 160, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, accountDetails_Panel, 0, SpringLayout.EAST, this);
		add(accountDetails_Panel);
		SpringLayout sl_accountDetails_Panel = new SpringLayout();
		accountDetails_Panel.setLayout(sl_accountDetails_Panel);
		
		Picture_panel = new TwitterPicturePanel(this.controller);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, Picture_panel, -6, SpringLayout.SOUTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, Picture_panel, 10, SpringLayout.NORTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, Picture_panel, 10, SpringLayout.WEST, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, Picture_panel, 160, SpringLayout.WEST, accountDetails_Panel);
		accountDetails_Panel.add(Picture_panel);
		c_controller.addView(Picture_panel);

		
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
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, ID_textField, 400, SpringLayout.EAST, lblTwitterName);
		ID_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, ID_textField, 0, SpringLayout.SOUTH, lblId);
		accountDetails_Panel.add(ID_textField);
		ID_textField.setColumns(10);
		
		twitterName_textField = new JTextField();
		twitterName_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, twitterName_textField, 0, SpringLayout.WEST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, twitterName_textField, 0, SpringLayout.SOUTH, lblTwitterName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, twitterName_textField, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(twitterName_textField);
		twitterName_textField.setColumns(10);
		
		JTabbedPane twitter_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, twitter_tabbedPane, 10, SpringLayout.SOUTH, accountDetails_Panel);
		springLayout.putConstraint(SpringLayout.WEST, twitter_tabbedPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, twitter_tabbedPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, twitter_tabbedPane, 0, SpringLayout.EAST, this);
		add(twitter_tabbedPane);
		
		JPanel tweets_Panel = new JPanel();
		twitter_tabbedPane.addTab("Tweets", null, tweets_Panel, null);
		tweets_Panel.setBorder(new TitledBorder(null, "Tweets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_tweets_Panel = new SpringLayout();
		tweets_Panel.setLayout(sl_tweets_Panel);
		
		JScrollPane tweets_scrollPane = new JScrollPane();
		sl_tweets_Panel.putConstraint(SpringLayout.NORTH, tweets_scrollPane, 0, SpringLayout.NORTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.WEST, tweets_scrollPane, 0, SpringLayout.WEST, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.SOUTH, tweets_scrollPane, -0, SpringLayout.SOUTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.EAST, tweets_scrollPane, -0, SpringLayout.EAST, tweets_Panel);
		tweets_Panel.add(tweets_scrollPane);
		
		tweets_table = new TweetsTable();
		this.c_controller.addView(tweets_table);
		tweets_scrollPane.add(tweets_table);
		tweets_scrollPane.setViewportView(tweets_table);
		
		JPanel Friends_Panels = new JPanel();
		twitter_tabbedPane.addTab("Friends", null, Friends_Panels, null);
		Friends_Panels.setBorder(new TitledBorder(null, "Friends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Friends_Panels = new SpringLayout();
		Friends_Panels.setLayout(sl_Friends_Panels);
		
		JScrollPane friends_scrollPane = new JScrollPane();
		sl_Friends_Panels.putConstraint(SpringLayout.NORTH, friends_scrollPane, 0, SpringLayout.NORTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.WEST, friends_scrollPane, 0, SpringLayout.WEST, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.SOUTH, friends_scrollPane, -0, SpringLayout.SOUTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.EAST, friends_scrollPane, -0, SpringLayout.EAST, Friends_Panels);
		Friends_Panels.add(friends_scrollPane);
		
		friends_table = new TFriendsTable();
		this.c_controller.addView(friends_table);
		friends_scrollPane.setViewportView(friends_table);
		
		JPanel Trends_Panel = new JPanel();
		twitter_tabbedPane.addTab("Trends", null, Trends_Panel, null);
		Trends_Panel.setBorder(new TitledBorder(null, "Trends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Trends_Panel = new SpringLayout();
		Trends_Panel.setLayout(sl_Trends_Panel);
		
		JScrollPane trends_scrollPane = new JScrollPane();
		sl_Trends_Panel.putConstraint(SpringLayout.NORTH, trends_scrollPane, 0, SpringLayout.NORTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.WEST, trends_scrollPane, 0, SpringLayout.WEST, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.SOUTH, trends_scrollPane, -0, SpringLayout.SOUTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.EAST, trends_scrollPane, -0, SpringLayout.EAST, Trends_Panel);
		Trends_Panel.add(trends_scrollPane);
		
		trends_table = new TrendTable();
		this.c_controller.addView(trends_table);
		trends_scrollPane.setViewportView(trends_table);
		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			
			// Get the ID from Database
			List<TwitterData> tmp = c.getTwitterDatasByType(TwitterDataType.ID);
			if(tmp != null && !tmp.isEmpty()){
				this.ID_textField.setText("" + tmp.get(0).getDataString());
			}else{
				this.ID_textField.setText("");
			}
			
			// Get the user Name
			tmp = c.getTwitterDatasByType(TwitterDataType.TWITTER_NAME);
			if(tmp != null && !tmp.isEmpty()){
				this.twitterName_textField.setText("@" + tmp.get(0).getDataString());
			}else{
				this.twitterName_textField.setText("");
			}			
		}		
	}	
}
