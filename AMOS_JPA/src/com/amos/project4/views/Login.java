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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.amos.project4.controllers.UserContollerInterface;
import com.amos.project4.controllers.UserController;
import com.amos.project4.models.User;
import com.amos.project4.utils.AMOSUtils;

public class Login extends JDialog implements AbstractControlledView {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField usernametextField;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JLabel lblLoginMessage;
	private UserController user_controller;
	private UserViewModel u_model;
	

	/**
	 * Create the dialog.
	 */
	public Login() {
		init();
		user_controller = new UserController();
		u_model = new UserViewModel();
		user_controller.addModel(u_model);
		user_controller.addView(this);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	

	public void init() {
		setTitle("AMOS Project 4");
		setBounds(100, 100, 450, 170);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		JLabel lblUsername = new JLabel("Username:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblUsername, 10,
				SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblUsername, 10,
				SpringLayout.WEST, contentPanel);
		contentPanel.add(lblUsername);

		usernametextField = new JTextField();
		usernametextField.setToolTipText("Username: test");
		sl_contentPanel.putConstraint(SpringLayout.WEST, usernametextField, 20,
				SpringLayout.EAST, lblUsername);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, usernametextField, 0,
				SpringLayout.SOUTH, lblUsername);
		contentPanel.add(usernametextField);
		usernametextField.setColumns(25);
		{
			lblPassword = new JLabel("Password:");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPassword, 10,
					SpringLayout.SOUTH, lblUsername);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPassword, 0,
					SpringLayout.WEST, lblUsername);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblPassword, 0,
					SpringLayout.EAST, lblUsername);
			contentPanel.add(lblPassword);
		}

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Password: test");
		sl_contentPanel.putConstraint(SpringLayout.WEST, passwordField, 20,
				SpringLayout.EAST, lblPassword);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, passwordField, 0,
				SpringLayout.SOUTH, lblPassword);
		sl_contentPanel.putConstraint(SpringLayout.EAST, passwordField, 0,
				SpringLayout.EAST, usernametextField);
		contentPanel.add(passwordField);

		lblLoginMessage = new JLabel("Please enter the login data ...");
		lblLoginMessage.setVerticalAlignment(SwingConstants.TOP);
		lblLoginMessage.setHorizontalAlignment(SwingConstants.LEFT);
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblLoginMessage, 10,
				SpringLayout.SOUTH, lblPassword);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblLoginMessage, 10,
				SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, lblLoginMessage, -10,
				SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblLoginMessage, -10,
				SpringLayout.EAST, contentPanel);
		contentPanel.add(lblLoginMessage);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Login");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new LoginAction());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Exit");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new CancelLoginAction());
				buttonPane.add(cancelButton);
			}
		}
	}

	private void setloginMessage(String msg, int severity) {
		switch (severity) {
		case 0:
			this.getLblLoginMessage().setText(msg);
			this.getLblLoginMessage().setForeground(Color.BLACK);
			break;
		case 1:
			this.getLblLoginMessage().setText(msg);
			this.getLblLoginMessage().setForeground(Color.RED);
			break;
		default:
			this.getLblLoginMessage().setText(msg);
			this.getLblLoginMessage().setForeground(Color.BLACK);
			break;
		}
	}

	public JLabel getLblLoginMessage() {
		return lblLoginMessage;
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (o.getClass().equals(UserViewModel.class) && ((String) arg).equalsIgnoreCase("Login")) {
			try {
				User _user = ((UserContollerInterface) user_controller)
						.getCurrent_user();
				if (_user == null) {
					setloginMessage("Username doesn't exist!", 1);
					return;
				}
				char[] input = passwordField.getPassword();
				String hasched_pass = AMOSUtils.makeMD5(new String(input));
				if (!hasched_pass.equalsIgnoreCase(_user.getUserpass())) {
					setloginMessage("Wrong password!", 1);
					return;
				}
				//this.user_controller.removeView(this);
				// Update UserViewModel
				u_model.updateModel(_user);
				AMOSMainUI window = AMOSMainUI.getInstance((UserController)user_controller,u_model);
				window.getMainFrame().setVisible(true);
				setVisible(false);
			} catch (Exception e) {
				e.printStackTrace();
				setloginMessage("Something went wrong. Please try again!", 1);
				return;
			}
		}
	}
	
	
	
	private class LoginAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(u_model != null){
				try {
					u_model.setUserData(usernametextField.getText(), AMOSUtils.makeMD5(new String(passwordField.getPassword())));
				} catch (NoSuchAlgorithmException e1) {
					setloginMessage("Error while hashing your pwd. Please try again!", 1);
				}
			}
		}		
	}
	
	private class CancelLoginAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			System.exit(0);
		}
		
	}
}
