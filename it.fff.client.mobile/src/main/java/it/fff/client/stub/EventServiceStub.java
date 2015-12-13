package it.fff.client.stub;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import it.fff.clientserver.common.dto.AttendanceDTO;
import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.MessageDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class EventServiceStub  extends StubService{

	public List<MessageDTO> getEventMessages(String eventId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEventMessages, eventId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<MessageDTO> entity = response.readEntity(new GenericType<List<MessageDTO>>(){});
		return entity;
	}	
	
	
	public WriteResultDTO postEventStandardMessage(String eventId,String attendanceId,String sdtMsgId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_postEventStandardMessage, eventId,attendanceId,sdtMsgId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(null, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}
	
	
	public WriteResultDTO postEventMessage(String eventId,String attendanceId,String message,String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_postEventMessage, eventId,attendanceId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(message, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO addFeedback(AttendanceDTO attendanceToAddFeedback, String mediaType){
		Client client = super.getClientInstance();
		
		String eventId = attendanceToAddFeedback.getEventId();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_addFeedback, eventId,attendanceToAddFeedback.getId());
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(attendanceToAddFeedback, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO joinEvent(AttendanceDTO attendanceToCreate, String mediaType){
		Client client = super.getClientInstance();
		
		String eventId = attendanceToCreate.getEventId();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_joinEvent, eventId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(attendanceToCreate, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO cancelEvent(String eventIdToCancel, String organizerId, String mediaType){ //annulla evento da parte dell'organizzatore
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_DELETE_cancelEvent, eventIdToCancel);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath)
				.queryParam("organizerId", organizerId)
				.request(mediaType);
		Response response = requestBuilder.delete();
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO createEvent(EventDTO event, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_createEvent);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(event, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public List<AttendanceDTO> getAttendacesByEvent(String eventId, String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAttendacesByEvent,eventId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<AttendanceDTO> entity = response.readEntity(new GenericType<List<AttendanceDTO>>(){});
		return entity;
	}
	
	
	public EventDTO getEvent(String eventId,String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEvent,eventId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		EventDTO entity = response.readEntity(EventDTO.class);
		return entity;
	}
	
	public EventDTO getEventAsynch(String eventId,String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEvent,eventId);
		AsyncInvoker asyncInvoker  = client.target(getBaseURI()).path(restPath).request(mediaType).async();
		Future<Response> responseFuture = asyncInvoker.get();
		System.out.println("Request is being processed asynchronously.");
		Response response = null;
		try {
			response = responseFuture.get();
			System.out.println("Response received.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		EventDTO entity = response.readEntity(EventDTO.class);
		return entity;
	}	
	
	
	public List<EventDTO> searchEvents( String userGpsLat, 
										String userGpsLong, 
										String radiusKM, 
										String desideredGpsLat, 
										String desideredGpsLong,
										String idCategoria, 
										String partecipanti, 
										String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_searchEvents);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).
				queryParam("userGpsLat", userGpsLat).
				queryParam("userGpsLong", userGpsLong).
				queryParam("radiusKM", radiusKM).
				queryParam("desideredGpsLat", desideredGpsLat).
				queryParam("desideredGpsLong", desideredGpsLong).
				queryParam("idCategoria", idCategoria).
				queryParam("partecipanti", partecipanti).request(mediaType);
		Response response = requestBuilder.get();
		List<EventDTO> entity = response.readEntity(new GenericType<List<EventDTO>>(){});
		return entity;
	}	
}
