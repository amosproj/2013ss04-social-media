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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.border.TitledBorder;

public class SocialMediaScanDialog extends JDialog {
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

	/**
	 * Create the dialog.
	 */
	public SocialMediaScanDialog() {
		setTitle("AMOS Project 4 - Social Media Scan");
		setBounds(100, 100, 550, 500);
		setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		{
			facebookPanel = new JPanel();
			facebookPanel.setBorder(new TitledBorder(null, "Facebook",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, facebookPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, facebookPanel, 10,
					SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, facebookPanel, -404,
					SpringLayout.EAST, getContentPane());
			getContentPane().add(facebookPanel);
		}
		{
			twitterPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH, twitterPanel, -39,
					SpringLayout.SOUTH, getContentPane());
			twitterPanel.setBorder(new TitledBorder(null, "Twitter",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, twitterPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, twitterPanel, 10,
					SpringLayout.EAST, facebookPanel);
			springLayout.putConstraint(SpringLayout.EAST, twitterPanel, 130,
					SpringLayout.EAST, facebookPanel);
			getContentPane().add(twitterPanel);
		}
		{
			xingPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, xingPanel, 10, SpringLayout.EAST, twitterPanel);
			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, 0,
					SpringLayout.SOUTH, facebookPanel);
			springLayout.putConstraint(SpringLayout.EAST, xingPanel, -144, SpringLayout.EAST, getContentPane());
			xingPanel.setBorder(new TitledBorder(null, "Xing",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, xingPanel, 10,
					SpringLayout.NORTH, getContentPane());
			twitterPanel.setLayout(new SpringLayout());
			getContentPane().add(xingPanel);
		}

		{
			linkedInPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.WEST, linkedInPanel, 10, SpringLayout.EAST, xingPanel);
			springLayout.putConstraint(SpringLayout.SOUTH, linkedInPanel, 0,
					SpringLayout.SOUTH, facebookPanel);
			springLayout.putConstraint(SpringLayout.EAST, linkedInPanel, -14, SpringLayout.EAST, getContentPane());
			linkedInPanel.setBorder(new TitledBorder(null, "LinkedIn",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			xingPanel.setLayout(new SpringLayout());
			springLayout.putConstraint(SpringLayout.NORTH, linkedInPanel, 10,
					SpringLayout.NORTH, getContentPane());
			getContentPane().add(linkedInPanel);
			linkedInPanel.setLayout(new SpringLayout());
		}
		{
			buttonPane = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH, buttonPane, 0, SpringLayout.SOUTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 0,
					SpringLayout.EAST, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6,
					SpringLayout.NORTH, buttonPane);
			SpringLayout sl_facebookPanel = new SpringLayout();
			facebookPanel.setLayout(sl_facebookPanel);
			
			JCheckBox chckbxRelationship = new JCheckBox("Relationship");
			facebookPanel.add(chckbxRelationship);
			{
				chckbxBirthday = new JCheckBox("Birthday");
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxRelationship, 0, SpringLayout.WEST, chckbxBirthday);
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxBirthday, 39, SpringLayout.NORTH, facebookPanel);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxBirthday, 10, SpringLayout.WEST, facebookPanel);
				facebookPanel.add(chckbxBirthday);
			}
			
			JCheckBox chckbxNewCheckBox = new JCheckBox("Last post");
			sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxRelationship, 6, SpringLayout.SOUTH, chckbxNewCheckBox);
			sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 10, SpringLayout.WEST, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 6, SpringLayout.SOUTH, chckbxBirthday);
			facebookPanel.add(chckbxNewCheckBox);
			
			JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Biography");
			sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_1, 10, SpringLayout.WEST, facebookPanel);
			sl_facebookPanel.putConstraint(SpringLayout.SOUTH, chckbxNewCheckBox_1, -6, SpringLayout.NORTH, chckbxBirthday);
			facebookPanel.add(chckbxNewCheckBox_1);
			{
				chckbxEducation = new JCheckBox("Education");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxEducation, 6, SpringLayout.SOUTH, chckbxRelationship);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxEducation, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxEducation);
			}
			{
				chckbxNewCheckBox_2 = new JCheckBox("Work");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_2, 6, SpringLayout.SOUTH, chckbxEducation);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_2, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxNewCheckBox_2);
			}
			{
				chckbxEvents = new JCheckBox("Events");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxEvents, 6, SpringLayout.SOUTH, chckbxNewCheckBox_2);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxEvents, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxEvents);
			}
			{
				chckbxInterests = new JCheckBox("Interests");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxInterests, 6, SpringLayout.SOUTH, chckbxEvents);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxInterests, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxInterests);
			}
			{
				chckbxFriends = new JCheckBox("Friends");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxFriends, 6, SpringLayout.SOUTH, chckbxInterests);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxFriends, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxFriends);
			}
			{
				chckbxMutualFriends = new JCheckBox("Mutual Friends");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxMutualFriends, 6, SpringLayout.SOUTH, chckbxFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxMutualFriends, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxMutualFriends);
			}
			{
				chckbxProfilePicture = new JCheckBox("Profile picture");
				sl_facebookPanel.putConstraint(SpringLayout.NORTH, chckbxProfilePicture, 6, SpringLayout.SOUTH, chckbxMutualFriends);
				sl_facebookPanel.putConstraint(SpringLayout.WEST, chckbxProfilePicture, 0, SpringLayout.WEST, chckbxRelationship);
				facebookPanel.add(chckbxProfilePicture);
			}
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0,
					SpringLayout.WEST, getContentPane());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
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
}
