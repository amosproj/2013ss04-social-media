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

public class NotificationSettingsDialog extends JDialog implements
		AbstractControlledView {

	private static final long serialVersionUID = 1L;
	private JPanel facebookPanel;
	private JPanel buttonPane;
	private JPanel twitterPanel;
	private JPanel xingPanel;
	private JPanel linkedInPanel;
	private JCheckBox FchckbxBirthday;
	private JCheckBox FchckbxWork;
	private JCheckBox TchckbxTweets;
	private JCheckBox XchckbxBirthday;
	private JCheckBox XchckbxCompany;
	private SpringLayout sl_xingPanel;
	private JCheckBox LchckbxJobTitle;
	private JCheckBox LchckbxStatus;
	private SpringLayout sl_linkedInPanel;
	private JCheckBox btnLSelectAll;
	private JCheckBox btnFSelectAll;
	private JCheckBox btnXSelectAll;
	private JCheckBox btnTSelectAll;
	private JCheckBox btnSelectAll;

	private SocialMediaScanModel model;
	private SocialMediaScanController controller;

//	private User user;
//	private List<Client> clients;

	private JCheckBox FchckbxLastPost;

	public NotificationSettingsDialog(User user, List<Client> clients, JFrame frame) {
		this(frame);
		this.model = new SocialMediaScanModel();
		this.controller = new SocialMediaScanController();
		this.controller.addModel(model);
		this.controller.addView(this);

	}

	/**
	 * Create the dialog.
	 */
	private NotificationSettingsDialog(JFrame frame) {
		super(frame);
		setTitle("AMOS Project 4 - Notification Settings");

		setSize(658, 251);
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
			springLayout.putConstraint(SpringLayout.WEST, twitterPanel, 5,
					SpringLayout.EAST, facebookPanel);
			springLayout.putConstraint(SpringLayout.EAST, twitterPanel, -327,
					SpringLayout.EAST, getContentPane());
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
			springLayout.putConstraint(SpringLayout.WEST, xingPanel, 5,
					SpringLayout.EAST, twitterPanel);
			springLayout.putConstraint(SpringLayout.EAST, xingPanel, -172,
					SpringLayout.EAST, getContentPane());
			xingPanel.setBorder(new TitledBorder(null, "Xing",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			SpringLayout sl_twitterPanel = new SpringLayout();
			twitterPanel.setLayout(sl_twitterPanel);
			{
				TchckbxTweets = new JCheckBox("Tweets");
				sl_twitterPanel.putConstraint(SpringLayout.NORTH, TchckbxTweets, 10, SpringLayout.NORTH, twitterPanel);
				TchckbxTweets.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// if(TchckbxTweets.isSelected())
						// model.addSelectedTwitterDataType(TwitterDataType.TWEETS);
						// else
						// model.removeSelectedTwitterDataType(TwitterDataType.TWEETS);
					}
				});
				twitterPanel.add(TchckbxTweets);
			}

			btnTSelectAll = new JCheckBox("Select all");
			sl_twitterPanel.putConstraint(SpringLayout.WEST, TchckbxTweets, 0, SpringLayout.WEST, btnTSelectAll);
			sl_twitterPanel.putConstraint(SpringLayout.WEST, btnTSelectAll, 10, SpringLayout.WEST, twitterPanel);
			sl_twitterPanel.putConstraint(SpringLayout.SOUTH, btnTSelectAll, 0,
					SpringLayout.SOUTH, twitterPanel);
			twitterPanel.add(btnTSelectAll);
			btnTSelectAll.addActionListener(new TSelectAllAction());
			getContentPane().add(xingPanel);
		}

		{
			linkedInPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, linkedInPanel, 5,
					SpringLayout.EAST, xingPanel);
			springLayout.putConstraint(SpringLayout.EAST, linkedInPanel, -17,
					SpringLayout.EAST, getContentPane());
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
				LchckbxJobTitle = new JCheckBox("Headline");
				sl_linkedInPanel.putConstraint(SpringLayout.NORTH, LchckbxJobTitle, 10, SpringLayout.NORTH, linkedInPanel);
