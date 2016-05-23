package it.fff.client.test.stub;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.fff.client.util.ClientConfiguration;
import it.fff.client.util.ClientConstants;
import it.fff.clientserver.common.util.FlokkerUtils;

public class WebServiceRestTest{
	
	private static final Logger logger = LogManager.getLogger(WebServiceRestTest.class);

	private String pathForJsonDTO;
	private ObjectMapper jsonMapper;
	
	public WebServiceRestTest(){
		pathForJsonDTO = ClientConfiguration.getInstance().getClientConfigurationProperties().getProperty(ClientConstants.PROP_DTO_JSON_DIR);
		this.createDirIfNotExists(pathForJsonDTO);
		this.jsonMapper = new ObjectMapper();
		logger.info("WebServiceRestTest inizializzato. Path per JSON files: "+pathForJsonDTO);
	}

	public void saveJsonResult(Object result, String filename) {
		try {
			String jsonString = this.jsonMapper.writeValueAsString(result);
			
			String completeFilename = filename+".json";
			
			File f = new File(this.pathForJsonDTO+completeFilename);
			FlokkerUtils.writeStringToFile(f,jsonString);
			logger.info("JSON risultato salvato su file: "+completeFilename);
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
