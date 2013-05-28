/*
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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.SocialMediaScanController;
import com.amos.project4.models.Client;
import com.amos.project4.models.User;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaScanDialog extends JDialog implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private JPanel facebookPanel;
	private JPanel buttonPane;
	private JPanel twitterPanel;
	private JPanel xingPanel;
	private JPanel linkedInPanel;
	private JCheckBox chckbxBirthday;
	private JCheckBox chckbxEducation;
	private JCheckBox chckbxNewCheckBox_2;
	private JCheckBox chckbxEvents;
	private JCheckBox chckbxInterests;
	private JCheckBox chckbxFriends;
	private JCheckBox chckbxMutualFriends;
	private JCheckBox chckbxProfilePicture;
	private JCheckBox chckbxTwitterName;
	private JCheckBox chckbxId;
	private JCheckBox chckbxTweets;
	private JCheckBox checkBox;
	private JCheckBox chckbxBirthday_1;
	private JCheckBox chckbxContacts;
	private JCheckBox chckbxProfileMessage;
	private JCheckBox chckbxCompan;
	private SpringLayout sl_xingPanel;
	private JCheckBox chckbxProfileVisits;
	private JCheckBox chckbxContacts_1;
	private JCheckBox chckbxSpecialties;
	private JCheckBox chckbxCompany;
	private JCheckBox chckbxJobTitle;
	private JCheckBox chckbxStatus;
	private SpringLayout sl_linkedInPanel;
	private JCheckBox chckbxPositions;
	private JCheckBox chckbxInterests_1;
	private JCheckBox chckbxEducation_1;
	private JCheckBox chckbxProfileViews;
	private JCheckBox chckbxPhoneNumber;
	private JCheckBox chckbxTwitterAccount;
	private JCheckBox chckbxUsername;
	private JCheckBox chckbxTrends;
	private JCheckBox chckbxPicture;
	
	private SocialMediaScanModel model;
	private SocialMediaScanController controller;
	
	private User user;
	private List<Client> clients;
	
	private JCheckBox chckbxNewCheckBox_1;
	private JCheckBox chckbxNewCheckBox;
	private AbstractButton chckbxRelationship;
	
	private JFrame frame;
	
	public SocialMediaScanDialog(User user,List<Client> clients, JFrame frame){
		this();
		this.model = new SocialMediaScanModel();
		this.controller = new SocialMediaScanController();
		this.controller.addModel(model);
		this.controller.addView(this);
		this.frame = frame;
		
		this.user = user;
		this.clients = clients;
	}
	/**
	 * Create the dialog.
	 */
	private SocialMediaScanDialog() {
		super();
		setTitle("AMOS Project 4 - Social Media Scan");

		setSize( 658, 500);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(frame);

		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		{
			facebookPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.EAST, facebookPanel, -482,
					SpringLayout.EAST, getContentPane());
			facebookPanel.setBorder(new TitledBorder(null, "Facebook",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, facebookPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, facebookPanel, 10,
					SpringLayout.WEST, getContentPane());
			getContentPane().add(facebookPanel);
		}
		{
			twitterPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, twitterPanel, 5, SpringLayout.EAST, facebookPanel);
			springLayout.putConstraint(SpringLayout.SOUTH, twitterPanel, -39,
					SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, twitterPanel, -327, SpringLayout.EAST, getContentPane());
			twitterPanel.setBorder(new TitledBorder(null, "Twitter",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, twitterPanel, 10,
					SpringLayout.NORTH, getContentPane());
			getContentPane().add(twitterPanel);
		}
		{
			xingPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.NORTH, xingPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, xingPanel, 5, SpringLayout.EAST, twitterPanel);
			springLayout.putConstraint(SpringLayout.EAST, xingPanel, -172, SpringLayout.EAST, getContentPane());
			xingPanel.setBorder(new TitledBorder(null, "Xing",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			SpringLayout sl_twitterPanel = new SpringLayout();
			twitterPanel.setLayout(sl_twitterPanel);
			{
				chckbxTwitterName = new JCheckBox("Twitter name");
				chckbxTwitterName.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxTwitterName.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.WEST,
						chckbxTwitterName, 10, SpringLayout.WEST, twitterPanel);
				twitterPanel.add(chckbxTwitterName);
			}
			{
				chckbxId = new JCheckBox("ID");
				chckbxId.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxId.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.ID);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.ID);				
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH,
						chckbxTwitterName, -6, SpringLayout.NORTH, chckbxId);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, chckbxId, 0,
						SpringLayout.WEST, chckbxTwitterName);
				twitterPanel.add(chckbxId);
			}
			{
				chckbxTweets = new JCheckBox("Tweets");
				chckbxTweets.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxTweets.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TWEETS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TWEETS);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH, chckbxTweets,
						-299, SpringLayout.SOUTH, twitterPanel);
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH, chckbxId, -6,
						SpringLayout.NORTH, chckbxTweets);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, chckbxTweets,
						0, SpringLayout.WEST, chckbxTwitterName);
				twitterPanel.add(chckbxTweets);
			}
			{
				checkBox = new JCheckBox("Friends");
				checkBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkBox.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.FRIENDS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.FRIENDS);				
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, checkBox, 6,
						SpringLayout.SOUTH, chckbxTweets);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, checkBox, 0,
						SpringLayout.WEST, chckbxTwitterName);
				twitterPanel.add(checkBox);
			}
			{
				chckbxTrends = new JCheckBox("Trends");
				chckbxTrends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxTrends.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TRENDS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TRENDS);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, chckbxTrends,
						6, SpringLayout.SOUTH, checkBox);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, chckbxTrends,
						0, SpringLayout.WEST, chckbxTwitterName);
				twitterPanel.add(chckbxTrends);
			}
			
			{
				chckbxPicture = new JCheckBox("User photo");
				chckbxPicture.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxPicture.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.USER_PICTURE);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.USER_PICTURE);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, chckbxPicture, 6, SpringLayout.SOUTH, chckbxTrends);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, chckbxPicture,0, SpringLayout.WEST, chckbxTwitterName);
				twitterPanel.add(chckbxPicture);
			}
			
			
			
			getContentPane().add(xingPanel);
		}

		{
			linkedInPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, linkedInPanel, 5, SpringLayout.EAST, xingPanel);
			springLayout.putConstraint(SpringLayout.EAST, linkedInPanel, -17, SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.NORTH, linkedInPanel, 10,
					SpringLayout.NORTH, getContentPane());
			linkedInPanel.setBorder(new TitledBorder(null, "LinkedIn",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			sl_xingPanel = new SpringLayout();
			xingPanel.setLayout(sl_xingPanel);
			getContentPane().add(linkedInPanel);
			sl_linkedInPanel = new SpringLayout();
			linkedInPanel.setLayout(sl_linkedInPanel);
		}
		{
			buttonPane = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH, linkedInPanel, -6,
					SpringLayout.NORTH, buttonPane);
			{
				chckbxContacts_1 = new JCheckBox("Contacts");
				sl_linkedInPanel
						.putConstraint(SpringLayout.NORTH, chckbxContacts_1,
								10, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxContacts_1, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(chckbxContacts_1);
			}
			{
				chckbxSpecialties = new JCheckBox("Current job");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxSpecialties, 126, SpringLayout.NORTH,
						linkedInPanel);
				sl_linkedInPanel
						.putConstraint(SpringLayout.WEST, chckbxSpecialties,
								10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(chckbxSpecialties);
			}
			{
				chckbxCompany = new JCheckBox("Company");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxCompany, 68, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxCompany, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(chckbxCompany);
			}
			{
				chckbxJobTitle = new JCheckBox("Headline");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxJobTitle, 39, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxJobTitle, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(chckbxJobTitle);
			}
			{
				chckbxStatus = new JCheckBox("Status");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxStatus, 97, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST, chckbxStatus,
						10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(chckbxStatus);
			}
			{
				chckbxPositions = new JCheckBox("Positions");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxPositions, 6, SpringLayout.SOUTH,
						chckbxSpecialties);
				sl_linkedInPanel
						.putConstraint(SpringLayout.WEST, chckbxPositions, 0,
								SpringLayout.WEST, chckbxContacts_1);
				linkedInPanel.add(chckbxPositions);
			}
			{
				chckbxInterests_1 = new JCheckBox("Interests");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxInterests_1, 6, SpringLayout.SOUTH,
						chckbxPositions);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxInterests_1, 0, SpringLayout.WEST,
						chckbxContacts_1);
				linkedInPanel.add(chckbxInterests_1);
			}
			{
				chckbxEducation_1 = new JCheckBox("Education");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxEducation_1, 6, SpringLayout.SOUTH,
						chckbxInterests_1);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxEducation_1, 0, SpringLayout.WEST,
						chckbxContacts_1);
				linkedInPanel.add(chckbxEducation_1);
			}
			{
				chckbxProfileViews = new JCheckBox("Profile views");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxProfileViews, 6, SpringLayout.SOUTH,
						chckbxEducation_1);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxProfileViews, 0, SpringLayout.WEST,
						chckbxContacts_1);
				linkedInPanel.add(chckbxProfileViews);
			}
			{
				chckbxPhoneNumber = new JCheckBox("Phone number");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxPhoneNumber, 6, SpringLayout.SOUTH,
						chckbxProfileViews);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxPhoneNumber, 0, SpringLayout.WEST,
						chckbxContacts_1);
				linkedInPanel.add(chckbxPhoneNumber);
			}
			{
				chckbxTwitterAccount = new JCheckBox("Twitter Account");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						chckbxTwitterAccount, 6, SpringLayout.SOUTH,
						chckbxPhoneNumber);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						chckbxTwitterAccount, 0, SpringLayout.WEST,
						chckbxContacts_1);
				linkedInPanel.add(chckbxTwitterAccount);
			}
			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, -6,
					SpringLayout.NORTH, buttonPane);
			{
				chckbxBirthday_1 = new JCheckBox("Birthday");
				sl_xingPanel.putConstraint(SpringLayout.WEST, chckbxBirthday_1,
						10, SpringLayout.WEST, xingPanel);
				xingPanel.add(chckbxBirthday_1);
			}
			{
				chckbxContacts = new JCheckBox("Contacts");
				sl_xingPanel.putConstraint(SpringLayout.WEST, chckbxContacts,
						0, SpringLayout.WEST, chckbxBirthday_1);
				xingPanel.add(chckbxContacts);
			}
			{
				chckbxProfileMessage = new JCheckBox("ProfileMessage");
				sl_xingPanel.putConstraint(SpringLayout.NORTH,
						chckbxProfileMessage, 6, SpringLayout.SOUTH,
						chckbxContacts);
				sl_xingPanel.putConstraint(SpringLayout.WEST,
						chckbxProfileMessage, 0, SpringLayout.WEST,
						chckbxBirthday_1);
				xingPanel.add(chckbxProfileMessage);
			}
			{
				chckbxCompan = new JCheckBox("Company");
				sl_xingPanel.putConstraint(SpringLayout.SOUTH, chckbxCompan,
						-328, SpringLayout.SOUTH, xingPanel);
				sl_xingPanel.putConstraint(SpringLayout.NORTH, chckbxContacts,
						6, SpringLayout.SOUTH, chckbxCompan);
				sl_xingPanel.putConstraint(SpringLayout.SOUTH,
						chckbxBirthday_1, -6, SpringLayout.NORTH, chckbxCompan);
				sl_xingPanel.putConstraint(SpringLayout.WEST, chckbxCompan, 0,
						SpringLayout.WEST, chckbxBirthday_1);
				xingPanel.add(chckbxCompan);
			}
			{
				chckbxProfileVisits = new JCheckBox("Profile visits");
				sl_xingPanel.putConstraint(SpringLayout.NORTH,
						chckbxProfileVisits, 6, SpringLayout.SOUTH,
						chckbxProfileMessage);
				sl_xingPanel.putConstraint(SpringLayout.WEST,
						chckbxProfileVisits, 0, SpringLayout.WEST,
						chckbxBirthday_1);
				xingPanel.add(chckbxProfileVisits);
			}
			springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, 0,
					SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 0,
					SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6,
					SpringLayout.NORTH, buttonPane);
			SpringLayout sl_facebookPanel = new SpringLayout();
			facebookPanel.setLayout(sl_facebookPanel);

			chckbxRelationship = new JCheckBox("Relationship");
			chckbxRelationship.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxRelationship.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);	
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.NORTH,
					chckbxRelationship, 97, SpringLayout.NORTH, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.WEST,
					chckbxRelationship, 10, SpringLayout.WEST, facebookPanel);
			facebookPanel.add(chckbxRelationship);
			{
				chckbxBirthday = new JCheckBox("Birthday");
				chckbxBirthday.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxBirthday.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.BIRTHDAY);	
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxBirthday, 0, SpringLayout.WEST,
						chckbxRelationship);
				facebookPanel.add(chckbxBirthday);
			}

			chckbxNewCheckBox = new JCheckBox("Last post");
			chckbxNewCheckBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxNewCheckBox.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.LAST_POST);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.LAST_POST);					
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH, chckbxBirthday,
					-6, SpringLayout.NORTH, chckbxNewCheckBox);
			sl_facebookPanel
					.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 0,
							SpringLayout.WEST, chckbxRelationship);
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
					chckbxNewCheckBox, -6, SpringLayout.NORTH,
					chckbxRelationship);
			facebookPanel.add(chckbxNewCheckBox);

			chckbxNewCheckBox_1 = new JCheckBox("Biography");
			chckbxNewCheckBox_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(chckbxNewCheckBox_1.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);		
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
					chckbxNewCheckBox_1, -357, SpringLayout.SOUTH,
					facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.NORTH,
					chckbxNewCheckBox_1, 10, SpringLayout.NORTH, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.WEST,
					chckbxNewCheckBox_1, 10, SpringLayout.WEST, facebookPanel);
			facebookPanel.add(chckbxNewCheckBox_1);
			{
				chckbxEducation = new JCheckBox("Education");
				chckbxEducation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxEducation.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.EDUCATION);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.EDUCATION);
					}
				});
				sl_facebookPanel
						.putConstraint(SpringLayout.NORTH, chckbxEducation,
								126, SpringLayout.NORTH, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxEducation, 10, SpringLayout.WEST, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
						chckbxRelationship, -6, SpringLayout.NORTH,
						chckbxEducation);
				facebookPanel.add(chckbxEducation);
			}
			{
				chckbxNewCheckBox_2 = new JCheckBox("Work");
				chckbxNewCheckBox_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxNewCheckBox_2.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.WORK);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.WORK);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxNewCheckBox_2, 155, SpringLayout.NORTH,
						facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxNewCheckBox_2, 10, SpringLayout.WEST,
						facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
						chckbxEducation, -6, SpringLayout.NORTH,
						chckbxNewCheckBox_2);
				facebookPanel.add(chckbxNewCheckBox_2);
			}
			{
				chckbxEvents = new JCheckBox("Events");
				chckbxEvents.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxEvents.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.EVENTS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.EVENTS);				
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxEvents, 6, SpringLayout.SOUTH,
						chckbxNewCheckBox_2);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxEvents,
						10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(chckbxEvents);
			}
			{
				chckbxInterests = new JCheckBox("Interests");
				chckbxInterests.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxInterests.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.INTERESTS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.INTERESTS);			
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxInterests, 6, SpringLayout.SOUTH, chckbxEvents);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxInterests, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(chckbxInterests);
			}
			{
				chckbxFriends = new JCheckBox("Friends");
				chckbxFriends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxFriends.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.FRIENDS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.FRIENDS);			
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxFriends, 6, SpringLayout.SOUTH, chckbxInterests);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxFriends, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(chckbxFriends);
			}
			{
				chckbxMutualFriends = new JCheckBox("Mutual Friends");
				chckbxMutualFriends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxMutualFriends.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxMutualFriends, 6, SpringLayout.SOUTH,
						chckbxFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxMutualFriends, 10, SpringLayout.WEST,
						facebookPanel);
				facebookPanel.add(chckbxMutualFriends);
			}
			{
				chckbxProfilePicture = new JCheckBox("Profile picture");
				chckbxProfilePicture.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxProfilePicture.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxProfilePicture, 6, SpringLayout.SOUTH,
						chckbxMutualFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxProfilePicture, 10, SpringLayout.WEST,
						facebookPanel);
				facebookPanel.add(chckbxProfilePicture);
			}
			{
				chckbxUsername = new JCheckBox("Username");
				chckbxUsername.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxUsername.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.USERNAME);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.USERNAME);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						chckbxUsername, 6, SpringLayout.SOUTH,
						chckbxProfilePicture);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						chckbxUsername, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(chckbxUsername);
			}
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0,
					SpringLayout.WEST, getContentPane());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Start");
				okButton.addActionListener(new StartAction());
				//okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelAction());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private class CancelAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}

	}
	
	private class StartAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			SocialMediaProgressBar bar = new SocialMediaProgressBar(controller, model, clients, user,frame);
			bar.start();
			//dispose();
		}

	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		//System.out.println("ADDED "+ arg.getClass().toString() + " " + arg.toString());
	}
	
}
