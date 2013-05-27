package com.amos.project4.controllers;

import java.util.Observable;

import com.amos.project4.socialMedia.facebook.FacebookDataRetriever;
import com.amos.project4.socialMedia.twitter.TwitterDataRetriever;

public class SocialMediaScanController extends AbstractController{
	
	private FacebookDataRetriever f_retriever;
	private TwitterDataRetriever t_retriever;
	
	public SocialMediaScanController() {
		super();
		this.f_retriever = FacebookDataRetriever.getInstance();
		this.t_retriever = TwitterDataRetriever.getInstance();		
	}

	@Override
	public void updateInternally(Object arg, Observable o) {		
	}

	public FacebookDataRetriever getF_retriever() {
		return f_retriever;
	}

	public TwitterDataRetriever getT_retriever() {
		return t_retriever;
	}

}
