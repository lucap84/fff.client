package it.fff.client.test.stub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import org.junit.Test;

import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.*;
import it.fff.clientserver.common.enums.FeedbackEnum;
import it.fff.clientserver.common.enums.UserSexEnum;
import it.fff.client.test.stub.WebServiceRestTest;

public class UserServiceTest extends WebServiceRestTest{


	public UserServiceTest(){
	}	
	

	@Test
	public void modifyUserDataShouldReturnConfirm(){
		
		UserDTO  user = new UserDTO();
		user.setId(1);
		user.setSesso(UserSexEnum.F);
		user.setDataNascita("1900-01-01");
		user.setNome("Nome mod");
		user.setCognome("cognome mod");
		user.setDescrizione("Descrizione mod");

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setEmail("nuovamail@mail.it");
		user.setAccount(accountDTO);
		
		NationDTO nazionalita = new NationDTO();
		nazionalita.setId(1);
		user.setNazionalita(nazionalita);
		
		LanguageDTO l1 = new LanguageDTO();
		l1.setId(1);
		LanguageDTO l2 = new LanguageDTO();
		l1.setId(2);
		List<LanguageDTO> lingue = new ArrayList<LanguageDTO>();
		lingue.add(l1);
		lingue.add(l2);
		user.setLingue(lingue);
		
		//impostate in modo silente dal sistema
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
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
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}		
		
	}	
	
	
	@Test
	public void getEventsByUserShouldReturnAtLeastOneEvent(){
		
		int userId = 2;

		List<EventDTO> result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getEventsByUser(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			assertNotNull(result.get(0));
			assertNotNull(result.get(0).getId());
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
	}
	
	@Test
	public void getUserShouldReturnOneUser(){

		int userId = 1;

		UserDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUser(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getId());
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	@Test
	public void updateProfileImageShouldReturnConfirm(){
		
		String imageLocation ="C:\\Users\\Luca\\fff\\client\\imagetest.jpg";
		int userId = 1;
		
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.updateProfileImage(userId, imageLocation, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}	

	}
	
	@Test
	public void getProfileImageShouldReturnAnImage(){
		
		int userId = 1;
		
		ProfileImageDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getProfileImage(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getFileName()!=null);
			assertNotNull(result.getImageAsB64());
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}	

	}	
	
	@Test
	public void cancelAttendanceShouldReturnConfirm(){//annulla partecipazione da parte di uno partecipante (abbandona evento)
		
		int eventId = 1;
		int userId = 1;
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.cancelAttendance(eventId, userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	@Test
	public void isExistingMailShouldReturnBoolean(){//controllo se email presente su DB
		
		String email = "lucap84@gmail.com";
		EmailInfoDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.isExistingEmail(email, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}	
	
//	@Test
	public void getFacebookUserDataShouldReturnOneUser(){

		String socialToken = "CAAOUWrdCLQkBAH2YWsYs2EBRymSTmbQ93LpWTcOozjqkM7Xxf67mnqz1isSwQTZBV5PnQxbHzpDs2KzQoNhiNBd9SHiZBPNSqrK55BCWGXevSRzQ343EQgFa2jneS6slqhA4eEmZC2ou1Pi1r4MvVHSuZCap89tD0Gck7ZCe1fDZAXlcPkMvZBuFWrZAjtjXCa2KS1fyg53ehQZDZD";
		int socialTokenExpires = 999999999; 
		UserDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getFacebookUserData(socialToken, socialTokenExpires, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertNotNull(result.getId());
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	@Test
	public void getUserFeedbacksShouldReturnListOfFeedbacks(){

		int userId = 1;

		List<FeedbackEnum> result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUserFeedbacks(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
	}
	
	@Test
	public void getUserAttendancesShouldReturnListOfAttendances(){

		int userId = 1;

		List<AttendanceDTO> result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUserAttendances(userId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.size()>0);
			
			super.saveJsonResult(result.get(0), result.get(0).getClass().getSimpleName());
		}
	}
	
	@Test
	public void getUserAccountByEmailShouldReturnOneAccount(){

		String email = "lucap84@gmail.com";
		
		AccountDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUserAccountByEmail(email, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertEquals(email, result.getEmail());
			assertTrue(result.isOk());
			assertNotNull(result.getId());
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	@Test
	public void getUserAccountByFacebookIdShouldReturnOneAccount(){

		long facebookId = 10209492373854191L;
		
		AccountDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.getUserAccountByFacebookId(facebookId, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertEquals(facebookId, result.getFacebookId());
			assertTrue(result.isOk());
			assertNotNull(result.getId());
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}
	}
	
	@Test
	public void setUserCurrentPositionShouldReturnConfirm(){
		
		int userId = 1;
		PlaceDTO currentPosition = new PlaceDTO();
		currentPosition.setGpsLat(1.001);
		currentPosition.setGpsLong(2.001);
		
		WriteResultDTO result = null;

		{//Test JSON
			UserServiceStub stub = new UserServiceStub();
			result = stub.setCurrentPosition(userId, currentPosition, MediaType.APPLICATION_JSON);
			assertNotNull(result);
			assertTrue(result.isOk());
			assertTrue(result.getAffectedRecords()>0);
			assertNotNull(result.getIdentifier());
			assertFalse(result.getIdentifier()<=0);
			
			super.saveJsonResult(result, result.getClass().getSimpleName());
		}	
	}	
	
	public static void main(String[] args) {
		UserServiceTest test = new UserServiceTest();
		test.updateProfileImageShouldReturnConfirm();
	}
	
}
