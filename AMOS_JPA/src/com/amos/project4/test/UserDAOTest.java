package com.amos.project4.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.utils.AMOSUtils;

public class UserDAOTest {
	
	private UserDAO uDAO;
	
	@Before
	public void setUp() throws Exception {
		uDAO = UserDAO.getUserDAOInstance();
		List<User> users;
		users = uDAO.getAllUsers();
		// Do we have entries?
		boolean createNewEntries = (users.size() < 10);

		// No, so lets create new entries
		if (createNewEntries) {
			for (int i = 0; i < 10 - users.size(); i++) {
				User user = new User();
				user.setUsername("Jupiter_" + i);
				user.setUsermail("jupiterbak_" +i+ "@yahoo.fr");
				// hash the Password				
				user.setUserpass(AMOSUtils.makeMD5("Jupiter_" + i));
				uDAO.addUser(user);
			}
		}
	}
	
	

	@Test
	public void checkAvailableUsers() {
		List<User> users;
		users = uDAO.getAllUsers();
		assertTrue(users.size() == 10);
	}
	
	@Test	
	public void TestGetUserByID(){
		Integer ID = new Integer(115);//((int) (Math.random() * 10));
		User user = uDAO.getUserByID(ID);
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID));
	}
	
	@Test
	public void TestGetUserByUsername(){
		Integer ID = new Integer(115);//(int) (Math.random() * 10));
		User user = uDAO.getUserByUsername("Jupiter_3"); // + ID);
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID));
	}
	
	@Test
	public void TestGetUserByUsermail(){
		Integer ID = new Integer(115);//(int) (Math.random() * 10));
		User user = uDAO.getUserByMail("jupiterbak_3@yahoo.fr");
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID));
	}
	
	@Test
	public void TestUpdateUser(){
		Integer ID = new Integer(115);//(int) (Math.random() * 10));
		User user = uDAO.getUserByID(ID);
		try {
			user.setUserpass(AMOSUtils.makeMD5("TEST_USER_FOR_ID_" + ID));
		} catch (NoSuchAlgorithmException e) {
			fail("Error while hashing");
		}
		uDAO.updateUser(user);
		
		User _user = uDAO.getUserByID(ID);
		assertTrue( user.equals(_user));
		
	}

	@Test
	public void deleteUser() {
		
//		int count = (int) (Math.random() * 10);
//		
//		for (int i = 0; i < count; i++) {
//			Integer ID = new Integer((int) (Math.random() * 10));
//			User user = uDAO.getUserByID(ID);
//			uDAO.deleteUser(user);
//		}
//		List<User> users;
//		users = uDAO.getAllUsers();
//		assertTrue(users.size() == 10 - count);
	}

//	@After
//	public void test() {
//		for(User u: uDAO.getAllUsers()){
//			uDAO.deleteUser(u);
//		}
//		
//	}

}
