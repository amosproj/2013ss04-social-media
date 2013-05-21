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
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import com.amos.project4.controllers.UserController;
import com.amos.project4.models.SocialMediaType;

public class GenerateURLDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField pin_textField;
	private JTextField url_textField;
	private UserController user_controller;
	private String url;
	private UserViewModel viewModel;
	private SocialMediaType sType;
	
	private GenerateURLDialog me;

	/**
	 * Create the dialog.
	 */
	public  GenerateURLDialog(UserController user,UserViewModel viewModel,SocialMediaType sType) {
		this.sType = sType;
		this.user_controller = user;
		this.viewModel = viewModel;
		this.url = user_controller.getAccessTokenRequestURL(sType);
		setTitle("Url window");
		setBounds(100, 100, 600, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		JLabel lblTitel = new JLabel("Please go to the following url, login and copy the generated Token");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblTitel, 5, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblTitel, 5, SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblTitel, 0, SpringLayout.EAST, contentPanel);
		contentPanel.add(lblTitel);
		
		JLabel lblGeneratedPin = new JLabel("Generated PIN* :");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblGeneratedPin, 20, SpringLayout.SOUTH, lblTitel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblGeneratedPin, 0, SpringLayout.WEST, lblTitel);
		contentPanel.add(lblGeneratedPin);
		
		pin_textField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.WEST, pin_textField, 6, SpringLayout.EAST, lblGeneratedPin);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, pin_textField, 0, SpringLayout.SOUTH, lblGeneratedPin);
		sl_contentPanel.putConstraint(SpringLayout.EAST, pin_textField, 0, SpringLayout.EAST, lblTitel);
		contentPanel.add(pin_textField);
		pin_textField.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, lblUrl, 18, SpringLayout.SOUTH, lblGeneratedPin);
		sl_contentPanel.putConstraint(SpringLayout.WEST, lblUrl, 0, SpringLayout.WEST, lblTitel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, lblUrl, 0, SpringLayout.EAST, lblGeneratedPin);
		contentPanel.add(lblUrl);
		
		url_textField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.EAST, url_textField, -80, SpringLayout.EAST, pin_textField);
		url_textField.setEditable(false);
		sl_contentPanel.putConstraint(SpringLayout.WEST, url_textField, 0, SpringLayout.WEST, pin_textField);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, url_textField, 0, SpringLayout.SOUTH, lblUrl);
		url_textField.setText(url);
		contentPanel.add(url_textField);
		
		JButton btnGo = new JButton("Go");
		btnGo.addActionListener(new GoAction());
		sl_contentPanel.putConstraint(SpringLayout.WEST, btnGo, 4, SpringLayout.EAST, url_textField);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, btnGo, 0, SpringLayout.SOUTH, lblUrl);
		sl_contentPanel.putConstraint(SpringLayout.EAST, btnGo, 0, SpringLayout.EAST, pin_textField);
		contentPanel.add(btnGo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new OKActionListener());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new CancelActionListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		me = this;
	}
	
	public String getPin(){
		return this.pin_textField.getText();
	}
	
	private class GoAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(Desktop.isDesktopSupported())
			{
			  try {
				Desktop.getDesktop().browse(new URI(url));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(me, "Could not open the URL. Please copy it manually !", "Error launching the browser", JOptionPane.ERROR_MESSAGE);
				}
			}
		}		
	}
	
	private class OKActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {			
			if(!user_controller.checkAndSetAccessToken(url, pin_textField.getText(),sType)){
				JOptionPane.showMessageDialog(me, "The token: " + pin_textField.getText() + " is ivalid !", "Invalid token", JOptionPane.ERROR_MESSAGE);
			}else{
				String[]  access_tokens = user_controller.getAccessToken(sType);
				viewModel.setSocialMediaDatas(
						sType == SocialMediaType.FACEBOOK?access_tokens[0]:viewModel.getF_token(), 
						sType == SocialMediaType.FACEBOOK?access_tokens[1]:viewModel.getF_token_secret(),
						sType == SocialMediaType.TWITTER?access_tokens[0]:viewModel.getT_token(), 
						sType == SocialMediaType.TWITTER?access_tokens[1]:viewModel.getT_token_secret(), 
						sType == SocialMediaType.XING?access_tokens[0]:viewModel.getX_token(),
						sType == SocialMediaType.XING?access_tokens[1]:viewModel.getX_token_secret(),
						sType == SocialMediaType.LINKEDIN?access_tokens[0]:viewModel.getL_token(),
						sType == SocialMediaType.LINKEDIN?access_tokens[1]:viewModel.getL_token_secret());
				
			}			
			dispose();		
		}		
	}
	
	private class CancelActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();			
		}		
	}
}
