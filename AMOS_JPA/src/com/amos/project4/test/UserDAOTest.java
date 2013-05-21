/*
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
package com.amos.project4.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amos.project4.models.User;
import com.amos.project4.models.UserDAO;
import com.amos.project4.utils.AMOSUtils;

public class UserDAOTest {
	
	private UserDAO uDAO;
	private ArrayList<Integer> ID_list = new ArrayList<Integer>();
	private int before_count;
	private int after_count;
	@Before
	public void setUp() throws Exception {
		uDAO = UserDAO.getUserDAOInstance();
		List<User> users;
		users = uDAO.getAllUsers();
		before_count = users.size();
		// Do we have entries?
		boolean createNewEntries = (users.size() < 20);

		// No, so lets create new entries
		if (createNewEntries) {
			for (int i = 0; i < 20 - users.size(); i++) {
				User user = new User();
				user.setUsername("Jupiter_" + i);
				user.setUsermail("jupiterbak_" +i+ "@yahoo.fr");
				// hash the Password				
				user.setUserpass(AMOSUtils.makeMD5("Jupiter_" + i));
				uDAO.addUser(user);
				ID_list.add(user.getID());
			}
		}		
		after_count = before_count + ID_list.size();
	}
	
	

	@Test
	public void checkAvailableUsers() {
		List<User> users;
		users = uDAO.getAllUsers();
		assertTrue(users.size() >= 20);
	}
	
	@Test	
	public void TestGetUserByID(){
		Integer ID = ID_list.get(((int) (Math.random() * ID_list.size())));
		User user = uDAO.getUserByID(ID);
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID));
	}
	
	@Test
	public void TestGetUserByUsername(){
		Integer ID = (int) (Math.random() * ID_list.size());
		User user = uDAO.getUserByUsername("Jupiter_" + ID);
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID_list.get(ID)));
	}
	
	@Test
	public void TestGetUserByUsermail(){
		Integer ID = (int) (Math.random() * ID_list.size());
		User user = uDAO.getUserByMail("jupiterbak_" + ID + "@yahoo.fr");
		assertTrue( user != null );
		assertTrue(user.getID().equals(ID_list.get(ID)));
	}
	
	@Test
	public void TestUpdateUser(){
		Integer ID = ID_list.get(((int) (Math.random() * ID_list.size())));
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
		
		int count = (int) (Math.random() * ID_list.size());
		
		for (int i = 0; i < count; i++) {
			Integer ID = ID_list.get(((int) (Math.random() * ID_list.size())));
			User user = uDAO.getUserByID(ID);
			uDAO.deleteUser(user);
			ID_list.remove(ID);
		}
		List<User> users;
		users = uDAO.getAllUsers();
		assertTrue(users.size() == after_count - count);
	}

	@After
	public void test() {
		for(Integer id : ID_list){
			User u = uDAO.getUserByID(id);
			uDAO.deleteUser(u);
		}		
	}

}
