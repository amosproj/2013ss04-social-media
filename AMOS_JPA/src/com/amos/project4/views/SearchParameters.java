package com.amos.project4.views;

import com.amos.project4.models.AbstractViewModel;

public class SearchParameters extends AbstractViewModel {

	public SearchParameters() {
		super();
	}
	public SearchParameters(int search_cat, String search_text) {
		super();
		this.search_cat = search_cat;
		this.search_text = search_text;
	}
	private int search_cat = 1;
	private String search_text ;
	
	public int getSearch_cat() {
		return search_cat;
	}
	public void setSearchParameters(int search_cat,String search_text) {
		this.search_cat = search_cat;
		this.search_text = search_text;
		this.firePropertyChange("NEW SEARCH", this, this);
	}
	public String getSearch_text() {
		if(search_text == null || search_text.equalsIgnoreCase("")){
			return "%";
		}
		return search_text;
	}
}
