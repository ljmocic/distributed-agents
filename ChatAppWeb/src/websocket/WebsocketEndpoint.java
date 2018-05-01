package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import services.AuthorizationService;
import services.UserServiceProxy;

@ServerEndpoint("/websocket")
@Startup
@Singleton
@LocalBean
public class WebsocketEndpoint {
	
	@EJB
	UserServiceProxy userService;
	
	@EJB
	AuthorizationService authService;

	Logger log = Logger.getLogger("[INFO] WebsocketEndpoint: ");
	
	private HashMap<String, Session> loggedInSessions = new HashMap<String, Session>();
	private List<Session> allActiveSessions = new ArrayList<Session>();

	public WebsocketEndpoint() {

	}

	@OnOpen
	public void onOpen(Session session) {
		if (!allActiveSessions.contains(session)) {
			allActiveSessions.add(session);
			log.info("[INFO] Added session: " + session.getId());
		}
	}

	@OnMessage
	public void echoTextMessage(Session session, String message, boolean last) {
		if(session.isOpen()) {
			Gson gson = new GsonBuilder().create();
			WebsocketCommand command = gson.fromJson(message, WebsocketCommand.class);
			
			switch (command.getType()) {
				case "LOGIN":
					System.out.println("Trying to login through Websockets!");
					System.out.println(command.getUsername() + command.getPassword());
					String responseLogin = userService.getRest().loginUser(command.getUsername(), command.getPassword());
					String debugLogin = responseLogin.equals("User succesfully logged in") ? "Logged in!" : "Not logged in!";
					
					if(debugLogin.equals("Logged in!")) {
						try {
							loggedInSessions.put(command.getUsername(), session);
							session.getBasicRemote().sendText(debugLogin);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				
				case "LOGOUT":
					System.out.println("Trying to logout through Websockets!");
					String responseLogout = userService.getRest().logoutUser(command.getUsername());
					String debugLogout = responseLogout.equals("User succesfully logged in") ? "Logged in!" : "Not logged in!";
					try {
						for(Session sessionToRemove : loggedInSessions.values()) {
							if(sessionToRemove.equals(session)) {
								loggedInSessions.remove(sessionToRemove);
								break;
							}
						}
						session.getBasicRemote().sendText(debugLogout);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
	
				case "MESSAGE":
					System.out.println("Trying to message through Websockets!");
					// sending to all logged in to websocket on this node
					for (Session sessionToNotify : loggedInSessions.values()) {
						try {
							sessionToNotify.getBasicRemote().sendText(command.getMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				default:
					break;
			}
			
		}
	}

	@OnClose
	public void close(Session sessionToRemove) {
		for(Session session : loggedInSessions.values()) {
			if(sessionToRemove.equals(session)) {
				loggedInSessions.remove(sessionToRemove);
				break;
			}
		}
		allActiveSessions.remove(sessionToRemove);
		log.info("[CLOSE] Closed session: " + sessionToRemove.getId());
	}

	@OnError
	public void error(Session sessionToRemove, Throwable t) {
		for(Session session : loggedInSessions.values()) {
			if(sessionToRemove.equals(session)) {
				loggedInSessions.remove(sessionToRemove);
				break;
			}
		}
		allActiveSessions.remove(sessionToRemove);
		log.info("[ERROR] " + sessionToRemove.getId());
		t.printStackTrace();
	}
}