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
package com.amos.project4.help;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.URL;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class HelpFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JHelp helpViewer_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpFrame dialog = new HelpFrame();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public HelpFrame(JFrame frame) {
		super();
		setIconImage(frame.getIconImage());
		setLocationRelativeTo(null);
		init();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public HelpFrame() {
		super();
		init();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private void init() {
		setTitle("AMOS Project 4 - Help");
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(new BorderLayout(0, 0));
		SpringLayout sl_contentPanel = new SpringLayout();
		getContentPane().add(contentPanel);
		contentPanel.setLayout(sl_contentPanel);
		helpViewer_1 = initHelper();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, helpViewer_1, 0,SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, helpViewer_1, 0,SpringLayout.WEST, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, helpViewer_1, 0,SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, helpViewer_1, 0,SpringLayout.EAST, contentPanel);
		contentPanel.add(helpViewer_1);
	}

	private JHelp initHelper() {
		try {
			// Get the classloader of this class.
			ClassLoader cl = HelpFrame.class.getClassLoader();
			URL url = this.getClass().getResource("/com/amos/project4/help/HelpSet.hs");
			return new JHelp(new HelpSet(cl, url));
		} catch (Exception e) {
			System.err.println("API Help Set not found");
			return new JHelp();
		}
	}

}
