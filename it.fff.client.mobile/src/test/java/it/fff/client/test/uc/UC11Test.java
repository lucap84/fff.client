package it.fff.client.test.uc;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC11Test {
	
	@Test
	public void testUC11(){//Logout
		/*
		 * Preconditions:  UC9 Login (oppure UC1)
		 */
		
		UC09Test loginTest = new UC09Test();
		loginTest.testUC9();
		
		/*
		 * UC3
		 */
		SecurityServiceStub securityService = new SecurityServiceStub();

		WriteResultDTO result = null;
		result = securityService.logout(MediaType.APPLICATION_JSON);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertTrue(result.getAffectedRecords()>0);
		assertNotNull(result.getIdentifier());
		assertFalse(result.getIdentifier().isEmpty());
		
		/*
		 * Postconditions:  User and device are disconnected
		 */
		
		assertNotEquals("", securityService.getSecureConfiguration().getUserId());
		assertNotEquals("", securityService.getSecureConfiguration().getDeviceId());
		assertEquals("", securityService.getSecureConfiguration().getSharedKey());
		
	}
}
