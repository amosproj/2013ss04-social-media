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
package com.amos.project4.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.amos.project4.models.XingData;
import com.amos.project4.socialMedia.LinkedIn.LinkedInDataType;
import com.amos.project4.socialMedia.Xing.XingDataType;
import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaScanModel extends AbstractViewModel {
	
	private List<FacebookDataType> f_selectedDataTypes;	
	private List<TwitterDataType> t_selectedDataTypes;
	private List<XingDataType> x_selectedDataTypes;
	private List<LinkedInDataType> l_selectedDataTypes;
	
	public SocialMediaScanModel() {
		super();
		this.f_selectedDataTypes = new ArrayList<FacebookDataType>();
		this.t_selectedDataTypes = new ArrayList<TwitterDataType>();
		this.x_selectedDataTypes = new ArrayList<XingDataType>();
		this.l_selectedDataTypes = new ArrayList<LinkedInDataType>();
	}
	
	public List<FacebookDataType> getF_selectedDataTypes() {
		return f_selectedDataTypes;
	}
	public List<TwitterDataType> getT_selectedDataTypes() {
		return t_selectedDataTypes;
	}
	
	public List<XingDataType> getX_selectedDataTypes() {
		return x_selectedDataTypes;
	}

	public List<LinkedInDataType> getL_selectedDataTypes() {
		return l_selectedDataTypes;
	}

	public void addSelectedFacebookDataType(FacebookDataType type){
		List<FacebookDataType> f_selectedDataTypes_ = new ArrayList<FacebookDataType>(this.f_selectedDataTypes);
		if(!this.f_selectedDataTypes.contains(type)){
			this.f_selectedDataTypes.add(type);
		}
		fireChange("DATATYPE_CHANGE", f_selectedDataTypes_, this.f_selectedDataTypes);
	}
	
	public void removeSelectedFacebookDataType(FacebookDataType type){
		this.f_selectedDataTypes.remove(type);
		fireChange("DATATYPE_CHANGE", this.f_selectedDataTypes, this.f_selectedDataTypes);
	}
	
	public void addSelectedTwitterDataType(TwitterDataType type){
		List<TwitterDataType> f_selectedDataTypes_ = new ArrayList<TwitterDataType>(this.t_selectedDataTypes);
		if(!this.t_selectedDataTypes.contains(type)){
			this.t_selectedDataTypes.add(type);
		}
		fireChange("DATATYPE_CHANGE", f_selectedDataTypes_, this.t_selectedDataTypes);
	}
	
	public void removeSelectedTwitterDataType(TwitterDataType type){
		this.t_selectedDataTypes.remove(type);
		fireChange("DATATYPE_CHANGE", this.f_selectedDataTypes, this.f_selectedDataTypes);
	}
	
	public void addSelectedXingDataType(XingDataType type){
		List<XingDataType> f_selectedDataTypes_ = new ArrayList<XingDataType>(this.x_selectedDataTypes);
		if(!this.x_selectedDataTypes.contains(type)){
			this.x_selectedDataTypes.add(type);
		}
		fireChange("DATATYPE_CHANGE", f_selectedDataTypes_, this.x_selectedDataTypes);
	}
	
	public void removeSelectedXingDataType(XingDataType type){
		List<XingDataType> f_selectedDataTypes_ = new ArrayList<XingDataType>(this.x_selectedDataTypes);
		this.x_selectedDataTypes.remove(type);
		fireChange("DATATYPE_CHANGE", f_selectedDataTypes_, this.x_selectedDataTypes);
	}
	
	public void addSelectedLinkedInDataType(LinkedInDataType type){
		List<LinkedInDataType> l_selectedDataTypes_ = new ArrayList<LinkedInDataType>(this.l_selectedDataTypes);
		if(!this.l_selectedDataTypes.contains(type)){
			this.l_selectedDataTypes.add(type);
		}
		fireChange("DATATYPE_CHANGE",l_selectedDataTypes_, this.l_selectedDataTypes);
	}
	
	public void removeSelectedLinkedInDataType(LinkedInDataType type){
		List<LinkedInDataType> l_selectedDataTypes_ = new ArrayList<LinkedInDataType>(this.l_selectedDataTypes);
		this.l_selectedDataTypes.remove(type);
		fireChange("DATATYPE_CHANGE",l_selectedDataTypes_, this.l_selectedDataTypes);
	}
}
