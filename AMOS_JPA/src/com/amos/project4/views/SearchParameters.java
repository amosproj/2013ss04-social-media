/*
 * 
 * This file is part of the software project "Social Media and Datev".
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
