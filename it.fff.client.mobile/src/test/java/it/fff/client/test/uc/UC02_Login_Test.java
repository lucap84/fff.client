package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.AccountDTO;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.LoginInputDTO;
import it.fff.clientserver.common.dto.SessionDTO;

public class UC02_Login_Test {

	@Test
	public void test(){ //Login UTENTE
		/*
		 * Preconditions:  not logged
		 */
		SecurityServiceStub securityService = new SecurityServiceStub();
		
		LoginInputDTO loginInfo = new LoginInputDTO();
		loginInfo.setEmail("lucap84@gmail.com");
		loginInfo.setPassword(DigestUtils.md5Hex("mypassword"));
		
		AuthDataResponseDTO loginOutput = null;
		loginOutput = securityService.login(loginInfo, MediaType.APPLICATION_JSON);
		assertNotNull(loginOutput);
		assertTrue(loginOutput.isOk());
		assertNotNull(loginOutput.getUserId());
		assertFalse(loginOutput.getUserId().isEmpty());
		assertNotNull(loginOutput.getServerPublicKey());
		assertFalse(loginOutput.getServerPublicKey().isEmpty());			
		
	}
}
