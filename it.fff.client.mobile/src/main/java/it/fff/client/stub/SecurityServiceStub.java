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
import it.fff.clientserver.common.dto.LoginInputDTO;
import it.fff.clientserver.common.dto.RegistrationInputDTO;
import it.fff.clientserver.common.dto.ResetPasswordDTO;
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
	
	public AuthDataResponseDTO registerUser(RegistrationInputDTO dtoInput, String mediaType){
		Client client = super.getClientInstance();
		
		String deviceId = "android-mobile-0001";
		super.getSecureConfiguration().setDeviceId(deviceId); //lo imposto perch� viene ricercato nel filtro prima che la chiamata sia partita
		
		AuthDataResponseDTO resultDTO = null;

		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_registerUser);
		Builder requestBuilder = client.target(getBaseURI()).path(restPath).request(mediaType);
		try{
			KeyAgreement clientKeyAgree = null;
			DHUtils dhUtil = null;
			if(StubService.isSecurityEnabled()){
				clientKeyAgree = KeyAgreement.getInstance("DH");
				dhUtil = new DHUtils();
				String clientPpublicKey = dhUtil.generateClientPublicKey(clientKeyAgree);
				requestBuilder = requestBuilder.header("dh", clientPpublicKey);
			}
			
			Response response = requestBuilder.post(Entity.entity(dtoInput, mediaType));
			
			resultDTO = (AuthDataResponseDTO)response.readEntity(AuthDataResponseDTO.class);
			
			String sharedSecret = null;
			if(StubService.isSecurityEnabled()){
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

	public WriteResultDTO logout(int userId, String deviceId, String mediaType){
		
		Client client = super.getClientInstance();
		
		WriteResultDTO result = null;	
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_logout, String.valueOf(userId));
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		
		Response response = requestBuilder.post(null);
		
		result = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		if(result!=null && result.isOk() && result.getIdentifier()>0){
			Integer userIdInt = Integer.valueOf(userId);
			super.getSecureConfiguration().removeSharedKey(userIdInt, deviceId);
		}
		
		return result;
	}
	
	public AuthDataResponseDTO login(LoginInputDTO loginInfo, String mediaType){
		Client client = super.getClientInstance();
		
		String deviceId = super.getSecureConfiguration().getDeviceId();
		
		AuthDataResponseDTO resultDTO = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_POST_login);
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		try{
			KeyAgreement clientKeyAgree = null;
			DHUtils dhUtil = null;
			if(StubService.isSecurityEnabled()){
				clientKeyAgree = KeyAgreement.getInstance("DH");
				dhUtil = new DHUtils();
				String clientPpublicKey = dhUtil.generateClientPublicKey(clientKeyAgree);
				requestBuilder = requestBuilder.header("dh", clientPpublicKey);
			}
			
			Response responseJSON = requestBuilder.post(Entity.entity(loginInfo, mediaType));
			
			resultDTO = (AuthDataResponseDTO)responseJSON.readEntity(AuthDataResponseDTO.class);
			
			String sharedSecret = null;
			if(resultDTO.isOk()){
				if(StubService.isSecurityEnabled()){
					byte[] serverPublicKey =  Base64.decodeBase64(resultDTO.getServerPublicKey());
					sharedSecret = dhUtil.generateSharedSecret(clientKeyAgree, serverPublicKey);			
				}
				//Salvo sul client la chiave segreta condivisa con il server
				Integer userId = Integer.valueOf(resultDTO.getUserId());
				super.getSecureConfiguration().storeSharedKey(userId, deviceId, sharedSecret);
			}
			
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

	public WriteResultDTO resetPassword(ResetPasswordDTO resetPasswordInput, String mediaType) {
		Client client = super.getClientInstance();
		
		resetPasswordInput.setNewPassword(DigestUtils.md5Hex(resetPasswordInput.getNewPassword()));
		
		WriteResultDTO writeResult = null;
		
		String restPath = super.getWsRspath(mediaType, StubService.WSRS_PATH_PUT_resetPassword,resetPasswordInput.getEmail());
		Builder requestBuilder  = client.target(getBaseURI()).path(restPath).request(mediaType);
		Response response = requestBuilder.put(Entity.entity(resetPasswordInput,mediaType));
		writeResult = (WriteResultDTO)response.readEntity(WriteResultDTO.class);
		
		return writeResult;
	}
}
