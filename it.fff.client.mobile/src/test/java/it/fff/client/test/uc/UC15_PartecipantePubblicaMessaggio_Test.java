package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.MessageDTO;
import it.fff.clientserver.common.dto.MessageStandardDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC15_PartecipantePubblicaMessaggio_Test {
	
	@Test
	public void test(){//Partecipante evento pubblica messaggio predefinito
		/*
		 * Preconditions:  user logged e partecipa ad un evento
		 */
		
		EventServiceStub eventService = new EventServiceStub();
		UserServiceStub userService = new UserServiceStub();
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		
		int userId = Integer.valueOf(eventService.getSecureConfiguration().getUserId());
		
		//Recupero i miei eventi
		List<EventDTO> eventsByUser = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		
		//scelgo un evento di cui sono partecipante
		EventDTO eventDTO = eventsByUser.get(0);
		int eventId = eventDTO.getId();
		
		//recupero la mia partecipazione
		List<AttendanceDTO> partecipazioni = eventDTO.getPartecipazioni();
		AttendanceDTO attendanceDTO = partecipazioni.get(0);
		int attendanceId = attendanceDTO.getId();
		
		//scelgo uno dei possibili messaggi standard
		List<MessageStandardDTO> allStandardMessages = typologicalService.getAllStandardMessages(MediaType.APPLICATION_JSON);
		MessageStandardDTO messageStandardDTO = allStandardMessages.get(0);
		int sdtMsgId = messageStandardDTO.getId();
		
		WriteResultDTO postEventStandardMessageResult = eventService.postEventStandardMessage(eventId, attendanceId, sdtMsgId, MediaType.APPLICATION_JSON);
		assertNotNull(postEventStandardMessageResult);
		assertTrue(postEventStandardMessageResult.isOk());
		assertTrue(postEventStandardMessageResult.getAffectedRecords()>0);
		assertNotNull(postEventStandardMessageResult.getIdentifier());
		assertFalse(postEventStandardMessageResult.getIdentifier()<=0);		
		
		/*
		 * Postconditions:  il messaggio e' visibile nell'evento
		 */
		
		List<MessageDTO> eventMessages = eventService.getEventMessages(eventId, MediaType.APPLICATION_JSON);
		boolean isPresent = false;
		for (MessageDTO m : eventMessages) {
			if(m.getText().equalsIgnoreCase(messageStandardDTO.getText()) && m.isStandard()==true){
				isPresent = true;
				break;
			}
		}
		assertTrue(isPresent);
	}
}
