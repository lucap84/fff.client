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
import it.fff.clientserver.common.dto.RegistrationDataRequestDTO;

public class UC01Test {
	
	@Test
	public void testUC1(){ //Registra Utente
		/*
		 * Preconditions:  not logged
		 */
		StubService service = new StubService();
//		assertEquals("", service.getSecureConfiguration().getDeviceId());
//		assertEquals("", service.getSecureConfiguration().getSharedKey());
		
		/*
		 * UC1
		 */
		SecurityServiceStub stub = new SecurityServiceStub();
		
		RegistrationDataRequestDTO input1 = new RegistrationDataRequestDTO();
		input1.setNome("Luca");
		input1.setCognome("Pelosi");
		input1.setSesso("M");
		input1.setDataNascita("1984-02-09");
		input1.setEmail("lucap84@gmail.com");
		input1.setEncodedPassword(DigestUtils.md5Hex("mypassword"));

		AuthDataResponseDTO result = stub.registerUser(input1, MediaType.APPLICATION_JSON);
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
//			assertFalse(result.isOk());
//			assertNotNull(result.getErrorsMap());
//			assertTrue(result.getErrorsMap().size()>0);
//		}
		
		/*
		 * Postconditions: User is logged
		 */
		
		assertNotEquals("", service.getSecureConfiguration().getDeviceId());
		assertNotEquals("", service.getSecureConfiguration().getSharedKey());
	}

}
