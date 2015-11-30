package it.fff.client.secure;

import java.security.SecureRandom;
import java.util.Properties;

import it.fff.client.util.ClientConfiguration;
import it.fff.client.util.ClientConstants;
import it.fff.clientserver.common.secure.DHSecureConfiguration;

public class ClientSecureConfiguration implements DHSecureConfiguration {

	private static ClientSecureConfiguration secureConfiguration;
	
	public static SecureRandom SECURE_RANDOM = new SecureRandom();
	
	private String userId;
	private String deviceId;
	private String sharedKey;
	
	private ClientSecureConfiguration(){
		loadSecureConfiguration();
	}
	
	public static ClientSecureConfiguration getInstance() {
		if(secureConfiguration==null){
			secureConfiguration = new ClientSecureConfiguration();
		}
		return secureConfiguration;
	}
	
	@Override
	public void removeSharedKey(Integer userId, String deviceId) {
		storeProperties(userId, deviceId, "");
	}

	@Override
	public void storeSharedKey(Integer userId, String deviceId, String sharedKey) {
		storeProperties(userId, deviceId, sharedKey);
	}
	
	private void loadSecureConfiguration(){
		ClientConfiguration clientConfiguration = ClientConfiguration.getInstance();
		Properties secureConfigurationProperties = clientConfiguration.loadSecureConfigurationProperties();
		// get the property value and print it out
		this.userId = secureConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_USER);
		this.deviceId = secureConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_DEVICE);
		this.sharedKey = secureConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_SHAREDKEY);
	}	
	
	public void storeProperties(Integer userId, String deviceId, String sharedKey) {

		ClientConfiguration clientConfiguration = ClientConfiguration.getInstance();
		clientConfiguration.storeSecureConfigurationProperties(String.valueOf(userId), deviceId, sharedKey);
		
		this.userId = String.valueOf(userId);
		this.deviceId = deviceId;
		this.sharedKey = sharedKey;

	}
	


	@Override
	public String retrieveSharedKey(Integer userId, String deviceId) {
		String userIdStr = String.valueOf(userId);
		if(this.userId.equals(userIdStr) && this.deviceId.equals(deviceId)){
			return this.sharedKey;
		}
		return "";
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSharedKey() {
		return sharedKey;
	}

	

}
