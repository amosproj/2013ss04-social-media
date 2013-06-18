package com.amos.project4.socialMedia.twitter;

import java.util.List;

import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class TwitterAccountSearchResult implements AccountSearchResultInterface {
	List<AccountSearchResultItem> list;
	long NumResults;
	
	public List<AccountSearchResultItem> getList() {
		return list;
	}

	public void setList(List<AccountSearchResultItem> list) {
		this.list = list;
	}

	public void setNumResults(long numResults) {
		NumResults = numResults;
	}

	@Override
	public long getNumResults() {
		return NumResults > list.size()?NumResults:list.size();
	}

	@Override
	public List<AccountSearchResultItem> getValues() {
		return list;
	}

	public TwitterAccountSearchResult(List<AccountSearchResultItem> list) {
		super();
		this.list = list;
	}

}
