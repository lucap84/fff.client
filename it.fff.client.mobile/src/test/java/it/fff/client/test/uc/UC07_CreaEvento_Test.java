package it.fff.client.test.uc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.PlaceServiceStub;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.client.util.ClientConstants;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventCategoryDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.PlaceDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.enums.AttendanceStateEnum;
import it.fff.clientserver.common.enums.EventStateEnum;

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
		
		int userId = Integer.valueOf(eventService.getSecureConfiguration().getUserId());
		double userGpsLat = 1.236;
		double userGpsLong = 1.237;
		
		EventDTO event = new EventDTO();
		
		List<AttendanceDTO> partecipazioni = new ArrayList<AttendanceDTO>();
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUserId(userId);
		attendance.setOrganizer(true);
		attendance.setValid(true);
		int numeroOspiti = 3;
		attendance.setNumeroOspiti(numeroOspiti);
		attendance.setEventId(0);//non conosco l'id ancora perché l'evento ancora non esiste
		attendance.setStato(AttendanceStateEnum.UNDETECTED);
		partecipazioni.add(attendance);

		event.setPartecipazioni(partecipazioni);
		
		String titoloEvento = "nuovo evento zzzz";
		String descrizioneEvento = "nuovo evento zzzz";
		event.setTitolo(titoloEvento);
		event.setDescrizione(descrizioneEvento);


		event.setStato(EventStateEnum.ACTIVE);
		
		//Scelta luogo
		PlaceServiceStub placeService = new PlaceServiceStub();
		List<PlaceDTO> getPlacesResult = null;
		{//Test JSON
			getPlacesResult =  placeService.getPlacesByDescription("Huston", userGpsLat, userGpsLong, MediaType.APPLICATION_JSON);
			assertNotNull(getPlacesResult);
			assertTrue(getPlacesResult.size()>0);
			assertNotNull(getPlacesResult.get(0));
			assertNotNull(getPlacesResult.get(0).getGpsLat());
			assertNotNull(getPlacesResult.get(0).getGpsLong());
			assertNotNull(getPlacesResult.get(0).getCity());
			assertNotNull(getPlacesResult.get(0).getNome());
//			assertNotNull(getPlacesResult.get(0).getId()); // IL place potrebbe non avere ID perche' il risultato proviene direttamente da google e non da flokker
//			assertTrue(getPlacesResult.get(0).getId() > 0);
			assertNotNull(getPlacesResult.get(0).getPlaceType());
		}	
		
		PlaceDTO luogo = getPlacesResult.get(0);//utente sceglie un luogo tra i vari proposti
		
		event.setLocation(luogo);
		
		String dataStartEvento = ClientConstants.DATE_FORMATTER.format(new Date());
		event.setDataInizio(dataStartEvento);
		int duraraEventoOre = 5;
		event.setDurata(duraraEventoOre);
		
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
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
			assertFalse(createEventResult.getIdentifier()<=0);
		}		
		
		
		/*
		 * Postconditions:  evento presente nel sistema e ricercabile
		 */
		
		EventDTO getEventResult = null;
		{//Test JSON
			getEventResult = eventService.getEvent(createEventResult.getIdentifier(), MediaType.APPLICATION_JSON);
			assertNotNull(getEventResult);
			assertNotNull(getEventResult.getId());
			assertEquals(titoloEvento, getEventResult.getTitolo());
			assertEquals(descrizioneEvento, getEventResult.getDescrizione());
			assertNotNull(getEventResult.getDataInizio());
			assertTrue(getEventResult.getDataInizio().startsWith(dataStartEvento));
			assertEquals(duraraEventoOre, getEventResult.getDurata());
			assertNotNull(getEventResult.getCategoria());
			assertEquals(categoriaEvento.getId(), getEventResult.getCategoria().getId());
			assertEquals(categoriaEvento.getNome(), getEventResult.getCategoria().getNome());
		}
		
		List<AttendanceDTO> getAttendacesByEventResult = null;
		{
			getAttendacesByEventResult = eventService.getAttendacesByEvent(createEventResult.getIdentifier(), MediaType.APPLICATION_JSON);
			assertNotNull(getAttendacesByEventResult);
			assertTrue(getAttendacesByEventResult.size()>0);
			assertNotNull(getAttendacesByEventResult.get(0));
			assertTrue(getAttendacesByEventResult.get(0).isValid());
			assertEquals(userId, getAttendacesByEventResult.get(0).getUserId());
			assertTrue(getAttendacesByEventResult.get(0).isOrganizer());
			assertEquals(numeroOspiti, getAttendacesByEventResult.get(0).getNumeroOspiti());
			assertNotNull(getAttendacesByEventResult.get(0).getStato());
		}
	}

}
