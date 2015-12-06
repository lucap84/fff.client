package it.fff.client.stub;


import java.util.ArrayList;

import javax.crypto.KeyAgreement;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import it.fff.client.secure.ClientSecureConfiguration;
import it.fff.client.util.DHUtils;
import it.fff.clientserver.common.dto.AuthDataResponseDTO;
import it.fff.clientserver.common.dto.RegistrationDataRequestDTO;
import it.fff.clientserver.common.dto.SessionDTO;
import it.fff.clientserver.common.dto.UpdatePasswordDTO;
import it.fff.clientserver.common.dto.WriteResultDTO;

public class SecurityServiceStub extends StubService{
	
	public SecurityServiceStub(){
		super();
	}
	
	public SecurityServiceStub(ClientSecureConfiguration secureConfiguration){
		super(secureConfiguration);
	}
	
	public AuthDataResponseDTO registerUser(RegistrationDataRequestDTO dtoInput, String mediaType, boolean enableSecurity){
		Client client = super.getClientInstance();
		
		String deviceId = "android-mobile-0001";
		super.getSecureConfiguration().setDeviceId(deviceId); //lo imposto perché viene ricercato nel filtro prima che la chiamata sia partita
		
		AuthDataResponseDTO resultDTO = null;

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_registerUser);
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		try{
			KeyAgreement clientKeyAgree = null;
			DHUtils dhUtil = null;
			if(enableSecurity){
				clientKeyAgree = KeyAgreement.getInstance("DH");
				dhUtil = new DHUtils();
				String clientPpublicKey = dhUtil.generateClientPublicKey(clientKeyAgree);
				requestBuilder = requestBuilder.header("dh", clientPpublicKey);
			}
			
			Response response = requestBuilder.post(Entity.entity(dtoInput, mediaType));
			
			resultDTO = (AuthDataResponseDTO)response.readEntity(AuthDataResponseDTO.class);
			
			String sharedSecret = null;
			if(enableSecurity){
				byte[] serverPublicKey =  Base64.decodeBase64(resultDTO.getServerPublicKey());
				 sharedSecret = dhUtil.generateSharedSecret(clientKeyAgree, serverPublicKey);			
			}
			//Salvo sul client la chiave segreta condivisa con il server
			Integer userId = Integer.valueOf(resultDTO.getUserId());
			super.getSecureConfiguration().storeSharedKey(userId, deviceId, sharedSecret);
        
		}
		catch(Exception e){
			e.printStackTrace();
		}		        
		
		return resultDTO;
	}

	public WriteResultDTO logout(String mediaType){
		
		Client client = super.getClientInstance();
		
		String userId = super.getSecureConfiguration().getUserId();
		String deviceId = super.getSecureConfiguration().getDeviceId();
		
		WriteResultDTO result = null;	
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_logout, userId);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		
		Response response = requestBuilder.post(null);
		
		result = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		if(result!=null && result.isOk() && result.getIdentifier()!=null && !"".equals(result.getIdentifier())){
			Integer userIdInt = Integer.valueOf(userId);
			super.getSecureConfiguration().removeSharedKey(userIdInt, deviceId);
		}
		
		return result;
	}
	
	public AuthDataResponseDTO login(SessionDTO dtoInput, String mediaType, boolean enableSecurity){
		Client client = super.getClientInstance();
		
		String deviceId = super.getSecureConfiguration().getDeviceId();
		
		AuthDataResponseDTO resultDTO = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_login);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		try{
			KeyAgreement clientKeyAgree = null;
			DHUtils dhUtil = null;
			if(enableSecurity){
				clientKeyAgree = KeyAgreement.getInstance("DH");
				dhUtil = new DHUtils();
				String clientPpublicKey = dhUtil.generateClientPublicKey(clientKeyAgree);
				requestBuilder = requestBuilder.header("dh", clientPpublicKey);
			}
			
			Response responseJSON = requestBuilder.post(Entity.entity(dtoInput, mediaType));
			
			resultDTO = (AuthDataResponseDTO)responseJSON.readEntity(AuthDataResponseDTO.class);
			
			String sharedSecret = null;
			if(enableSecurity){
				byte[] serverPublicKey =  Base64.decodeBase64(resultDTO.getServerPublicKey());
				sharedSecret = dhUtil.generateSharedSecret(clientKeyAgree, serverPublicKey);			
			}
			//Salvo sul client la chiave segreta condivisa con il server
			Integer userId = Integer.valueOf(resultDTO.getUserId());
			super.getSecureConfiguration().storeSharedKey(userId, deviceId, sharedSecret);				
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

		
		return resultDTO;
	}
	
	public WriteResultDTO updatePassword(UpdatePasswordDTO dtoInput, String mediaType){
		Client client = super.getClientInstance();
		
		dtoInput.setOldPassword(DigestUtils.md5Hex(dtoInput.getOldPassword()));
		dtoInput.setNewPassword(DigestUtils.md5Hex(dtoInput.getNewPassword()));
		
		WriteResultDTO writeResult = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_PUT_updatePassword,dtoInput.getEmail());
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.put(Entity.entity(dtoInput,mediaType));
		writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;
	}
	
	public WriteResultDTO checkVerificationCode(String email, String verificationCode, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_PUT_checkVerificationCode,email);
		WriteResultDTO writeResult = null;
		
		Builder requestBuilder  =  client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.put(Entity.entity(verificationCode,mediaType));
		writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;		
	}
	
	public WriteResultDTO sendVerificationCode(String email, String mediaType){
		Client client = super.getClientInstance();
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_sendVerificationCode,email);
		WriteResultDTO writeResult = null;
		
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.post(null);
		writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;
	}
}
