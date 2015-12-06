package it.fff.client.test.stub;

import it.fff.client.stub.EventServiceStub;
import it.fff.clientserver.common.dto.*;
import javax.ws.rs.core.MediaType;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class EventServiceTest extends WebServiceRestTest{
	
	public EventServiceTest() {
	}	
		
	
	@Test
	public void getEventMessagesShouldReturnAtLeastOneMessage(){
		
		String eventId = "1";
		
		List<MessageDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getEventMessages(eventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}	
	
	@Test
	public void postEventStandardMessageShouldReturnConfirm(){
		
		String eventId = "1";
		String attendanceId = "1";
		String sdtMsgId = "1";
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.postEventStandardMessage(eventId, attendanceId, sdtMsgId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}
	
	@Test
	public void postEventMessageShouldReturnConfirm(){
		
		
		String eventId = "1";
		String attendanceId = "1";
		String message = "Very long message posted to server Very long message posted to server Very long message posted to server Very long message posted to server...";
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.postEventMessage(eventId, attendanceId, message, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void cancelAttendanceShouldReturnConfirm(){//annulla partecipazione da parte di uno partecipante
		
		String eventId = "1";
		String attendanceToDel = "1";
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.cancelAttendance(eventId, attendanceToDel, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void addFeedbackShouldReturnConfirm(){
		
		EventDTO event = new EventDTO();
		event.setId("1");
		UserDTO attendee = new UserDTO();
		attendee.setId("1");
		FeedbackDTO feedback = new FeedbackDTO();
		feedback.setPositiveFeedback(true);
		AttendanceDTO attendanceToAddFeedback = new AttendanceDTO();
		attendanceToAddFeedback.setId("1");
		attendanceToAddFeedback.setEvent(event);
		attendanceToAddFeedback.setUser(attendee);
		attendanceToAddFeedback.setOrganizer(false);
		attendanceToAddFeedback.setNumPartecipanti("22");
		attendanceToAddFeedback.setFeedback(feedback);
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.addFeedback(attendanceToAddFeedback, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void joinEventShouldReturnAnAttendance(){
		
		EventDTO event = new EventDTO();
		event.setId("1");
		UserDTO attendee = new UserDTO();
		attendee.setId("1");
		
		FeedbackDTO feedback = new FeedbackDTO();
		feedback.setPositiveFeedback(true);

		AttendanceDTO attendanceToCreate = new AttendanceDTO();
		attendanceToCreate.setId("1");
		attendanceToCreate.setEvent(event);
		attendanceToCreate.setUser(attendee);
		attendanceToCreate.setOrganizer(false);
		attendanceToCreate.setNumPartecipanti("22");
		attendanceToCreate.setFeedback(feedback);
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.joinEvent(attendanceToCreate, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void cancelEventShouldReturnConfirm(){ //annulla evento da parte dell'organizzatore
		
		String eventToDelete = "1";
		String userOrganizerId = "1";
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.cancelEvent(eventToDelete, userOrganizerId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void createEventShouldReturnConfirm(){
		
		UserDTO organizer = new UserDTO();
		organizer.setId("1");
		organizer.setNome("Nome organizer");
		organizer.setCognome("Cognome organizer");
		
		EventDTO event = new EventDTO();
		event.setTitolo("nuovo evento");
		event.setDescrizione("Descr nuovo evento");
		event.setDurata("3");
		
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUser(organizer);
		attendance.setOrganizer(true);
		attendance.setValid(true);
		
		List<AttendanceDTO> partecipazioni = new ArrayList<AttendanceDTO>();
		partecipazioni.add(attendance);
		
		event.setPartecipazioni(partecipazioni);
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.createEvent(event, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void getAttendacesByEventShouldReturnAtLeastOneAttendance(){
		
		String requestedEventId = "1";
		
		List<AttendanceDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getAttendacesByEvent(requestedEventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
		
	}
	
	@Test
	public void getEventShouldReturnOneEvent(){
		
		String requestedEventId = "1";
		
		EventDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getEvent(requestedEventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertNotNull(result.getId());
		}
	}

	@Test
	public void getEventShouldReturnOneEventAsynch(){
		
		String requestedEventId = "1";
		
		EventDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getEventAsynch(requestedEventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertNotNull(result.getId());
		}
	}

	
	@Test
	public void searchEventsShouldReturnAtLeastOneEvent(){
		
		
		String gpsLat = "1.1234";
		String gpsLong = "2.4567";
		String idCategoria = "1";
		String partecipanti = "3";
		
		List<EventDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.searchEvents(gpsLat, gpsLong, idCategoria, partecipanti, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
		
	}	
	
	public static void main(String[] args) {
		EventServiceTest eventServiceTest = new EventServiceTest();
//		eventServiceTest.joinEventShouldReturnAnAttendance();
		eventServiceTest.getEventShouldReturnOneEventAsynch();
	}
	
}
