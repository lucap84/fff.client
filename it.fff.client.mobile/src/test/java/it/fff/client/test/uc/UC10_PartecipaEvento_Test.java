package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.UserDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.enums.AttendanceStateEnum;

public class UC10_PartecipaEvento_Test {
	
	@Test
	public void test(){//Partecipa ad un evento
		/*
		 * Preconditions:  user registered and logged
		 */
		EventServiceStub eventService = new EventServiceStub();
		
		String userId = eventService.getSecureConfiguration().getUserId();
		UserDTO attendee = new UserDTO();
		attendee.setId(userId);
		
		//Cerco un evento
		String userGpsLat = "1.23";
		String userGpsLong = "1.33";
		String radiusKM = "10"; //ipotizzo di ricercare in un raggio di 10KM
		String desideredGpsLat = "1.24";
		String desideredGpsLong = "1.32";		
		String idCategoria = "1";
		String partecipanti = "3";
		
		List<EventDTO> searchEventsOutput = eventService.searchEvents(userGpsLat, userGpsLong, radiusKM, desideredGpsLat, desideredGpsLong, idCategoria, partecipanti, MediaType.APPLICATION_JSON);
		assertNotNull(searchEventsOutput);
		assertTrue(searchEventsOutput.size()>0);
		assertNotNull(searchEventsOutput.get(0));
		assertNotNull(searchEventsOutput.get(0).getId());
		
		
		//Scelgo un evento
		EventDTO event = searchEventsOutput.get(0);
		
		//creo la partecipazione a questo evento
		AttendanceDTO attendanceToCreate = new AttendanceDTO();
		attendanceToCreate.setEventId(event.getId()); //la lego all'evento
		attendanceToCreate.setUser(attendee);
		attendanceToCreate.setOrganizer(false);
		attendanceToCreate.setNumPartecipanti("3");
		attendanceToCreate.setStato(AttendanceStateEnum.UNDETECTED);
		attendanceToCreate.setValid(true);
		
		WriteResultDTO resultJoinEvent = null;

		resultJoinEvent = eventService.joinEvent(attendanceToCreate, MediaType.APPLICATION_JSON);
		assertNotNull(resultJoinEvent);
		assertTrue(resultJoinEvent.isOk());
		assertTrue(resultJoinEvent.getAffectedRecords()>0);
		assertNotNull(resultJoinEvent.getIdentifier());
		assertFalse(resultJoinEvent.getIdentifier().isEmpty());
		
		/*
		 * Postconditions:  
		 */
	}
}
