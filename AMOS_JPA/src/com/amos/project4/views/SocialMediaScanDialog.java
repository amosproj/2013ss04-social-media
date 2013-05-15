/*
 *
 setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

public class SocialMediaScanDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel facebookPanel;
	private JPanel buttonPane;
	private JPanel twitterPanel;
	private JPanel xingPanel;
	private JPanel linkedInPanel;

	/**
	 * Create the dialog.
	 */
	public SocialMediaScanDialog() {
		setTitle("AMOS Project 4 - Social Media Scan");
		setBounds(100, 100, 550, 300);
		SpringLayout springLayout = new SpringLayout();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
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
			twitterPanel.setBorder(new TitledBorder(null, "Twitter",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, twitterPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, twitterPanel, 10,
					SpringLayout.EAST, facebookPanel);
			springLayout.putConstraint(SpringLayout.SOUTH, twitterPanel, 223,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, twitterPanel, 130,
					SpringLayout.EAST, facebookPanel);
			getContentPane().add(twitterPanel);
		}
		{
			xingPanel = new JPanel();
			xingPanel.setBorder(new TitledBorder(null, "Xing",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.WEST, xingPanel, 10,
					SpringLayout.EAST, twitterPanel);
			springLayout.putConstraint(SpringLayout.NORTH, xingPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, 223,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, xingPanel, 130,
					SpringLayout.EAST, twitterPanel);
			getContentPane().add(xingPanel);
		}

		{
			linkedInPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.EAST, linkedInPanel, 130,
					SpringLayout.EAST, xingPanel);
			linkedInPanel.setBorder(new TitledBorder(null, "LinkedIn",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.WEST, linkedInPanel, 10,
					SpringLayout.EAST, xingPanel);
			springLayout.putConstraint(SpringLayout.NORTH, linkedInPanel, 10,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, linkedInPanel, 223,
					SpringLayout.NORTH, getContentPane());
			getContentPane().add(linkedInPanel);
		}
		{
			buttonPane = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6,
					SpringLayout.NORTH, buttonPane);
			springLayout.putConstraint(SpringLayout.NORTH, buttonPane, 229,
					SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0,
					SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 444,
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
