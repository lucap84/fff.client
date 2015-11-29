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
		
		String description = "chiringuito";
		
		List<PlaceDTO> result = null;

		PlaceServiceStub stub = new PlaceServiceStub();
		{//Test JSON
			result = stub.getPlacesByDescription(description, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getGpsLat());
			assertNotNull(result.get(0).getGpsLong());
		}
	}	

	public static void main(String[] args) {
//		new PlaceServiceTest("1","99").getPlacesByDescriptionShouldReturnAtLeastOnePlace();
	}
}
