package com.amos.project4.views;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

public class TwitterDetailPanel_2 extends JPanel implements
AbstractControlledView {
	private JTextField ID_textField;
	private JTextField twitterName_textField;
	private JTable tweets_table;
	
	private TwitterDataController controller;
	private JTable friends_table;
	private JTable trends_table;
	
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, panel, 120, SpringLayout.NORTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, panel, 160, SpringLayout.WEST, accountDetails_Panel);
		accountDetails_Panel.add(panel);
		
		JLabel lblId = new JLabel("ID :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblId, 10, SpringLayout.NORTH, panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblId, 6, SpringLayout.EAST, panel);
		accountDetails_Panel.add(lblId);
		
		JLabel lblTwitterName = new JLabel("Twitter  name :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblTwitterName, 12, SpringLayout.SOUTH, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblTwitterName, 6, SpringLayout.EAST, panel);
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
		sl_tweets_Panel.putConstraint(SpringLayout.NORTH, tweets_scrollPane, 6, SpringLayout.NORTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.WEST, tweets_scrollPane, 6, SpringLayout.WEST, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.SOUTH, tweets_scrollPane, -6, SpringLayout.SOUTH, tweets_Panel);
		sl_tweets_Panel.putConstraint(SpringLayout.EAST, tweets_scrollPane, -6, SpringLayout.EAST, tweets_Panel);
		tweets_Panel.add(tweets_scrollPane);
		
		tweets_table = new JTable();
		tweets_table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		tweets_scrollPane.setViewportView(tweets_table);
		
		JPanel Friends_Panels = new JPanel();
		Friends_Panels.setBorder(new TitledBorder(null, "Friends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(Friends_Panels);
		SpringLayout sl_Friends_Panels = new SpringLayout();
		Friends_Panels.setLayout(sl_Friends_Panels);
		
		JScrollPane friends_scrollPane = new JScrollPane();
		sl_Friends_Panels.putConstraint(SpringLayout.NORTH, friends_scrollPane, 6, SpringLayout.NORTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.WEST, friends_scrollPane, 6, SpringLayout.WEST, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.SOUTH, friends_scrollPane, -6, SpringLayout.SOUTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.EAST, friends_scrollPane, -6, SpringLayout.EAST, Friends_Panels);
		Friends_Panels.add(friends_scrollPane);
		
		friends_table = new JTable();
		friends_scrollPane.setRowHeaderView(friends_table);
		
		JPanel Trends_Panel = new JPanel();
		Trends_Panel.setBorder(new TitledBorder(null, "Trends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(Trends_Panel);
		SpringLayout sl_Trends_Panel = new SpringLayout();
		Trends_Panel.setLayout(sl_Trends_Panel);
		
		JScrollPane trends_scrollPane = new JScrollPane();
		sl_Trends_Panel.putConstraint(SpringLayout.NORTH, trends_scrollPane, 6, SpringLayout.NORTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.WEST, trends_scrollPane, 6, SpringLayout.WEST, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.SOUTH, trends_scrollPane, -6, SpringLayout.SOUTH, Trends_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.EAST, trends_scrollPane, -6, SpringLayout.EAST, Trends_Panel);
		Trends_Panel.add(trends_scrollPane);
		
		trends_table = new JTable();
		trends_scrollPane.setRowHeaderView(trends_table);
		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			controller.setSelected_client(c);
			TwitterDataDAO dao = controller.getTwitter_DAO();
			List<TwitterData> datas = dao.getAllTwitterDataOfClient(c.getID());
			
			// Get the ID from Database
			List<TwitterData> tmp = controller.getTwitter_DAO().getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.ID);
			if(tmp != null && !tmp.isEmpty()) this.ID_textField.setText(tmp.get(0).getDataString());
			
			// Get the user Name
			tmp = controller.getTwitter_DAO().getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TWITTER_NAME);
			if(tmp != null && !tmp.isEmpty()) this.twitterName_textField.setText(tmp.get(0).getDataString());
			
//			// Get the last Tweets
//			tmp = controller.getTwitter_DAO().getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.FRIENDS);
//			if(tmp != null && !tmp.isEmpty()) this.tweets_table.

//			// Get the user's friends
//			tmp = controller.getTwitter_DAO().getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TWEETS);
//			if(tmp != null && !tmp.isEmpty()) this.tweets_table.set(tmp.get(0).getDataString());
//			
//			// Get mutual friends
//			tmp = controller.getTwitter_DAO().getAllTwitterDataOfClientByType(c.getID(), TwitterDataType.TWEETS);
//			if(tmp != null && !tmp.isEmpty()) this.tweets_table.set(tmp.get(0).getDataString());
//						
						
		}
		
	}
}
