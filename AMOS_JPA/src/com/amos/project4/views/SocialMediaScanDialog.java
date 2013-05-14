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
import javax.swing.border.TitledBorder;

public class SocialMediaScanDialog extends JDialog {
	private JPanel facebookPanel;
	private JPanel buttonPane;
	private JPanel twitterPanel;
	private JPanel xingPanel;
	private JPanel linkedInPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SocialMediaScanDialog dialog = new SocialMediaScanDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SocialMediaScanDialog() {
		setTitle("AMOS Project 4 - Social Media Scan");
		setBounds(100, 100, 550, 300);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		{
			facebookPanel = new JPanel();
			facebookPanel.setBorder(new TitledBorder(null, "Facebook", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, facebookPanel, 10, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, facebookPanel, 10, SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, facebookPanel, -404, SpringLayout.EAST, getContentPane());
			getContentPane().add(facebookPanel);
		}
		{
			twitterPanel = new JPanel();
			twitterPanel.setBorder(new TitledBorder(null, "Twitter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.NORTH, twitterPanel, 10, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, twitterPanel, 10, SpringLayout.EAST, facebookPanel);
			springLayout.putConstraint(SpringLayout.SOUTH, twitterPanel, 223, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, twitterPanel, 130, SpringLayout.EAST, facebookPanel);
			getContentPane().add(twitterPanel);
		}
		{
			xingPanel = new JPanel();
			xingPanel.setBorder(new TitledBorder(null, "Xing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.WEST, xingPanel, 10, SpringLayout.EAST, twitterPanel);
			springLayout.putConstraint(SpringLayout.NORTH, xingPanel, 10, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, xingPanel, 223, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, xingPanel, 130, SpringLayout.EAST, twitterPanel);
			getContentPane().add(xingPanel);
		}
		
		{
			linkedInPanel = new JPanel();
			springLayout.putConstraint(SpringLayout.EAST, linkedInPanel, 130, SpringLayout.EAST, xingPanel);
			linkedInPanel.setBorder(new TitledBorder(null, "LinkedIn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			springLayout.putConstraint(SpringLayout.WEST, linkedInPanel, 10, SpringLayout.EAST, xingPanel);
			springLayout.putConstraint(SpringLayout.NORTH, linkedInPanel, 10, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, linkedInPanel, 223, SpringLayout.NORTH, getContentPane());
			getContentPane().add(linkedInPanel);
		}
		{
			buttonPane = new JPanel();
			springLayout.putConstraint(SpringLayout.SOUTH, facebookPanel, -6, SpringLayout.NORTH, buttonPane);
			springLayout.putConstraint(SpringLayout.NORTH, buttonPane, 229, SpringLayout.NORTH, getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, buttonPane, 0, SpringLayout.WEST, getContentPane());
			springLayout.putConstraint(SpringLayout.EAST, buttonPane, 444, SpringLayout.WEST, getContentPane());
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
