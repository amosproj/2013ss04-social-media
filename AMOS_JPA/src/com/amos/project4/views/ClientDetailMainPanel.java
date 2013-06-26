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

package com.amos.project4.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.models.BirthdayClient;
import com.amos.project4.models.Client;
import com.amos.project4.models.FacebookData;
import com.amos.project4.models.LinkedInData;
import com.amos.project4.models.TwitterData;
import com.amos.project4.models.XingData;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataRetriever;
import com.amos.project4.socialMedia.Xing.XingDataRetriever;
import com.amos.project4.socialMedia.facebook.FacebookDataRetriever;
import com.amos.project4.socialMedia.twitter.TwitterDataRetriever;
import com.amos.project4.utils.AMOSUtils;

public class ClientDetailMainPanel extends JPanel implements
		AbstractControlledView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField name_textField;
	private JTextField firstnameTextField;
	private JTextField birthdateTextField;
	private JTextField PlaceTextField;
	private JTextField emailTextField;
	private ClientsController client_controller;
	private JLabel lblBirthday;
	private JLabel lblPost;
	private JLabel lblPost_1;
	private JLabel lblPost_3;
	private JLabel lblPost_2;
	private JLabel lblPost_4;
	private JLabel lblTwits;
	private JLabel lblTwits_1;
	private JLabel lblTwits_2;
	private JLabel lblTwits_3;
	private JLabel lblTwits_4;
	private JLabel lblCompany;
	private JLabel lblCompany_1;
	private JLabel lblCompany_2;
	private JLabel lblCompany_3;
	private JLabel lblCompany_4;
	private JLabel lblPosition;
	private JLabel lblPosition_1;
	private JLabel lblPosition_2;
	private JLabel lblPosition_3;
	private JLabel lblPosition_4;
	private twitterThread t;
	private facebookThread f;
	private xingThread x;
	private linkedInThread l;
	
	
	public ClientDetailMainPanel(ClientsController client_controller) {
		super();
		this.client_controller = client_controller;
		this.client_controller.addView(this);
		init();
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "NOTIFICATIONS");		
		fillbirthdayOfTheWeek();
	}

	private void fillbirthdayOfTheWeek() {
		String msg = "No Birthdays this week";
		if(client_controller == null){
			msg = "Client controller not initialised";
			msg = msg + ".";
			this.getLblBirthday().setText(msg);
			return;
		}
		Client c_client = client_controller.getSelectedClient();
		if(c_client == null && ! client_controller.getAllBirthDayOfTheWeek().isEmpty()){
			List<BirthdayClient> b_clients = client_controller.getAllBirthDayOfTheWeek();
			msg = "This weeks birthdays ("+ b_clients.size() +") : " ;
			for (int i = 0; i < b_clients.size(); i++) {
				msg += b_clients.get(i).getFirstname() + " " + b_clients.get(i).getName() ;
				if(i!= b_clients.size()-1){
					msg += ", ";
				}
			}
			msg = msg + ".";
		}else if(c_client != null ){
			Calendar cal = Calendar.getInstance();
		    cal.setTime(client_controller.getSelectedClient().getBirthdate());
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    cal.setTime(new Date(System.currentTimeMillis()));
		    int c_month = cal.get(Calendar.MONTH);
		    int c_day = cal.get(Calendar.DAY_OF_MONTH);
		    if(day == c_day && month == c_month){
		    	msg = c_client.getFirstname() + " " + c_client.getName() +" has birthday today !";
		    	msg = msg + ".";
		    }else{
		    	msg = "";
		    }	
		}
		this.getLblBirthday().setText(msg);
	}

	private void init() {		
		this.setLayout(new CardLayout());
		this.add(initOnlyNotifications(),"NOTIFICATIONS");
		this.add(initOnlyPersonnalInformation(),"PERSONAL");
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, "NOTIFICATIONS");		
	}
	private JPanel initOnlyNotifications(){
		JPanel top = new JPanel();
		SpringLayout springLayout = new SpringLayout();
		top.setLayout(springLayout);
		lblBirthday = new JLabel("This Weeks birthdays ( 9) : Jupiter, Alex, Matthias, Isabella");
		springLayout.putConstraint(SpringLayout.NORTH, lblBirthday, 20, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.WEST, lblBirthday, 6, SpringLayout.WEST, top);
		springLayout.putConstraint(SpringLayout.SOUTH, lblBirthday, 36, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.EAST, lblBirthday, -6, SpringLayout.EAST, top);
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 11));
		top.add(lblBirthday);
		
		JPanel Notification_container = initNotificationPanel();
		springLayout.putConstraint(SpringLayout.NORTH, Notification_container, 10, SpringLayout.SOUTH, lblBirthday);
		springLayout.putConstraint(SpringLayout.WEST, Notification_container, 6, SpringLayout.WEST, top);
		springLayout.putConstraint(SpringLayout.SOUTH, Notification_container, -6, SpringLayout.SOUTH, top);
		springLayout.putConstraint(SpringLayout.EAST, Notification_container, -6, SpringLayout.EAST, top);
		top.add(Notification_container);
		
		return top;
	}
	private JPanel initOnlyPersonnalInformation(){
		JPanel top = new JPanel();
		SpringLayout springLayout = new SpringLayout();
		top.setLayout(springLayout);

		JPanel personalInformationsPanel = initClientDetails();
		springLayout.putConstraint(SpringLayout.NORTH,
				personalInformationsPanel, 10, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.WEST,
				personalInformationsPanel, 4, SpringLayout.WEST, top);
		springLayout.putConstraint(SpringLayout.SOUTH,
				personalInformationsPanel, 130, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.EAST,
				personalInformationsPanel, 1, SpringLayout.EAST, top);
		top.add(personalInformationsPanel);
		return top;
	}

	private JPanel initNotificationPanel() {
		JPanel Notification_container = new JPanel();
		
		Notification_container.setLayout(new GridLayout(0, 2, 6, 6));
		
		JPanel facebook_panel = new JPanel();
		Notification_container.add(facebook_panel);
//		springLayout.putConstraint(SpringLayout.NORTH, facebook_panel, 10, SpringLayout.SOUTH, lblBirthday);
//		springLayout.putConstraint(SpringLayout.WEST, facebook_panel, 0, SpringLayout.WEST, personalInformationsPanel);
//		springLayout.putConstraint(SpringLayout.SOUTH, facebook_panel, 120, SpringLayout.SOUTH, lblBirthday);
		facebook_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
//		springLayout.putConstraint(SpringLayout.EAST, facebook_panel, 371, SpringLayout.WEST, this);
		SpringLayout sl_facebook_panel = new SpringLayout();
		facebook_panel.setLayout(sl_facebook_panel);
		
		JLabel lblFacebook = new JLabel("");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblFacebook, 6, SpringLayout.NORTH, facebook_panel);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblFacebook, 6, SpringLayout.WEST, facebook_panel);
		sl_facebook_panel.putConstraint(SpringLayout.SOUTH, lblFacebook, 56, SpringLayout.NORTH, facebook_panel);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblFacebook, 56, SpringLayout.WEST, facebook_panel);
		lblFacebook.setIcon(AMOSUtils.makeIcon(AMOSUtils.FACEBOOK_SMALL_LOGO_URL, 50, 50));
		facebook_panel.add(lblFacebook);
		
		lblPost = new JLabel("post_1");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblPost, 0, SpringLayout.NORTH, lblFacebook);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblPost, 6, SpringLayout.EAST, lblFacebook);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblPost, 0, SpringLayout.EAST, facebook_panel);
		facebook_panel.add(lblPost);
		
		lblPost_1 = new JLabel("post_2");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblPost_1, 6, SpringLayout.SOUTH, lblPost);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblPost_1, 0, SpringLayout.WEST, lblPost);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblPost_1, 0, SpringLayout.EAST, facebook_panel);
		facebook_panel.add(lblPost_1);
		
		lblPost_2 = new JLabel("post_3");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblPost_2, 6, SpringLayout.SOUTH, lblPost_1);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblPost_2, 0, SpringLayout.WEST, lblPost);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblPost_2, 0, SpringLayout.EAST, facebook_panel);
		facebook_panel.add(lblPost_2);
		
		lblPost_3 = new JLabel("post_4");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblPost_3, 6, SpringLayout.SOUTH, lblPost_2);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblPost_3, 0, SpringLayout.WEST, lblPost);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblPost_3, 0, SpringLayout.EAST, facebook_panel);
		facebook_panel.add(lblPost_3);
		
		lblPost_4 = new JLabel("post_5");
		sl_facebook_panel.putConstraint(SpringLayout.NORTH, lblPost_4, 6, SpringLayout.SOUTH, lblPost_3);
		sl_facebook_panel.putConstraint(SpringLayout.WEST, lblPost_4, 0, SpringLayout.WEST, lblPost);
		sl_facebook_panel.putConstraint(SpringLayout.EAST, lblPost_4, 0, SpringLayout.EAST, facebook_panel);
		facebook_panel.add(lblPost_4);
		
		JPanel twitter_panel = new JPanel();
		twitter_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Notification_container.add(twitter_panel);
		SpringLayout sl_twitter_panel = new SpringLayout();
		twitter_panel.setLayout(sl_twitter_panel);
		
		JLabel label = new JLabel("");
		label.setIcon(AMOSUtils.makeIcon(AMOSUtils.TWITTER_SMALL_LOGO_URL, 50, 50));
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, label, 6, SpringLayout.NORTH, twitter_panel);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, twitter_panel);
		sl_twitter_panel.putConstraint(SpringLayout.SOUTH, label, 56, SpringLayout.NORTH, twitter_panel);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, label, 60, SpringLayout.WEST, twitter_panel);
		twitter_panel.add(label);
		
		lblTwits = new JLabel("twits_1");
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, lblTwits, 0, SpringLayout.NORTH, label);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, lblTwits, 6, SpringLayout.EAST, label);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, lblTwits, 0, SpringLayout.EAST, twitter_panel);
		twitter_panel.add(lblTwits);
		
		lblTwits_1 = new JLabel("Twits_2");
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, lblTwits_1, 6, SpringLayout.SOUTH, lblTwits);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, lblTwits_1, 0, SpringLayout.WEST, lblTwits);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, lblTwits_1, 0, SpringLayout.EAST, twitter_panel);
		twitter_panel.add(lblTwits_1);
		
		lblTwits_2 = new JLabel("Twits_3");
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, lblTwits_2, 6, SpringLayout.SOUTH, lblTwits_1);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, lblTwits_2, 0, SpringLayout.WEST, lblTwits_1);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, lblTwits_2, 0, SpringLayout.EAST, twitter_panel);
		twitter_panel.add(lblTwits_2);
		
		lblTwits_3 = new JLabel("Twits_4");
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, lblTwits_3, 7, SpringLayout.SOUTH, lblTwits_2);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, lblTwits_3, 0, SpringLayout.WEST, lblTwits);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, lblTwits_3, 0, SpringLayout.EAST, lblTwits);
		twitter_panel.add(lblTwits_3);
		
		lblTwits_4 = new JLabel("Twits_5");
		sl_twitter_panel.putConstraint(SpringLayout.NORTH, lblTwits_4, 6, SpringLayout.SOUTH, lblTwits_3);
		sl_twitter_panel.putConstraint(SpringLayout.WEST, lblTwits_4, 0, SpringLayout.WEST, lblTwits);
		sl_twitter_panel.putConstraint(SpringLayout.EAST, lblTwits_4, 0, SpringLayout.EAST, lblTwits);
		twitter_panel.add(lblTwits_4);
		
		JPanel Xing_panel = new JPanel();
		Xing_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Notification_container.add(Xing_panel);
		SpringLayout sl_Xing_panel = new SpringLayout();
		Xing_panel.setLayout(sl_Xing_panel);
		
		JLabel lblLabel = new JLabel("");
		sl_Xing_panel.putConstraint(SpringLayout.SOUTH, lblLabel, 46, SpringLayout.NORTH, Xing_panel);
		lblLabel.setIcon(AMOSUtils.makeIcon(AMOSUtils.XING_LOGO_URL, 50, 40));
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblLabel, 6, SpringLayout.NORTH, Xing_panel);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblLabel, 6, SpringLayout.WEST, Xing_panel);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblLabel, 56, SpringLayout.WEST, Xing_panel);
		Xing_panel.add(lblLabel);
		
		lblCompany = new JLabel("Company_1");
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblCompany, 0, SpringLayout.NORTH, lblLabel);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblCompany, 6, SpringLayout.EAST, lblLabel);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblCompany, 0, SpringLayout.EAST, Xing_panel);
		Xing_panel.add(lblCompany);
		
		lblCompany_1 = new JLabel("Company_2");
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblCompany_1, 6, SpringLayout.SOUTH, lblCompany);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblCompany_1, 0, SpringLayout.WEST, lblCompany);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblCompany_1, 0, SpringLayout.EAST, Xing_panel);
		Xing_panel.add(lblCompany_1);
		
		lblCompany_2 = new JLabel("Company_3");
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblCompany_2, 6, SpringLayout.SOUTH, lblCompany_1);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblCompany_2, 0, SpringLayout.WEST, lblCompany);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblCompany_2, 0, SpringLayout.EAST, Xing_panel);
		Xing_panel.add(lblCompany_2);
		
		lblCompany_3 = new JLabel("Company_4");
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblCompany_3, 6, SpringLayout.SOUTH, lblCompany_2);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblCompany_3, 0, SpringLayout.WEST, lblCompany);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblCompany_3, 0, SpringLayout.EAST, Xing_panel);
		Xing_panel.add(lblCompany_3);
		
		lblCompany_4 = new JLabel("Company_5");
		sl_Xing_panel.putConstraint(SpringLayout.NORTH, lblCompany_4, 6, SpringLayout.SOUTH, lblCompany_3);
		sl_Xing_panel.putConstraint(SpringLayout.WEST, lblCompany_4, 0, SpringLayout.WEST, lblCompany);
		sl_Xing_panel.putConstraint(SpringLayout.EAST, lblCompany_4, 0, SpringLayout.EAST, Xing_panel);
		Xing_panel.add(lblCompany_4);
		
		JPanel linkedIn_panel = new JPanel();
		linkedIn_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		Notification_container.add(linkedIn_panel);
		SpringLayout sl_linkedIn_panel = new SpringLayout();
		linkedIn_panel.setLayout(sl_linkedIn_panel);
		
		JLabel LinkedIn_LOGO = new JLabel("");
		sl_linkedIn_panel.putConstraint(SpringLayout.SOUTH, LinkedIn_LOGO, 46, SpringLayout.NORTH, linkedIn_panel);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, LinkedIn_LOGO, 56, SpringLayout.WEST, linkedIn_panel);
		LinkedIn_LOGO.setIcon(AMOSUtils.makeIcon(AMOSUtils.LINKEDIN_LOGO_URL, 50, 40));
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, LinkedIn_LOGO, 6, SpringLayout.NORTH, linkedIn_panel);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, LinkedIn_LOGO, 6, SpringLayout.WEST, linkedIn_panel);
		linkedIn_panel.add(LinkedIn_LOGO);
		
		lblPosition = new JLabel("Position_1");
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, lblPosition, 0, SpringLayout.NORTH, LinkedIn_LOGO);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, lblPosition, 6, SpringLayout.EAST, LinkedIn_LOGO);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, lblPosition, 0, SpringLayout.EAST, linkedIn_panel);
		linkedIn_panel.add(lblPosition);
		
		lblPosition_1 = new JLabel("Position_2");
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, lblPosition_1, 6, SpringLayout.SOUTH, lblPosition);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, lblPosition_1, 0, SpringLayout.WEST, lblPosition);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, lblPosition_1, 0, SpringLayout.EAST, lblPosition);
		linkedIn_panel.add(lblPosition_1);
		
		lblPosition_2 = new JLabel("Position_3");
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, lblPosition_2, 6, SpringLayout.SOUTH, lblPosition_1);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, lblPosition_2, 0, SpringLayout.WEST, lblPosition);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, lblPosition_2, 0, SpringLayout.EAST, lblPosition);
		linkedIn_panel.add(lblPosition_2);
		
		lblPosition_3 = new JLabel("Position_4");
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, lblPosition_3, 6, SpringLayout.SOUTH, lblPosition_2);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, lblPosition_3, 0, SpringLayout.WEST, lblPosition);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, lblPosition_3, 0, SpringLayout.EAST, lblPosition);
		linkedIn_panel.add(lblPosition_3);
		
		lblPosition_4 = new JLabel("Position_5");
		sl_linkedIn_panel.putConstraint(SpringLayout.NORTH, lblPosition_4, 6, SpringLayout.SOUTH, lblPosition_3);
		sl_linkedIn_panel.putConstraint(SpringLayout.WEST, lblPosition_4, 0, SpringLayout.WEST, lblPosition);
		sl_linkedIn_panel.putConstraint(SpringLayout.EAST, lblPosition_4, 0, SpringLayout.EAST, lblPosition);
		linkedIn_panel.add(lblPosition_4);
		
		return Notification_container;
	}

	private JPanel initClientDetails() {
		JPanel personalInformationsPanel = new JPanel();
		
		personalInformationsPanel.setBorder(new TitledBorder(new LineBorder(
				new Color(0, 0, 0)), "Personal informations",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		SpringLayout sl_personalInformationsPanel = new SpringLayout();
		personalInformationsPanel.setLayout(sl_personalInformationsPanel);

		JLabel lblName = new JLabel("Name :");
		lblName.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH, lblName,
				10, SpringLayout.NORTH, personalInformationsPanel);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblName,
				10, SpringLayout.WEST, personalInformationsPanel);
		personalInformationsPanel.add(lblName);

		name_textField = new JTextField();
		name_textField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				name_textField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				name_textField, 10, SpringLayout.EAST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				name_textField, 350, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(name_textField);
		name_textField.setColumns(50);

		JLabel lblFirstname = new JLabel("Firstname :");
		lblFirstname.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblFirstname, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				lblFirstname, 10, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(lblFirstname);

		firstnameTextField = new JTextField();
		firstnameTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				firstnameTextField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				firstnameTextField, 10, SpringLayout.EAST, lblFirstname);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				firstnameTextField, 280, SpringLayout.EAST, lblFirstname);
		personalInformationsPanel.add(firstnameTextField);
		firstnameTextField.setColumns(50);

		JLabel lblBirthdate = new JLabel("Birthdate :");
		lblBirthdate.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblBirthdate, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				lblBirthdate, 0, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(lblBirthdate);

		birthdateTextField = new JTextField();
		birthdateTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				birthdateTextField, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				birthdateTextField, 10, SpringLayout.EAST, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				birthdateTextField, 0, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(birthdateTextField);
		birthdateTextField.setColumns(50);

		JLabel lblPlace = new JLabel("Place :");
		lblPlace.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblPlace, 0, SpringLayout.NORTH, birthdateTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblPlace,
				0, SpringLayout.WEST, lblFirstname);
		personalInformationsPanel.add(lblPlace);

		PlaceTextField = new JTextField();
		PlaceTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				PlaceTextField, 0, SpringLayout.NORTH, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				PlaceTextField, 10, SpringLayout.EAST, lblPlace);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				PlaceTextField, 280, SpringLayout.EAST, lblPlace);
		personalInformationsPanel.add(PlaceTextField);
		PlaceTextField.setColumns(50);

		JLabel lblEmail = new JLabel("E-Mail :");
		lblEmail.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblEmail, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblEmail,
				0, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(lblEmail);

		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				emailTextField, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				emailTextField, 10, SpringLayout.EAST, lblEmail);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				emailTextField, 0, SpringLayout.EAST, birthdateTextField);
		personalInformationsPanel.add(emailTextField);
		emailTextField.setColumns(50);
		
		return personalInformationsPanel;		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
