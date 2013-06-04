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
package com.amos.project4.controllers;

import java.util.Observable;

import com.amos.project4.socialMedia.LinkedIn.LinkedInDataRetriever;
import com.amos.project4.socialMedia.Xing.XingDataRetriever;
import com.amos.project4.socialMedia.facebook.FacebookDataRetriever;
import com.amos.project4.socialMedia.twitter.TwitterDataRetriever;

public class SocialMediaScanController extends AbstractController{
	
//	private FacebookDataRetriever f_retriever;
//	private TwitterDataRetriever t_retriever;
//	private XingDataRetriever x_retriever;
	
	
	public SocialMediaScanController() {
		super();
//		this.f_retriever = FacebookDataRetriever.getInstance();
//		this.t_retriever = TwitterDataRetriever.getInstance();	
//		this.x_retriever = XingDataRetriever.getInstance();	
	}

	@Override
	public void updateInternally(Object arg, Observable o) {		
	}

	public FacebookDataRetriever getF_retriever() {
		return FacebookDataRetriever.getInstance();
	}

	public TwitterDataRetriever getT_retriever() {
		return TwitterDataRetriever.getInstance();
	}

	public XingDataRetriever getX_retriever() {
		return XingDataRetriever.getInstance();
	}
	public LinkedInDataRetriever getL_retriever() {
		return LinkedInDataRetriever.getInstance();
	}

}
