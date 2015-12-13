package it.fff.client.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.fff.client.secure.ClientSecureConfiguration;
import it.fff.clientserver.common.secure.AuthenticationUtil;
import it.fff.clientserver.common.secure.DHSecureConfiguration;

public class AuthorizationClientRequestFilter implements ClientRequestFilter {

	private static final Logger logger = LogManager.getLogger(AuthorizationClientRequestFilter.class);
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {

		String requestPath = requestContext.getUri().getPath();
		int indexOf = requestPath.indexOf("restapi/");
		indexOf = indexOf +"restapi/".length();
		requestPath = requestPath.substring(indexOf);
		
		String httpMethod = requestContext.getMethod();
		
		String formattedDate = DHSecureConfiguration.DATE_FORMATTER.format(new Date()); 
		ClientSecureConfiguration secureConfigurationInstance = ClientSecureConfiguration.getInstance();
		String deviceId = secureConfigurationInstance.getDeviceId();
		
		requestContext.getHeaders().add("Date", formattedDate);
		requestContext.getHeaders().add("Device", deviceId);		
		
		if(isToAuthorize(requestPath)){
			logger.debug("Request path is to authorize: "+requestPath);
			Integer userId = Integer.valueOf(secureConfigurationInstance.getUserId());
			
			if(userId==null){
				logger.error("Client is not registerd");
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Client is not registerd").build());
				return;
			}
			String retrievedSharedKey = secureConfigurationInstance.retrieveSharedKey(userId, deviceId);
			
			if(retrievedSharedKey==null || "".equals(retrievedSharedKey)){
				logger.error("Client is not logged");
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Client is not logged").build());
				return;
			}
			
			String nonce = new BigInteger(32,ClientSecureConfiguration.SECURE_RANDOM).toString();
			String authorizationHeader = AuthenticationUtil.generateHMACAuthorizationHeader(retrievedSharedKey, userId, httpMethod, requestPath, formattedDate, nonce);
			requestContext.getHeaders().add("Authorization", authorizationHeader);
		}
		
	}
	
	private boolean isToAuthorize(String requestPath) {
		boolean isToAuthorize = true;
		//Bisogna sempre procedere con l'autorizzazione, tranne che per registrarsi o per fare login
		isToAuthorize &= !requestPath.matches("^security/registration.*");
		isToAuthorize &= !requestPath.matches("^security/login.*");
		
		//servizi per reset password
		isToAuthorize &= !requestPath.matches("^security/.*/password/reset/.*");
		isToAuthorize &= !requestPath.matches("^security/.*/verificationcode/.*");		
		
		return isToAuthorize;
	}	
	

}
