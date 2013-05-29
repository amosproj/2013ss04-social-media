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
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
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
	private ClientsController client_controller;
	
	public ClientDetailMainPanel(ClientsController client_controller) {
		super();
		this.client_controller = client_controller;
		this.client_controller.addView(this);
		init();
	}

	private void init() {
		SpringLayout springLayout = new SpringLayout();
		this.setLayout(springLayout);

		JPanel personalInformationsPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH,
				personalInformationsPanel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST,
				personalInformationsPanel, 4, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH,
				personalInformationsPanel, 130, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST,
				personalInformationsPanel, 1, SpringLayout.EAST, this);
		personalInformationsPanel.setBorder(new TitledBorder(new LineBorder(
				new Color(0, 0, 0)), "Personal informations",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		this.add(personalInformationsPanel);
		SpringLayout sl_personalInformationsPanel = new SpringLayout();
		personalInformationsPanel.setLayout(sl_personalInformationsPanel);

		JLabel lblName = new JLabel("Name :");
		lblName.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH, lblName,
				10, SpringLayout.NORTH, personalInformationsPanel);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblName,
				10, SpringLayout.WEST, personalInformationsPanel);
		personalInformationsPanel.add(lblName);

		name_textField = new JTextField();
		name_textField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				name_textField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				name_textField, 10, SpringLayout.EAST, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				name_textField, 350, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(name_textField);
		name_textField.setColumns(50);

		JLabel lblFirstname = new JLabel("Firstname :");
		lblFirstname.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblFirstname, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				lblFirstname, 10, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(lblFirstname);

		firstnameTextField = new JTextField();
		firstnameTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				firstnameTextField, 0, SpringLayout.NORTH, lblName);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				firstnameTextField, 10, SpringLayout.EAST, lblFirstname);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				firstnameTextField, 280, SpringLayout.EAST, lblFirstname);
		personalInformationsPanel.add(firstnameTextField);
		firstnameTextField.setColumns(50);

		JLabel lblBirthdate = new JLabel("Birthdate :");
		lblBirthdate.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblBirthdate, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				lblBirthdate, 0, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(lblBirthdate);

		birthdateTextField = new JTextField();
		birthdateTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				birthdateTextField, 10, SpringLayout.SOUTH, name_textField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				birthdateTextField, 10, SpringLayout.EAST, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				birthdateTextField, 0, SpringLayout.EAST, name_textField);
		personalInformationsPanel.add(birthdateTextField);
		birthdateTextField.setColumns(50);

		JLabel lblPlace = new JLabel("Place :");
		lblPlace.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblPlace, 0, SpringLayout.NORTH, birthdateTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblPlace,
				0, SpringLayout.WEST, lblFirstname);
		personalInformationsPanel.add(lblPlace);

		PlaceTextField = new JTextField();
		PlaceTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				PlaceTextField, 0, SpringLayout.NORTH, lblBirthdate);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				PlaceTextField, 10, SpringLayout.EAST, lblPlace);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				PlaceTextField, 280, SpringLayout.EAST, lblPlace);
		personalInformationsPanel.add(PlaceTextField);
		PlaceTextField.setColumns(50);

		JLabel lblEmail = new JLabel("E-Mail :");
		lblEmail.setPreferredSize(new Dimension(70, 20));
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				lblEmail, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST, lblEmail,
				0, SpringLayout.WEST, lblName);
		personalInformationsPanel.add(lblEmail);

		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		sl_personalInformationsPanel.putConstraint(SpringLayout.NORTH,
				emailTextField, 10, SpringLayout.SOUTH, PlaceTextField);
		sl_personalInformationsPanel.putConstraint(SpringLayout.WEST,
				emailTextField, 10, SpringLayout.EAST, lblEmail);
		sl_personalInformationsPanel.putConstraint(SpringLayout.EAST,
				emailTextField, 0, SpringLayout.EAST, birthdateTextField);
		personalInformationsPanel.add(emailTextField);
		emailTextField.setColumns(50);
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
		}
	}

}
