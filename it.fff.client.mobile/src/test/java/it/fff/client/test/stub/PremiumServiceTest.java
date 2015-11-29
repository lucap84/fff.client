package it.fff.client.test.stub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.PremiumServiceStub;
import it.fff.clientserver.common.dto.SubscriptionDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class PremiumServiceTest extends WebServiceRestTest{

	public PremiumServiceTest(){
	}	
	
	@Test
	public void upgradeToPremiumShouldReturnConfirm(){
		SubscriptionDTO subscription = new SubscriptionDTO();
		subscription.setDurata("3");
		
		WriteResultDTO result = null;

		PremiumServiceStub stub = new PremiumServiceStub();
		{//Test JSON
			result = stub.upgradeToPremium(subscription, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}
	
	public static void main(String[] args) {
//		new PremiumServiceTest("1","99").upgradeToPremiumShouldReturnConfirm();
	}
	
}
