package it.fff.client.test.stub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import it.fff.client.stub.PlaceServiceStub;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.*;

public class PlaceServiceTest extends WebServiceRestTest{
	
	public PlaceServiceTest(){
	}	
	
	@Test
	public void getPlacesByDescriptionShouldReturnAtLeastOnePlace(){
		
		String description = "Bar";
		double myGpsLat = 41.8569478; double myGpsLong = 12.4811428; //Viale leonardo da vinci roma
//		double myGpsLat = 52.52000659999999; double myGpsLong = 13.404953999999975; //Berlino
//		double myGpsLat = 40.7127837; double myGpsLong = -74.00594130000002; //New York
//		double myGpsLat = 35.6894875; double myGpsLong = 139.69170639999993; //Tokyo
		
		
		List<PlaceDTO> result = null;

		PlaceServiceStub stub = new PlaceServiceStub();
		{//Test JSON
			result = stub.getPlacesByDescription(description, myGpsLat, myGpsLong, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getGpsLat());
			assertNotNull(result.get(0).getGpsLong());
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
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
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	public static void main(String[] args) {
//		new PlaceServiceTest("1","99").getPlacesByDescriptionShouldReturnAtLeastOnePlace();
	}
}
