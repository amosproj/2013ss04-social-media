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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class SearchAccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private AccountSearchList<AccountSearchResultItem> searchResultlist;
	private AccountSearchResult<AccountSearchResultItem> search_result;
	private JLabel lblStatus;

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
	
	public SearchAccountDialog(AccountSearchResult<AccountSearchResultItem> search_result) {
		super();
		this.search_result = search_result;
		init();
	}

	private void init() {
		setTitle("AMOS Project 4 : Account Search Results");
		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				searchResultlist = new AccountSearchList<AccountSearchResultItem>(search_result);
				scrollPane.setViewportView(searchResultlist);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				lblStatus = new JLabel("0 - 0 of 0 Results");
				lblStatus.setHorizontalAlignment(SwingConstants.TRAILING);
				lblStatus.setPreferredSize(new Dimension(200, 14));
				lblStatus.setSize(new Dimension(200, 0));
				buttonPane.add(lblStatus);
			}
			{
				JButton btnStart = new JButton("<<");
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
				JButton btnPrevious = new JButton("<");
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
				JButton btnNext = new JButton(">");
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
				JButton btnEnd = new JButton(">>");
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
				JButton cancelButton = new JButton("Cancel");
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
		initStatusLabel();
	}

	public AccountSearchList<AccountSearchResultItem> getSearchResultlist() {
		return searchResultlist;
	}
	
	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	private void initStatusLabel(){
		if(search_result != null){
			int current_page = search_result.getPageIndex();
			int total = search_result.getResultCount();
			int count_begin = (search_result.getPageCount() * current_page);
			int count_end = (search_result.getPageCount() * current_page) + search_result.size();
			String text = count_begin + " - " + count_end + " of " + total + " Results";
			getLblStatus().setText(text );
		}
	}
	
	private void updateUI(){
		getSearchResultlist().update();
		initStatusLabel();
		this.repaint();
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
	
}
