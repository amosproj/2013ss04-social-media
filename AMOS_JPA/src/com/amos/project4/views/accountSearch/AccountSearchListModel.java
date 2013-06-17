package com.amos.project4.views.accountSearch;

import javax.swing.AbstractListModel;

import com.amos.project4.socialMedia.AccountSearchResult;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class AccountSearchListModel<T extends AccountSearchResultItem> extends AbstractListModel<T>{

	private static final long serialVersionUID = 1L;
	private AccountSearchResult<?> searchResult;
	public AccountSearchListModel(AccountSearchResult<?> searchResult){
		this.setSearchResult(searchResult);
	}
	@Override
	public int getSize() {
		return searchResult.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getElementAt(int index) {
		return (T) searchResult.get(index);
	}
	
	public AccountSearchResult<?> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(AccountSearchResult<?> searchResult) {
		this.searchResult = searchResult;
	}
	@Override
	protected void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}
	
}
