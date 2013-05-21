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
package com.amos.project4.socialMedia.facebook;

import com.amos.project4.socialMedia.MediaConnectInterface;
import com.restfb.DefaultFacebookClient;
import com.restfb.types.User;

public class FacebookConnect implements MediaConnectInterface {
	
	private static String ACCESS_TOKEN = "CAACEdEose0cBAP6kQuLHEaOg7vXkDlMsAm8OWoBGnPvsmmxk88b31iQm3iW2HPjZByTXdUwupyEr7AEJ1XfevuDNf0IGjJuDHhp6ng4PycRsWuLckOBiYkoR80NRaJufsOK90JZCXCCFOF5arSeQAeI311Vd8SGUZBZAltm4qQZDZD";

	private DefaultFacebookClient facebookClient;
	private static FacebookConnect instance;
	
	public static  FacebookConnect getInstance(){
		if(instance == null){
			instance = new FacebookConnect();
		}
		return instance;
	}	

	private FacebookConnect() {
		super();
		init();
	}
	
	public void init(){
		facebookClient = new DefaultFacebookClient(ACCESS_TOKEN);
	}

	@Override
	public boolean login(String token, String secret_token) {
		User user = facebookClient.fetchObject("me", User.class);
		return user != null && user.getId().length() > 0;
	}

	@Override
	public String getAccessUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkAndSetAccessToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
