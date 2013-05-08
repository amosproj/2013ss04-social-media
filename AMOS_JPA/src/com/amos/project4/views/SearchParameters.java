package com.amos.project4.views;

public class SearchParameters extends AbstractViewModel {

	private int search_cat = 1;
	private String search_text;

	public SearchParameters(int search_cat, String search_text) {
		super();
		this.search_cat = search_cat;
		this.search_text = search_text;
	}

	public SearchParameters() {
		this(1, "");
	}

	public void setSearchText(String search_text) {
		this.search_text = search_text;
		this.fireChange("NEW SEARCH", this, this);
	}
	
	public void setSearchCat(int search_cat) {
		this.search_cat = search_cat;
		this.fireChange("NEW SEARCH", this, this);
	}

	public String getSearch_text() {
		if (search_text == null || search_text.equalsIgnoreCase("")) {
			return "%";
		}
		return search_text;
	}
	
	public int getSearch_cat() {
		return search_cat;
	}
}
