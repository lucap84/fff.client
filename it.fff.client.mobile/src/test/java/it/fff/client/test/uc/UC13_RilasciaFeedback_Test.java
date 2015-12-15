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
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.enums.FeedbackEnum;

public class UC13_RilasciaFeedback_Test {
	
	@Test
	public void test(){//Rilascia un feedback
		/*
		 * Preconditions:  logged
		 */
		EventServiceStub eventService = new EventServiceStub();
		UserServiceStub userService = new UserServiceStub();
		
		String userId = eventService.getSecureConfiguration().getUserId();
		
		//Recupero i miei eventi
		List<EventDTO> eventsByUser = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		
		//scelgo un evento a cui ho partecipato
		EventDTO eventDTO = eventsByUser.get(0);
		
		//recupero la mia partecipazione
//		List<AttendanceDTO> attendacesByEvent = eventService.getAttendacesByEvent(eventDTO.getId(), MediaType.APPLICATION_JSON);
		List<AttendanceDTO> partecipazioni = eventDTO.getPartecipazioni();
		AttendanceDTO attendanceToAddFeedback = partecipazioni.get(0);
		
		//Imposto il feedback positivo/negativo
		attendanceToAddFeedback.setFeedback(FeedbackEnum.POSITIVE);
		
		//Salvo il nuovo feedback
		WriteResultDTO result = eventService.addFeedback(attendanceToAddFeedback, MediaType.APPLICATION_JSON);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertTrue(result.getAffectedRecords()>0);
		assertNotNull(result.getIdentifier());
		assertFalse(result.getIdentifier().isEmpty());
		
		/*
		 * Postconditions:  
		 */
	}
}