//			CardLayout cl = (CardLayout) this.getLayout();
//			cl.show(this, "PERSONAL");	
			lunchSearchMediaUpdates();
			name_textField.setText("" +c.getName());
			firstnameTextField.setText("" + c.getFirstname());
			birthdateTextField.setText("" + c.getBirthdate());
			PlaceTextField.setText("" +c.getPlace());
			emailTextField.setText("" +c.getMail());
			if(c.getBirthdate() == null) return;
			String msg = "No birthdays this week";			
			Calendar cal = Calendar.getInstance();
		    cal.setTime(c.getBirthdate());
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    cal.setTime(new Date(System.currentTimeMillis()));
		    int c_month = cal.get(Calendar.MONTH);
		    int c_day = cal.get(Calendar.DAY_OF_MONTH);
		    if(day == c_day && month == c_month){
		    	msg = c.getFirstname() + " " + c +" has birthday today !";
		    	msg = msg + ".";
		    }else{
		    	msg = "";
		    }			
			this.getLblBirthday().setText(msg);
		}
	}
	
	public JLabel getLblBirthday() {
		return lblBirthday;
	}
	
	public synchronized void fillFacebookPanel(List<FacebookData> datas){
		if(datas != null && datas.size() >= 1){
			lblPost.setText("\"" + datas.get(0).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(0).getDataString().split("#")[1] 
					+ " by " + datas.get(0).getOwner().getFirstname() + " " 
					+ datas.get(0).getOwner().getName());
		}else{
			if(datas.isEmpty()){
				lblPost.setText("No posts found !");
			}else{
				lblPost.setText("");
			}
		}
		if(datas != null && datas.size() >= 2){
			lblPost_1.setText("\"" + datas.get(1).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(1).getDataString().split("#")[1] 
					+ " by " + datas.get(1).getOwner().getFirstname() + " " 
					+ datas.get(1).getOwner().getName());
		}else{
			lblPost_1.setText("");
		}
		if(datas != null && datas.size() >= 3){
			lblPost_2.setText("\"" + datas.get(2).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(2).getDataString().split("#")[1] 
					+ " by " + datas.get(2).getOwner().getFirstname() + " " 
					+ datas.get(2).getOwner().getName());
		}else{
			lblPost_2.setText("");
		}
		if(datas != null && datas.size() >= 4){
			lblPost_3.setText("\"" + datas.get(3).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(3).getDataString().split("#")[1] 
					+ " by " + datas.get(3).getOwner().getFirstname() + " " 
					+ datas.get(3).getOwner().getName());
		}else{
			lblPost_3.setText("");
		}
		if(datas != null && datas.size() >= 5){
			lblPost_4.setText("\"" + datas.get(4).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(4).getDataString().split("#")[1] 
					+ " by " + datas.get(4).getOwner().getFirstname() + " " 
					+ datas.get(4).getOwner().getName());
		}else{
			lblPost_4.setText("");
		}
	}
	
	private class facebookThread extends Thread {
		public void run() {
			if(client_controller!= null){
				List<Client> clients = client_controller.getAllClients();
				lblPost.setText("Searching for last posts ...");
				lblPost_1.setText("");
				lblPost_2.setText("");
				lblPost_3.setText("");
				lblPost_4.setText("");
				fillFacebookPanel(FacebookDataRetriever.getInstance().getLastPostOfClients(clients, 5));
			}
		}
	}
	
	public synchronized void fillTwitterPanel(List<TwitterData> datas){
		if(datas != null && datas.size() >= 1){
			lblTwits.setText("\"" + datas.get(0).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(0).getDataString().split("#")[1] 
					+ " by " + datas.get(0).getOwner().getFirstname() + " " 
					+ datas.get(0).getOwner().getName());
		}else{
			if(datas.isEmpty()){
				lblTwits.setText("No tweets found !");
			}else{
				lblTwits.setText("");
			}
		}
		if(datas != null && datas.size() >= 2){
			lblTwits_1.setText("\"" + datas.get(1).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(1).getDataString().split("#")[1] 
					+ " by " + datas.get(1).getOwner().getFirstname() + " " 
					+ datas.get(1).getOwner().getName());
		}else{
			lblTwits_1.setText("");
		}
		if(datas != null && datas.size() >= 3){
			lblTwits_2.setText("\"" + datas.get(2).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(2).getDataString().split("#")[1] 
					+ " by " + datas.get(2).getOwner().getFirstname() + " " 
					+ datas.get(2).getOwner().getName());
		}else{
			lblTwits_2.setText("");
		}
		if(datas != null && datas.size() >= 4){
			lblTwits_3.setText("\"" + datas.get(3).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(3).getDataString().split("#")[1] 
					+ " by " + datas.get(3).getOwner().getFirstname() + " " 
					+ datas.get(3).getOwner().getName());
		}else{
			lblTwits_3.setText("");
		}
		if(datas != null && datas.size() >= 5){
			lblTwits_4.setText("\"" + datas.get(4).getDataString().split("#")[2] +"\"" 
					+ " on " + datas.get(4).getDataString().split("#")[1] 
					+ " by " + datas.get(4).getOwner().getFirstname() + " " 
					+ datas.get(4).getOwner().getName());
		}else{
			lblTwits_4.setText("");
		}
	}
	
	private class twitterThread extends Thread {
		public void run() {
			if(client_controller!= null){
				List<Client> clients = client_controller.getAllClients();
				lblTwits.setText("Searching for last tweets...");
				lblTwits_1.setText("");
				lblTwits_2.setText("");
				lblTwits_3.setText("");
				lblTwits_4.setText("");
				fillTwitterPanel(TwitterDataRetriever.getInstance().getLastTweetsOfClients(clients, 5));
			}
		}
	}
	
	public synchronized void fillXingPanel(List<XingData> datas){
		if(datas != null && datas.size() >= 1){
			lblCompany.setText(
					datas.get(0).getOwner().getFirstname() + " " 
							+ datas.get(0).getOwner().getName()
							+ " on " + datas.get(0).getDataString().split("#")[0]
							+ " to \"" + datas.get(0).getDataString().split("#")[1]
							
							+ "\" as " + datas.get(0).getDataString().split("#")[2]
					);
					
		}else{
			if(datas.isEmpty()){
				lblCompany.setText("No compagnies updates found !");
			}else{
				lblCompany.setText("");
			}
		}
		if(datas != null && datas.size() >= 2){
			lblCompany_1.setText(
					datas.get(1).getOwner().getFirstname() + " " 
							+ datas.get(1).getOwner().getName()
							+ " on " + datas.get(1).getDataString().split("#")[0]
							+ " to \"" + datas.get(1).getDataString().split("#")[1]
							
							+ "\" as " + datas.get(1).getDataString().split("#")[2]
					);
		}else{
			lblCompany_1.setText("");
		}
		if(datas != null && datas.size() >= 3){
			lblCompany_2.setText(
					datas.get(0).getOwner().getFirstname() + " " 
							+ datas.get(2).getOwner().getName()
							+ " on " + datas.get(2).getDataString().split("#")[0]
							+ " to \"" + datas.get(2).getDataString().split("#")[1]
							
							+ "\" as " + datas.get(2).getDataString().split("#")[2]
					);
		}else{
			lblCompany_2.setText("");
		}
		if(datas != null && datas.size() >= 4){
			lblCompany_3.setText(
					datas.get(3).getOwner().getFirstname() + " " 
							+ datas.get(3).getOwner().getName()
							+ " on " + datas.get(3).getDataString().split("#")[0]
							+ " to \"" + datas.get(3).getDataString().split("#")[1]
							
							+ "\" as " + datas.get(3).getDataString().split("#")[2]
					);
		}else{
			lblCompany_3.setText("");
		}
		if(datas != null && datas.size() >= 5){
			lblCompany_4.setText(
					datas.get(4).getOwner().getFirstname() + " " 
							+ datas.get(4).getOwner().getName()
							+ " on " + datas.get(4).getDataString().split("#")[0]
							+ " to \"" + datas.get(4).getDataString().split("#")[1]
							
							+ "\" as " + datas.get(4).getDataString().split("#")[2]
					);
		}else{
			lblCompany_4.setText("");
		}
	}
	
	private class xingThread extends Thread {
		public void run() {
			if(client_controller!= null){
				List<Client> clients = client_controller.getAllClients();
				lblCompany.setText("Searching for last companies updates ...");
				lblCompany_1.setText("");
				lblCompany_2.setText("");
				lblCompany_3.setText("");
				lblCompany_4.setText("");
				fillXingPanel(XingDataRetriever.getInstance().getLastModifiedCompanies(clients, 5));
			}
		}
	}
	
	public synchronized void fillLinkedInPanel(List<LinkedInData> list){
		if(list != null && list.size() >= 1){
			lblPosition.setText(
					list.get(0).getOwner().getFirstname() + " " 
							+ list.get(0).getOwner().getName()
							+ "changed headline to " + list.get(0).getDataString().split("#")[2]
							+ " on " + list.get(0).getDataString().split("#")[1]
					);
					
		}else{
			if(list.isEmpty()){
				lblPosition.setText("No headline found !");
			}else{
				lblPosition.setText("");
			}
		}
		if(list != null && list.size() >= 2){
			lblPosition_1.setText(
					list.get(1).getOwner().getFirstname() + " " 
							+ list.get(1).getOwner().getName()
							+ "changed headline to " + list.get(1).getDataString().split("#")[2]
							+ " on " + list.get(1).getDataString().split("#")[1]
					);
		}else{
			lblPosition_1.setText("");
		}
		if(list != null && list.size() >= 3){
			lblPosition_2.setText(
					list.get(2).getOwner().getFirstname() + " " 
							+ list.get(2).getOwner().getName()
							+ "changed headline to " + list.get(2).getDataString().split("#")[2]
							+ " on " + list.get(2).getDataString().split("#")[1]
					);
		}else{
			lblPosition_2.setText("");
		}
		if(list != null && list.size() >= 4){
			lblPosition_3.setText(
					list.get(3).getOwner().getFirstname() + " " 
							+ list.get(3).getOwner().getName()
							+ "changed headline to " + list.get(3).getDataString().split("#")[2]
							+ " on " + list.get(3).getDataString().split("#")[1]
					);
		}else{
			lblPosition_3.setText("");
		}
		if(list != null && list.size() >= 5){
			lblPosition_4.setText(
					list.get(4).getOwner().getFirstname() + " " 
							+ list.get(4).getOwner().getName()
							+ "changed headline to " + list.get(4).getDataString().split("#")[2]
							+ " on " + list.get(4).getDataString().split("#")[1]
					);
		}else{
			lblPosition_4.setText("");
		}
	}
	
	private class linkedInThread extends Thread {
		public void run() {
			if(client_controller!= null){
				List<Client> clients = client_controller.getAllClients();
				lblPosition.setText("Searching for last headlines updates ...");
				lblPosition_1.setText("");
				lblPosition_2.setText("");
				lblPosition_3.setText("");
				lblPosition_4.setText("");
				fillLinkedInPanel(LinkedInDataRetriever.getInstance().getLastModifiedClients(clients, 5));
			}
		}
	}
	
	public void setDetailsUI(){		
		CardLayout cl = (CardLayout) this.getLayout();
		if(client_controller == null || client_controller.getSelectedClient() == null){
			cl.show(this, "NOTIFICATIONS");
			java.awt.EventQueue.invokeLater(new Runnable() {
			    @Override
			    public void run() {
			    	fillbirthdayOfTheWeek();
					lunchSearchMediaUpdates();
			    }
			});
		}else{
			cl.show(this, "PERSONAL");
		}		
	}
	
	private void lunchSearchMediaUpdates() {
		// Start facebook search
    	if(f == null) f = new facebookThread();
		if(f != null && !f.isAlive()){
			//f.destroy();
			f = new facebookThread();
			f.start();
		}else{
			
		}
		// Start twitter search
		if(t == null) t = new twitterThread();
		if(t != null && !t.isAlive()){
			t = new twitterThread();
			t.start();
		}
		// Start xing search
		if(x == null) x = new xingThread();
		if(x != null && !x.isAlive()){
			x = new xingThread();
			x.start();
		}
		// Start LinkedIn search
		if(l == null) l = new linkedInThread();
		if(f != null && !l.isAlive()){
			l = new linkedInThread();
			l.start();
		}
		
	}
}
