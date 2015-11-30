package it.fff.client.test.uc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import it.fff.client.stub.EventServiceStub;
import it.fff.client.stub.StubService;
import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.CityDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.PlaceDTO;
import it.fff.clientserver.common.dto.UserDTO;
import it.fff.clientserver.common.secure.SecureConfiguration;

public class UC03Test {
	
	@Test
	public void testUC3(){//Crea Evento
		/*
		 * Preconditions:  utente registrato e autenticato
		 */
		StubService service = new StubService();
		String clientSharedKey = service.getSecureConfiguration().getSharedKey();
		String clientDeviceId = service.getSecureConfiguration().getDeviceId();
		if(clientSharedKey==null || "".equals(clientSharedKey) || clientDeviceId==null || "".equals(clientDeviceId)){
			UC01Test registerTest = new UC01Test();
			registerTest.testUC1();		
		}
		/*
		 * UC3
		 */
		EventServiceStub eventService = new EventServiceStub();
		
		UserDTO organizer = new UserDTO();
		organizer.setId(eventService.getSecureConfiguration().getUserId());
		
		EventDTO event = new EventDTO();
		event.setTitolo("nuovo evento");
		event.setDescrizione("Descr nuovo evento");
		
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUser(organizer);
		attendance.setOrganizer(true);
		attendance.setValid(true);
		
		List<AttendanceDTO> partecipazioni = new ArrayList<AttendanceDTO>();
		partecipazioni.add(attendance);
		event.setPartecipazioni(partecipazioni);
		
		PlaceDTO luogo = new PlaceDTO();
		luogo.setGpsLat("1.234");
		luogo.setGpsLat("1.456");
		luogo.setVia("Via di prova");
		luogo.setCivico("22");
		luogo.setCap("00100");
		CityDTO citta = new CityDTO();
		citta.setId("1");
		luogo.setCity(citta);
		
		event.setLocation(luogo);
		
		String dataStartEvento = SecureConfiguration.DATE_FORMATTER.format(new Date());
		event.setDataInizio(dataStartEvento);
		
//		event.setStato(stato);
		
		/*
		 * Postconditions:  
		 */
	}

}
