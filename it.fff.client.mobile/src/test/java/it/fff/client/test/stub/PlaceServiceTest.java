package it.fff.client.test.stub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import it.fff.client.stub.PlaceServiceStub;
import it.fff.clientserver.common.dto.*;

public class PlaceServiceTest extends WebServiceRestTest{
	
	public PlaceServiceTest(){
	}	
	
	@Test
	public void getPlacesByDescriptionShouldReturnAtLeastOnePlace(){
		
		String description = "Leonardo da vinci";
		double gpsLat = 41.856947;
		double gpsLong = 12.481142;
		
		List<PlaceDTO> result = null;

		PlaceServiceStub stub = new PlaceServiceStub();
		{//Test JSON
			result = stub.getPlacesByDescription(description, gpsLat, gpsLong, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getGpsLat());
			assertNotNull(result.get(0).getGpsLong());
		}
	}
	
	@Test
	public void getCityByNameShouldReturnoneCity(){
		
		String cityName = "Roma";
		String nationInternationalCode = "ITA";
		
		CityDTO result = null;

		PlaceServiceStub stub = new PlaceServiceStub();
		{//Test JSON
			result = stub.getCityByName(cityName, nationInternationalCode, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.getId()>0);
			assertNotNull(result.getNazione());
		}
	}	

	public static void main(String[] args) {
//		new PlaceServiceTest("1","99").getPlacesByDescriptionShouldReturnAtLeastOnePlace();
	}
}
