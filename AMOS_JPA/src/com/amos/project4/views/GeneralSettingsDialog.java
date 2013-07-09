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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.UserController;
import com.amos.project4.models.SocialMediaType;
import com.amos.project4.models.User;

/**
 * 
 * @author Jupiter BAKAKEU
 *
 */
public class GeneralSettingsDialog extends JDialog implements AbstractControlledView{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField f_tokenTextField;
	private JTextField f_token_secretTextField;
	private JTextField t_accestokenTextField;
	private JTextField t_accessTokenSecretField;
	private JLabel t_lbl_access_token;
	private JLabel t_lbl_accesstoken_secret;
	private JTextField x_access_tokenTextField;
	private JLabel x_lbl_access_token;
	private JTextField x_access_token_secretTextField;
	private JLabel x_lbl_access_token_secret;
	private JTextField l_access_tokenTextField;
	private JLabel l_lbl_access_token;
	private JTextField l_access_token_TextField;
	private JLabel l_lbl_access_token_secret;
	//private JButton btnConnect;
	
	private UserViewModel viewModel;
	private UserController user_controller;
	private JLabel t_lblStatus;
	private JButton t_btnGenerateToken;
	private JButton f_btnGenerateToken;
	private JLabel f_lblStatus;
	private JButton x_btnGenerateToken;
	private JLabel x_lblStatus;
	private JButton l_btnGenerateToken;
	private JLabel l_lblStatus;
	
	private JFrame frame;
	