//				sl_linkedInPanel.putConstraint(SpringLayout.NORTH, LchckbxJobTitle, 6, SpringLayout.SOUTH, LchckbxSpecialties);
//				sl_linkedInPanel.putConstraint(SpringLayout.WEST, LchckbxJobTitle, 0, SpringLayout.WEST, LchckbxSpecialties);
				LchckbxJobTitle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// if(LchckbxJobTitle.isSelected())
						// model.addSelectedLinkedInDataType(LinkedInDataType.HEADLINES);
						// else
						// model.removeSelectedLinkedInDataType(LinkedInDataType.HEADLINES);
					}
				});
				linkedInPanel.add(LchckbxJobTitle);
			}

			btnLSelectAll = new JCheckBox("Select all");
			sl_linkedInPanel.putConstraint(SpringLayout.EAST, LchckbxJobTitle, 0, SpringLayout.EAST, btnLSelectAll);
			sl_linkedInPanel.putConstraint(SpringLayout.WEST, btnLSelectAll, 10, SpringLayout.WEST, linkedInPanel);
			sl_linkedInPanel.putConstraint(SpringLayout.SOUTH, btnLSelectAll,
					0, SpringLayout.SOUTH, linkedInPanel);
			linkedInPanel.add(btnLSelectAll);
			btnLSelectAll.addActionListener(new LSelectAllAction());

			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, -6,
					SpringLayout.NORTH, buttonPane);
			{
				XchckbxCompany = new JCheckBox("Company");
				XchckbxCompany.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// if(XchckbxCompany.isSelected())
						// model.addSelectedXingDataType(XingDataType.COMPANY);
						// else
						// model.removeSelectedXingDataType(XingDataType.COMPANY);
					}
				});
				xingPanel.add(XchckbxCompany);
			}

			btnXSelectAll = new JCheckBox("Select all");
			sl_xingPanel.putConstraint(SpringLayout.WEST, XchckbxCompany, 0, SpringLayout.WEST, btnXSelectAll);
			sl_xingPanel.putConstraint(SpringLayout.WEST, btnXSelectAll, 10, SpringLayout.WEST, xingPanel);
			sl_xingPanel.putConstraint(SpringLayout.SOUTH, btnXSelectAll, 0,
					SpringLayout.SOUTH, xingPanel);
			xingPanel.add(btnXSelectAll);
			{
				XchckbxBirthday = new JCheckBox("Birthday");
				sl_xingPanel.putConstraint(SpringLayout.NORTH, XchckbxCompany, 6, SpringLayout.SOUTH, XchckbxBirthday);
				sl_xingPanel.putConstraint(SpringLayout.WEST, XchckbxBirthday, 10, SpringLayout.WEST, xingPanel);
				sl_xingPanel.putConstraint(SpringLayout.NORTH, XchckbxBirthday, 10, SpringLayout.NORTH, xingPanel);
				xingPanel.add(XchckbxBirthday);
			}
			btnXSelectAll.addActionListener(new XSelectAllAction());

			springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, 0,
					SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 0,
					SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6,
					SpringLayout.NORTH, buttonPane);
			SpringLayout sl_facebookPanel = new SpringLayout();
			facebookPanel.setLayout(sl_facebookPanel);
			{
				FchckbxBirthday = new JCheckBox("Birthday");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, FchckbxBirthday, 10, SpringLayout.NORTH, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, FchckbxBirthday, 10, SpringLayout.WEST, facebookPanel);
				FchckbxBirthday.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// if(FchckbxBirthday.isSelected())
						// model.addSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
						// else
						// model.removeSelectedFacebookDataType(FacebookDataType.BIRTHDAY);
					}
				});
				facebookPanel.add(FchckbxBirthday);
			}

			FchckbxLastPost = new JCheckBox("Last post");
			sl_facebookPanel.putConstraint(SpringLayout.NORTH, FchckbxLastPost, 6, SpringLayout.SOUTH, FchckbxBirthday);
			sl_facebookPanel.putConstraint(SpringLayout.WEST, FchckbxLastPost, 0, SpringLayout.WEST, FchckbxBirthday);
			FchckbxLastPost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// if(FchckbxLastPost.isSelected())
					// model.addSelectedFacebookDataType(FacebookDataType.LAST_POST);
					// else
					// model.removeSelectedFacebookDataType(FacebookDataType.LAST_POST);
				}
			});
			facebookPanel.add(FchckbxLastPost);
			{
				FchckbxWork = new JCheckBox("Work");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, FchckbxWork, 6, SpringLayout.SOUTH, FchckbxLastPost);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, FchckbxWork, 0, SpringLayout.WEST, FchckbxBirthday);
				FchckbxWork.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// if(FchckbxWork.isSelected())
						// model.addSelectedFacebookDataType(FacebookDataType.WORKS);
						// else
						// model.removeSelectedFacebookDataType(FacebookDataType.WORKS);
					}
				});
				facebookPanel.add(FchckbxWork);
			}

			btnFSelectAll = new JCheckBox("Select all");
			sl_facebookPanel.putConstraint(SpringLayout.WEST, btnFSelectAll, 10, SpringLayout.WEST, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH, btnFSelectAll,
					0, SpringLayout.SOUTH, facebookPanel);
			facebookPanel.add(btnFSelectAll);
			btnFSelectAll.addActionListener(new FSelectAllAction());

			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0,
					SpringLayout.WEST, getContentPane());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				btnSelectAll = new JCheckBox("Select all");
				buttonPane.add(btnSelectAll);
				btnSelectAll.addActionListener(new SelectAllAction());

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
			// SocialMediaProgressBar bar = new
			// SocialMediaProgressBar(controller, model, clients, user,frame);
			// bar.start();
			// //dispose();
		}

	}

	private class SelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!btnSelectAll.isSelected()) {
				//LchckbxCompany.setSelected(false);
				LchckbxJobTitle.setSelected(false);
				//LchckbxPositions.setSelected(false);
				LchckbxStatus.setSelected(false);

				TchckbxTweets.setSelected(false);

				FchckbxBirthday.setSelected(false);
				FchckbxLastPost.setSelected(false);
				FchckbxWork.setSelected(false);

				XchckbxBirthday.setSelected(false);
				XchckbxCompany.setSelected(false);
				
				btnFSelectAll.setSelected(false);
				btnXSelectAll.setSelected(false);
				btnLSelectAll.setSelected(false);
				btnTSelectAll.setSelected(false);
			} else {
				TchckbxTweets.setSelected(true);

				FchckbxBirthday.setSelected(true);
				FchckbxLastPost.setSelected(true);
				FchckbxWork.setSelected(true);

				XchckbxBirthday.setSelected(true);
				XchckbxCompany.setSelected(true);

				//LchckbxCompany.setSelected(true);
				LchckbxJobTitle.setSelected(true);
				//LchckbxPositions.setSelected(true);
				LchckbxStatus.setSelected(true);

				btnFSelectAll.setSelected(true);
				btnXSelectAll.setSelected(true);
				btnLSelectAll.setSelected(true);
				btnTSelectAll.setSelected(true);
				
			}
		}
	}

	private class TSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!btnTSelectAll.isSelected()) {
				TchckbxTweets.setSelected(false);
			} else {
				TchckbxTweets.setSelected(true);

			}
		}
	}

	private class FSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!btnFSelectAll.isSelected()) {
				FchckbxBirthday.setSelected(false);
				FchckbxLastPost.setSelected(false);
				FchckbxWork.setSelected(false);
			} else {
				FchckbxBirthday.setSelected(true);
				FchckbxLastPost.setSelected(true);
				FchckbxWork.setSelected(true);
			}
		}
	}

	private class XSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!btnXSelectAll.isSelected()) {
				XchckbxBirthday.setSelected(false);
				XchckbxCompany.setSelected(false);
			} else {
				XchckbxBirthday.setSelected(true);
				XchckbxCompany.setSelected(true);
			}
		}
	}

	private class LSelectAllAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!btnLSelectAll.isSelected()) {
				//LchckbxCompany.setSelected(false);
				LchckbxJobTitle.setSelected(false);
				//LchckbxPositions.setSelected(false);
				LchckbxStatus.setSelected(false);
			} else {
				//LchckbxCompany.setSelected(true);
				LchckbxJobTitle.setSelected(true);
				//LchckbxPositions.setSelected(true);
				LchckbxStatus.setSelected(true);
			}
		}
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		// System.out.println("ADDED "+ arg.getClass().toString() + " " +
		// arg.toString());
	}
}

