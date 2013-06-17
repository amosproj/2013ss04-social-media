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
package com.amos.project4.views.linkedIn;

import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.LinkedInDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.LinkedInData;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.views.AbstractControlledView;

public class LinkedInDetailPanel extends JPanel implements AbstractControlledView {

	private static final long serialVersionUID = 1L;
	private JTextField ID_textField;
	private JTextField headline_textField;
	private LinkedInPicturePanel Picture_panel;

	private LinkedInDataController controller;
	private ClientsController c_controller;

	private PositionTable positions_table;
	private ContactsTable contacts_table;
	private EducationsTable educations_table;
	private PhonesTable phones_table;
	private JTextField txtCompany;
	private JTextField cJob_textField;
	private JTextField twitter_textField;

	public LinkedInDetailPanel(ClientsController c_controller) {
		super();
		this.c_controller = c_controller;
		this.c_controller.addView(this);
		this.controller = new LinkedInDataController();
		this.controller.addView(this);
		init();
	}

	private void init() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JPanel accountDetails_Panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, accountDetails_Panel, 0,
				SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, accountDetails_Panel, 0,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, accountDetails_Panel,
				160, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, accountDetails_Panel, 0,
				SpringLayout.EAST, this);
		add(accountDetails_Panel);
		SpringLayout sl_accountDetails_Panel = new SpringLayout();
		accountDetails_Panel.setLayout(sl_accountDetails_Panel);
		
		JTabbedPane linkedIn_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, linkedIn_tabbedPane, 10,	SpringLayout.SOUTH, accountDetails_Panel);
		springLayout.putConstraint(SpringLayout.WEST, linkedIn_tabbedPane, 0,SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, linkedIn_tabbedPane, 0,SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, linkedIn_tabbedPane, 0,SpringLayout.EAST, this);
		add(linkedIn_tabbedPane);

		 Picture_panel = new LinkedInPicturePanel(this.controller);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH,
		 Picture_panel, -6, SpringLayout.SOUTH, accountDetails_Panel);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH,
		 Picture_panel, 10, SpringLayout.NORTH, accountDetails_Panel);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST,
		 Picture_panel, 10, SpringLayout.WEST, accountDetails_Panel);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST,
		 Picture_panel, 160, SpringLayout.WEST, accountDetails_Panel);
		 accountDetails_Panel.add(Picture_panel);
		 c_controller.addView(Picture_panel);

		JLabel lblId = new JLabel("ID :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblId, 10, SpringLayout.NORTH, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblId, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblId);

		 JLabel lblHeadline = new JLabel("Headline :");
		 sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH,	 lblHeadline, 12, SpringLayout.SOUTH, lblId);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST,	 lblHeadline, 6, SpringLayout.EAST, Picture_panel);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, lblHeadline, 0, SpringLayout.EAST, lblId);
		 accountDetails_Panel.add(lblHeadline);
		 
		JLabel lblcurentJob = new JLabel("Current Job :");
		 sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH,	 lblcurentJob, 12, SpringLayout.SOUTH, lblHeadline);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST,	 lblcurentJob, 6, SpringLayout.EAST, Picture_panel);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, lblcurentJob, 0, SpringLayout.EAST, lblId);
		 accountDetails_Panel.add(lblcurentJob);
		 
		 JLabel lblCompany = new JLabel("Company :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblCompany, 12, SpringLayout.SOUTH, lblcurentJob);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblCompany, 6, SpringLayout.EAST, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, lblCompany, 0, SpringLayout.EAST, lblId);
		accountDetails_Panel.add(lblCompany);
		
		JLabel lbltwitter = new JLabel("Principal Twitter account :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lbltwitter, 12, SpringLayout.SOUTH, lblCompany);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lbltwitter, 6, SpringLayout.EAST, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, lblId, 0, SpringLayout.EAST, lbltwitter);		
		accountDetails_Panel.add(lbltwitter);
		

		ID_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, ID_textField, 6, SpringLayout.EAST, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, ID_textField, 400, SpringLayout.EAST, lblId);
		ID_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, ID_textField, 0, SpringLayout.SOUTH, lblId);
		accountDetails_Panel.add(ID_textField);
		ID_textField.setColumns(10);

		 headline_textField = new JTextField();
		 headline_textField.setEditable(false);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, headline_textField, 0, SpringLayout.WEST, ID_textField);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, headline_textField, 0, SpringLayout.SOUTH, lblHeadline);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, headline_textField, 0, SpringLayout.EAST, ID_textField);
		 accountDetails_Panel.add(headline_textField);
		 headline_textField.setColumns(10);
		 
		 cJob_textField = new JTextField();
		 cJob_textField.setEditable(false);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, cJob_textField, 0, SpringLayout.WEST, ID_textField);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH,cJob_textField, 0, SpringLayout.SOUTH, lblcurentJob);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, cJob_textField, 0, SpringLayout.EAST, ID_textField);
		 accountDetails_Panel.add(cJob_textField);
		 cJob_textField.setColumns(10);		
		
		txtCompany = new JTextField();
		txtCompany.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, txtCompany, 0, SpringLayout.WEST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, txtCompany, 0, SpringLayout.SOUTH, lblCompany);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, txtCompany, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(txtCompany);
		txtCompany.setColumns(10);
		
		twitter_textField = new JTextField();
		twitter_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, twitter_textField, 0, SpringLayout.WEST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, twitter_textField, 0, SpringLayout.SOUTH, lbltwitter);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, twitter_textField, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(twitter_textField);
		twitter_textField.setColumns(10);
		
		LinkedInClientAccountSetting account_setting_panel = new LinkedInClientAccountSetting(c_controller,controller);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, account_setting_panel, 0, SpringLayout.NORTH, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, account_setting_panel, 6, SpringLayout.EAST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, account_setting_panel, 0, SpringLayout.SOUTH, Picture_panel);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, account_setting_panel, -10, SpringLayout.EAST, accountDetails_Panel);
		accountDetails_Panel.add(account_setting_panel);
		

		JPanel positions_Panel = new JPanel();
		linkedIn_tabbedPane.addTab("Positions", null, positions_Panel, null);
		positions_Panel.setBorder(new TitledBorder(null, "Positions",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_positions_Panel = new SpringLayout();
		positions_Panel.setLayout(sl_positions_Panel);

		JScrollPane position_scrollPane = new JScrollPane();
		sl_positions_Panel.putConstraint(SpringLayout.NORTH, position_scrollPane,0, SpringLayout.NORTH, positions_Panel);
		sl_positions_Panel.putConstraint(SpringLayout.WEST, position_scrollPane,0, SpringLayout.WEST, positions_Panel);
		sl_positions_Panel.putConstraint(SpringLayout.SOUTH, position_scrollPane,-0, SpringLayout.SOUTH, positions_Panel);
		sl_positions_Panel.putConstraint(SpringLayout.EAST, position_scrollPane,-0, SpringLayout.EAST, positions_Panel);
		positions_Panel.add(position_scrollPane);

		positions_table = new PositionTable();
		this.c_controller.addView(positions_table);
		position_scrollPane.add(positions_table);
		position_scrollPane.setViewportView(positions_table);

		JPanel contact_Panels = new JPanel();
		linkedIn_tabbedPane.addTab("Contacts", null, contact_Panels, null);
		contact_Panels.setBorder(new TitledBorder(null, "Contacts",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_contacts_Panels = new SpringLayout();
		contact_Panels.setLayout(sl_contacts_Panels);

		JScrollPane contact_scrollPane = new JScrollPane();
		sl_contacts_Panels.putConstraint(SpringLayout.NORTH, contact_scrollPane,0, SpringLayout.NORTH, contact_Panels);
		sl_contacts_Panels.putConstraint(SpringLayout.WEST, contact_scrollPane,0, SpringLayout.WEST, contact_Panels);
		sl_contacts_Panels.putConstraint(SpringLayout.SOUTH, contact_scrollPane,-0, SpringLayout.SOUTH, contact_Panels);
		sl_contacts_Panels.putConstraint(SpringLayout.EAST, contact_scrollPane,-0, SpringLayout.EAST, contact_Panels);
		contact_Panels.add(contact_scrollPane);

		contacts_table = new ContactsTable();
		this.c_controller.addView(contacts_table);
		contact_scrollPane.setViewportView(contacts_table);
		
		
		JPanel education_Panels = new JPanel();
		linkedIn_tabbedPane.addTab("Educations", null, education_Panels, null);
		education_Panels.setBorder(new TitledBorder(null, "Educations",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_educations_Panels = new SpringLayout();
		education_Panels.setLayout(sl_educations_Panels);

		JScrollPane education_scrollPane = new JScrollPane();
		sl_educations_Panels.putConstraint(SpringLayout.NORTH, education_scrollPane,0, SpringLayout.NORTH, education_Panels);
		sl_educations_Panels.putConstraint(SpringLayout.WEST, education_scrollPane,0, SpringLayout.WEST, education_Panels);
		sl_educations_Panels.putConstraint(SpringLayout.SOUTH, education_scrollPane,-0, SpringLayout.SOUTH, education_Panels);
		sl_educations_Panels.putConstraint(SpringLayout.EAST, education_scrollPane,	-0, SpringLayout.EAST, education_Panels);
		education_Panels.add(education_scrollPane);

		educations_table = new EducationsTable();
		this.c_controller.addView(educations_table);
		education_scrollPane.setViewportView(educations_table);
		
		

		JPanel phones_Panel = new JPanel();
		linkedIn_tabbedPane.addTab("Phones", null, phones_Panel, null);
		phones_Panel.setBorder(new TitledBorder(null, "Phones",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Phones_Panel = new SpringLayout();
		phones_Panel.setLayout(sl_Phones_Panel);

		JScrollPane phones_scrollPane = new JScrollPane();
		sl_Phones_Panel.putConstraint(SpringLayout.NORTH, phones_scrollPane, 0,	SpringLayout.NORTH, phones_Panel);
		sl_Phones_Panel.putConstraint(SpringLayout.WEST, phones_scrollPane, 0,SpringLayout.WEST, phones_Panel);
		sl_Phones_Panel.putConstraint(SpringLayout.SOUTH, phones_scrollPane,0, SpringLayout.SOUTH, phones_Panel);
		sl_Phones_Panel.putConstraint(SpringLayout.EAST, phones_scrollPane, -0,	SpringLayout.EAST, phones_Panel);
		phones_Panel.add(phones_scrollPane);

		phones_table = new PhonesTable();
		this.c_controller.addView(phones_table);
		phones_scrollPane.setViewportView(phones_table);

	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			this.controller.setSelected_client(c);
			
			 // Get the ID from Database
			 List<LinkedInData> tmp =	 c.getLinkedInDatasByType(LinkedInDataType.ID);
			 if(tmp != null && !tmp.isEmpty()){
				 this.ID_textField.setText("" + tmp.get(0).getDataString());
			 }else{
				 this.ID_textField.setText("");
			 }
			
			// Get the Headline from Database
			 tmp =	 c.getLinkedInDatasByType(LinkedInDataType.HEADLINES);
			 if(tmp != null && !tmp.isEmpty()){
				 this.headline_textField.setText("" + tmp.get(0).getDataString());
			 }else{
				 this.headline_textField.setText("");
			 }
			 
			// Get the curent Job from Database
			 tmp =	 c.getLinkedInDatasByType(LinkedInDataType.CURRENT_JOB);
			 if(tmp != null && !tmp.isEmpty()){
				 if(tmp.get(0).getDataString().contains("#")){
					this.cJob_textField.setText("" + (tmp.get(0).getDataString().split("#"))[1]);
				}else{
					this.cJob_textField.setText("" + (tmp.get(0).getDataString()));
				}
			 }else{
				 this.cJob_textField.setText("");
			 }
			 
			// Get the company from Database
			 tmp =	 c.getLinkedInDatasByType(LinkedInDataType.COMPANY);
			 if(tmp != null && !tmp.isEmpty()){
				 if(tmp.get(0).getDataString().contains("#")){
					this.txtCompany.setText("" + (tmp.get(0).getDataString().split("#"))[1]);
				}else{
					this.txtCompany.setText("" + (tmp.get(0).getDataString()));
				}
			 }else{
				 this.txtCompany.setText("");
			 }
			 
			// Get the Twitter from Database
			 tmp =	 c.getLinkedInDatasByType(LinkedInDataType.TWITTER_ACCOUNT);
			 if(tmp != null && !tmp.isEmpty()){
				this.twitter_textField.setText("" + (tmp.get(0).getDataString()));			 
			 }else{
				 this.twitter_textField.setText("");
			 }
		}
	}
}
