package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.UpdatePasswordDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC05_CambioPassword_Test {

	@Test
	public void test(){//Cambio password (conoscendo la vecchia password)
		/*
		 * Preconditions:  login / registrazione
		 */
		SecurityServiceStub securityService = new SecurityServiceStub();
		
		UpdatePasswordDTO updatePasswordInput = new UpdatePasswordDTO();
		updatePasswordInput.setUserId(Integer.valueOf(securityService.getSecureConfiguration().getUserId()));
		updatePasswordInput.setEmail("lucap84@gmail.com");
		updatePasswordInput.setOldPassword("mypassword");
		updatePasswordInput.setNewPassword("mypasswordupdated");
		
		WriteResultDTO result = null;
		{//Test JSON
			result = securityService.updatePassword(updatePasswordInput, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
		}		
		
		/*
		 * Postconditions:  
		 */
	}
}
