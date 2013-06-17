package com.amos.project4.views.accountSearch;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class AccountSearchList<T extends AccountSearchResultItem> extends JList<T>{

	private static final long serialVersionUID = 1L;
	private AccountSearchResult<T> searchResult;
	
	private AccountSearchListModel<T> model;
	private AccountSearchList<T> me;
	
	public AccountSearchList(AccountSearchResult<T> searchResult){
		super();
		this.setSearchResult(searchResult);
		if(searchResult != null){
			this.model =  new AccountSearchListModel<T>(searchResult);
			this.setModel(model);
		}
		this.setCellRenderer(new AccountSearchListRenderer<T>());
		me = this;
		this.addMouseListener(new dblClicAdapter());
	}
	
	public AccountSearchResult<?> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(AccountSearchResult<T> searchResult) {
		this.searchResult = searchResult;
	}
	
	public void update(){
		this.model.fireContentsChanged(model, 0, model.getSize() -1);
	}
	
	private class dblClicAdapter extends MouseAdapter {
	    public void mouseClicked(MouseEvent evt) {
	    	if(evt.getSource() instanceof AccountSearchList){	    	
		        if (evt.getClickCount() == 2) {
		        	AccountSearchResultItem item = getSelectedValue();
		        	if(item != null && Desktop.isDesktopSupported()){
		        		String profileURL = item.getProfileURL();
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
	    }
	}

}
