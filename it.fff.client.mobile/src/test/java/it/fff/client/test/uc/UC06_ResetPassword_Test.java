package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.ResetPasswordDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC06_ResetPassword_Test {
	
	@Test
	public void test(){//Reset password (password dimenticata)
		/*
		 * Preconditions:  registered but no logged
		 */
		
		String email = "lucap84@gmail.com";
		
		SecurityServiceStub securityService = new SecurityServiceStub();

		WriteResultDTO resultSendVerificationCode = null;
		{//Test JSON
			resultSendVerificationCode = securityService.sendVerificationCode(email, MediaType.APPLICATION_JSON);
			assertNotNull(resultSendVerificationCode);
			assertTrue(resultSendVerificationCode.isOk());
		}
		
		String verificationCode = "1947307542";//preso dalla mail
		
		WriteResultDTO resultCheckVerificationCode = null;
		{//Test JSON
			resultCheckVerificationCode = securityService.checkVerificationCode(email, verificationCode, MediaType.APPLICATION_JSON);
			assertNotNull(resultCheckVerificationCode);
			assertTrue(resultCheckVerificationCode.isOk());
			assertTrue(resultCheckVerificationCode.getAffectedRecords()>0);
			assertNotNull(resultCheckVerificationCode.getIdentifier());
			assertFalse(resultCheckVerificationCode.getIdentifier()<=0);
		}		
		
		//Se ho passato gli assert allora ci sono record modificati e il codice di verifica e' corretto (ho marcato come verificato l'account)
		
		ResetPasswordDTO resetPasswordInput = new ResetPasswordDTO();
		resetPasswordInput.setEmail(email);
		resetPasswordInput.setNewPassword("mypasswordresettata");
		resetPasswordInput.setVerificationCode(verificationCode);
		
		WriteResultDTO resultResetPassword = null;
		{//Test JSON
			resultResetPassword = securityService.resetPassword(resetPasswordInput, MediaType.APPLICATION_JSON);
			assertNotNull(resultResetPassword);
			assertTrue(resultResetPassword.isOk());
			assertTrue(resultResetPassword.getAffectedRecords()>0);
			assertNotNull(resultResetPassword.getIdentifier());
			assertFalse(resultResetPassword.getIdentifier()<=0);
		}
		
		/*
		 * Postconditions:  E' possibile fare login con email e password nuova
		 */
		
		
	}
}
