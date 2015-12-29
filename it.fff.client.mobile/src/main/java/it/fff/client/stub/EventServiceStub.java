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
import it.fff.clientserver.common.enums.FeedbackEnum;

public class EventServiceStub  extends StubService{

	public List<MessageDTO> getEventMessages(int eventId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEventMessages, String.valueOf(eventId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<MessageDTO> entity = response.readEntity(new GenericType<List<MessageDTO>>(){});
		return entity;
	}	
	
	
	public WriteResultDTO postEventStandardMessage(int eventId,int attendanceId,int sdtMsgId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_postEventStandardMessage, String.valueOf(eventId),String.valueOf(attendanceId),String.valueOf(sdtMsgId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(null, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}
	
	
	public WriteResultDTO postEventMessage(int eventId,int attendanceId,String message,String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_postEventMessage, String.valueOf(eventId),String.valueOf(attendanceId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(message, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO addFeedback(int eventId, int attendanceId, FeedbackEnum feedback, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_addFeedback, String.valueOf(eventId), String.valueOf(attendanceId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(feedback, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
	
	
	public WriteResultDTO joinEvent(AttendanceDTO attendanceToCreate, String mediaType){
		Client client = super.getClientInstance();
		
		int eventId = attendanceToCreate.getEventId();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_joinEvent, String.valueOf(eventId));
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
	
	
	public List<AttendanceDTO> getAttendacesByEvent(int eventId, String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAttendacesByEvent,String.valueOf(eventId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<AttendanceDTO> entity = response.readEntity(new GenericType<List<AttendanceDTO>>(){});
		return entity;
	}
	
	
	public EventDTO getEvent(int eventId,String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEvent,String.valueOf(eventId));
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
	
	
	public List<EventDTO> searchEvents( double userGpsLat, 
										double userGpsLong, 
										double radiusKM, 
										double desideredGpsLat, 
										double desideredGpsLong,
										int idCategoria, 
										int partecipanti, 
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
