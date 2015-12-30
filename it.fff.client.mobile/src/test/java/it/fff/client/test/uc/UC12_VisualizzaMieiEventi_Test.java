package it.fff.client.test.uc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.EventDTO;

public class UC12_VisualizzaMieiEventi_Test {
	
	@Test
	public void test(){//Visualizza i miei eventi
		/*
		 * Preconditions: user registered and logged 
		 */
		
//		UC01_RegistraUtente_Test registerTest = new UC01_RegistraUtente_Test();
//		registerTest.test();
		
		/*
		 * UC6
		 */
		
		UserServiceStub userService = new UserServiceStub();
		
		int userId = Integer.valueOf(userService.getSecureConfiguration().getUserId());
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
