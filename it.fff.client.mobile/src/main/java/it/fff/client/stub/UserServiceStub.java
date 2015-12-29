package it.fff.client.stub;


import java.io.File;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import it.fff.clientserver.common.dto.EventDTO;
import it.fff.clientserver.common.dto.PlaceDTO;
import it.fff.clientserver.common.dto.UserDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class UserServiceStub  extends StubService{

	public WriteResultDTO modifyUserData(UserDTO  userToUpdate, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_PUT_modifyUserData,String.valueOf(userToUpdate.getId()));
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.put(Entity.entity(userToUpdate, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;
	}	
	
	public WriteResultDTO setCurrentPosition(int userId, int eventId, PlaceDTO currentPlace, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_setCurrentPosition,String.valueOf(userId),String.valueOf(eventId));
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(Entity.entity(currentPlace, mediaType));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;
	}	
	
	public List<EventDTO> getEventsByUser(int userId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getEventsByUser,String.valueOf(userId));
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		final List<EventDTO> entity = response.readEntity(new GenericType<List<EventDTO>>(){});
		
		return entity;
	}
	
	public UserDTO getUser(int userId, String mediaType){
		Client client = super.getClientInstance();

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getUser,String.valueOf(userId));
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		UserDTO entity = response.readEntity(UserDTO.class);
		
		return entity;
	}
	
	public WriteResultDTO updateProfileImage(int userId, String imageLocation, String mediaType){
		Client client = super.getClientInstance();
		
		File f = new File(imageLocation);
		
		//TODO check tipo immagine e dimensione lato client
		
		FileDataBodyPart uploadFilePart = new FileDataBodyPart("file",f);
		final FormDataMultiPart multipart = new FormDataMultiPart();
		multipart.bodyPart(uploadFilePart);

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_updateProfileImage,String.valueOf(userId));
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request();
		Response response = requestBuilder.post(Entity.entity(multipart, MediaType.MULTIPART_FORM_DATA));
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);	

		return writeResult;
	}
	
	public WriteResultDTO cancelAttendance(int eventId, int userId, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_DELETE_cancelAttendance, String.valueOf(eventId),String.valueOf(userId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.delete();
		WriteResultDTO writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		return writeResult;
	}	
}
