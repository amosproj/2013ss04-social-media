package com.amos.project4.socialMedia.Xing;

import java.util.ArrayList;
import java.util.List;

import com.amos.project4.socialMedia.AccountSearchResultInterface;
import com.amos.project4.socialMedia.AccountSearchResultItem;

public class XingAccountSearchResult implements AccountSearchResultInterface{
	
	List<AccountSearchResultItem> list;
	public List<AccountSearchResultItem> getList() {
		return list;
	}
	public XingAccountSearchResult() {
		super();
		list = new ArrayList<AccountSearchResultItem>();
	}
	public void setList(List<AccountSearchResultItem> list) {
		this.list = list;
	}

	@Override
	public long getNumResults() {
		return list.size();
	}

	@Override
	public List<AccountSearchResultItem> getValues() {
		return list;
	}

}
