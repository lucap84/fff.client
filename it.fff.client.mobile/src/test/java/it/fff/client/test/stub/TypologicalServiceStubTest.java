package it.fff.client.test.stub;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.TypologicalServiceStub;
import it.fff.clientserver.common.dto.AchievementTypeDTO;
import it.fff.clientserver.common.dto.EventCategoryDTO;
import it.fff.clientserver.common.dto.LanguageDTO;
import it.fff.clientserver.common.dto.MessageStandardDTO;
import it.fff.clientserver.common.dto.SubscriptionTypeDTO;
import it.fff.clientserver.common.enums.EventStateEnum;

public class TypologicalServiceStubTest extends WebServiceRestTest{

	@Test
	public void getAllLanguagesShouldReturnAtLeastOneElement(){
		
		List<LanguageDTO> result = null;
		{//Test JSON
			TypologicalServiceStub stub = new TypologicalServiceStub();
			result = stub.getAllLanguages(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}
	
	@Test
	public void getAllSubscriptionTypesShouldReturnAtLeastOneElement(){
		
		List<SubscriptionTypeDTO> result = null;
		{//Test JSON
			TypologicalServiceStub stub = new TypologicalServiceStub();
			result = stub.getAllSubscriptionTypes(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}
	
	@Test
	public void getAllAchievementTypesShouldReturnAtLeastOneElement(){
		
		List<AchievementTypeDTO> result = null;
		{//Test JSON
			TypologicalServiceStub stub = new TypologicalServiceStub();
			result = stub.getAllAchievementTypes(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}	
	
	@Test
	public void getAllStandardMessagesShouldReturnAtLeastOneElement(){
		
		List<MessageStandardDTO> result = null;
		{//Test JSON
			TypologicalServiceStub stub = new TypologicalServiceStub();
			result = stub.getAllStandardMessages(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}
	
//	@Test
//	public void getAllAttendanceStatesShouldReturnAtLeastOneElement(){
//		
//		List<AttendanceStateDTO> result = null;
//		{//Test JSON
//			TypologicalServiceStub stub = new TypologicalServiceStub();
//			result = stub.getAllAttendanceStates(MediaType.APPLICATION_JSON);
//			assertNotNull(result);
//			assertTrue(result.size()>0);
//			assertNotNull(result.get(0));
//			assertNotNull(result.get(0).getId());
//		}
//	}
	
//	@Test
//	public void getAllEventStatesShouldReturnAtLeastOneElement(){
//		
//		List<EventStateEnum> result = null;
//		{//Test JSON
//			TypologicalServiceStub stub = new TypologicalServiceStub();
//			result = stub.getAllEventStates(MediaType.APPLICATION_JSON);
//			assertNotNull(result);
//			assertTrue(result.size()>0);
//			assertNotNull(result.get(0));
//		}
//	}
	
	@Test
	public void getAllEventCategoriesShouldReturnAtLeastOneElement(){
		
		List<EventCategoryDTO> result = null;
		{//Test JSON
			TypologicalServiceStub stub = new TypologicalServiceStub();
			result = stub.getAllEventCategories(MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}
}
