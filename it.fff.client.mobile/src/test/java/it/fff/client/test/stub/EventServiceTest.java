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
		
		int eventId = 1;
		
		List<MessageDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getEventMessages(eventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
	}	
	
	@Test
	public void postEventStandardMessageShouldReturnConfirm(){
		
		int eventId = 1;
		int attendanceId = 1;
		int sdtMsgId = 1;
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.postEventStandardMessage(eventId, attendanceId, sdtMsgId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
			
		}
	}
	
	@Test
	public void postEventMessageShouldReturnConfirm(){
		
		
		int eventId = 1;
		int attendanceId = 1;
		String message = "Very long message posted to server Very long message posted to server Very long message posted to server Very long message posted to server...";
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.postEventMessage(eventId, attendanceId, message, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
	
	@Test
	public void addFeedbackShouldReturnConfirm(){
		
		int eventId = 1;
		int attendanceId = 1;
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.addFeedback(eventId, attendanceId, FeedbackEnum.NEGATIVE, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
	@Test
	public void joinEventShouldReturnAnAttendance(){
		
		AttendanceDTO attendanceToCreate = new AttendanceDTO();
		attendanceToCreate.setId(1);
		attendanceToCreate.setEventId(1);
		attendanceToCreate.setUserId(1);
		attendanceToCreate.setOrganizer(false);
		attendanceToCreate.setNumeroOspiti(22);
		attendanceToCreate.setFeedback(FeedbackEnum.POSITIVE);
		
		WriteResultDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.joinEvent(attendanceToCreate, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
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
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
	@Test
	public void createEventShouldReturnConfirm(){
		
		EventDTO event = new EventDTO();
		event.setTitolo("nuovo evento test x Juna");
		event.setDescrizione("Descr nuovo evento test x Juna");
		event.setDurata(3);
		
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setUserId(1);
		attendance.setOrganizer(true);
		attendance.setValid(true);
		attendance.setEventId(event.getId());
		attendance.setNumeroOspiti(3);
		
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
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
	@Test
	public void getAttendacesByEventShouldReturnAtLeastOneAttendance(){
		
		int requestedEventId = 1;
		
		List<AttendanceDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getAttendacesByEvent(requestedEventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
		
	}
	
	@Test
	public void getEventShouldReturnOneEvent(){
		
		int requestedEventId = 1;
		
		EventDTO result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.getEvent(requestedEventId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.getId()>0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
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
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}

	
	@Test
	public void searchEventsShouldReturnAtLeastOneEvent(){
		
		
		double userGpsLat = 1.1234;
		double userGgpsLong = 2.4567;
		double radiusKM = 10;
		double desideredGpsLat = 1.1235;
		double desideredGgpsLong = 2.4566;		
		int idCategoria = 1;
		int partecipanti = 3;
		
		List<EventDTO> result = null;

		{//Test JSON
			EventServiceStub stub = new EventServiceStub();
			result = stub.searchEvents(userGpsLat, userGgpsLong, radiusKM, desideredGpsLat, desideredGgpsLong, idCategoria, partecipanti, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
		
	}	
	
	public static void main(String[] args) {
		EventServiceTest eventServiceTest = new EventServiceTest();
//		eventServiceTest.joinEventShouldReturnAnAttendance();
		eventServiceTest.getEventShouldReturnOneEventAsynch();
	}
	
}
