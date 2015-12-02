package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.PlaceServiceStub;
import it.fff.client.stub.StubService;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.CityDTO;
import it.fff.clientserver.common.dto.EventCategoryDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.EventStateDTO;
import it.fff.clientserver.common.dto.PlaceDTO;
import it.fff.clientserver.common.dto.UserDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.secure.SecureConfiguration;

public class UC07_CreaEvento_Test {
	
	@Test
	public void test(){//Crea Evento
		/*
		 * Preconditions:  utente registrato e autenticato
		 */
//		StubService service = new StubService();
//		String clientSharedKey = service.getSecureConfiguration().getSharedKey();
//		String clientDeviceId = service.getSecureConfiguration().getDeviceId();
//		if(clientSharedKey==null || "".equals(clientSharedKey) || clientDeviceId==null || "".equals(clientDeviceId)){
//			UC01_RegistraUtente_Test registerTest = new UC01_RegistraUtente_Test();
//			registerTest.test();		
//		}

		EventServiceStub eventService = new EventServiceStub();
		
		UserDTO organizer = new UserDTO();
		organizer.setId(eventService.getSecureConfiguration().getUserId());
		
		
		List<AttendanceDTO> partecipazioni = new ArrayList<AttendanceDTO>();
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUser(organizer);
		attendance.setOrganizer(true);
		attendance.setValid(true);
		attendance.setNumPartecipanti("3");
		partecipazioni.add(attendance);

		EventDTO event = new EventDTO();
		event.setPartecipazioni(partecipazioni);

		event.setTitolo("nuovo evento");
		event.setDescrizione("Descr nuovo evento");

		//Scelgo stato ATTIVO in creazione evento
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		List<EventStateDTO> allEventStates = typologicalService.getAllEventStates(MediaType.APPLICATION_JSON);
		EventStateDTO statoAttivo = null;
		for (EventStateDTO eventStateDTO : allEventStates) {
			if(eventStateDTO.getNome().equalsIgnoreCase("ACTIVE")){
				statoAttivo = eventStateDTO;
			}
		}
		event.setStato(statoAttivo);
		
		//Scelta luogo
		PlaceServiceStub placeService = new PlaceServiceStub();
		List<PlaceDTO> getPlacesResult = null;
		{//Test JSON
			getPlacesResult =  placeService.getPlacesByDescription("luogo test", MediaType.APPLICATION_JSON);
			assertNotNull(getPlacesResult);
			assertTrue(getPlacesResult.size()>0);
			assertNotNull(getPlacesResult.get(0));
			assertNotNull(getPlacesResult.get(0).getGpsLat());
			assertNotNull(getPlacesResult.get(0).getGpsLong());
		}	
		
		PlaceDTO luogo = getPlacesResult.get(0);//utente sceglie un luogo tra i vari proposti
		
		event.setLocation(luogo);
		
		String dataStartEvento = SecureConfiguration.DATE_FORMATTER.format(new Date());
		event.setDataInizio(dataStartEvento);
		event.setDurata("5");
		
		List<EventCategoryDTO> allEventCategories = typologicalService.getAllEventCategories(MediaType.APPLICATION_JSON);
		EventCategoryDTO categoriaEvento = allEventCategories.get(0); //Scelgo categoria
		
		event.setCategoria(categoriaEvento);
		//TODO immagine evento solo con premium
		
		WriteResultDTO createEventResult = null;
		{//Test JSON
			createEventResult = eventService.createEvent(event, MediaType.APPLICATION_JSON);
			assertNotNull(createEventResult);
			assertTrue(createEventResult.isOk());
			assertTrue(createEventResult.getAffectedRecords()>0);
			assertNotNull(createEventResult.getIdentifier());
			assertFalse(createEventResult.getIdentifier().isEmpty());
		}		
		
		
		/*
		 * Postconditions:  evento presente nel sistema e ricercabile
		 */
		
		EventDTO getEventResult = null;
		{//Test JSON
			getEventResult = eventService.getEvent(createEventResult.getIdentifier(), MediaType.APPLICATION_JSON);
			assertNotNull(getEventResult);
			assertNotNull(getEventResult.getId());
		}
	}

}