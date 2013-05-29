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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.UserController;
import com.amos.project4.models.Client;
import com.amos.project4.views.facebook.FacebookDetailPanel;
import com.amos.project4.views.twitter.TwitterDetailPanel;

public class AMOSMainUI {

	private JFrame frame;
	JScrollPane clienTable_scrollPane;
	private ClientTable tclients;
	private JTextField search_textField;
	
	@SuppressWarnings("rawtypes")
	private JComboBox drop_down;
	private String[] dd_input = { " ", "ID", "Name", "Firstname", "Birthdate",
			"E-Mail", "Place", "ZipCode", "Gender" };
	private ClientDetailMainPanel clientDetailsPane;
	private TwitterDetailPanel twitterDetailPane;
	private FacebookDetailPanel facebookDetailPane;
	private JTree leftMenuTree;
	
	private SearchParameters search_params;
	private UserViewModel user_model;
	
	private UserController user_controller;	
	private ClientsController client_controller;
	//private TwitterDataController twitterData_controller;
	//private FacebookDataController facebookData_controller;

	private static AMOSMainUI instance;
	
	public static AMOSMainUI getInstance(UserController user_controller, UserViewModel u_model){
		if(instance == null){
			try {
				instance = new AMOSMainUI(user_controller, u_model);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
		
		instance.user_controller = user_controller;
		instance.user_model = u_model;
		return instance;
	}
	
	public JFrame getMainFrame(){
		return frame;
	}

	/**
	 * Create the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public AMOSMainUI() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		initialize();		
	}

	public AMOSMainUI(UserController user_controller, UserViewModel u_model) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super();
		this.user_controller = user_controller;
		this.user_model = u_model;
		initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private void initialize() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		// Initialized The Controller
		client_controller = new ClientsController();

		// Initialise and register the search view Model
		search_params = new SearchParameters();
		client_controller.addModel(search_params);
		
		// Initialize and register the TwitterData controller
//		twitterData_controller = new TwitterDataController();
//		facebookData_controller = new FacebookDataController();

		// Initialise the Frame
		frame = new JFrame();
		frame.setSize(820, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setLocationByPlatform(true);

		// Initialise The menus
		initMenus();

		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JSplitPane horizontalSplitPane = new JSplitPane();
		springLayout.putConstraint(SpringLayout.NORTH, horizontalSplitPane, 0,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, horizontalSplitPane, 0,
				SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(horizontalSplitPane);

		// The Status bar
		JPanel StatusbarPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, horizontalSplitPane, 0,
				SpringLayout.NORTH, StatusbarPanel);
		springLayout.putConstraint(SpringLayout.EAST, horizontalSplitPane, 0,
				SpringLayout.EAST, StatusbarPanel);

		springLayout.putConstraint(SpringLayout.WEST, StatusbarPanel, 0,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, StatusbarPanel, 0,
				SpringLayout.EAST, frame.getContentPane());
		StatusbarPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		springLayout.putConstraint(SpringLayout.NORTH, StatusbarPanel, -20,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, StatusbarPanel, 0,
				SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(StatusbarPanel);
		SpringLayout sl_StatusbarPanel = new SpringLayout();
		StatusbarPanel.setLayout(sl_StatusbarPanel);

		JLabel lbl_statusBar = new JLabel("Loading ...");
		sl_StatusbarPanel.putConstraint(SpringLayout.SOUTH, lbl_statusBar, 0,
				SpringLayout.SOUTH, StatusbarPanel);
		sl_StatusbarPanel.putConstraint(SpringLayout.WEST, lbl_statusBar, 5,
				SpringLayout.WEST, StatusbarPanel);
		StatusbarPanel.add(lbl_statusBar);

		// The left Panel
		// JPanel panel_left = new JPanel();
		// horizontalSplitPane.setLeftComponent(panel_left);

		// The right Panel
		JPanel panel_right = new JPanel();

		horizontalSplitPane.setRightComponent(panel_right);
		SpringLayout sl_panel_right = new SpringLayout();
		panel_right.setLayout(sl_panel_right);

		JSplitPane verticalSplitPane = new JSplitPane();
		sl_panel_right.putConstraint(SpringLayout.NORTH, verticalSplitPane, 0,
				SpringLayout.NORTH, panel_right);
		sl_panel_right.putConstraint(SpringLayout.WEST, verticalSplitPane, 0,
				SpringLayout.WEST, panel_right);
		sl_panel_right.putConstraint(SpringLayout.SOUTH, verticalSplitPane, 0,
				SpringLayout.SOUTH, panel_right);
		sl_panel_right.putConstraint(SpringLayout.EAST, verticalSplitPane, 0,
				SpringLayout.EAST, panel_right);
		verticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_right.add(verticalSplitPane);

		// The right Top panel
		verticalSplitPane.setLeftComponent(initRightTopPanel());

		// The right botom panel
		verticalSplitPane.setRightComponent(initRightBottomPanel());

		// The left Panel
		JScrollPane left_scroll_pane = initLeftpanel();
		horizontalSplitPane.setLeftComponent(left_scroll_pane);

		// Register The Views to the controller
		this.getClient_controller().addView(this.getTclients());

	}

	public ClientsController getClient_controller() {
		return client_controller;
	}

	private JScrollPane initLeftpanel() {
		JScrollPane left_scroll_pane = new JScrollPane();
		left_scroll_pane.setPreferredSize(new Dimension(200, 0));

		JPanel panel_left = new JPanel();
		panel_left.setPreferredSize(new Dimension(150, 0));
		left_scroll_pane.setViewportView(panel_left);
		panel_left.setLayout(new BorderLayout(0, 0));

		JTree leftMenuTree = initLeftmenuTree();
		panel_left.add(leftMenuTree);

		return left_scroll_pane;
	}

	private JTree initLeftmenuTree() {

		// Initialize the settings short cut
		Vector<String> settingsMenu_vec = new TreeNodeVector<String>(
				"Settings", new String[] { "General Settings" });

		// Initialize the user menu short cut
//		Vector<String> usersMenu_vec = new TreeNodeVector<String>("Users",
//				new String[] { "Profile", "Change password" });

		// Initialize the Social media menus short cut
		Vector<String> socialsMenu_vec = new TreeNodeVector<String>("Social",
				new String[] { "Search account", "Listen", "React" });

		// Initialize the Social media menus short cut
		Vector<String> exitsMenu_vec = new TreeNodeVector<String>("Exit",
				new String[] { "Logout", "Exit application" });

		// Initialize the categories
		Object rootNodes[] = new Object[] { socialsMenu_vec, // usersMenu_vec,
				settingsMenu_vec, exitsMenu_vec };

		Vector<Object> root_vec = new TreeNodeVector<Object>("Menu Root",
				rootNodes);
		leftMenuTree = new JTree(root_vec);
		leftMenuTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Put horizontal line to separe the categories
		UIManager.put("Tree.line", Color.GREEN);
		leftMenuTree.putClientProperty("JTree.lineStyle", "Horizontal");
		
		//add a mouse adapter for double click listening
		leftMenuTree.addMouseListener(new MenuSelectionMouseAdapter());
		
		// Customize the Selection listener
		leftMenuTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Expand All
		int row = 0;
		while (row < leftMenuTree.getRowCount()) {
			leftMenuTree.expandRow(row);
			row++;
		}
	    
		return leftMenuTree;

	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel initRightTopPanel() {
		JPanel panel_right_top = new JPanel();
		panel_right_top.setPreferredSize(new Dimension(10, 350));

		SpringLayout sl_panel_right_top = new SpringLayout();
		panel_right_top.setLayout(sl_panel_right_top);

		JButton btnCheckSocialMedia = new JButton("Check Social Media");
		btnCheckSocialMedia.addActionListener(new SocialMediaScanAction());
		sl_panel_right_top.putConstraint(SpringLayout.NORTH,	btnCheckSocialMedia, 10, SpringLayout.NORTH, panel_right_top);
		sl_panel_right_top.putConstraint(SpringLayout.EAST,		btnCheckSocialMedia, -5, SpringLayout.EAST, panel_right_top);
		btnCheckSocialMedia.setPreferredSize(new Dimension(150, 25));
		panel_right_top.add(btnCheckSocialMedia);


		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new searchAction());

		sl_panel_right_top.putConstraint(SpringLayout.NORTH, btnSearch, 0,	SpringLayout.NORTH, btnCheckSocialMedia);
		sl_panel_right_top.putConstraint(SpringLayout.EAST, btnSearch, -5,	SpringLayout.WEST, btnCheckSocialMedia);
		panel_right_top.add(btnSearch);
		btnSearch.setPreferredSize(new Dimension(75, 25));
		
		drop_down = new JComboBox(dd_input);
		drop_down.addActionListener(new SearchCatListener());
		sl_panel_right_top.putConstraint(SpringLayout.NORTH, drop_down, 0,	SpringLayout.NORTH, btnCheckSocialMedia);
		sl_panel_right_top.putConstraint(SpringLayout.WEST, drop_down, 10,	SpringLayout.WEST, panel_right_top);
		//sl_panel_right_top.putConstraint(SpringLayout.SOUTH, drop_down, 30,	SpringLayout.NORTH, panel_right_top);
		sl_panel_right_top.putConstraint(SpringLayout.EAST, drop_down, 150,	SpringLayout.WEST, panel_right_top);
		panel_right_top.add(drop_down);

		search_textField = new JTextField();
		search_textField.addActionListener(new searchAction());
		sl_panel_right_top.putConstraint(SpringLayout.NORTH, search_textField,0, SpringLayout.NORTH, btnCheckSocialMedia);
		sl_panel_right_top.putConstraint(SpringLayout.WEST, search_textField, 5, SpringLayout.EAST, drop_down);
		sl_panel_right_top.putConstraint(SpringLayout.EAST, search_textField,-5, SpringLayout.WEST, btnSearch);
		panel_right_top.add(search_textField);
		search_textField.setColumns(20);

		JPanel client_result_panel = InitClientTablePanel();
		sl_panel_right_top.putConstraint(SpringLayout.NORTH,
				client_result_panel, 10, SpringLayout.SOUTH,
				btnCheckSocialMedia);
		sl_panel_right_top.putConstraint(SpringLayout.WEST,
				client_result_panel, 0, SpringLayout.WEST, panel_right_top);
		sl_panel_right_top.putConstraint(SpringLayout.SOUTH,
				client_result_panel, -10, SpringLayout.SOUTH, panel_right_top);
		sl_panel_right_top.putConstraint(SpringLayout.EAST,
				client_result_panel, 0, SpringLayout.EAST, btnCheckSocialMedia);
		panel_right_top.add(client_result_panel);

//		JList list = new JList();
//		sl_panel_right_top.putConstraint(SpringLayout.NORTH, list, 22,
//				SpringLayout.NORTH, panel_right_top);
//		panel_right_top.add(list);
//
//		JList list_1 = new JList();
//		sl_panel_right_top.putConstraint(SpringLayout.NORTH, list_1, 10,
//				SpringLayout.NORTH, panel_right_top);
//		sl_panel_right_top.putConstraint(SpringLayout.WEST, list_1, -7,
//				SpringLayout.WEST, search_textField);
//		sl_panel_right_top.putConstraint(SpringLayout.SOUTH, list_1, 30,
//				SpringLayout.NORTH, panel_right_top);
//		sl_panel_right_top.putConstraint(SpringLayout.EAST, list_1, -166,
//				SpringLayout.WEST, search_textField);
//		panel_right_top.add(list_1);

		

		return panel_right_top;
	}

	private JPanel InitClientTablePanel() {
		JPanel client_result_panel = new JPanel();
		client_result_panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Search results",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		client_result_panel.setLayout(new BorderLayout(0, 0));

		clienTable_scrollPane = new JScrollPane();
		client_result_panel.add(clienTable_scrollPane);

		tclients = new ClientTable(client_controller);
		clienTable_scrollPane.setViewportView(tclients);
		return client_result_panel;

	}

	private JPanel initRightBottomPanel() {
		JPanel panel_right_bottom = new JPanel();
		panel_right_bottom.setLayout(new BorderLayout(0, 0));

		JTabbedPane mediaPanes = new JTabbedPane(JTabbedPane.TOP);
		panel_right_bottom.add(mediaPanes, BorderLayout.CENTER);

		// Initialise Client Detail Panel
		clientDetailsPane = new ClientDetailMainPanel(client_controller);
		mediaPanes.addTab("Client' details", null, clientDetailsPane, null);
		mediaPanes.setEnabledAt(0, true);

		facebookDetailPane = new FacebookDetailPanel(client_controller);
		mediaPanes.addTab("Facebook", null, facebookDetailPane, null);

		// Initialise the Twitter detail Pane
		twitterDetailPane = new TwitterDetailPanel(client_controller);
		mediaPanes.addTab("Twitter", null, twitterDetailPane, null);
		
		JPanel xingPane = new JPanel();
		mediaPanes.addTab("Xing", null, xingPane, null);

		JPanel linkedInPane = new JPanel();
		mediaPanes.addTab("LinkedIn", null, linkedInPane, null);

		return panel_right_bottom;
	}

	private void initMenus() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmUpdateDatabase = new JMenuItem("Update Database");
		mnFile.add(mntmUpdateDatabase);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new LogoutAction());
		mnFile.add(mntmLogout);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ExitAction());
		mnFile.add(mntmExit);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JMenuItem mntmGenSetting = new JMenuItem("General Settings");
		mntmGenSetting.addActionListener(new GeneralsettingsAction());
		mnSettings.add(mntmGenSetting);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
	}
	
	public ClientTable getTclients() {
		return tclients;
	}
	
	private void exit(){
		int dialogResult = JOptionPane.showConfirmDialog (frame, 
				"Exit the application ?","Exit confirmation",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.dispose();
			System.exit(0);
		}
	}
	
	private void showGeneralsettingDialog(){
		GeneralSettingsDialog dialog = new GeneralSettingsDialog(user_controller, user_model,frame);
		dialog.setVisible(true);
	}
	
	private void logout(){
		int dialogResult = JOptionPane.showConfirmDialog (frame, 
				"Are you sure you want to logout from the application ?","Logout confirmation",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			frame.dispose();
			Login dialog = new Login();
			dialog.setVisible(true);
		}
	}
	
	private void makeSimpleSearch(){
		search_params.setSearchCat(drop_down.getSelectedIndex());
		search_params.setSearchText(search_textField.getText());
		tclients.getModel().fireTableDataChanged();
		clienTable_scrollPane.invalidate();
		clienTable_scrollPane.revalidate();
		frame.repaint();
	}
	
	private void makeSearchWithCat(){
		search_params.setSearchCat(drop_down.getSelectedIndex());
		tclients.getModel().fireTableDataChanged();
		clienTable_scrollPane.invalidate();
		clienTable_scrollPane.revalidate();
		frame.repaint();
	}
	
	private void openSocialMediaScanDialog(){
		ArrayList<Client> c_list = new ArrayList<Client>();
		Client selected_client = client_controller.getSelectedClient();
		if(selected_client == null){
			c_list.addAll(client_controller.getAllClients());
		}else{
			c_list.add(selected_client);
		}
		SocialMediaScanDialog dialog = new SocialMediaScanDialog(user_controller.getCurrent_user(),c_list,frame);
		dialog.setVisible(true);
	}
	
	// Action listeners implementations
	private class MenuSelectionMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() <= 1) {
				return;
			}
			TreePath tp = leftMenuTree.getPathForLocation(me.getX(), me.getY());
			Object obj = tp.getLastPathComponent();
			String item = obj.toString();
			if (item.equalsIgnoreCase("Logout")) {
				logout();
			} else if (item.equalsIgnoreCase("Exit application")) {
				exit();
			} else if (item.equalsIgnoreCase("General Settings")) {
				showGeneralsettingDialog();
			} else {
				System.out.println("" + obj.toString());
			}
		}
	}

	private class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	}
	
	private class GeneralsettingsAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			showGeneralsettingDialog();
		}		
	}
	
	private class LogoutAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			logout();
		}		
	}

	private class searchAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			makeSimpleSearch();
		}
	}

	private class SearchCatListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			makeSearchWithCat();
		}
	}
	
	public class SocialMediaScanAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			openSocialMediaScanDialog();
		}
	}

}
