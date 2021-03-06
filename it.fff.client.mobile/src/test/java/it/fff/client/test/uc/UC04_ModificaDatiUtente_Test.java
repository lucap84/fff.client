package it.fff.client.test.uc;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import it.fff.client.stub.SecurityServiceStub;
import it.fff.client.stub.TypologicalServiceStub;
import it.fff.client.stub.UserServiceStub;
import it.fff.clientserver.common.dto.LanguageDTO;
import it.fff.clientserver.common.dto.NationDTO;
import it.fff.clientserver.common.dto.UserDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;
import it.fff.clientserver.common.enums.UserSexEnum;

public class UC04_ModificaDatiUtente_Test {
	
	@Test
	public void test(){//Modifica i dati utente
		/*
		 * Preconditions:   Login (oppure Registrazione)
		 */
		
		/*
		 * Modifica la propria immagine
		 * Modifica i propri dati
		 */		
		UserServiceStub userService = new UserServiceStub();
		
		//Carica una nuova immagine profilo
		String imageLocation ="C:\\Users\\Luca\\fff\\client\\imagetest.jpg";
		int userId = Integer.valueOf(userService.getSecureConfiguration().getUserId());
		
		WriteResultDTO updateProfileImageResult = userService.updateProfileImage(userId, imageLocation, MediaType.APPLICATION_JSON);
		assertNotNull(updateProfileImageResult);
		assertTrue(updateProfileImageResult.isOk());
		assertTrue(updateProfileImageResult.getAffectedRecords()>0);
		assertNotNull(updateProfileImageResult.getIdentifier());
		assertFalse(updateProfileImageResult.getIdentifier()<=0);
		
		//Modifica i propri dati
		UserDTO  user = new UserDTO();
		user.setId(userId);
//		user.setEmail("nuovamail@mail.it");
		user.setSesso(UserSexEnum.F);
		user.setDataNascita("1900-01-01");
		user.setNome("Nome mod");
		user.setCognome("cognome mod");
		user.setDescrizione("Descrizione mod");
		
		TypologicalServiceStub typologicalService = new TypologicalServiceStub();
		List<NationDTO> allNations = typologicalService.getAllNations(MediaType.APPLICATION_JSON);
		NationDTO nazionalita = allNations.get(0); //scelgo nazione
		user.setNazionalita(nazionalita);
		
		List<LanguageDTO> allLanguages = typologicalService.getAllLanguages(MediaType.APPLICATION_JSON);
		List<LanguageDTO> lingue = new ArrayList<LanguageDTO>();
		lingue.add(allLanguages.get(0));//Scelgo lingue
		lingue.add(allLanguages.get(1));
		user.setLingue(lingue);
		
		//TODO info impostate in modo silente dal sistema
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		user.setLastPositionDate(df.format(new Date()));
		user.setLastPositionLat("1.234");
		user.setLastPositionLong("2.345");
		
		WriteResultDTO modifyUserDataResult = userService.modifyUserData(user, MediaType.APPLICATION_JSON);
		assertNotNull(modifyUserDataResult);
		assertTrue(modifyUserDataResult.isOk());
		assertTrue(modifyUserDataResult.getAffectedRecords()>0);
		assertNotNull(modifyUserDataResult.getIdentifier());
		assertFalse(modifyUserDataResult.getIdentifier()<=0);
		
		
		/*
		 * Postconditions:  i dati utente sono cambiati
		 */
		
		UserDTO userCheck = userService.getUser(userId, MediaType.APPLICATION_JSON);
//		assertEquals(user.getEmail(),userCheck.getEmail());
		assertEquals(user.getSesso(),userCheck.getSesso());
		assertEquals(user.getDataNascita(),userCheck.getDataNascita());
		assertEquals(user.getNome(),userCheck.getNome());
		assertEquals(user.getCognome(),userCheck.getCognome());
		assertEquals(user.getDescrizione(),userCheck.getDescrizione());

	}
}
