package it.fff.client.stub;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import it.fff.clientserver.common.dto.CityDTO;
import it.fff.clientserver.common.dto.PlaceDTO;

public class PlaceServiceStub extends StubService{

	public List<PlaceDTO> getPlacesByDescription(String description, double gpsLat, double gpsLong, String mediaType){
		Client client = super.getClientInstance();
		
		List<PlaceDTO> entityFromJSON = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getPlacesByDescription);
		{//Test JSON
			Builder requestBuilderJSON = client.target(getBaseURI()).path(restPath).
					queryParam("description", description).
					queryParam("userGpsLat", gpsLat).
					queryParam("userGpsLong", gpsLong).
					request(mediaType);
			Response responseJSON = requestBuilderJSON.get();
			entityFromJSON = responseJSON.readEntity(new GenericType<List<PlaceDTO>>(){});
		}
		
		return entityFromJSON;
	}
	
	public CityDTO getCityByName(String cityName, String nationKey, String mediaType){
		Client client = super.getClientInstance();
		
		CityDTO entityFromJSON = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_GET_getCityByName,cityName);
		{//Test JSON
			Builder requestBuilderJSON = client.target(getBaseURI()).path(restPath).
					queryParam("nationKey", nationKey).
					request(mediaType);
			Response responseJSON = requestBuilderJSON.get();
			entityFromJSON = (CityDTO)responseJSON.readEntity(CityDTO.class);
		}
		
		return entityFromJSON;
	}	
}
