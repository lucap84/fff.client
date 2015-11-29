package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.LoginDataRequestDTO;

public class UC09Test {

	@Test
	public void testUC9(){ //Login UTENTE
		SecurityServiceStub securityService = new SecurityServiceStub();
		
		LoginDataRequestDTO loginInput = new LoginDataRequestDTO();
		loginInput.setEmail("lucap84@gmail.com");
		loginInput.setEncodedPassword(DigestUtils.md5Hex("mypassword"));
		
		AuthDataResponseDTO loginOutput = null;
		loginOutput = securityService.login(loginInput, MediaType.APPLICATION_JSON);
		assertNotNull(loginOutput);
		assertTrue(loginOutput.isOk());
		assertNotNull(loginOutput.getUserId());
		assertFalse(loginOutput.getUserId().isEmpty());
		assertNotNull(loginOutput.getServerPublicKey());
		assertFalse(loginOutput.getServerPublicKey().isEmpty());			
		
	}
}
