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
import it.fff.clientserver.common.dto.SessionDTO;

public class UC02_Login_Test {

	@Test
	public void test(){ //Login UTENTE
		/*
		 * Preconditions:  not logged
		 */
		SecurityServiceStub securityService = new SecurityServiceStub();
		
//		LoginDataRequestDTO loginInput = new LoginDataRequestDTO();
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setEmail("lucap84@gmail.com");
		accountDTO.setPassword(DigestUtils.md5Hex("mypassword"));
		
		SessionDTO sessionToCreate = new SessionDTO();
		sessionToCreate.setAccount(accountDTO);
		
		AuthDataResponseDTO loginOutput = null;
		loginOutput = securityService.login(sessionToCreate, MediaType.APPLICATION_JSON, true);
		assertNotNull(loginOutput);
		assertTrue(loginOutput.isOk());
		assertNotNull(loginOutput.getUserId());
		assertFalse(loginOutput.getUserId().isEmpty());
		assertNotNull(loginOutput.getServerPublicKey());
		assertFalse(loginOutput.getServerPublicKey().isEmpty());			
		
	}
}
