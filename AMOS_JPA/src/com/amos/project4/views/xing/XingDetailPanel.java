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
package com.amos.project4.views.xing;

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
import com.amos.project4.controllers.XingDataController;
import com.amos.project4.models.Client;
import com.amos.project4.models.XingData;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.views.AbstractControlledView;

public class XingDetailPanel extends JPanel implements AbstractControlledView {

	private static final long serialVersionUID = 1L;
	private JTextField ID_textField;
	private JTextField birthday_textField;
	private XingPicturePanel Picture_panel;

	private XingDataController controller;
	private ClientsController c_controller;

	private MessageTable message_table;
	private XFriendsTable friends_table;
	private VisitsTable visits_table;
	private JTextField txtCompany;

	public XingDetailPanel(ClientsController c_controller) {
		super();
		this.c_controller = c_controller;
		this.c_controller.addView(this);
		this.controller = new XingDataController();
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

		 Picture_panel = new XingPicturePanel(this.controller);
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

		 JLabel lblBirthday = new JLabel("Birthday :");
		 sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH,	 lblBirthday, 12, SpringLayout.SOUTH, lblId);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST,	 lblBirthday, 6, SpringLayout.EAST, Picture_panel);
		 accountDetails_Panel.add(lblBirthday);

		ID_textField = new JTextField();
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, ID_textField, 6, SpringLayout.EAST, lblId);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, ID_textField, 400, SpringLayout.EAST, lblId);
		ID_textField.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, ID_textField, 0, SpringLayout.SOUTH, lblId);
		accountDetails_Panel.add(ID_textField);
		ID_textField.setColumns(10);

		 birthday_textField = new JTextField();
		 birthday_textField.setEditable(false);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, birthday_textField, 0, SpringLayout.WEST, ID_textField);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, birthday_textField, 0, SpringLayout.SOUTH, lblBirthday);
		 sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, birthday_textField, 0, SpringLayout.EAST, ID_textField);
		 accountDetails_Panel.add(birthday_textField);
		 birthday_textField.setColumns(10);

		JTabbedPane xing_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, xing_tabbedPane, 10,
				SpringLayout.SOUTH, accountDetails_Panel);
		
		JLabel lblCompany = new JLabel("Company :");
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, lblId, 0, SpringLayout.EAST, lblCompany);
		sl_accountDetails_Panel.putConstraint(SpringLayout.NORTH, lblCompany, 12, SpringLayout.SOUTH, lblBirthday);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, lblCompany, 6, SpringLayout.EAST, Picture_panel);
		accountDetails_Panel.add(lblCompany);
		
		txtCompany = new JTextField();
		txtCompany.setEditable(false);
		sl_accountDetails_Panel.putConstraint(SpringLayout.WEST, txtCompany, 0, SpringLayout.WEST, ID_textField);
		sl_accountDetails_Panel.putConstraint(SpringLayout.SOUTH, txtCompany, 0, SpringLayout.SOUTH, lblCompany);
		sl_accountDetails_Panel.putConstraint(SpringLayout.EAST, txtCompany, 0, SpringLayout.EAST, ID_textField);
		accountDetails_Panel.add(txtCompany);
		txtCompany.setColumns(10);
		springLayout.putConstraint(SpringLayout.WEST, xing_tabbedPane, 0,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, xing_tabbedPane, 0,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, xing_tabbedPane, 0,
				SpringLayout.EAST, this);
		add(xing_tabbedPane);

		JPanel message_Panel = new JPanel();
		xing_tabbedPane.addTab("Messages", null, message_Panel, null);
		message_Panel.setBorder(new TitledBorder(null, "Profile Messages",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_message_Panel = new SpringLayout();
		message_Panel.setLayout(sl_message_Panel);

		JScrollPane message_scrollPane = new JScrollPane();
		sl_message_Panel.putConstraint(SpringLayout.NORTH, message_scrollPane,
				0, SpringLayout.NORTH, message_Panel);
		sl_message_Panel.putConstraint(SpringLayout.WEST, message_scrollPane,
				0, SpringLayout.WEST, message_Panel);
		sl_message_Panel.putConstraint(SpringLayout.SOUTH, message_scrollPane,
				-0, SpringLayout.SOUTH, message_Panel);
		sl_message_Panel.putConstraint(SpringLayout.EAST, message_scrollPane,
				-0, SpringLayout.EAST, message_Panel);
		message_Panel.add(message_scrollPane);

		message_table = new MessageTable();
		this.c_controller.addView(message_table);
		message_scrollPane.add(message_table);
		message_scrollPane.setViewportView(message_table);

		JPanel Friends_Panels = new JPanel();
		xing_tabbedPane.addTab("Contacts", null, Friends_Panels, null);
		Friends_Panels.setBorder(new TitledBorder(null, "Contacts",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Friends_Panels = new SpringLayout();
		Friends_Panels.setLayout(sl_Friends_Panels);

		JScrollPane friends_scrollPane = new JScrollPane();
		sl_Friends_Panels.putConstraint(SpringLayout.NORTH, friends_scrollPane,
				0, SpringLayout.NORTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.WEST, friends_scrollPane,
				0, SpringLayout.WEST, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.SOUTH, friends_scrollPane,
				-0, SpringLayout.SOUTH, Friends_Panels);
		sl_Friends_Panels.putConstraint(SpringLayout.EAST, friends_scrollPane,
				-0, SpringLayout.EAST, Friends_Panels);
		Friends_Panels.add(friends_scrollPane);

		friends_table = new XFriendsTable();
		this.c_controller.addView(friends_table);
		friends_scrollPane.setViewportView(friends_table);

		JPanel visits_Panel = new JPanel();
		xing_tabbedPane.addTab("Visits", null, visits_Panel, null);
		visits_Panel.setBorder(new TitledBorder(null, "Visits",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_Visits_Panel = new SpringLayout();
		visits_Panel.setLayout(sl_Visits_Panel);

		JScrollPane trends_scrollPane = new JScrollPane();
		sl_Visits_Panel.putConstraint(SpringLayout.NORTH, trends_scrollPane, 0,
				SpringLayout.NORTH, visits_Panel);
		sl_Visits_Panel.putConstraint(SpringLayout.WEST, trends_scrollPane, 0,
				SpringLayout.WEST, visits_Panel);
		sl_Visits_Panel.putConstraint(SpringLayout.SOUTH, trends_scrollPane,
				-0, SpringLayout.SOUTH, visits_Panel);
		sl_Visits_Panel.putConstraint(SpringLayout.EAST, trends_scrollPane, -0,
				SpringLayout.EAST, visits_Panel);
		visits_Panel.add(trends_scrollPane);

		visits_table = new VisitsTable();
		this.c_controller.addView(visits_table);
		trends_scrollPane.setViewportView(visits_table);

	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			controller.setSelected_client(c);
			
			 // Get the ID from Database
			 List<XingData> tmp =	 c.getXingDatasByType(XingDataType.ID);
			 if(tmp != null && !tmp.isEmpty()){
				 this.ID_textField.setText("" + tmp.get(0).getDataString());
			 }else{
				 this.ID_textField.setText("");
			 }
			
			// Get the Birthday from Database
			 tmp =	 c.getXingDatasByType(XingDataType.BIRTHDAY);
			 if(tmp != null && !tmp.isEmpty()){
				 this.birthday_textField.setText("" + tmp.get(0).getDataString());
			 }else{
				 this.birthday_textField.setText("");
			 }
			 
			// Get the Company from Database
			 tmp =	 c.getXingDatasByType(XingDataType.COMPANY);
			 if(tmp != null && !tmp.isEmpty()){
				if(tmp.get(0).getDataString().contains("#")){
					this.txtCompany.setText("" + (tmp.get(0).getDataString().split("#"))[0]);
				}else{
					this.txtCompany.setText("" + (tmp.get(0).getDataString()));
				}				 
			 }else{
				 this.txtCompany.setText("");
			 }
		}
	}
}
