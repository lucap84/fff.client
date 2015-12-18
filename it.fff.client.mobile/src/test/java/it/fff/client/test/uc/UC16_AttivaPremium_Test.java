package it.fff.client.test.uc;

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

public class UC16_AttivaPremium_Test {
	
	@Test
	public void test(){//Utente attiva servizi premium
		/*
		 * Preconditions:  logged
		 */
		
		
		//Seleziono il tipo di abbonamento
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		PremiumServiceStub premiumService = new PremiumServiceStub();

		int userIdAbbonato = Integer.valueOf(premiumService.getSecureConfiguration().getUserId()); 
		
		List<SubscriptionTypeDTO> allSubscriptionTypes = typologicalService.getAllSubscriptionTypes(MediaType.APPLICATION_JSON);
		SubscriptionTypeDTO subscriptionTypeDTO = allSubscriptionTypes.get(0);
				
		SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
		
		//associo l'abbonamento all'utente
		subscriptionDTO.setUserIdAbbonato(userIdAbbonato);
		
		//scelgo il tipo di abbonamento
		subscriptionDTO.setTipo(subscriptionTypeDTO);
		
		//seleziono la data inizio dell'abbonamento
		Date dataInizio = new Date();
		
		//viene calcolata e impostata la data fine
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataInizio);
		calendar.add(Calendar.MONTH, Integer.valueOf(subscriptionTypeDTO.getDurataMesi()));
		calendar.add(Calendar.DATE, Integer.valueOf(subscriptionTypeDTO.getDurataGiorni()));
		Date dataFine = calendar.getTime();
		subscriptionDTO.setDataInizio(ClientConstants.DATE_FORMATTER.format(dataInizio));
		subscriptionDTO.setDataFine(ClientConstants.DATE_FORMATTER.format(dataFine));
		
		//In caso sia contemplato, si aggiunge uno sconto
		subscriptionDTO.setSconto("3");
		
		
		WriteResultDTO upgradeToPremiumResult = premiumService.upgradeToPremium(subscriptionDTO, MediaType.APPLICATION_JSON);
		assertNotNull(upgradeToPremiumResult);
		assertTrue(upgradeToPremiumResult.isOk());
		assertTrue(upgradeToPremiumResult.getAffectedRecords()>0);
		assertNotNull(upgradeToPremiumResult.getIdentifier());
		assertFalse(upgradeToPremiumResult.getIdentifier()<=0);

		
		
		
		/*
		 * Postconditions:  
		 */
	}
}
