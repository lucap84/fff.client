package it.fff.client.test.stub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import org.junit.Test;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.*;

public class UserServiceTest extends WebServiceRestTest{


	public UserServiceTest(){
	}	
	

	@Test
	public void modifyUserDataShouldReturnConfirm(){
		
		UserDTO  user = new UserDTO();
		user.setId("1");
		user.setEmail("nuovamail@mail.it");
		user.setSesso("F");
		user.setDataNascita("1900-01-01");
		user.setNome("Nome mod");
		user.setCognome("cognome mod");
		user.setDescrizione("Descrizione mod");
		
		NationDTO nazionalita = new NationDTO();
		nazionalita.setId("1");
		user.setNazionalita(nazionalita);
		
		LanguageDTO l1 = new LanguageDTO();
		l1.setId("1");
		LanguageDTO l2 = new LanguageDTO();
		l1.setId("2");
		List<LanguageDTO> lingue = new ArrayList<LanguageDTO>();
		lingue.add(l1);
		lingue.add(l2);
		user.setLingue(lingue);
		
		//impostate in modo silente dal sistema
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		user.setLastPositionDate(df.format(new Date()));
		user.setLastPositionLat("1.234");
		user.setLastPositionLong("2.345");
		
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.modifyUserData(user, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}		
		
	}	
	
	@Test
	public void setCurrentPositionShouldReturnConfirm(){
		
		String userId = "1";
		String eventId = "1";
		PlaceDTO currentPlace = new PlaceDTO();
		currentPlace.setGpsLat("1001");
		currentPlace.setGpsLong("2001");
		
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.setCurrentPosition(userId, eventId, currentPlace, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}	
	}	
	
	@Test
	public void getEventsByUserShouldReturnAtLeastOneEvent(){
		
		String userId = "1";

		List<EventDTO> result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getEventsByUser(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
		}
	}
	
	@Test
	public void getUserShouldReturnOneUser(){

		String userId = "1";

		UserDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUser(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getId());
		}
	}
	
	@Test
	public void updateProfileImageShouldReturnConfirm(){
		
		String imageLocation ="C:\\Users\\Luca\\fff\\imagetest.jpg";
		String userId = "1";
		
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.updateProfileImage(userId, imageLocation, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier().isEmpty());
		}	

	}
	
	public static void main(String[] args) {
		UserServiceTest test = new UserServiceTest();
		test.updateProfileImageShouldReturnConfirm();
	}
	
}
