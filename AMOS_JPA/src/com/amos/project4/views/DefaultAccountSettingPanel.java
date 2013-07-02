package com.amos.project4.views;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.amos.project4.controllers.ClientsController;
import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.models.Client;
import com.amos.project4.views.accountSearch.SearchAccountDialog;

public abstract class DefaultAccountSettingPanel extends JPanel implements AbstractControlledView{

	private static final long serialVersionUID = 1L;
	ClientsController controller;
	private JButton btnSearchAndAssign;
	private JButton btnViewProfileOn;
	private JLabel lblNewLabel;
	private JLabel lblMakeASearch;
	private String profileURL;
	private DefaultAccountSettingPanel me;
	
	private MediaSearchController media_controller;
	
	public DefaultAccountSettingPanel(ClientsController controller,MediaSearchController media_controller) {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Client Account Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.controller = controller;
		this.controller.addView(this);
		this.media_controller = media_controller;
		
		setLayout(new GridLayout(2, 0, 0, 0));
		me = this;
		JPanel search_panel = new JPanel();
		add(search_panel);
		SpringLayout sl_search_panel = new SpringLayout();
		search_panel.setLayout(sl_search_panel);
		
		btnSearchAndAssign = new JButton("Search and assign client' s account");
		btnSearchAndAssign.addActionListener(new SearchAction());
		sl_search_panel.putConstraint(SpringLayout.NORTH, btnSearchAndAssign, 6, SpringLayout.NORTH, search_panel);
		sl_search_panel.putConstraint(SpringLayout.WEST, btnSearchAndAssign, 6, SpringLayout.WEST, search_panel);
		search_panel.add(btnSearchAndAssign);
		
		lblMakeASearch = new JLabel("Make a search on website based on client's data and assign an account to the client");
		sl_search_panel.putConstraint(SpringLayout.NORTH, lblMakeASearch, 6, SpringLayout.SOUTH, btnSearchAndAssign);
		sl_search_panel.putConstraint(SpringLayout.SOUTH, lblMakeASearch, -10, SpringLayout.SOUTH, search_panel);
		lblMakeASearch.setVerticalTextPosition(SwingConstants.TOP);
		lblMakeASearch.setVerticalAlignment(SwingConstants.TOP);
		sl_search_panel.putConstraint(SpringLayout.WEST, lblMakeASearch, 6, SpringLayout.WEST, search_panel);
		sl_search_panel.putConstraint(SpringLayout.EAST, lblMakeASearch, -10, SpringLayout.EAST, search_panel);
		search_panel.add(lblMakeASearch);
		
		JPanel visit_profil_panel = new JPanel();
		add(visit_profil_panel);
		SpringLayout sl_visit_profil_panel = new SpringLayout();
		visit_profil_panel.setLayout(sl_visit_profil_panel);
		
		btnViewProfileOn = new JButton("View Profile on Website");
		btnViewProfileOn.addActionListener(new profileURLAction());
		sl_visit_profil_panel.putConstraint(SpringLayout.NORTH, btnViewProfileOn, 6, SpringLayout.NORTH, visit_profil_panel);
		sl_visit_profil_panel.putConstraint(SpringLayout.WEST, btnViewProfileOn, 6, SpringLayout.WEST, visit_profil_panel);
		visit_profil_panel.add(btnViewProfileOn);
		
		lblNewLabel = new JLabel("View client profile on website");
		sl_visit_profil_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, btnViewProfileOn);
		lblNewLabel.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		sl_visit_profil_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 6, SpringLayout.WEST, visit_profil_panel);
		sl_visit_profil_panel.putConstraint(SpringLayout.SOUTH, lblNewLabel, -10, SpringLayout.SOUTH, visit_profil_panel);
		sl_visit_profil_panel.putConstraint(SpringLayout.EAST, lblNewLabel, -10, SpringLayout.EAST, visit_profil_panel);
		visit_profil_panel.add(lblNewLabel);
	}
	public String getProfileURL() {
		return profileURL;
	}
	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}
	public JButton getBtnSearchAndAssign() {
		return btnSearchAndAssign;
	}
	public JButton getBtnViewProfileOn() {
		return btnViewProfileOn;
	}
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}
	public JLabel getLblMakeASearch() {
		return lblMakeASearch;
	}
	
	public void updateButtonTitle(String provider){
		if(provider != null && !provider.isEmpty()){
			this.getBtnViewProfileOn().setText("View Profile on " + provider);
			this.getBtnSearchAndAssign().setText("Search and assign client' s account on " + provider);
		}
	}
	
	public void updateButtonIcon(ImageIcon img){
		if(img != null ){
			this.getBtnViewProfileOn().setIcon(img);
		}
	}
	@Override
	public void modelPropertyChange(Observable o, Object arg) {
		if(arg != null && arg instanceof Client){
			updatePanel((Client) arg);
		}		
	}
	
	public void disableLink(){
		this.getBtnViewProfileOn().setEnabled(false);
		this.getLblNewLabel().setEnabled(false);		
	}
	
	public void eneableLink(){
		this.getBtnViewProfileOn().setEnabled(true);
		this.getLblNewLabel().setEnabled(true);		
	}
	protected abstract void updatePanel(Client arg);
	
	private class profileURLAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(Desktop.isDesktopSupported()){
				try {
					Desktop.getDesktop().browse(new URI(profileURL));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(me, "Could not open the URL. Please go to the folloeing url manually: \n" +
								profileURL, "Error launching the browser", JOptionPane.ERROR_MESSAGE);
					}
			}
		}
		
	}
	
	private class SearchAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			makeSearchAccountDialog();
		}	
	}
	
	public void makeSearchAccountDialog() {
		
		SearchAccountDialog dialog = new SearchAccountDialog(media_controller);
		dialog.setVisible(true);
	}
}
