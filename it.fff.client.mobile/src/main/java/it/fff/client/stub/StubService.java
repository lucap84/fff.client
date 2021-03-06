package it.fff.client.stub;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ContextResolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.moxy.xml.MoxyXmlFeature;

import it.fff.client.filter.AuthorizationClientRequestFilter;
import it.fff.client.secure.ClientSecureConfiguration;
import it.fff.client.util.ClientConfiguration;
import it.fff.client.util.ClientConstants;


public class StubService{
	private static final Logger logger = LogManager.getLogger(StubService.class);


	public static final String WSRS_PATH_GET_getEventMessages 			= "events/#/messages/";
	public static final String WSRS_PATH_POST_postEventStandardMessage	= "events/#/attendances/#/messages/standard/#/";
	public static final String WSRS_PATH_POST_postEventMessage 			= "events/#/attendances/#/messages/";
	public static final String WSRS_PATH_POST_addFeedback 				= "events/#/attendances/#/feedback/";
	public static final String WSRS_PATH_POST_joinEvent 				= "events/#/attendances/";
	public static final String WSRS_PATH_DELETE_cancelEvent 			= "events/#/";
	public static final String WSRS_PATH_POST_createEvent 				= "events/";
	public static final String WSRS_PATH_GET_getAttendacesByEvent 		= "events/#/attendances/";
	public static final String WSRS_PATH_GET_getEvent 					= "events/#/";
	public static final String WSRS_PATH_GET_searchEvents 				= "events/";
	public static final String WSRS_PATH_GET_getPlacesByDescription 	= "places/";
	public static final String WSRS_PATH_GET_getCityByName 				= "places/cities/#/";
	public static final String WSRS_PATH_POST_upgradeToPremium 			= "premium/subscriptions/#/";
	public static final String WSRS_PATH_POST_registerUser 				= "security/registration/";
	public static final String WSRS_PATH_POST_login 					= "security/login/";
	public static final String WSRS_PATH_POST_logout 					= "security/#/logout/";
	public static final String WSRS_PATH_PUT_updatePassword 			= "security/#/password/";
	public static final String WSRS_PATH_PUT_resetPassword 				= "security/#/password/reset/";
	public static final String WSRS_PATH_PUT_checkVerificationCode 		= "security/#/verificationcode/";
	public static final String WSRS_PATH_POST_sendVerificationCode 		= "security/#/verificationcode/";
	public static final String WSRS_PATH_PUT_modifyUserData 			= "users/#/";
	public static final String WSRS_PATH_PUT_setCurrentPosition 		= "users/#/position/";
	public static final String WSRS_PATH_GET_getEventsByUser 			= "users/#/events/";
	public static final String WSRS_PATH_DELETE_cancelAttendance 		= "users/#/events/#/attendances/";
	public static final String WSRS_PATH_GET_getUser 					= "users/#/";
	public static final String WSRS_PATH_GET_getUserFeedbacks			= "users/#/feedbacks/";
	public static final String WSRS_PATH_GET_getUserAttendances			= "users/#/attendances/";
	public static final String WSRS_PATH_GET_getFacebookUserData		= "users/fb/";
	public static final String WSRS_PATH_GET_getUserAccountByFacebookId	= "users/fb/#/account/";
	public static final String WSRS_PATH_GET_getUserAccountByEmail		= "users/emails/#/account/";
	public static final String WSRS_PATH_GET_isExistingEmail			= "users/emails/#/";
	public static final String WSRS_PATH_POST_updateProfileImage 		= "users/#/images/";
	public static final String WSRS_PATH_GET_getProfileImage 			= "users/#/images/";
	public static final String WSRS_PATH_GET_getAllLanguages 			= "typological/languages/";
	public static final String WSRS_PATH_GET_getAllSubscriptionTypes 	= "typological/subscriptionTypes/";
	public static final String WSRS_PATH_GET_getAllAchievementTypes 	= "typological/achievementTypes/";
	public static final String WSRS_PATH_GET_getAllStandardMessages 	= "typological/standardMessages/";
	public static final String WSRS_PATH_GET_getAllEventCategories 		= "typological/eventCategories/";
	public static final String WSRS_PATH_GET_getAllNations 				= "typological/nations/";
	


	private ClientSecureConfiguration secureConfiguration;
	private static Boolean isSecure;

	public StubService(ClientSecureConfiguration secureConfiguration){
		this.secureConfiguration = secureConfiguration;
	}	
	
	public StubService(){
		this(ClientSecureConfiguration.getInstance());
	}
	
	private static Client client;


	
	public static Client getClientInstance(){
		if(client!=null){
			return client;
		}
		client = ClientBuilder.newBuilder().build();
		
//		client.register(AttendanceDTOMessageBodyRW.class);
//		client.register(CreateUserDTOMessageBodyRW.class);
//		client.register(EventDTOMessageBodyRW.class);
//		client.register(PlaceDTOMessageBodyRW.class);
//		client.register(UserDTOMessageBodyRW.class);
//		client.register(WriteResultDTOMessageBodyRW.class);
		
		//features
		client.register(MultiPartFeature.class);
		client.register(MoxyJsonFeature.class);
		client.register(MoxyXmlFeature.class);
		client.register(AuthorizationClientRequestFilter.class);
		ContextResolver<MoxyJsonConfig> jsonConfigResolver = getJsonConfigResolver();
		client.register(jsonConfigResolver);
		
		return client;
	}


	private static ContextResolver<MoxyJsonConfig> getJsonConfigResolver() {
		final Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
		namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		 
		final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig()
//		            .setNamespacePrefixMapper(namespacePrefixMapper)
//		            .setNamespaceSeparator(':')
		            ;
		 
		final ContextResolver<MoxyJsonConfig> jsonConfigResolver = moxyJsonConfig.resolver();
		
		return jsonConfigResolver;
	}

	protected static URI getBaseURI() {
		Properties clientConfigurationProperties = ClientConfiguration.getInstance().getClientConfigurationProperties();
		String uri = clientConfigurationProperties.getProperty(ClientConstants.PROP_SERVER_REST_URI);
		return UriBuilder.fromUri(uri).build();
	}

	
	public ClientSecureConfiguration getSecureConfiguration() {
		return secureConfiguration;
	}


	public void setSecureConfiguration(ClientSecureConfiguration secureConfiguration) {
		this.secureConfiguration = secureConfiguration;
	}

	public String getWsRspath(String mediaType, String servicePath, String... params){
		String path = servicePath;
		for (String param : params) {
			path = path.replaceFirst("#", param);
		}
		path += mediaType.toLowerCase().substring("application/".length());
		return path;
	}
	
	public static boolean isSecurityEnabled(){
		if(StubService.isSecure==null){
			Properties clientConfigurationProperties = ClientConfiguration.getInstance().getClientConfigurationProperties();
			String securityEnabled = clientConfigurationProperties.getProperty(ClientConstants.PROP_SECURITY_ENABLED);
			StubService.isSecure = "1".equals(securityEnabled);		
		}
		return StubService.isSecure;
	}

}
