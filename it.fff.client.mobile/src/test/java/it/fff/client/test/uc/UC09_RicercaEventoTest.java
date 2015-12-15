package it.fff.client.test.uc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.PlaceServiceStub;
import it.fff.client.stub.StubService;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.PlaceDTO;
import it.fff.clientserver.common.dto.UserDTO;

public class UC09_RicercaEventoTest {

	
	@Test
	public void test(){ //Ricerca evento
		/*
		 * Preconditions: Register or Login 
		 */
		
//		StubService service = new StubService();
//		String clientSharedKey = service.getSecureConfiguration().getSharedKey();
//		String clientDeviceId = service.getSecureConfiguration().getDeviceId();
//		if(clientSharedKey==null || "".equals(clientSharedKey) || clientDeviceId==null || "".equals(clientDeviceId)){
//			UC01_RegistraUtente_Test registerTest = new UC01_RegistraUtente_Test();
//			registerTest.test();		
//		}
		
		
		/* 
		 * getPlacesByDescription
		 * SearchEvents
		 * GetEvent
		 */
		EventServiceStub eventService = new EventServiceStub();
		UserServiceStub userService = new UserServiceStub();
		PlaceServiceStub placeService = new PlaceServiceStub();

		//individuo la posizione dell'utente
		double userGpsLat = 1.2;
		double userGpsLong = 1.3;
		
		double radiusKM = 10; //ipotizzo di ricercare in un raggio di 10KM
		
		//cerco un luogo dove vorrei l'evento 
		String description = "chiringuito";

		List<PlaceDTO> resultGetPlacesByDescription = placeService.getPlacesByDescription(description, MediaType.APPLICATION_JSON);
		assertNotNull(resultGetPlacesByDescription);
		assertTrue(resultGetPlacesByDescription.size()>0);

		//filtro i luoghi desiderati che rientrano a nella distanza imposta
		List<PlaceDTO> filteredResults = this.filterByposition(resultGetPlacesByDescription, userGpsLat, userGpsLong, radiusKM);
		//Scelgo uno tra i luoghi filtrati
		PlaceDTO placeDTO = filteredResults.get(0);
		String desideredGpsLat = placeDTO.getGpsLat();
		String desideredGpsLong = placeDTO.getGpsLong();

		//Altri criteri di ricerca
		String idCategoria = "1";
		String partecipanti = "3";
		

		// SearchEvents
		List<EventDTO> searchEventsOutput = eventService.searchEvents(	String.valueOf(userGpsLat), 
																		String.valueOf(userGpsLong), 
																		String.valueOf(radiusKM),
																		String.valueOf(desideredGpsLat), 
																		String.valueOf(desideredGpsLong),
																		idCategoria, 
																		partecipanti, 
																		MediaType.APPLICATION_JSON);
		assertNotNull(searchEventsOutput);
		assertTrue(searchEventsOutput.size()>0);
		assertNotNull(searchEventsOutput.get(0));
		assertNotNull(searchEventsOutput.get(0).getId());
		
		// getEvent
		String eventId = searchEventsOutput.get(0).getId();
		EventDTO getEventOutput = eventService.getEvent(eventId, MediaType.APPLICATION_JSON);
		assertNotNull(getEventOutput);
		assertNotNull(getEventOutput.getId());
		assertNotNull(getEventOutput.getDescrizione());
		 
		/*
		 * Postconditions: evento trovato e visualizzabile con i suoi dettagli:
		 * getAttendancesByEvent
		 * getUser
		 */
		
		// getAttendancesByEvent
		String eventId2 = getEventOutput.getId();
		List<AttendanceDTO> getAttendacesByEventOutput = eventService.getAttendacesByEvent(eventId2, MediaType.APPLICATION_JSON);
		assertNotNull(getAttendacesByEventOutput);
		assertTrue(getAttendacesByEventOutput.size()>0);
		assertNotNull(getAttendacesByEventOutput.get(0));
		assertNotNull(getAttendacesByEventOutput.get(0).getId());
		
		 //getUser
		String userId = getAttendacesByEventOutput.get(0).getUserId();
		UserDTO getUserOutput = userService.getUser(userId, MediaType.APPLICATION_JSON);
		assertNotNull(getUserOutput);
		assertTrue(getUserOutput.isOk());
		assertNotNull(getUserOutput.getId());
		
	}

	private List<PlaceDTO> filterByposition(List<PlaceDTO> resultGetPlacesByDescription, 
											double userGpsLat,
											double userGpsLong, 
											double radiusKM) {
		// TODO Auto-generated method stub
		return resultGetPlacesByDescription;
	}
}
