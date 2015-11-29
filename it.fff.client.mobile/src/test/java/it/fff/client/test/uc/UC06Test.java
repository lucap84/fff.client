package it.fff.client.test.uc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.EventDTO;

public class UC06Test {
	
	@Test
	public void testUC6(){//Visualizza i miei eventi
		/*
		 * Preconditions: UC1/UC9 + UC3/UC4 
		 */
		
		UC01Test registerTest = new UC01Test();
		registerTest.testUC1();
		
		/*
		 * UC6
		 */
		
		UserServiceStub userService = new UserServiceStub();
		
		String userId = userService.getSecureConfiguration().getUserId();
		List<EventDTO> result = null;

		result = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		assertNotNull(result);
		assertTrue(result.size()>0);
		assertNotNull(result.get(0));
		assertNotNull(result.get(0).getId());
		
		/*
		 * Postconditions:  
		 */
	}
}
