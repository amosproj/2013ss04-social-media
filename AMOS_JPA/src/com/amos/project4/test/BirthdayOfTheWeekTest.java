package com.amos.project4.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amos.project4.models.BirthdayClient;
import com.amos.project4.models.ClientDAO;

public class BirthdayOfTheWeekTest {
	private ClientDAO cDAO;

	@Before
	public void setUp() throws Exception {
		cDAO = ClientDAO.getInstance();
	}

	@Test
	public void checkBirthDayOfTheWeek() {
		List<BirthdayClient> clients = cDAO.getAllBirthdayOfTheWeek();
		for(BirthdayClient c: clients){
			System.out.println(c.getID() + " " + c.getFirstname() + " " + c.getName() + " " + c.getBirthdate());
		}
		assertTrue(clients.size() > 0);
	}
}
