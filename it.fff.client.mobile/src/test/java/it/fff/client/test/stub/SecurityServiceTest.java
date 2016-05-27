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

import it.fff.clientserver.common.dto.RegistrationInputDTO;
import it.fff.clientserver.common.dto.ResetPasswordDTO;
import it.fff.clientserver.common.dto.SessionDTO;
import it.fff.clientserver.common.dto.UpdatePasswordDTO;
import it.fff.client.stub.SecurityServiceStub;
import it.fff.clientserver.common.dto.AccountDTO;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.LoginInputDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.enums.UserSexEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityServiceTest extends WebServiceRestTest{
	
	public SecurityServiceTest() {
	}

	@Test
	public void t1_registerUserShouldReturnConfirm(){
		
		RegistrationInputDTO dtoInput = new RegistrationInputDTO();
		dtoInput.setNome("Luca");
		dtoInput.setCognome("Pelosi");
		dtoInput.setSesso(UserSexEnum.M);
		dtoInput.setDataNascita("1984-02-09");
		dtoInput.setEmail("lucap84@gmail.com");
		dtoInput.setEncodedPassword(DigestUtils.md5Hex("mypassword"));

		AuthDataResponseDTO result = null;

		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.registerUser(dtoInput, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
//			assertNotNull(result.getServerPublicKey());
			assertNotNull(result.getUserId());
			assertFalse(result.getUserId()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
		
	}	
	
	
	@Test
	public void t2_logoutShouldReturnConfirm(){
		WriteResultDTO result = null;
		
		int userId = 1;
		String deviceId = "android-123";
		
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.logout(userId, deviceId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}

	}
	
	@Test
	public void t3_loginShouldReturnConfirm(){
		LoginInputDTO loginInfo = new LoginInputDTO();
		loginInfo.setEmail("lucap84@gmail.com");
		loginInfo.setPassword(DigestUtils.md5Hex("mypassword"));
		
		AuthDataResponseDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.login(loginInfo, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getUserId());
			assertFalse(result.getUserId()<=0);
//			assertNotNull(result.getServerPublicKey());
//			assertFalse(result.getServerPublicKey().isEmpty());	
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
		
	}		

	@Test
	public void t4_updatePasswordShouldReturnConfirm(){
		
		UpdatePasswordDTO updatePasswordInput = new UpdatePasswordDTO();
		updatePasswordInput.setUserId(1);
		updatePasswordInput.setEmail("lucap84@gmail.com");
		updatePasswordInput.setOldPassword("mypassword");
		updatePasswordInput.setNewPassword("mypasswordupdated");
		
		WriteResultDTO result = null;
		{//Test JSON
			SecurityServiceStub stub = new SecurityServiceStub();
			result = stub.updatePassword(updatePasswordInput, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
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
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
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
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
	@Test
	public void t7_resetPasswordShouldReturnConfirm(){
		
		ResetPasswordDTO resetPasswordInput = new ResetPasswordDTO();
		resetPasswordInput.setEmail("lucap84@gmail.com");
		resetPasswordInput.setNewPassword("mypasswordresettata");
		resetPasswordInput.setVerificationCode("1234567890");
		
		WriteResultDTO result = null;
		
		SecurityServiceStub stub = new SecurityServiceStub();
		{//Test JSON
			result = stub.resetPassword(resetPasswordInput, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	

	
	public static void main(String[] args) {
		SecurityServiceTest securityServiceTest = new SecurityServiceTest();
		securityServiceTest.t1_registerUserShouldReturnConfirm();
//		securityServiceTest.t2_logoutShouldReturnConfirm();
//		securityServiceTest.t3_loginShouldReturnConfirm();
	}
	
   

}
