package it.fff.client.test.uc;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC03_Logout_Test {
	
	@Test
	public void test(){//Logout
		/*
		 * Preconditions:  Login (oppure registrazione)
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
