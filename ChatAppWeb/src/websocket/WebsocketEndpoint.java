package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
@Startup
@Singleton
@LocalBean
public class WebsocketEndpoint {

	Logger log = Logger.getLogger("[INFO] WebsocketEndpoint: ");

	static List<Session> sessions = new ArrayList<Session>();

	public WebsocketEndpoint() {

	}

	@OnOpen
	public void onOpen(Session session) {
		if (!sessions.contains(session)) {
			sessions.add(session);
			log.info("[INFO] Added session: " + session.getId());
		}
	}

	@OnMessage
	public void echoTextMessage(Session session, String message, boolean last) {
		try {
			if (session.isOpen()) {
				log.info("[INFO] Received message: " + message + " from session: " + session.getId());
				for (Session s : sessions) {
					if (s.isOpen()) {
						s.getBasicRemote().sendText(message, true);
					}
				}
				log.info("[INFO] Message sent to all active sessions");
			}
		} catch (IOException e) {
			try {
				session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@OnClose
	public void close(Session session) {
		sessions.remove(session);
		log.info("[CLOSE] Closed session: " + session.getId());
	}

	@OnError
	public void error(Session session, Throwable t) {
		sessions.remove(session);
		log.info("[ERROR] " + session.getId());
		t.printStackTrace();
	}
}