package com.amos.project4.views;

import java.util.ArrayList;
import java.util.List;

import com.amos.project4.socialMedia.facebook.FacebookDataType;
import com.amos.project4.socialMedia.twitter.TwitterDataType;

public class SocialMediaScanModel extends AbstractViewModel {
	
	private List<FacebookDataType> f_selectedDataTypes;	
	private List<TwitterDataType> t_selectedDataTypes;
	
	public SocialMediaScanModel() {
		super();
		this.f_selectedDataTypes = new ArrayList<FacebookDataType>();
		this.t_selectedDataTypes = new ArrayList<TwitterDataType>();
	}
	public List<FacebookDataType> getF_selectedDataTypes() {
		return f_selectedDataTypes;
	}
	public List<TwitterDataType> getT_selectedDataTypes() {
		return t_selectedDataTypes;
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
}
