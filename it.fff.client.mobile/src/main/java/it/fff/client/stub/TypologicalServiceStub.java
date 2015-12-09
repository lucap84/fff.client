package it.fff.client.stub;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.fff.client.secure.ClientSecureConfiguration;
import it.fff.clientserver.common.dto.AchievementTypeDTO;
import it.fff.clientserver.common.dto.EventCategoryDTO;
import it.fff.clientserver.common.dto.LanguageDTO;
import it.fff.clientserver.common.dto.MessageStandardDTO;
import it.fff.clientserver.common.dto.NationDTO;
import it.fff.clientserver.common.dto.SubscriptionTypeDTO;
import it.fff.clientserver.common.enums.EventStateEnum;

public class TypologicalServiceStub extends StubService{
	
	public TypologicalServiceStub(){
		super();
	}
	
	public TypologicalServiceStub(ClientSecureConfiguration secureConfiguration){
		super(secureConfiguration);
	}	

	public List<LanguageDTO> getAllLanguages(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllLanguages);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<LanguageDTO> entity = response.readEntity(new GenericType<List<LanguageDTO>>(){});
		return entity;
	}
	
	public List<SubscriptionTypeDTO> getAllSubscriptionTypes(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllSubscriptionTypes);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<SubscriptionTypeDTO> entity = response.readEntity(new GenericType<List<SubscriptionTypeDTO>>(){});
		return entity;
	}
	
	public List<AchievementTypeDTO> getAllAchievementTypes(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllAchievementTypes);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(MediaType.APPLICATION_JSON);
		Response response = requestBuilder.get();
		List<AchievementTypeDTO> entity = response.readEntity(new GenericType<List<AchievementTypeDTO>>(){});
		return entity;
	}
	
	public List<MessageStandardDTO> getAllStandardMessages(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllStandardMessages);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<MessageStandardDTO> entity = response.readEntity(new GenericType<List<MessageStandardDTO>>(){});
		return entity;
	}
	
//	public List<AttendanceStateDTO> getAllAttendanceStates(String mediaType){
//		Client client = super.getClientInstance();
//		
//		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllAttendanceStates);
//		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
//		Response response = requestBuilder.get();
//		List<AttendanceStateDTO> entity = response.readEntity(new GenericType<List<AttendanceStateDTO>>(){});
//		return entity;
//	}
	
//	public List<EventStateEnum> getAllEventStates(String mediaType){
//		Client client = super.getClientInstance();
//		
//		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllEventStates);
//		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
//		Response response = requestBuilder.get();
//		List<EventStateEnum> entity = response.readEntity(new GenericType<List<EventStateEnum>>(){});
//		return entity;
//	}
	
	public List<EventCategoryDTO> getAllEventCategories(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllEventCategories);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<EventCategoryDTO> entity = response.readEntity(new GenericType<List<EventCategoryDTO>>(){});
		return entity;
	}
	
	public List<NationDTO> getAllNations(String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getAllNations);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.get();
		List<NationDTO> entity = response.readEntity(new GenericType<List<NationDTO>>(){});
		return entity;
	}	
	
	public static void main(String[] args) {
		new TypologicalServiceStub().getAllLanguages(MediaType.APPLICATION_JSON);
	}
	

}
