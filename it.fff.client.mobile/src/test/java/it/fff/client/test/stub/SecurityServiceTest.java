package it.fff.client.test.stub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import it.fff.clientserver.common.dto.RegistrationDataRequestDTO;
import it.fff.clientserver.common.dto.SessionDTO;
import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.AccountDTO;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityServiceTest extends WebServiceRestTest{
	
	public SecurityServiceTest() {
	}

	@Test
	public void t1_registerUserShouldReturnConfirm(){
		
		RegistrationDataRequestDTO dtoInput = new RegistrationDataRequestDTO();
		dtoInput.setNome("Luca");
		dtoInput.setCognome("Pelosi");
		dtoInput.setSesso("M");
		dtoInput.setDataNascita("1984-02-09");
		dtoInput.setEmail("lucap84@gmail.com2");
		dtoInput.setEncodedPassword(DigestUtils.md5Hex("mypassword"));

		AuthDataResponseDTO result = null;

		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.registerUser(dtoInput, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getServerPublicKey());
			assertNotNull(result.getUserId());
			assertFalse(result.getUserId().isEmpty());
		}
		
	}	
	
	
	@Test
	public void t2_logoutShouldReturnConfirm(){
		WriteResultDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.logout(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}

	}
	
	@Test
	public void t3_loginShouldReturnConfirm(){
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setEmail("lucap84@gmail.com");
		accountDTO.setPassword(DigestUtils.md5Hex("mypassword"));
		
		SessionDTO sessionToCreate = new SessionDTO();
		sessionToCreate.setAccount(accountDTO);
		
		AuthDataResponseDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.login(sessionToCreate, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getUserId());
			assertFalse(result.getUserId().isEmpty());
			assertNotNull(result.getServerPublicKey());
			assertFalse(result.getServerPublicKey().isEmpty());			
		}
		
	}		

	@Test
	public void t4_updatePasswordShouldReturnConfirm(){
		String email = "lucap84@gmail.com";
		String oldPassword = "mypassword";
		String newPassword = "mypasswordUpdated";
		WriteResultDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.updatePassword(email, oldPassword, newPassword, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void t5_sendVerificationCodeShouldReturnConfirm(){
		String email = "lucap84@gmail.com";
		WriteResultDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.sendVerificationCode(email, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void t6_checkVerificationCodeShouldReturnConfirm(){
		String email = "lucap84@gmail.com";
		String verificationCode = "123456789";
		WriteResultDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.checkVerificationCode(email, verificationCode, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	

	
	public static void main(String[] args) {
		SecurityServiceTest securityServiceTest = new SecurityServiceTest();
		securityServiceTest.t1_registerUserShouldReturnConfirm();
//		securityServiceTest.t2_logoutShouldReturnConfirm();
//		securityServiceTest.t3_loginShouldReturnConfirm();
	}
	
   

}