	public GeneralSettingsDialog(UserController user_controller,UserViewModel viewModel, JFrame frame) {
		super(frame);
		this.frame = frame;		
		this.user_controller = user_controller;
		this.user_controller.InitConnector();
		this.user_controller.addView(this);
		this.viewModel = viewModel;
		init();
		this.updateView(this.user_controller.getCurrent_user());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);		
	}
	
	private void init(){
		setTitle("AMOS Project 4 General settings");
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 2, 10, 10));
		{
			JPanel FacebookPanel = new JPanel();
			FacebookPanel.setBorder(new TitledBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Facebook", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(FacebookPanel);
			SpringLayout sl_FacebookPanel = new SpringLayout();
			FacebookPanel.setLayout(sl_FacebookPanel);
			
			JLabel f_lbl_access_token = new JLabel("Access Token :");
			sl_FacebookPanel.putConstraint(SpringLayout.NORTH, f_lbl_access_token, 10, SpringLayout.NORTH, FacebookPanel);
			sl_FacebookPanel.putConstraint(SpringLayout.WEST, f_lbl_access_token, 10, SpringLayout.WEST, FacebookPanel);
			FacebookPanel.add(f_lbl_access_token);
			
			f_tokenTextField = new JTextField();
			f_tokenTextField.setEditable(false);
			sl_FacebookPanel.putConstraint(SpringLayout.WEST, f_tokenTextField, 20, SpringLayout.EAST, f_lbl_access_token);
			sl_FacebookPanel.putConstraint(SpringLayout.EAST, f_tokenTextField, -6, SpringLayout.EAST, FacebookPanel);
			sl_FacebookPanel.putConstraint(SpringLayout.SOUTH, f_tokenTextField, 0, SpringLayout.SOUTH, f_lbl_access_token);
			FacebookPanel.add(f_tokenTextField);
			
			JLabel f_lbl_access_token_secret = new JLabel("A. T. secret :");
			sl_FacebookPanel.putConstraint(SpringLayout.NORTH, f_lbl_access_token_secret, 12, SpringLayout.SOUTH, f_lbl_access_token);
			sl_FacebookPanel.putConstraint(SpringLayout.WEST, f_lbl_access_token_secret, 0, SpringLayout.WEST, f_lbl_access_token);
			sl_FacebookPanel.putConstraint(SpringLayout.EAST, f_lbl_access_token_secret, 0, SpringLayout.EAST, f_lbl_access_token);
			FacebookPanel.add(f_lbl_access_token_secret);
			
			f_token_secretTextField = new JTextField();
			f_token_secretTextField.setEditable(false);
			sl_FacebookPanel.putConstraint(SpringLayout.WEST, f_token_secretTextField, 0, SpringLayout.WEST, f_tokenTextField);
			sl_FacebookPanel.putConstraint(SpringLayout.SOUTH, f_token_secretTextField, 0, SpringLayout.SOUTH, f_lbl_access_token_secret);
			sl_FacebookPanel.putConstraint(SpringLayout.EAST, f_token_secretTextField, 0, SpringLayout.EAST, f_tokenTextField);
			FacebookPanel.add(f_token_secretTextField);
			
			f_btnGenerateToken = new JButton("Generate");
			sl_FacebookPanel.putConstraint(SpringLayout.SOUTH, f_btnGenerateToken, -6, SpringLayout.SOUTH, FacebookPanel);
			sl_FacebookPanel.putConstraint(SpringLayout.EAST, f_btnGenerateToken, -6, SpringLayout.EAST, FacebookPanel);
			f_btnGenerateToken.addActionListener(new F_GenereateAction());
			FacebookPanel.add(f_btnGenerateToken);
			
			f_lblStatus = new JLabel("Status:");
			sl_FacebookPanel.putConstraint(SpringLayout.NORTH, f_lblStatus, 6, SpringLayout.SOUTH, f_token_secretTextField);
			sl_FacebookPanel.putConstraint(SpringLayout.WEST, f_lblStatus, 0, SpringLayout.WEST, f_lbl_access_token);
			sl_FacebookPanel.putConstraint(SpringLayout.EAST, f_lblStatus, 0, SpringLayout.EAST, f_btnGenerateToken);
			FacebookPanel.add(f_lblStatus);
		}
		{
			JPanel TwitterPanel = new JPanel();
			TwitterPanel.setBorder(new TitledBorder(null, "Twitter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(TwitterPanel);
			SpringLayout sl_TwitterPanel = new SpringLayout();
			TwitterPanel.setLayout(sl_TwitterPanel);
			{
				t_lbl_access_token = new JLabel("Acces token :");
				sl_TwitterPanel.putConstraint(SpringLayout.NORTH, t_lbl_access_token, 10, SpringLayout.NORTH, TwitterPanel);
				sl_TwitterPanel.putConstraint(SpringLayout.WEST, t_lbl_access_token, 10, SpringLayout.WEST, TwitterPanel);
				TwitterPanel.add(t_lbl_access_token);
			}
			{
				t_accestokenTextField = new JTextField();
				t_accestokenTextField.setEditable(false);
				sl_TwitterPanel.putConstraint(SpringLayout.WEST, t_accestokenTextField, 20, SpringLayout.EAST, t_lbl_access_token);
				sl_TwitterPanel.putConstraint(SpringLayout.SOUTH, t_accestokenTextField, 0, SpringLayout.SOUTH, t_lbl_access_token);
				sl_TwitterPanel.putConstraint(SpringLayout.EAST, t_accestokenTextField, -6, SpringLayout.EAST, TwitterPanel);
				TwitterPanel.add(t_accestokenTextField);
			}
			{
				t_lbl_accesstoken_secret = new JLabel("A. T. secret :");
				sl_TwitterPanel.putConstraint(SpringLayout.NORTH, t_lbl_accesstoken_secret, 12, SpringLayout.SOUTH, t_lbl_access_token);
				sl_TwitterPanel.putConstraint(SpringLayout.WEST, t_lbl_accesstoken_secret, 0, SpringLayout.WEST, t_lbl_access_token);
				TwitterPanel.add(t_lbl_accesstoken_secret);
			}
			{
				t_accessTokenSecretField = new JTextField();
				t_accessTokenSecretField.setEditable(false);
				sl_TwitterPanel.putConstraint(SpringLayout.WEST, t_accessTokenSecretField, 0, SpringLayout.WEST, t_accestokenTextField);
				sl_TwitterPanel.putConstraint(SpringLayout.SOUTH, t_accessTokenSecretField, 0, SpringLayout.SOUTH, t_lbl_accesstoken_secret);
				sl_TwitterPanel.putConstraint(SpringLayout.EAST, t_accessTokenSecretField, 0, SpringLayout.EAST, t_accestokenTextField);
				TwitterPanel.add(t_accessTokenSecretField);
			}		
			
			t_btnGenerateToken = new JButton("Generate");
			sl_TwitterPanel.putConstraint(SpringLayout.SOUTH, t_btnGenerateToken, -6, SpringLayout.SOUTH, TwitterPanel);
			sl_TwitterPanel.putConstraint(SpringLayout.EAST, t_btnGenerateToken, -6, SpringLayout.EAST, TwitterPanel);
			t_btnGenerateToken.addActionListener(new T_GenereateAction());
			TwitterPanel.add(t_btnGenerateToken);
			
			t_lblStatus = new JLabel("Status:");
			sl_TwitterPanel.putConstraint(SpringLayout.NORTH, t_lblStatus, 6, SpringLayout.SOUTH, t_lbl_accesstoken_secret);
			sl_TwitterPanel.putConstraint(SpringLayout.WEST, t_lblStatus, 0, SpringLayout.WEST, t_lbl_access_token);
			sl_TwitterPanel.putConstraint(SpringLayout.EAST, t_lblStatus, 0, SpringLayout.EAST, t_btnGenerateToken);
			TwitterPanel.add(t_lblStatus);
		}
		{
			JPanel XingPanel = new JPanel();
			XingPanel.setBorder(new TitledBorder(null, "Xing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(XingPanel);
			SpringLayout sl_XingPanel = new SpringLayout();
			XingPanel.setLayout(sl_XingPanel);
			{
				x_lbl_access_token = new JLabel("Access Token :");
				sl_XingPanel.putConstraint(SpringLayout.NORTH, x_lbl_access_token, 10, SpringLayout.NORTH, XingPanel);
				sl_XingPanel.putConstraint(SpringLayout.WEST, x_lbl_access_token, 10, SpringLayout.WEST, XingPanel);
				XingPanel.add(x_lbl_access_token);
			}
			{
				x_access_tokenTextField = new JTextField();
				x_access_tokenTextField.setEditable(false);
				sl_XingPanel.putConstraint(SpringLayout.WEST, x_access_tokenTextField, 20, SpringLayout.EAST, x_lbl_access_token);
				sl_XingPanel.putConstraint(SpringLayout.SOUTH, x_access_tokenTextField, 0, SpringLayout.SOUTH, x_lbl_access_token);
				sl_XingPanel.putConstraint(SpringLayout.EAST, x_access_tokenTextField, -6, SpringLayout.EAST,XingPanel);
				XingPanel.add(x_access_tokenTextField);
			}
			{
				x_lbl_access_token_secret = new JLabel("A. T. secret :");
				sl_XingPanel.putConstraint(SpringLayout.NORTH, x_lbl_access_token_secret, 12, SpringLayout.SOUTH, x_lbl_access_token);
				sl_XingPanel.putConstraint(SpringLayout.WEST, x_lbl_access_token_secret, 0, SpringLayout.WEST, x_lbl_access_token);
				sl_XingPanel.putConstraint(SpringLayout.EAST, x_lbl_access_token_secret, 0, SpringLayout.EAST, x_lbl_access_token);
				XingPanel.add(x_lbl_access_token_secret);
			}
			{
				x_access_token_secretTextField = new JTextField();
				x_access_token_secretTextField.setEditable(false);
				sl_XingPanel.putConstraint(SpringLayout.WEST, x_access_token_secretTextField, 0, SpringLayout.WEST, x_access_tokenTextField);
				sl_XingPanel.putConstraint(SpringLayout.SOUTH, x_access_token_secretTextField, 0, SpringLayout.SOUTH, x_lbl_access_token_secret);
				sl_XingPanel.putConstraint(SpringLayout.EAST, x_access_token_secretTextField, 0, SpringLayout.EAST, x_access_tokenTextField);
				XingPanel.add(x_access_token_secretTextField);
			}
			
			x_btnGenerateToken = new JButton("Generate");
			sl_XingPanel.putConstraint(SpringLayout.SOUTH, x_btnGenerateToken, -6, SpringLayout.SOUTH, XingPanel);
			sl_XingPanel.putConstraint(SpringLayout.EAST, x_btnGenerateToken, -6, SpringLayout.EAST, XingPanel);
			x_btnGenerateToken.addActionListener(new X_GenereateAction());
			XingPanel.add(x_btnGenerateToken);
			
			x_lblStatus = new JLabel("Status:");
			sl_XingPanel.putConstraint(SpringLayout.NORTH, x_lblStatus, 6, SpringLayout.SOUTH, x_lbl_access_token_secret);
			sl_XingPanel.putConstraint(SpringLayout.WEST, x_lblStatus, 0, SpringLayout.WEST, x_lbl_access_token);
			sl_XingPanel.putConstraint(SpringLayout.EAST, x_lblStatus, 0, SpringLayout.EAST, x_btnGenerateToken);
			XingPanel.add(x_lblStatus);
		}
		{
			JPanel LinkedInPanel = new JPanel();
			LinkedInPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "LinkedIn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(LinkedInPanel);
			SpringLayout sl_LinkedInPanel = new SpringLayout();
			LinkedInPanel.setLayout(sl_LinkedInPanel);
			{
				l_lbl_access_token = new JLabel("Access Token :");
				sl_LinkedInPanel.putConstraint(SpringLayout.NORTH, l_lbl_access_token, 10, SpringLayout.NORTH, LinkedInPanel);
				sl_LinkedInPanel.putConstraint(SpringLayout.WEST, l_lbl_access_token, 10, SpringLayout.WEST, LinkedInPanel);
				LinkedInPanel.add(l_lbl_access_token);
			}
			{
				l_access_tokenTextField = new JTextField();
				l_access_tokenTextField.setEditable(false);
				sl_LinkedInPanel.putConstraint(SpringLayout.WEST, l_access_tokenTextField, 20, SpringLayout.EAST, l_lbl_access_token);
				sl_LinkedInPanel.putConstraint(SpringLayout.SOUTH, l_access_tokenTextField, 0, SpringLayout.SOUTH, l_lbl_access_token);
				sl_LinkedInPanel.putConstraint(SpringLayout.EAST, l_access_tokenTextField, -6, SpringLayout.EAST, LinkedInPanel);
				l_access_tokenTextField.setColumns(15);
				LinkedInPanel.add(l_access_tokenTextField);
			}
			{
				l_lbl_access_token_secret = new JLabel("A. T. secret :");
				sl_LinkedInPanel.putConstraint(SpringLayout.NORTH, l_lbl_access_token_secret, 12, SpringLayout.SOUTH, l_lbl_access_token);
				sl_LinkedInPanel.putConstraint(SpringLayout.WEST, l_lbl_access_token_secret, 0, SpringLayout.WEST, l_lbl_access_token);
				sl_LinkedInPanel.putConstraint(SpringLayout.EAST, l_lbl_access_token_secret, 0, SpringLayout.EAST, l_lbl_access_token);
				LinkedInPanel.add(l_lbl_access_token_secret);
			}
			{
				l_access_token_TextField = new JTextField();
				l_access_token_TextField.setEditable(false);
				sl_LinkedInPanel.putConstraint(SpringLayout.WEST, l_access_token_TextField, 0, SpringLayout.WEST, l_access_tokenTextField);
				sl_LinkedInPanel.putConstraint(SpringLayout.SOUTH, l_access_token_TextField, 0, SpringLayout.SOUTH, l_lbl_access_token_secret);
				sl_LinkedInPanel.putConstraint(SpringLayout.EAST, l_access_token_TextField, 0, SpringLayout.EAST, l_access_tokenTextField);
				LinkedInPanel.add(l_access_token_TextField);
			}
			
			l_btnGenerateToken = new JButton("Generate");
			sl_LinkedInPanel.putConstraint(SpringLayout.SOUTH, l_btnGenerateToken, -6, SpringLayout.SOUTH, LinkedInPanel);
			sl_LinkedInPanel.putConstraint(SpringLayout.EAST, l_btnGenerateToken, -6, SpringLayout.EAST, LinkedInPanel);
			l_btnGenerateToken.addActionListener(new L_GenereateAction());
			LinkedInPanel.add(l_btnGenerateToken);
			
			l_lblStatus = new JLabel("Status:");
			sl_LinkedInPanel.putConstraint(SpringLayout.NORTH, l_lblStatus, 6, SpringLayout.SOUTH, l_lbl_access_token_secret);
			sl_LinkedInPanel.putConstraint(SpringLayout.WEST, l_lblStatus, 0, SpringLayout.WEST, l_lbl_access_token);
			sl_LinkedInPanel.putConstraint(SpringLayout.EAST, l_lblStatus, 0, SpringLayout.EAST, l_btnGenerateToken);
			LinkedInPanel.add(l_lblStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
//				btnConnect = new JButton("Connect");
//				buttonPane.add(btnConnect);
			}
			{
				JButton okButton = new JButton("Save");
				okButton.setActionCommand("OK");
				okButton.addActionListener( new OKAction());
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


	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		User _user = user_controller.getCurrent_user();
		updateView(_user);
	}
	
	private void updateView(User user){
		this.f_tokenTextField.setText(user.getF_token());
		this.f_token_secretTextField.setText(user.getF_token_secret());
		this.t_accestokenTextField.setText(user.getT_token());
		this.t_accessTokenSecretField.setText(user.getT_token_secret());
		this.x_access_tokenTextField.setText(user.getX_token());
		this.x_access_token_secretTextField.setText(user.getX_token_secret());
		this.l_access_tokenTextField.setText(user.getL_token());
		this.l_access_token_TextField.setText(user.getL_token_secret());
		
		// For Twitter
		if(user_controller.checkToken(user.getT_token(),user.getT_token_secret(),SocialMediaType.TWITTER)){
			this.makeConnectionStatus(SocialMediaType.TWITTER, "Valid Token", 0);
		}else{
			this.makeConnectionStatus(SocialMediaType.TWITTER, "Invalid Token", 1);			
		}
		
		// For Facebook
		if(user_controller.checkToken(user.getF_token(),user.getF_token_secret(),SocialMediaType.FACEBOOK)){
			this.makeConnectionStatus(SocialMediaType.FACEBOOK, "Valid Token", 0);
		}else{
			this.makeConnectionStatus(SocialMediaType.FACEBOOK, "Invalid Token", 1);			
		}
		
		// For Xing
		if(user_controller.checkToken(user.getX_token(),user.getX_token_secret(),SocialMediaType.XING)){
			this.makeConnectionStatus(SocialMediaType.XING, "Valid Token", 0);
		}else{
			this.makeConnectionStatus(SocialMediaType.XING, "Invalid Token", 1);			
		}
		
		// For LinedIn
		if(user_controller.checkToken(user.getL_token(),user.getL_token_secret(),SocialMediaType.LINKEDIN)){
			this.makeConnectionStatus(SocialMediaType.LINKEDIN, "Valid Token", 0);
		}else{
			this.makeConnectionStatus(SocialMediaType.LINKEDIN, "Invalid Token", 1);			
		}
		
	}
	
	private class OKAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			viewModel.setSocialMediaDatas(
					f_tokenTextField.getText(), 
					f_token_secretTextField.getText(),
					t_accestokenTextField.getText(), 
					t_accessTokenSecretField.getText(),
					x_access_tokenTextField.getText(), 
					x_access_token_secretTextField.getText(),
					l_access_tokenTextField.getText(), 
					l_access_token_TextField.getText());
			dispose();
		}
		
	}
	
	private class CancelAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();		
		}		
	}
	
	
	private class T_GenereateAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog URLDialog = new GenerateURLDialog(user_controller, viewModel,SocialMediaType.TWITTER,frame);
			URLDialog.setVisible(true);
		}		
	}
	
	private class F_GenereateAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog URLDialog = new GenerateURLDialog(user_controller, viewModel,SocialMediaType.FACEBOOK,frame);
			URLDialog.setVisible(true);
		}		
	}
	
	private class X_GenereateAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog URLDialog = new GenerateURLDialog(user_controller, viewModel,SocialMediaType.XING,frame);
			URLDialog.setVisible(true);
		}		
	}
	
	private class L_GenereateAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog URLDialog = new GenerateURLDialog(user_controller, viewModel,SocialMediaType.LINKEDIN,frame);
			URLDialog.setVisible(true);
		}		
	}
	
	private void makeConnectionStatus(SocialMediaType sType, String message, int severity){
		switch (sType) {
		case TWITTER:
			setMessage(message,severity,this.t_lblStatus);
			break;
		case FACEBOOK:
			setMessage(message,severity,this.f_lblStatus);
			break;
		case XING:
			setMessage(message,severity,this.x_lblStatus);
			break;
		case LINKEDIN:
			setMessage(message,severity,this.l_lblStatus);
			break;
		default:
			break;
		}
	}
	
	private void setMessage(String msg, int severity,JLabel component) {
		switch (severity) {
		case 0:
			component.setText(msg);
			component.setForeground(Color.GREEN);
			break;
		case 1:
			component.setText(msg);
			component.setForeground(Color.RED);
			break;
		default:
			component.setText(msg);
			component.setForeground(Color.BLACK);
			break;
		}
	}
	
	
}
