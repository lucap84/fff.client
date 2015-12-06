package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.EventStateDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC08_AnnullaEvento_Test {
	
	@Test
	public void test(){//Annulla un Evento
		/*
		 * Preconditions:  utente registrato e autenticato, utente ha creato un evento
		 */
		
		EventServiceStub eventService = new EventServiceStub();
		
		String organizerId = eventService.getSecureConfiguration().getUserId();
		String eventIdToCancel = "1";
		
		WriteResultDTO cancelEventResult = null;
		{//Test JSON
			cancelEventResult =  eventService.cancelEvent(eventIdToCancel, organizerId, MediaType.APPLICATION_JSON);
			assertNotNull(cancelEventResult);
			assertTrue(cancelEventResult.isOk());
			assertTrue(cancelEventResult.getAffectedRecords()>0);
			assertNotNull(cancelEventResult.getIdentifier());
			assertFalse(cancelEventResult.getIdentifier().isEmpty());
		}	
		
		/*
		 * Postconditions:  the event is canceled
		 */
		
		//Scelgo stato CANCELED
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		List<EventStateDTO> allEventStates = typologicalService.getAllEventStates(MediaType.APPLICATION_JSON);
		EventStateDTO statoAnnullato = null;
		for (EventStateDTO eventStateDTO : allEventStates) {
			if(eventStateDTO.getNome().equalsIgnoreCase("Annullato")){
				statoAnnullato = eventStateDTO;
			}
		}
		
		EventDTO eventToCheck = eventService.getEvent(cancelEventResult.getIdentifier(), MediaType.APPLICATION_JSON);
		assertEquals(eventToCheck.getStato().getNome(),statoAnnullato);
	}
}
