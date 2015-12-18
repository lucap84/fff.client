package it.fff.client.test.stub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.PremiumServiceStub;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.client.util.ClientConstants;
import it.fff.clientserver.common.dto.SubscriptionDTO;
import it.fff.clientserver.common.dto.SubscriptionTypeDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class PremiumServiceTest extends WebServiceRestTest{

	public PremiumServiceTest(){
	}	
	
	@Test
	public void upgradeToPremiumShouldReturnConfirm(){
		

		int userIdAbbonato = 1; 
				
		SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
		subscriptionDTO.setUserIdAbbonato(userIdAbbonato);
		SubscriptionTypeDTO st = new SubscriptionTypeDTO();
		st.setId("1");
		st.setDescrizione("descr");
		st.setDurataGiorni("7");
		st.setDurataMesi("1");
		st.setNome("nome");
		
		subscriptionDTO.setTipo(st);
		subscriptionDTO.setDataInizio("2015-12-01");
		subscriptionDTO.setDataFine("2015-12-31");
		subscriptionDTO.setSconto("3");
		
		WriteResultDTO result = null;

		PremiumServiceStub stub = new PremiumServiceStub();
		{//Test JSON
			result = stub.upgradeToPremium(subscriptionDTO, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
		}
	}
	
	public static void main(String[] args) {
//		new PremiumServiceTest("1","99").upgradeToPremiumShouldReturnConfirm();
	}
	
}
