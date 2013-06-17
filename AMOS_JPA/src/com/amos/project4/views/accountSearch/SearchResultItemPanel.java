package com.amos.project4.views.accountSearch;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;

public class SearchResultItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String profileURL;

	private SearchResultItemPanel me;
	private JLabel lblTitle;
	private JLabel lblTitle_1;
	private PicturePanel picture_panel;
	public SearchResultItemPanel(String profileURL, String title_1,
			String title_2, String pictureURL) {
		this();
		this.profileURL = profileURL;
		
	}

	public SearchResultItemPanel() {
		init();
		me = this;
	}

	private void init() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		picture_panel = new PicturePanel();
		springLayout.putConstraint(SpringLayout.NORTH, picture_panel, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, picture_panel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, picture_panel, -6, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, picture_panel, 58, SpringLayout.WEST, this);
		picture_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(picture_panel);
		
		lblTitle = new JLabel("Title 1");
		springLayout.putConstraint(SpringLayout.NORTH, lblTitle, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblTitle, 6, SpringLayout.EAST, picture_panel);
		lblTitle.setPreferredSize(new Dimension(300, 14));
		lblTitle.setSize(new Dimension(300, 0));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblTitle);
		
		lblTitle_1 = new JLabel("Title 2");
		springLayout.putConstraint(SpringLayout.NORTH, lblTitle_1, 6, SpringLayout.SOUTH, lblTitle);
		springLayout.putConstraint(SpringLayout.WEST, lblTitle_1, 0, SpringLayout.WEST, lblTitle);
		springLayout.putConstraint(SpringLayout.EAST, lblTitle_1, 0, SpringLayout.EAST, lblTitle);
		add(lblTitle_1);
		
		JButton btnViewOnSite = new JButton("View");
		btnViewOnSite.addActionListener(new profileURLAction());
		springLayout.putConstraint(SpringLayout.NORTH, btnViewOnSite, 6, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnViewOnSite, 6, SpringLayout.EAST, lblTitle);
		add(btnViewOnSite);
		
		JButton btnSelect = new JButton("Select");
		springLayout.putConstraint(SpringLayout.EAST, btnViewOnSite, 0, SpringLayout.EAST, btnSelect);
		springLayout.putConstraint(SpringLayout.WEST, btnSelect, 6, SpringLayout.EAST, lblTitle_1);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSelect, 0, SpringLayout.SOUTH, picture_panel);
		add(btnSelect);
		
	}
	
	private class profileURLAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Desktop.isDesktopSupported()){
				try {
						if(profileURL != null && profileURL.length() > 0)
							Desktop.getDesktop().browse(new URI(profileURL));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(me, "Could not open the URL. Please go to the folloeing url manually: \n" +
								profileURL, "Error launching the browser", JOptionPane.ERROR_MESSAGE);
					}
			}
		}
		
	}
	public void updateResultItem(String title_1,String title_2,String profileURL,String pictureURL) throws IOException{
		this.profileURL = profileURL;
		this.getLblTitle().setText(title_1);
		this.getLblTitle_1().setText(title_2);
		this.getPicture_panel().repaintProfileImage(pictureURL);
	}
	public JLabel getLblTitle() {
		return lblTitle;
	}
	public JLabel getLblTitle_1() {
		return lblTitle_1;
	}
	public PicturePanel getPicture_panel() {
		return picture_panel;
	}
}
