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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import com.amos.project4.controllers.AbstractController;
import com.amos.project4.controllers.UserContollerInterface;
import com.amos.project4.controllers.UserController;
import com.amos.project4.models.User;
import com.amos.project4.utils.AMOSUtils;

public class Login extends JDialog implements AbstractControlledView {

	private final JPanel contentPanel = new JPanel();
	private JTextField usernametextField;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JLabel lblLoginMessage;
	private AbstractController user_controller;
	private UserViewModel u_model;
	
	JDialog me;

	/**
	 * Create the dialog.
	 */
	public Login() {
		init();
		user_controller = new UserController();
		u_model = new UserViewModel();
		user_controller.addModel(u_model);
		me = this;
		user_controller.addView(this);
	}

	public void init() {
		setTitle("AMOS Project 4");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);

		JLabel lblUsername = new JLabel("Username :");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblUsername, 50,
				SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblUsername, 10,
				SpringLayout.WEST, contentPanel);
		contentPanel.add(lblUsername);

		usernametextField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.WEST, usernametextField, 20,
				SpringLayout.EAST, lblUsername);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, usernametextField, 0,
				SpringLayout.SOUTH, lblUsername);
		contentPanel.add(usernametextField);
		usernametextField.setColumns(10);
		{
			lblPassword = new JLabel("Password :");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, lblPassword, 10,
					SpringLayout.SOUTH, lblUsername);
			sl_contentPanel.putConstraint(SpringLayout.WEST, lblPassword, 0,
					SpringLayout.WEST, lblUsername);
			sl_contentPanel.putConstraint(SpringLayout.EAST, lblPassword, 0,
					SpringLayout.EAST, lblUsername);
			contentPanel.add(lblPassword);
		}

		passwordField = new JPasswordField();
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
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new LoginAction());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
		if (arg.getClass().equals(UserViewModel.class)) {
			try {
				User _user = ((UserContollerInterface) user_controller)
						.getCurrent_user();
				if (_user == null) {
					setloginMessage("Username doesn't exist !", 1);
					return;
				}
				char[] input = passwordField.getPassword();
				String hasched_pass = AMOSUtils.makeMD5(new String(input));
				if (!hasched_pass.equalsIgnoreCase(_user.getUserpass())) {
					setloginMessage("Wrong password !", 1);
					return;
				}
				AMOSMainUI window = new AMOSMainUI();
				window.getMainFrame().setVisible(true);
				this.dispose();
			} catch (Exception e) {
				setloginMessage("Something went wrong. Please try again !", 1);
				return;
			}

		}

	}
	
	
	
	private class LoginAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(u_model != null){
				u_model.setUserData(usernametextField.getText(), new String(passwordField.getPassword()));
			}
		}		
	}
	
	private class CancelLoginAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			me.dispose();
			me = null;
		}
		
	}
}
