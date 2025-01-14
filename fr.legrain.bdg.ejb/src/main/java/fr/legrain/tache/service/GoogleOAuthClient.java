//package fr.legrain.tache.service;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.DateTime;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.calendar.CalendarScopes;
//import com.google.api.services.calendar.model.Event;
//import com.google.api.services.calendar.model.Events;
//
//import fr.legrain.droits.service.TenantInfo;
//
//@Stateless
//public class GoogleOAuthClient {
//	
//    /** Application name. */
//    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
//
//    /** Directory to store user credentials for this application. */
//    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/calendar-java-quickstart");
//
//    /** Global instance of the {@link FileDataStoreFactory}. */
//    private static FileDataStoreFactory DATA_STORE_FACTORY;
//
//    /** Global instance of the JSON factory. */
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    /** Global instance of the HTTP transport. */
//    private static HttpTransport HTTP_TRANSPORT;
//
//    /** Global instance of the scopes required by this quickstart.
//     *
//     * If modifying these scopes, delete your previously saved credentials
//     * at ~/.credentials/calendar-java-quickstart
//     */
//    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY);
//
//    static {
//        try {
//            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
//        } catch (Throwable t) {
//            t.printStackTrace();
//            System.exit(1);
//        }
//    }
//
//	private String url;
//	@Inject private TenantInfo tenantInfo;
//	
//	@PostConstruct
//	public void init() {
//		
//	}
//	
//	public Credential authorize() throws IOException {
//		try { 
//			
//	        // Load client secrets.
//	        InputStream in = GoogleOAuthClient.class.getResourceAsStream("client_secret.json");
//	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//	
//	        // Build flow and trigger user authorization request.
//	        GoogleAuthorizationCodeFlow flow =
//	                new GoogleAuthorizationCodeFlow.Builder(
//	                		HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)       
//	                .setDataStoreFactory(DATA_STORE_FACTORY)
//	                .setAccessType("offline")
//	                .setClientId(tenantInfo.getTenantId())
//	                .build();
//	        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//	        System.out.println( "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
//	        return credential;
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//    }
//	
//	public void testCalendar() {
//		try { 
//			//identification et création du service
//			Credential credential = authorize();
//			com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
//	                HTTP_TRANSPORT, JSON_FACTORY, credential)
//	                .setApplicationName(APPLICATION_NAME)
//	                .build();
//			
//			
//			
//			
//			
//			 // List the next 10 events from the primary calendar.
//	        DateTime now = new DateTime(System.currentTimeMillis());
//	        
//	        Events events = service.events().list("primary")
//	            .setMaxResults(10)
//	            .setTimeMin(now)
//	            .setOrderBy("startTime")
//	            .setSingleEvents(true)
//	            .execute();
//	        List<Event> items = events.getItems();
//	        
//	        if (items.size() == 0) {
//	            System.out.println("No upcoming events found.");
//	        } else {
//	            System.out.println("Upcoming events");
//	            for (Event event : items) {
//	                DateTime start = event.getStart().getDateTime();
//	                if (start == null) {
//	                    start = event.getStart().getDate();
//	                }
//	                System.out.printf("%s (%s)\n", event.getSummary(), start);
//	            }
//	        }
//			
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
