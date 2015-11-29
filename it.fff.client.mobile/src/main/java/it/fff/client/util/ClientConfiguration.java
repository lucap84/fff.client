package it.fff.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ClientConfiguration {
	
	private static ClientConfiguration clientConfiguration;
	private Properties clientConfigurationProperties;
	
	private ClientConfiguration(){
		this.clientConfigurationProperties = this.loadClientConfigurationProperties(ClientConstants.CLIENT_CONFIG_FILENAME);
	}
	
	public static ClientConfiguration getInstance(){
		if(clientConfiguration==null){
			clientConfiguration = new ClientConfiguration();
		}
		return clientConfiguration;
	}
	
	public Properties loadSecureConfigurationProperties(){
		String secureFilePath = clientConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_FILEPATH);
		Properties secureConfigurationProperties = this.loadSecureConfigurationProperties(secureFilePath);
		return secureConfigurationProperties;		
	}

	public Properties getClientConfigurationProperties(){
		if(clientConfigurationProperties==null){
			this.clientConfigurationProperties = this.loadClientConfigurationProperties(ClientConstants.CLIENT_CONFIG_FILENAME);
		}
		return clientConfigurationProperties;
	}
	
	public void storeSecureConfigurationProperties(String userId, String deviceId, String sharedKey){
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			String secureFilePath = clientConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_FILEPATH);
			File secureConfigurationFile = getSecureConfigFile(secureFilePath);
			output = new FileOutputStream(secureConfigurationFile);

			// set the properties value
			prop.setProperty(ClientConstants.PROP_SECURE_USER, userId==null?"":userId);
			prop.setProperty(ClientConstants.PROP_SECURE_DEVICE, deviceId==null?"":deviceId);
			prop.setProperty(ClientConstants.PROP_SECURE_SHAREDKEY, sharedKey==null?"":sharedKey);

			// save properties to project root folder
			prop.store(output, null);
			

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}			
	}
	
	
	private Properties loadClientConfigurationProperties(String confFilename){
		Properties prop = new Properties();
		InputStream input = null;

		try {
//			File file = new File(confFilename);
//			if(!file.exists()) {
//				System.out.println("file inesistente!");
//			}
//			input = new FileInputStream(file);

			// load a properties file
//			prop.load(input);
			prop.load(getClass().getResourceAsStream("/"+confFilename));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
	}
	
	private Properties loadSecureConfigurationProperties(String secureConfFilePath){
		Properties prop = new Properties();
		InputStream input = null;

		try {
			File secureConfigurationFile = getSecureConfigFile(secureConfFilePath);
			input = new FileInputStream(secureConfigurationFile);

			// load a properties file
			prop.load(input);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return prop;
	}	
	
	private File getSecureConfigFile(String filePath){
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				file = this.createNewSecureFile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	private File createNewSecureFile() throws FileNotFoundException, UnsupportedEncodingException {
		String secureFilePath = clientConfigurationProperties.getProperty(ClientConstants.PROP_SECURE_FILEPATH);

		PrintWriter writer = new PrintWriter(secureFilePath, "UTF-8");
		writer.println(ClientConstants.PROP_SECURE_USER+"=");
		writer.println(ClientConstants.PROP_SECURE_DEVICE+"=");
		writer.println(ClientConstants.PROP_SECURE_SHAREDKEY+"=");
		writer.close();
		
		return new File(secureFilePath);
		
	}	

}
