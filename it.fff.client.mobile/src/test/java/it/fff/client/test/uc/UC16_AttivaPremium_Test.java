package it.fff.client.test.uc;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.TypologicalServiceStub;
import it.fff.clientserver.common.dto.SubscriptionDTO;
import it.fff.clientserver.common.dto.SubscriptionTypeDTO;

public class UC16_AttivaPremium_Test {
	
	@Test
	public void test(){//Utente attiva servizi premium
		/*
		 * Preconditions:  logged
		 */
		
		//Seleziono il tipo di abbonamento
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		List<SubscriptionTypeDTO> allSubscriptionTypes = typologicalService.getAllSubscriptionTypes(MediaType.APPLICATION_JSON);
		SubscriptionTypeDTO subscriptionTypeDTO = allSubscriptionTypes.get(0);
		
//		subscriptionTypeDTO.get
//		
//		SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
//		subscriptionDTO.setDurata("3");
//		subscriptionDTO.
		
		/*
		 * Postconditions:  
		 */
	}
}
