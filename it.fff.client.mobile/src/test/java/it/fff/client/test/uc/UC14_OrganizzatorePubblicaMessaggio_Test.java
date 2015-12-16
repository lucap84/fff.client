package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.MessageDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC14_OrganizzatorePubblicaMessaggio_Test {
	
	@Test
	public void test(){//Organizzatore evento pubblica messaggio
		/*
		 * Preconditions:  user logged e ha creato un evento
		 */
		
		EventServiceStub eventService = new EventServiceStub();
		UserServiceStub userService = new UserServiceStub();
		
		String userId = eventService.getSecureConfiguration().getUserId();
		
		//Recupero i miei eventi
		List<EventDTO> eventsByUser = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		
		//scelgo un evento di cui sono organizzatore
		EventDTO eventDTO = eventsByUser.get(0);
		String eventId = eventDTO.getId();
		
		//recupero la mia partecipazione
		List<AttendanceDTO> partecipazioni = eventDTO.getPartecipazioni();
		AttendanceDTO attendanceDTO = partecipazioni.get(0);
		String attendanceId = attendanceDTO.getId();
		
		//Scrivo il mio messaggio custom a testo libero
		String message = "Appuntamento davanti al chiosco!";
		
		WriteResultDTO postEventMessageResult = eventService.postEventMessage(eventId, attendanceId, message, MediaType.APPLICATION_JSON);
		assertNotNull(postEventMessageResult);
		assertTrue(postEventMessageResult.isOk());
		assertTrue(postEventMessageResult.getAffectedRecords()>0);
		assertNotNull(postEventMessageResult.getIdentifier());
		assertFalse(postEventMessageResult.getIdentifier().isEmpty());
		
		/*
		 * Postconditions:  il messaggio è visibile nell'evento
		 */
		
		List<MessageDTO> eventMessages = eventService.getEventMessages(eventId, MediaType.APPLICATION_JSON);
		boolean isPresent = false;
		for (MessageDTO m : eventMessages) {
			if(m.getText().equalsIgnoreCase(message) && m.isStandard()==false){
				isPresent = true;
				break;
			}
		}
		assertTrue(isPresent);
	}
}
