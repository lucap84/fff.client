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

		int userId = Integer.valueOf(securityService.getSecureConfiguration().getUserId());
		String deviceId = securityService.getSecureConfiguration().getDeviceId();
		
		WriteResultDTO result = null;
		result = securityService.logout(userId, deviceId, MediaType.APPLICATION_JSON);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertTrue(result.getAffectedRecords()>0);
		assertNotNull(result.getIdentifier());
		assertFalse(result.getIdentifier()<=0);
		
		/*
		 * Postconditions:  User and device are disconnected
		 */
		
		assertNotEquals("", userId);
		assertNotEquals("", deviceId);
		assertEquals("", securityService.getSecureConfiguration().getSharedKey());
		
	}
}
