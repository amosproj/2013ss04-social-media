package com.amos.project4.socialMedia;

public interface MediaConnectInterface {

	/**
	 * Login the specified user into the Twitter platform
	 * @param secret_token TODO
	 * @param username
	 * @param password
	 * @return true if successful
	 */
	public abstract boolean login(String token, String secret_token);

	public abstract String getAccessUrl();
	
	public boolean checkAndSetAccessToken(String token);
	
	public String[] getAccessToken();

}