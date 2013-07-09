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
package com.amos.project4.views.accountSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.amos.project4.controllers.MediaSearchController;
import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class SearchAccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private AccountSearchList<AccountSearchResultItem> searchResultlist;
	private AccountSearchResult<AccountSearchResultItem> search_result;
	private MediaSearchController media_controller;
	
	private JLabel lblStatus;
	private JButton btnStart;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnEnd;
	private JButton cancelButton;
	private JButton btnView;
	private JButton btnSelect;
	private SearchAccountDialog me;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SearchAccountDialog dialog = new SearchAccountDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SearchAccountDialog() {
		init();
	}
	
	public SearchAccountDialog(MediaSearchController media_controller, JFrame frame) {
		super(frame);
		this.media_controller = media_controller;
		init();
		searchAndInitList();
		me = this;
	}

	private void init() {
		setTitle("AMOS Project 4 : Account Search Results");
		setBounds(100, 100, 620, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnStart = new JButton("<<");
				btnStart.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						first();						
					}
				});
				btnStart.setToolTipText("Start");
				buttonPane.add(btnStart);
			}
			{
				btnPrevious = new JButton("<");
				btnPrevious.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						previous();						
					}
				});
				btnPrevious.setToolTipText("Previous");
				buttonPane.add(btnPrevious);
			}
			{
				btnNext = new JButton(">");
				btnNext.setToolTipText("Next");
				btnNext.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						next();						
					}
				});
				buttonPane.add(btnNext);
			}
			{
				btnEnd = new JButton(">>");
				btnEnd.setToolTipText("End");
				btnEnd.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						last();						
					}
				});
				buttonPane.add(btnEnd);
			}
			{
				lblStatus = new JLabel("Search ...");
				lblStatus.setPreferredSize(new Dimension(170, 14));
				lblStatus.setSize(new Dimension(170, 0));
				buttonPane.add(lblStatus);
			}
			{
				btnView = new JButton("View");
				btnView.setEnabled(false);
				btnView.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						openViewOnWebsite();
					}
				});
				btnView.setToolTipText("View profile on website");
				buttonPane.add(btnView);
			}
			{
				btnSelect = new JButton("Select");
				btnSelect.setEnabled(false);
				btnSelect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveSelectedAccount();
					}
				});
				btnSelect.setToolTipText("Select account");
				buttonPane.add(btnSelect);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();						
					}
				});
				buttonPane.add(cancelButton);
			}
			
		}
		updateUI();
	}
	
	private void searchAndInitList(){
		SwingWorker<String, String> worker = new SwingWorker<String, String>() {

			@Override
			protected String doInBackground() throws Exception {
				publish("Seach");
				if(media_controller != null){
					search_result = media_controller.getAccountSearchresult();
					searchResultlist = new AccountSearchList<AccountSearchResultItem>(search_result);
				}else{
					publish("Error");
				}
				return null;
			}

			@Override
			protected void process(List<String> chunks) {
				if(chunks != null && !chunks.isEmpty()){
					initStatusLabel(chunks.get(0).equalsIgnoreCase("Error"));
				}
			}

			@Override
			protected void done() {
				if(searchResultlist != null && scrollPane != null){
					searchResultlist.addListSelectionListener(new AccountListSelectionListener());
					scrollPane.setViewportView(searchResultlist);
					initStatusLabel(false);
				}else{
					initStatusLabel(true);
				}
				
			}			
		};
		worker.execute();
	}

	public AccountSearchList<AccountSearchResultItem> getSearchResultlist() {
		return searchResultlist;
	}
	
	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	private synchronized void initStatusLabel(boolean error){
		if(error){
			getLblStatus().setText("Error !");
			getLblStatus().setForeground(Color.RED);
			return;
		}
		if(search_result != null ){
			int total = search_result.getResultCount();
			int count_begin = search_result.size() > 0 ?search_result.getStart()+1:search_result.getStart();
			int count_end = search_result.size() > 0 ? search_result.getEnd()+1:search_result.getEnd();
			String text = count_begin + " - " + count_end + " of " + total + " Results";
			getLblStatus().setText(text );
		}else{
			getLblStatus().setText("Please wait ...");
		}
	}
	
	private void updateUI(){
		if(search_result != null) getSearchResultlist().update();
		initStatusLabel(false);
		this.repaint();
		// Next
		this.btnNext.setEnabled(search_result != null && search_result.isNextPageAvailable());
		// Previous
		this.btnPrevious.setEnabled(search_result != null && search_result.isPreviousPageAvailable());
		// first
		this.btnStart.setEnabled(search_result != null && !this.search_result.isFirstPage());
		// last
		this.btnEnd.setEnabled(search_result != null && !this.search_result.isLastPage());
		
	}
	
	private void next(){
		if(search_result != null && search_result.isNextPageAvailable() && search_result.nextPage()){
			updateUI();
		}
	}
	
	private void previous(){
		if(search_result != null && search_result.isPreviousPageAvailable() && search_result.previousPage()){
			updateUI();
		}
	}
	
	private void first(){
		if(search_result != null && !search_result.isFirstPage()){
			search_result.gotoPage(0);
			updateUI();
		}
	}
	
	private void last(){
		if(search_result != null && !search_result.isLastPage()){
			search_result.gotoPage(search_result.getPageCount()-1);
			updateUI();
		}
	}
	
	private void saveSelectedAccount(){
		AccountSearchResultItem item = searchResultlist.getSelectedValue();
    	if(item != null && Desktop.isDesktopSupported()){
    		String ID = item.getAccountKey();
    		try {
				if(ID != null && ID.length() > 0 && media_controller != null){
					media_controller.setSelectedClientAccount(ID);
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(me, "Could not the selected Account for the user. ACCOUNT ID : " +
						ID, "Error launching the browser", JOptionPane.ERROR_MESSAGE);
			}
		}
    	dispose();
	}
	
	private void openViewOnWebsite(){
		AccountSearchResultItem item = searchResultlist.getSelectedValue();
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
	
	private class AccountListSelectionListener implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent arg0) {
			AccountSearchResultItem item = searchResultlist.getSelectedValue();
	    	if(item != null && Desktop.isDesktopSupported()){
	    		btnView.setEnabled(true);
	    		btnSelect.setEnabled(true);
			}
		}
	}
}
