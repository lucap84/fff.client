package it.fff.client.test.stub;

import java.io.File;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.fff.client.util.ClientConfiguration;
import it.fff.client.util.ClientConstants;
import it.fff.clientserver.common.util.FlokkerUtils;

public class WebServiceRestTest{
	
	private String pathForJsonDTO;
	private ObjectMapper jsonMapper;
	
	public WebServiceRestTest(){
		pathForJsonDTO = ClientConfiguration.getInstance().getClientConfigurationProperties().getProperty(ClientConstants.PROP_DTO_JSON_DIR);
		this.createDirIfNotExists(pathForJsonDTO);
		this.jsonMapper = new ObjectMapper();
	}

	public void saveJsonResult(Object result, String filename) {
		try {
			String jsonString = this.jsonMapper.writeValueAsString(result);
			
			File f = new File(this.pathForJsonDTO+filename+".json");
			FlokkerUtils.writeStringToFile(f,jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private boolean createDirIfNotExists(String dirPath) {
		boolean exists = false;
		File dir = new File(dirPath);
		exists = dir.exists();
		if(!exists){
			try{
				exists = dir.mkdir();
			}
			catch(SecurityException se){
				exists = false;
			}
		}
		return exists;
	}	
}
