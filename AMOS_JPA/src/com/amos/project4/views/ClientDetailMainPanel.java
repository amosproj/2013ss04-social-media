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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.models.BirthdayClient;
import com.amos.project4.models.Client;

public class ClientDetailMainPanel extends JPanel implements
		AbstractControlledView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField name_textField;
	private JTextField firstnameTextField;
	private JTextField birthdateTextField;
	private JTextField PlaceTextField;
	private JTextField emailTextField;
	private JTextField txtAdditionalInformations;
	private ClientsController client_controller;
	
	
	public ClientDetailMainPanel(ClientsController client_controller) {
		super();
		this.client_controller = client_controller;
		this.client_controller.addView(this);
		init();
	}

	private synchronized void fillbirthdayOfTheWeek() {
		String msg = "No Birthdays this week";

		List<BirthdayClient> b_clients = client_controller.getAllBirthDayOfTheWeek();
		if(!b_clients.isEmpty()){
			msg = "This weeks birthdays ("+ b_clients.size() +") : " ;
			for (int i = 0; i < b_clients.size(); i++) {
				msg += b_clients.get(i).getFirstname() + " " + b_clients.get(i).getName() ;
				if(i!= b_clients.size()-1){
					msg += ", ";
				}
			}
			msg = msg + ".";
		}
		txtAdditionalInformations.setText(msg);
	}

	private void init() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel accountDetails_Panel = initOnlyPersonnalInformation();
		springLayout.putConstraint(SpringLayout.NORTH, accountDetails_Panel, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, accountDetails_Panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, accountDetails_Panel, 160, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, accountDetails_Panel, 0, SpringLayout.EAST, this);
		add(accountDetails_Panel);
	}
	
	private JPanel initOnlyPersonnalInformation(){
		JPanel top = new JPanel();
		SpringLayout springLayout = new SpringLayout();
		top.setLayout(springLayout);

		JPanel personalInformationsPanel = initClientDetails();
		springLayout.putConstraint(SpringLayout.NORTH,
				personalInformationsPanel, 10, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.WEST,
				personalInformationsPanel, 4, SpringLayout.WEST, top);
		springLayout.putConstraint(SpringLayout.SOUTH,
				personalInformationsPanel, 130, SpringLayout.NORTH, top);
		springLayout.putConstraint(SpringLayout.EAST,
				personalInformationsPanel, 1, SpringLayout.EAST, top);
		top.add(personalInformationsPanel);
		
		return top;
	}

	private JPanel initClientDetails() {
		JPanel personalInformationsPanel = new JPanel();
		
		personalInformationsPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Personal informations",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		personalInformationsPanel.setPreferredSize(new Dimension(400,400));
		
		SpringLayout sl_personalInformationsPanel = new SpringLayout();
		personalInformationsPanel.setLayout(sl_personalInformationsPanel);

		JLabel lblName = new JLabel("Name :");
		//lblName.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH, lblName,10, SpringLayout.NORTH, personalInformationsPanel);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblName,10, SpringLayout.WEST, personalInformationsPanel);
		personalInformationsPanel.add(lblName);

		name_textField = new JTextField();
		name_textField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				name_textField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,	name_textField, 10, SpringLayout.EAST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,	name_textField, 350, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(name_textField);
		name_textField.setColumns(50);

		JLabel lblFirstname = new JLabel("Firstname :");
		//lblFirstname.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,lblFirstname, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,lblFirstname, 10, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(lblFirstname);

		firstnameTextField = new JTextField();
		firstnameTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				firstnameTextField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,firstnameTextField, 10, SpringLayout.EAST, lblFirstname);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,firstnameTextField, 280, SpringLayout.EAST, lblFirstname);
		personalInformationsPanel.add(firstnameTextField);
		firstnameTextField.setColumns(50);

		JLabel lblBirthdate = new JLabel("Birthdate :");
		//lblBirthdate.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,lblBirthdate, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblBirthdate, 0, SpringLayout.WEST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST, lblBirthdate, 0, SpringLayout.EAST, lblName);
		personalInformationsPanel.add(lblBirthdate);

		birthdateTextField = new JTextField();
		birthdateTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,	birthdateTextField, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,	birthdateTextField, 10, SpringLayout.EAST, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,	birthdateTextField, 0, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(birthdateTextField);
		birthdateTextField.setColumns(50);

		JLabel lblPlace = new JLabel("Place :");
		//lblPlace.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,	lblPlace, 0, SpringLayout.NORTH, birthdateTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblPlace,	0, SpringLayout.WEST, lblFirstname);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST, lblPlace,	0, SpringLayout.EAST, lblFirstname);
		personalInformationsPanel.add(lblPlace);

		PlaceTextField = new JTextField();
		PlaceTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				PlaceTextField, 0, SpringLayout.NORTH, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,PlaceTextField, 10, SpringLayout.EAST, lblPlace);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,PlaceTextField, 280, SpringLayout.EAST, lblPlace);
		personalInformationsPanel.add(PlaceTextField);
		PlaceTextField.setColumns(50);

		JLabel lblEmail = new JLabel("E-Mail :");
		//lblEmail.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,lblEmail, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblEmail,	0, SpringLayout.WEST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST, lblEmail,	0, SpringLayout.EAST, lblName);
		personalInformationsPanel.add(lblEmail);

		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,	emailTextField, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,	emailTextField, 10, SpringLayout.EAST, lblEmail);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,	emailTextField, 0, SpringLayout.EAST, birthdateTextField);
		personalInformationsPanel.add(emailTextField);
		emailTextField.setColumns(50);
		
		JLabel lblAdditionalInformations = new JLabel("Additional Information :");
		lblAdditionalInformations.setPreferredSize(new Dimension(120, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH, lblAdditionalInformations, 10, SpringLayout.SOUTH, emailTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblAdditionalInformations,0, SpringLayout.WEST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST, lblName,0, SpringLayout.EAST, lblAdditionalInformations);
		personalInformationsPanel.add(lblAdditionalInformations);

		txtAdditionalInformations = new JTextField();
		txtAdditionalInformations.setEditable(false);
		txtAdditionalInformations.setFont(new Font("Tahoma", Font.BOLD, 11));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,	txtAdditionalInformations, 10, SpringLayout.SOUTH, lblAdditionalInformations);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,	txtAdditionalInformations, 10, SpringLayout.EAST, lblAdditionalInformations);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,	txtAdditionalInformations, 0, SpringLayout.EAST, firstnameTextField);
		personalInformationsPanel.add(txtAdditionalInformations);
		
		return personalInformationsPanel;		
	}

	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if (arg != null && arg.getClass().equals(Client.class)) {
			Client c = (Client) arg;
			name_textField.setText("" +c.getName());
			firstnameTextField.setText("" + c.getFirstname());
			birthdateTextField.setText("" + c.getBirthdate());
			PlaceTextField.setText("" +c.getPlace());
			emailTextField.setText("" +c.getMail());
			
			if(c.getBirthdate() == null) return;
			String msg = "No birthdays this week";			
			Calendar cal = Calendar.getInstance();
		    cal.setTime(c.getBirthdate());
		    int month = cal.get(Calendar.MONTH);
		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    cal.setTime(new Date(System.currentTimeMillis()));
		    int c_month = cal.get(Calendar.MONTH);
		    int c_day = cal.get(Calendar.DAY_OF_MONTH);
		    if(day == c_day && month == c_month){
		    	msg = c.getFirstname() + " " + c +" has birthday today !";
		    	msg = msg + ".";
		    	txtAdditionalInformations.setText(msg);
		    }else{
		    	fillbirthdayOfTheWeek();
		    }
		}
	}
	
	
	
}
