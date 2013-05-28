package com.amos.project4.views.facebook;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.FacebookDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.views.AbstractControlledView;

public class FacebookDetailPanel extends JPanel implements AbstractControlledView {
	
	private static final long serialVersionUID = 1L;
	private JTextField ID_textField;
	private JTextField facebookName_textField;
	private JTextField birthday_textField;
	private JTextField relationship_textField;
	private JTextField biography_textField;
	
	private FacebookPicturePanel Picture_panel;
	
	private FacebookDataController controller;
	private ClientsController c_controller;
	
	private FPostTable posts_table;
	private FFriendsTable friends_table;
	private FEducationTable educations_table;
	private FMutualFriendsTable mutual_friends_table;
	private FWorkTable work_table;
	private FInterestTable interest_table;
	private FEventsTable events_table;
	
	public FacebookDetailPanel(ClientsController c_controller) {
		super();
		this.c_controller = c_controller;
		this.c_controller.addView(this);
		this.controller = new FacebookDataController();
		this.controller.addView(this);
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
		
		Picture_panel = new FacebookPicturePanel();
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, Picture_panel, -6, SpringLayout.SOUTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, Picture_panel, 10, SpringLayout.NORTH, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, Picture_panel, 10, SpringLayout.WEST, accountDetails_Panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, Picture_panel, 160, SpringLayout.WEST, accountDetails_Panel);
		accountDetails_Panel.add(Picture_panel);
		c_controller.addView(Picture_panel);

		
		JLabel lblId = new JLabel("ID:");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblId, 10, SpringLayout.NORTH, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblId, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblId);
		
		JLabel lblFacebookName = new JLabel("Facebook  name:");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblFacebookName, 12, SpringLayout.SOUTH, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblFacebookName, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblFacebookName);
		
		ID_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, ID_textField, 6, SpringLayout.EAST, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, ID_textField, 400, SpringLayout.EAST, lblFacebookName);
		ID_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, ID_textField, 0, SpringLayout.SOUTH, lblId);
		accountDetails_Panel.add(ID_textField);
		ID_textField.setColumns(10);
		
		facebookName_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, facebookName_textField, 6, SpringLayout.EAST, lblFacebookName);
		facebookName_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, facebookName_textField, 0, SpringLayout.SOUTH, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, facebookName_textField, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(facebookName_textField);
		facebookName_textField.setColumns(10);
		
		JTabbedPane facebook_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, facebook_tabbedPane, 10, SpringLayout.SOUTH, accountDetails_Panel);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblBirthday, 12, SpringLayout.SOUTH, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblBirthday, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblBirthday);
		
		birthday_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, birthday_textField, 6, SpringLayout.EAST, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, birthday_textField, 0, SpringLayout.EAST, ID_textField);
		birthday_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, birthday_textField, 6, SpringLayout.SOUTH, facebookName_textField);
		accountDetails_Panel.add(birthday_textField);
		birthday_textField.setColumns(10);
		
		JLabel lblRelationship = new JLabel("Relationship:");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblRelationship, 12, SpringLayout.SOUTH, lblBirthday);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblRelationship, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblRelationship);
		
		relationship_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, relationship_textField, 6, SpringLayout.EAST, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, relationship_textField, 0, SpringLayout.EAST, ID_textField);
		relationship_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, relationship_textField, 6, SpringLayout.SOUTH, birthday_textField);
		accountDetails_Panel.add(relationship_textField);
		relationship_textField.setColumns(10);
		
		JLabel lblBiography = new JLabel("Biography:");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblBiography, 12, SpringLayout.SOUTH, lblRelationship);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblBiography, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblBiography);
		
		biography_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, biography_textField, 6, SpringLayout.EAST, lblFacebookName);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, biography_textField, 0, SpringLayout.EAST, ID_textField);
		biography_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, biography_textField, 6, SpringLayout.SOUTH, relationship_textField);
		accountDetails_Panel.add(biography_textField);
		biography_textField.setColumns(10);
		springLayout.putConstraint(SpringLayout.WEST, facebook_tabbedPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, facebook_tabbedPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, facebook_tabbedPane, 0, SpringLayout.EAST, this);
		add(facebook_tabbedPane);
		
		JPanel post_Panel = new JPanel();
		facebook_tabbedPane.addTab("Posts", null, post_Panel, null);
		post_Panel.setBorder(new TitledBorder(null, "Last Posts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_posts_Panel = new SpringLayout();
		post_Panel.setLayout(sl_posts_Panel);
		
		JScrollPane posts_scrollPane = new JScrollPane();
		sl_posts_Panel.putConstraint(SpringLayout.NORTH, posts_scrollPane, 0, SpringLayout.NORTH, post_Panel);
		sl_posts_Panel.putConstraint(SpringLayout.WEST, posts_scrollPane, 0, SpringLayout.WEST, post_Panel);
		sl_posts_Panel.putConstraint(SpringLayout.SOUTH, posts_scrollPane, -0, SpringLayout.SOUTH, post_Panel);
		sl_posts_Panel.putConstraint(SpringLayout.EAST, posts_scrollPane, -0, SpringLayout.EAST, post_Panel);
		post_Panel.add(posts_scrollPane);
		
		posts_table = new FPostTable();
		this.c_controller.addView(posts_table);
		posts_scrollPane.add(posts_table);
		posts_scrollPane.setViewportView(posts_table);
		
		JPanel Educations_Panel = new JPanel();
		facebook_tabbedPane.addTab("Education", null, Educations_Panel, null);
		Educations_Panel.setBorder(new TitledBorder(null, "Education", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Trends_Panel = new SpringLayout();
		Educations_Panel.setLayout(sl_Trends_Panel);
		
		JScrollPane educations_scrollPane = new JScrollPane();
		sl_Trends_Panel.putConstraint(SpringLayout.NORTH, educations_scrollPane, 0, SpringLayout.NORTH, Educations_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.WEST, educations_scrollPane, 0, SpringLayout.WEST, Educations_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.SOUTH, educations_scrollPane, -0, SpringLayout.SOUTH, Educations_Panel);
		sl_Trends_Panel.putConstraint(SpringLayout.EAST, educations_scrollPane, -0, SpringLayout.EAST, Educations_Panel);
		Educations_Panel.add(educations_scrollPane);
		
		educations_table = new FEducationTable();
		this.c_controller.addView(educations_table);
		educations_scrollPane.setViewportView(educations_table);
		
		JPanel Friends_Panels = new JPanel();
		facebook_tabbedPane.addTab("Friends", null, Friends_Panels, null);
		Friends_Panels.setBorder(new TitledBorder(null, "Friends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Friends_Panels = new SpringLayout();
		Friends_Panels.setLayout(sl_Friends_Panels);
		
		JScrollPane friends_scrollPane = new JScrollPane();
		sl_Friends_Panels.putConstraint(SpringLayout.NORTH, friends_scrollPane, 0, SpringLayout.NORTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.WEST, friends_scrollPane, 0, SpringLayout.WEST, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.SOUTH, friends_scrollPane, -0, SpringLayout.SOUTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.EAST, friends_scrollPane, -0, SpringLayout.EAST, Friends_Panels);
		Friends_Panels.add(friends_scrollPane);
		
		friends_table = new FFriendsTable();
		this.c_controller.addView(friends_table);
		friends_scrollPane.setViewportView(friends_table);
		
		JPanel Mutal_friends = new JPanel();
		Mutal_friends.setBorder(new TitledBorder(null, "Mutual friends", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		facebook_tabbedPane.addTab("Mutual friends", null, Mutal_friends, null);
		Mutal_friends.setLayout(new BorderLayout(0, 0));
		
		JScrollPane mutual_friends_scrollPane = new JScrollPane();
		Mutal_friends.add(mutual_friends_scrollPane);
		
		mutual_friends_table = new FMutualFriendsTable();
		this.c_controller.addView(mutual_friends_table);
		mutual_friends_scrollPane.setViewportView(mutual_friends_table);
		
		JPanel works_panel = new JPanel();
		works_panel.setBorder(new TitledBorder(null, "Work", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		facebook_tabbedPane.addTab("Work", null, works_panel, null);
		works_panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane work_scrollPane = new JScrollPane();
		works_panel.add(work_scrollPane, BorderLayout.CENTER);
		
		work_table = new FWorkTable();
		this.c_controller.addView(work_table);
		work_scrollPane.setViewportView(work_table);
		
		JPanel interest_panel = new JPanel();
		interest_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Interests", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		facebook_tabbedPane.addTab("Interests", null, interest_panel, null);
		interest_panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane interest_scrollPane = new JScrollPane();
		interest_panel.add(interest_scrollPane, BorderLayout.CENTER);
		
		interest_table = new FInterestTable();
		this.c_controller.addView(interest_table);
		interest_scrollPane.setViewportView(interest_table);
		
		JPanel events_panel = new JPanel();
		events_panel.setBorder(new TitledBorder(null, "Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		facebook_tabbedPane.addTab("Events", null, events_panel, null);
		events_panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane events_scrollPane = new JScrollPane();
		events_panel.add(events_scrollPane, BorderLayout.CENTER);
		
		events_table = new FEventsTable();
		this.c_controller.addView(events_table);
		events_scrollPane.setViewportView(events_table);
		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			controller.setSelected_client(c);
			
			// Get the ID from Database
			List<FacebookData> tmp = c.getFacebookDatasByType(FacebookDataType.UID);
			if(tmp != null && !tmp.isEmpty()){
				this.ID_textField.setText(tmp.get(0).getDataString());
			}else{
				this.ID_textField.setText("");
			}
			
			// Get the user Name
			tmp =  c.getFacebookDatasByType(FacebookDataType.USERNAME);
			if(tmp != null && !tmp.isEmpty()){
				this.facebookName_textField.setText(tmp.get(0).getDataString());
			}else{
				this.facebookName_textField.setText("");
			}
			
			// Get Birthday
			tmp =  c.getFacebookDatasByType(FacebookDataType.BIRTHDAY);
			if(tmp != null && !tmp.isEmpty()){
				this.birthday_textField.setText(tmp.get(0).getDataString());
			}else{
				this.birthday_textField.setText("");
			}
			
			// Get Biography
			tmp =  c.getFacebookDatasByType(FacebookDataType.BIOGRAPHY);
			if(tmp != null && !tmp.isEmpty()){
				this.biography_textField.setText(tmp.get(0).getDataString());
			}else{
				this.biography_textField.setText("");
			}
			
			// Get Relationship
			tmp =  c.getFacebookDatasByType(FacebookDataType.RELATIONSHIP);
			if(tmp != null && !tmp.isEmpty()){
				this.relationship_textField.setText(tmp.get(0).getDataString());
			}else{
				this.relationship_textField.setText("");
			}
		}		
	}
}
