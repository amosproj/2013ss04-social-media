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
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaScanDialog extends JDialog implements AbstractControlledView{
	
	private static final long serialVersionUID = 1L;
	private JPanel facebookPanel;
	private JPanel buttonPane;
	private JPanel twitterPanel;
	private JPanel xingPanel;
	private JPanel linkedInPanel;
	private JCheckBox FchckbxBirthday;
	private JCheckBox FchckbxEducation;
	private JCheckBox FchckbxWork;
	private JCheckBox FchckbxEvents;
	private JCheckBox FchckbxInterests;
	private JCheckBox FchckbxFriends;
	private JCheckBox FchckbxMutualFriends;
	private JCheckBox FchckbxProfilePicture;
	private JCheckBox TchckbxTwitterName;
	private JCheckBox TchckbxId;
	private JCheckBox TchckbxTweets;
	private JCheckBox TchckbxFriends;
	private JCheckBox XchckbxBirthday;
	private JCheckBox XchckbxContacts;
	private JCheckBox XchckbxProfileMessage;
	private JCheckBox XchckbxCompany;
	private SpringLayout sl_xingPanel;
	private JCheckBox XchckbxProfileVisits;
	private JCheckBox LchckbxContacts;
	private JCheckBox LchckbxSpecialties;
	private JCheckBox LchckbxCompany;
	private JCheckBox LchckbxJobTitle;
	private JCheckBox LchckbxStatus;
	private SpringLayout sl_linkedInPanel;
	private JCheckBox LchckbxPositions;
	private JCheckBox LchckbxInterests;
	private JCheckBox LchckbxEducation;
	private JCheckBox LchckbxProfileViews;
	private JCheckBox LchckbxPhoneNumber;
	private JCheckBox LchckbxTwitterAccount;
	private JCheckBox FchckbxUsername;
	private JCheckBox TchckbxTrends;
	private JCheckBox TchckbxPicture;
	
	private SocialMediaScanModel model;
	private SocialMediaScanController controller;
	
	private User user;
	private List<Client> clients;
	
	private JCheckBox FchckbxBiography;
	private JCheckBox FchckbxLastPost;
	private AbstractButton FchckbxRelationship;
	
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
				TchckbxTwitterName = new JCheckBox("Twitter name");
				TchckbxTwitterName.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxTwitterName.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.WEST,
						TchckbxTwitterName, 10, SpringLayout.WEST, twitterPanel);
				twitterPanel.add(TchckbxTwitterName);
			}
			{
				TchckbxId = new JCheckBox("ID");
				TchckbxId.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxId.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.ID);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.ID);				
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH,
						TchckbxTwitterName, -6, SpringLayout.NORTH, TchckbxId);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxId, 0,
						SpringLayout.WEST, TchckbxTwitterName);
				twitterPanel.add(TchckbxId);
			}
			{
				TchckbxTweets = new JCheckBox("Tweets");
				TchckbxTweets.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxTweets.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TWEETS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TWEETS);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH, TchckbxTweets,
						-299, SpringLayout.SOUTH, twitterPanel);
				sl_twitterPanel.putConstraint(SpringLayout.SOUTH, TchckbxId, -6,
						SpringLayout.NORTH, TchckbxTweets);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxTweets,
						0, SpringLayout.WEST, TchckbxTwitterName);
				twitterPanel.add(TchckbxTweets);
			}
			{
				TchckbxFriends = new JCheckBox("Friends");
				TchckbxFriends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxFriends.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.FRIENDS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.FRIENDS);				
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, TchckbxFriends, 6,
						SpringLayout.SOUTH, TchckbxTweets);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxFriends, 0,
						SpringLayout.WEST, TchckbxTwitterName);
				twitterPanel.add(TchckbxFriends);
			}
			{
				TchckbxTrends = new JCheckBox("Trends");
				TchckbxTrends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxTrends.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.TRENDS);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.TRENDS);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, TchckbxTrends,
						6, SpringLayout.SOUTH, TchckbxFriends);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxTrends,
						0, SpringLayout.WEST, TchckbxTwitterName);
				twitterPanel.add(TchckbxTrends);
			}
			
			{
				TchckbxPicture = new JCheckBox("User photo");
				TchckbxPicture.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(TchckbxPicture.isSelected())
							model.addSelectedTwitterDataType(TwitterDataType.USER_PICTURE);
						else
							model.removeSelectedTwitterDataType(TwitterDataType.USER_PICTURE);						
					}
				});
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, TchckbxPicture, 6, SpringLayout.SOUTH, TchckbxTrends);
				sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxPicture,0, SpringLayout.WEST, TchckbxTwitterName);
				twitterPanel.add(TchckbxPicture);
			}
			
			JButton btnTSelectAll = new JButton("Select all");
			sl_twitterPanel.putConstraint(SpringLayout.WEST, btnTSelectAll, 0, SpringLayout.WEST, TchckbxTwitterName);
			sl_twitterPanel.putConstraint(SpringLayout.SOUTH, btnTSelectAll, 0, SpringLayout.SOUTH, twitterPanel);
			twitterPanel.add(btnTSelectAll);
			btnTSelectAll.addActionListener(new TSelectAllAction());
			
			
			
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
			springLayout.putConstraint(SpringLayout.SOUTH, twitterPanel, -6,
					SpringLayout.NORTH, buttonPane);
			springLayout.putConstraint(SpringLayout.SOUTH, linkedInPanel, -6,
					SpringLayout.NORTH, buttonPane);
			{
				LchckbxContacts = new JCheckBox("Contacts");
				LchckbxContacts.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxContacts.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.CONTACTS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.CONTACTS);		
					}
				});
				sl_linkedInPanel
						.putConstraint(SpringLayout.NORTH, LchckbxContacts,
								10, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxContacts, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(LchckbxContacts);
			}
			{
				LchckbxSpecialties = new JCheckBox("Current job");
				LchckbxSpecialties.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxSpecialties.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.CURRENT_JOB);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.CURRENT_JOB);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxSpecialties, 126, SpringLayout.NORTH,
						linkedInPanel);
				sl_linkedInPanel
						.putConstraint(SpringLayout.WEST, LchckbxSpecialties,
								10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(LchckbxSpecialties);
			}
			{
				LchckbxCompany = new JCheckBox("Company");
				LchckbxCompany.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxCompany.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.COMPANY);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.COMPANY);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxCompany, 68, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxCompany, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(LchckbxCompany);
			}
			{
				LchckbxJobTitle = new JCheckBox("Headline");
				LchckbxJobTitle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxJobTitle.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.HEADLINES);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.HEADLINES);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxJobTitle, 39, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxJobTitle, 10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(LchckbxJobTitle);
			}
			{
				LchckbxStatus = new JCheckBox("Status");
				LchckbxStatus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxStatus.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.STATUS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.STATUS);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxStatus, 97, SpringLayout.NORTH, linkedInPanel);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST, LchckbxStatus,
						10, SpringLayout.WEST, linkedInPanel);
				linkedInPanel.add(LchckbxStatus);
			}
			{
				LchckbxPositions = new JCheckBox("Positions");
				LchckbxPositions.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxPositions.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.POSITIONS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.POSITIONS);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxPositions, 6, SpringLayout.SOUTH,
						LchckbxSpecialties);
				sl_linkedInPanel
						.putConstraint(SpringLayout.WEST, LchckbxPositions, 0,
								SpringLayout.WEST, LchckbxContacts);
				linkedInPanel.add(LchckbxPositions);
			}
			{
				LchckbxInterests = new JCheckBox("Interests");
				LchckbxInterests.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxInterests.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.INTERESTS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.INTERESTS);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxInterests, 6, SpringLayout.SOUTH,
						LchckbxPositions);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxInterests, 0, SpringLayout.WEST,
						LchckbxContacts);
				linkedInPanel.add(LchckbxInterests);
			}
			{
				LchckbxEducation = new JCheckBox("Education");
				LchckbxEducation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxEducation.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.EDUCATIONS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.EDUCATIONS);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxEducation, 6, SpringLayout.SOUTH,
						LchckbxInterests);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxEducation, 0, SpringLayout.WEST,
						LchckbxContacts);
				linkedInPanel.add(LchckbxEducation);
			}
			{
				LchckbxProfileViews = new JCheckBox("Profile views");
				LchckbxProfileViews.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxProfileViews.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.PROFILE_VIEWS);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.PROFILE_VIEWS);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxProfileViews, 6, SpringLayout.SOUTH,
						LchckbxEducation);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxProfileViews, 0, SpringLayout.WEST,
						LchckbxContacts);
				linkedInPanel.add(LchckbxProfileViews);
			}
			{
				LchckbxPhoneNumber = new JCheckBox("Phone number");
				LchckbxPhoneNumber.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxPhoneNumber.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.PHONES_NUMBER);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.PHONES_NUMBER);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxPhoneNumber, 6, SpringLayout.SOUTH,
						LchckbxProfileViews);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxPhoneNumber, 0, SpringLayout.WEST,
						LchckbxContacts);
				linkedInPanel.add(LchckbxPhoneNumber);
			}
			{
				LchckbxTwitterAccount = new JCheckBox("Twitter Account");
				LchckbxTwitterAccount.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(LchckbxTwitterAccount.isSelected())
							model.addSelectedLinkedInDataType(LinkedInDataType.TWITTER_ACCOUNT);
						else
							model.removeSelectedLinkedInDataType(LinkedInDataType.TWITTER_ACCOUNT);		
					}
				});
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH,
						LchckbxTwitterAccount, 6, SpringLayout.SOUTH,
						LchckbxPhoneNumber);
				sl_linkedInPanel.putConstraint(SpringLayout.WEST,
						LchckbxTwitterAccount, 0, SpringLayout.WEST,
						LchckbxContacts);
				linkedInPanel.add(LchckbxTwitterAccount);
			}
			
			JButton btnLSelectAll = new JButton("Select all");
			sl_linkedInPanel.putConstraint(SpringLayout.WEST, btnLSelectAll, 0, SpringLayout.WEST, LchckbxContacts);
			sl_linkedInPanel.putConstraint(SpringLayout.SOUTH, btnLSelectAll, 0, SpringLayout.SOUTH, linkedInPanel);
			linkedInPanel.add(btnLSelectAll);
			btnLSelectAll.addActionListener(new LSelectAllAction());
			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, -6,
					SpringLayout.NORTH, buttonPane);
			{
				XchckbxBirthday = new JCheckBox("Birthday");
				XchckbxBirthday.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(XchckbxBirthday.isSelected())
							model.addSelectedXingDataType(XingDataType.BIRTHDAY);
						else
							model.removeSelectedXingDataType(XingDataType.BIRTHDAY);	
					}
				});
				sl_xingPanel.putConstraint(SpringLayout.WEST, XchckbxBirthday,
						10, SpringLayout.WEST, xingPanel);
				xingPanel.add(XchckbxBirthday);
			}
			{
				XchckbxContacts = new JCheckBox("Contacts");
				XchckbxContacts.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(XchckbxContacts.isSelected())
							model.addSelectedXingDataType(XingDataType.CONTACTS);
						else
							model.removeSelectedXingDataType(XingDataType.CONTACTS);	
					}
				});
				sl_xingPanel.putConstraint(SpringLayout.WEST, XchckbxContacts,
						0, SpringLayout.WEST, XchckbxBirthday);
				xingPanel.add(XchckbxContacts);
			}
			{
				XchckbxProfileMessage = new JCheckBox("ProfileMessage");
				XchckbxProfileMessage.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(XchckbxProfileMessage.isSelected())
							model.addSelectedXingDataType(XingDataType.PROFILE_MESSAGE);
						else
							model.removeSelectedXingDataType(XingDataType.PROFILE_MESSAGE);	
					}
				});
				sl_xingPanel.putConstraint(SpringLayout.NORTH,
						XchckbxProfileMessage, 6, SpringLayout.SOUTH,
						XchckbxContacts);
				sl_xingPanel.putConstraint(SpringLayout.WEST,
						XchckbxProfileMessage, 0, SpringLayout.WEST,
						XchckbxBirthday);
				xingPanel.add(XchckbxProfileMessage);
			}
			{
				XchckbxCompany = new JCheckBox("Company");
				XchckbxCompany.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(XchckbxCompany.isSelected())
							model.addSelectedXingDataType(XingDataType.COMPANY);
						else
							model.removeSelectedXingDataType(XingDataType.COMPANY);	
					}
				});
				sl_xingPanel.putConstraint(SpringLayout.SOUTH, XchckbxCompany,
						-328, SpringLayout.SOUTH, xingPanel);
				sl_xingPanel.putConstraint(SpringLayout.NORTH, XchckbxContacts,
						6, SpringLayout.SOUTH, XchckbxCompany);
				sl_xingPanel.putConstraint(SpringLayout.SOUTH,
						XchckbxBirthday, -6, SpringLayout.NORTH, XchckbxCompany);
				sl_xingPanel.putConstraint(SpringLayout.WEST, XchckbxCompany, 0,
						SpringLayout.WEST, XchckbxBirthday);
				xingPanel.add(XchckbxCompany);
			}
			{
				XchckbxProfileVisits = new JCheckBox("Profile visits");
				XchckbxProfileVisits.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(XchckbxCompany.isSelected())
							model.addSelectedXingDataType(XingDataType.PROFILE_VISITS);
						else
							model.removeSelectedXingDataType(XingDataType.PROFILE_VISITS);	
					}
				});
				sl_xingPanel.putConstraint(SpringLayout.NORTH,
						XchckbxProfileVisits, 6, SpringLayout.SOUTH,
						XchckbxProfileMessage);
				sl_xingPanel.putConstraint(SpringLayout.WEST,
						XchckbxProfileVisits, 0, SpringLayout.WEST,
						XchckbxBirthday);
				xingPanel.add(XchckbxProfileVisits);
			}
			
			JButton btnXSelectAll = new JButton("Select all");
			sl_xingPanel.putConstraint(SpringLayout.WEST, btnXSelectAll, 0, SpringLayout.WEST, XchckbxBirthday);
			sl_xingPanel.putConstraint(SpringLayout.SOUTH, btnXSelectAll, 0, SpringLayout.SOUTH, xingPanel);
			xingPanel.add(btnXSelectAll);
			btnXSelectAll.addActionListener(new XSelectAllAction());
			springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, 0,
					SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 0,
					SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6,
					SpringLayout.NORTH, buttonPane);
			SpringLayout sl_facebookPanel = new SpringLayout();
			facebookPanel.setLayout(sl_facebookPanel);

			FchckbxRelationship = new JCheckBox("Relationship");
			FchckbxRelationship.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(FchckbxRelationship.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);	
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.NORTH,
					FchckbxRelationship, 97, SpringLayout.NORTH, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.WEST,
					FchckbxRelationship, 10, SpringLayout.WEST, facebookPanel);
			facebookPanel.add(FchckbxRelationship);
			{
				FchckbxBirthday = new JCheckBox("Birthday");
				FchckbxBirthday.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxBirthday.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.BIRTHDAY);	
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxBirthday, 0, SpringLayout.WEST,
						FchckbxRelationship);
				facebookPanel.add(FchckbxBirthday);
			}

			FchckbxLastPost = new JCheckBox("Last post");
			FchckbxLastPost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(FchckbxLastPost.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.LAST_POST);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.LAST_POST);					
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH, FchckbxBirthday,
					-6, SpringLayout.NORTH, FchckbxLastPost);
			sl_facebookPanel
					.putConstraint(SpringLayout.WEST, FchckbxLastPost, 0,
							SpringLayout.WEST, FchckbxRelationship);
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
					FchckbxLastPost, -6, SpringLayout.NORTH,
					FchckbxRelationship);
			facebookPanel.add(FchckbxLastPost);

			FchckbxBiography = new JCheckBox("Biography");
			FchckbxBiography.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(FchckbxBiography.isSelected())
						model.addSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);
					else
						model.removeSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);		
				}
			});
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
					FchckbxBiography, -357, SpringLayout.SOUTH,
					facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.NORTH,
					FchckbxBiography, 10, SpringLayout.NORTH, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.WEST,
					FchckbxBiography, 10, SpringLayout.WEST, facebookPanel);
			facebookPanel.add(FchckbxBiography);
			{
				FchckbxEducation = new JCheckBox("Education");
				FchckbxEducation.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxEducation.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.EDUCATION);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.EDUCATION);
					}
				});
				sl_facebookPanel
						.putConstraint(SpringLayout.NORTH, FchckbxEducation,
								126, SpringLayout.NORTH, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxEducation, 10, SpringLayout.WEST, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
						FchckbxRelationship, -6, SpringLayout.NORTH,
						FchckbxEducation);
				facebookPanel.add(FchckbxEducation);
			}
			{
				FchckbxWork = new JCheckBox("Work");
				FchckbxWork.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxWork.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.WORKS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.WORKS);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxWork, 155, SpringLayout.NORTH,
						facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxWork, 10, SpringLayout.WEST,
						facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.SOUTH,
						FchckbxEducation, -6, SpringLayout.NORTH,
						FchckbxWork);
				facebookPanel.add(FchckbxWork);
			}
			{
				FchckbxEvents = new JCheckBox("Events");
				FchckbxEvents.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxEvents.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.EVENTS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.EVENTS);				
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxEvents, 6, SpringLayout.SOUTH,
						FchckbxWork);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, FchckbxEvents,
						10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(FchckbxEvents);
			}
			{
				FchckbxInterests = new JCheckBox("Interests");
				FchckbxInterests.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxInterests.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.INTERESTS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.INTERESTS);			
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxInterests, 6, SpringLayout.SOUTH, FchckbxEvents);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxInterests, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(FchckbxInterests);
			}
			{
				FchckbxFriends = new JCheckBox("Friends");
				FchckbxFriends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxFriends.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.FRIENDS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.FRIENDS);			
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxFriends, 6, SpringLayout.SOUTH, FchckbxInterests);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxFriends, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(FchckbxFriends);
			}
			{
				FchckbxMutualFriends = new JCheckBox("Mutual Friends");
				FchckbxMutualFriends.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxMutualFriends.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxMutualFriends, 6, SpringLayout.SOUTH,
						FchckbxFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxMutualFriends, 10, SpringLayout.WEST,
						facebookPanel);
				facebookPanel.add(FchckbxMutualFriends);
			}
			{
				FchckbxProfilePicture = new JCheckBox("Profile picture");
				FchckbxProfilePicture.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxProfilePicture.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxProfilePicture, 6, SpringLayout.SOUTH,
						FchckbxMutualFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxProfilePicture, 10, SpringLayout.WEST,
						facebookPanel);
				facebookPanel.add(FchckbxProfilePicture);
			}
			{
				FchckbxUsername = new JCheckBox("Username");
				FchckbxUsername.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(FchckbxUsername.isSelected())
							model.addSelectedFacebookDataType(FacebookDataType.USERNAME);
						else
							model.removeSelectedFacebookDataType(FacebookDataType.USERNAME);					
					}
				});
				sl_facebookPanel.putConstraint(SpringLayout.NORTH,
						FchckbxUsername, 6, SpringLayout.SOUTH,
						FchckbxProfilePicture);
				sl_facebookPanel.putConstraint(SpringLayout.WEST,
						FchckbxUsername, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(FchckbxUsername);
			}
			
			JButton btnFSelectAll = new JButton("Select all");
			sl_facebookPanel.putConstraint(SpringLayout.WEST, btnFSelectAll, 0, SpringLayout.WEST, FchckbxRelationship);
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH, btnFSelectAll, 0, SpringLayout.SOUTH, facebookPanel);
			facebookPanel.add(btnFSelectAll);
			btnFSelectAll.addActionListener(new FSelectAllAction());
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0,
					SpringLayout.WEST, getContentPane());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton btnSelectAll = new JButton("Select all");
				btnSelectAll.addActionListener(new SelectAllAction());
				buttonPane.add(btnSelectAll);
				
				JButton okButton = new JButton("Start");
				okButton.addActionListener(new StartAction());
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
			}

			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new CancelAction());
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
	private class SelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TchckbxFriends.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.FRIENDS);
			TchckbxId.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.ID);
			TchckbxPicture.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.USER_PICTURE);
			TchckbxTrends.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TRENDS);
			TchckbxTweets.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TWEETS);
			TchckbxTwitterName.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
		
			FchckbxBiography.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);
			FchckbxEducation.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.EDUCATION);
			FchckbxBirthday.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
			FchckbxEvents.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.EVENTS);
			FchckbxFriends.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.FRIENDS);
			FchckbxInterests.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.INTERESTS);
			FchckbxLastPost.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.LAST_POST);
			FchckbxMutualFriends.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
			FchckbxProfilePicture.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);
			FchckbxRelationship.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);
			FchckbxUsername.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.USERNAME);
			FchckbxWork.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.WORKS);
			
			XchckbxBirthday.setSelected(true);
			model.addSelectedXingDataType(XingDataType.BIRTHDAY);
			XchckbxCompany.setSelected(true);
			model.addSelectedXingDataType(XingDataType.COMPANY);
			XchckbxContacts.setSelected(true);
			model.addSelectedXingDataType(XingDataType.CONTACTS);
			XchckbxProfileMessage.setSelected(true);
			model.addSelectedXingDataType(XingDataType.PROFILE_MESSAGE);
			XchckbxProfileVisits.setSelected(true);
			model.addSelectedXingDataType(XingDataType.PROFILE_VISITS);
			
			LchckbxCompany.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.COMPANY);
			LchckbxContacts.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.CONTACTS);
			LchckbxEducation.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.EDUCATIONS);
			LchckbxInterests.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.INTERESTS);
			LchckbxJobTitle.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.CURRENT_JOB);
			LchckbxPhoneNumber.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.PHONES_NUMBER);
			LchckbxPositions.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.POSITIONS);
			LchckbxProfileViews.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.PROFILE_VIEWS);
			LchckbxSpecialties.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.HEADLINES);
			LchckbxStatus.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.STATUS);
			LchckbxTwitterAccount.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.TWITTER_ACCOUNT);
		
		}

	}
	
	private class TSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TchckbxFriends.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.FRIENDS);
			TchckbxId.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.ID);
			TchckbxPicture.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.USER_PICTURE);
			TchckbxTrends.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TRENDS);
			TchckbxTweets.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TWEETS);
			TchckbxTwitterName.setSelected(true);
			model.addSelectedTwitterDataType(TwitterDataType.TWITTER_NAME);
			
		}

	}
	private class FSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FchckbxBiography.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.BIOGRAPHY);
			FchckbxEducation.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.EDUCATION);
			FchckbxBirthday.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
			FchckbxEvents.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.EVENTS);
			FchckbxFriends.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.FRIENDS);
			FchckbxInterests.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.INTERESTS);
			FchckbxLastPost.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.LAST_POST);
			FchckbxMutualFriends.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.MUTUAL_FRIENDS);
			FchckbxProfilePicture.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.PROFILE_PICTURE);
			FchckbxRelationship.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.RELATIONSHIP);
			FchckbxUsername.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.USERNAME);
			FchckbxWork.setSelected(true);
			model.addSelectedFacebookDataType(FacebookDataType.WORKS);
		}

	}
	private class XSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			XchckbxBirthday.setSelected(true);
			model.addSelectedXingDataType(XingDataType.BIRTHDAY);
			XchckbxCompany.setSelected(true);
			model.addSelectedXingDataType(XingDataType.COMPANY);
			XchckbxContacts.setSelected(true);
			model.addSelectedXingDataType(XingDataType.CONTACTS);
			XchckbxProfileMessage.setSelected(true);
			model.addSelectedXingDataType(XingDataType.PROFILE_MESSAGE);
			XchckbxProfileVisits.setSelected(true);
			model.addSelectedXingDataType(XingDataType.PROFILE_VISITS);
		}

	}
	private class LSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LchckbxCompany.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.COMPANY);
			LchckbxContacts.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.CONTACTS);
			LchckbxEducation.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.EDUCATIONS);
			LchckbxInterests.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.INTERESTS);
			LchckbxJobTitle.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.CURRENT_JOB);
			LchckbxPhoneNumber.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.PHONES_NUMBER);
			LchckbxPositions.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.POSITIONS);
			LchckbxProfileViews.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.PROFILE_VIEWS);
			LchckbxSpecialties.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.HEADLINES);
			LchckbxStatus.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.STATUS);
			LchckbxTwitterAccount.setSelected(true);
			model.addSelectedLinkedInDataType(LinkedInDataType.TWITTER_ACCOUNT);
		}

	}
	


	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		//System.out.println("ADDED "+ arg.getClass().toString() + " " + arg.toString());
	}
}
