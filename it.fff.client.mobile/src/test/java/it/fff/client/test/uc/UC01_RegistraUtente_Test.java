package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.client.stub.StubService;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.RegistrationInputDTO;

public class UC01_RegistraUtente_Test {
	
	@Test
	public void test(){ //Registra Utente
		/*
		 * Preconditions:  not logged
		 */
		
		SecurityServiceStub securityService = new SecurityServiceStub();
		
		RegistrationInputDTO input1 = new RegistrationInputDTO();
		input1.setNome("Luca");
		input1.setCognome("Pelosi");
		input1.setSesso("M");
		input1.setDataNascita("1984-02-09");
		input1.setEmail("lucap84@gmail.com2");
		input1.setEncodedPassword(DigestUtils.md5Hex("mypassword"));

		AuthDataResponseDTO result = securityService.registerUser(input1, MediaType.APPLICATION_JSON, true);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertNotNull(result.getServerPublicKey());
		assertNotNull(result.getUserId());
		assertFalse(result.getUserId().isEmpty());
		
//		RegistrationDataRequestDTO input2 = new RegistrationDataRequestDTO();
//		input2.setNome("Tizio");
//		input2.setCognome("Caio");
//		input2.setSesso("M");
//		input2.setDataNascita("1997-01-01");
//		input2.setEmail("lucap84@gmail.com");//Stessa mail dell'utente precedente
//		input2.setEncodedPassword(DigestUtils.md5Hex("caiopassword"));
//		{//Test JSON
//			result = stub.registerUser(input2, MediaType.APPLICATION_JSON);
//			assertNotNull(result);
//			assertFalse(result.isOk()==null);
//			assertNotNull(result.getErrorsMap());
//			assertTrue(result.getErrorsMap().size()>0);
//		}
		
		/*
		 * Postconditions: User is logged
		 */
		
		assertNotEquals("", securityService.getSecureConfiguration().getDeviceId());
		assertNotEquals("", securityService.getSecureConfiguration().getSharedKey());
	}
	
	public static void main(String[] args) {
		long l = Long.
				valueOf("1450200050612");
		System.out.println(l);
	}

}
