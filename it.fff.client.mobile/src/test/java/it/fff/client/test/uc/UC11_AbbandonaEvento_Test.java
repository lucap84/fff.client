package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UC11_AbbandonaEvento_Test {
	
	@Test
	public void test(){//Abbandona Evento
		/*
		 * Preconditions:   user registered and logged + partecipa ad un evento
		 */
		
		UserServiceStub userService = new UserServiceStub();
		
		int userId = Integer.valueOf(userService.getSecureConfiguration().getUserId());

		
		//recupero gli eventi a cui partecipo
		List<EventDTO> resultGetEventsByUser = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		assertNotNull(resultGetEventsByUser);
		assertTrue(resultGetEventsByUser.size()>0);
		assertNotNull(resultGetEventsByUser.get(0));
		assertNotNull(resultGetEventsByUser.get(0).getId());
		
		//Scelgo l'evento da abbandonare tra i miei
		EventDTO eventDTO = resultGetEventsByUser.get(0);
		int idEventoDaAbbandonare = eventDTO.getId();
		
		//Abbandono evento
		WriteResultDTO cancelAttendanceResult = userService.cancelAttendance(idEventoDaAbbandonare, userId, MediaType.APPLICATION_JSON);
		assertNotNull(cancelAttendanceResult);
		assertTrue(cancelAttendanceResult.isOk());
		assertTrue(cancelAttendanceResult.getAffectedRecords()>0);
		assertNotNull(cancelAttendanceResult.getIdentifier());
		assertFalse(cancelAttendanceResult.getIdentifier()<=0);		
		
		/*
		 * Postconditions:  l'evento non e' piu tra gli eventi dell'utente
		 */
		List<EventDTO> resultGetEventsByUserAfter = userService.getEventsByUser(userId, MediaType.APPLICATION_JSON);
		for (EventDTO e : resultGetEventsByUserAfter) {
			assertNotEquals(idEventoDaAbbandonare, e.getId());
		}
		
	}
}
