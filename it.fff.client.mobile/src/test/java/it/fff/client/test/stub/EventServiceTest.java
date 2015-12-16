package it.fff.client.test.stub;

import it.fff.client.stub.EventServiceStub;
import it.fff.clientserver.common.dto.*;
import it.fff.clientserver.common.enums.FeedbackEnum;

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
	public void addFeedbackShouldReturnConfirm(){
		
		String eventId = "1";
		String attendanceId = "1";
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.addFeedback(eventId, attendanceId, FeedbackEnum.NEGATIVE, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}
	}	
	
	@Test
	public void joinEventShouldReturnAnAttendance(){
		
		AttendanceDTO attendanceToCreate = new AttendanceDTO();
		attendanceToCreate.setId("1");
		attendanceToCreate.setEventId("1");
		attendanceToCreate.setUserId("1");
		attendanceToCreate.setOrganizer(false);
		attendanceToCreate.setNumPartecipanti("22");
		attendanceToCreate.setFeedback(FeedbackEnum.POSITIVE);
		
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
		
		EventDTO event = new EventDTO();
		event.setTitolo("nuovo evento");
		event.setDescrizione("Descr nuovo evento");
		event.setDurata("3");
		
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUserId("1");
		attendance.setOrganizer(true);
		attendance.setValid(true);
		attendance.setEventId(event.getId());
		attendance.setNumPartecipanti("1");
		
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
		
		
		String userGpsLat = "1.1234";
		String userGgpsLong = "2.4567";
		String radiusKM = "10";
		String desideredGpsLat = "1.1235";
		String desideredGgpsLong = "2.4566";		
		String idCategoria = "1";
		String partecipanti = "3";
		
		List<EventDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.searchEvents(userGpsLat, userGgpsLong, radiusKM, desideredGpsLat, desideredGgpsLong, idCategoria, partecipanti, MediaType.APPLICATION_JSON);
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
