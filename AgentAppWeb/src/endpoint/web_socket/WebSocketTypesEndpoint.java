package endpoint.web_socket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import model.AgentType;

@ServerEndpoint("/websocket/agent-types")
@Startup
@Singleton
@LocalBean
public class WebSocketTypesEndpoint {
	
	List<Session> connections;
	
	@OnOpen
	public void onOpen(Session session) {
		connections = new ArrayList<>();
		connections.add(session);
	}

	public void syncAgentTypes(Collection<AgentType> types) {
		if(connections != null) {
			for(Session session: connections) {
				Gson gson = new GsonBuilder().create();
				String message = gson.toJson(types);
				
				sendMessage(session, message, false);
			}
		}
	}
	
	@OnMessage
	public void sendMessage(Session session, String message, boolean last) {
		if(session.isOpen()) {
			try {
				session.getBasicRemote().sendText(message);
			}catch(Exception e) {
				
			}
		}
	}

	@OnClose
	public void close(Session session) {
		connections.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable t) {
	}
}
