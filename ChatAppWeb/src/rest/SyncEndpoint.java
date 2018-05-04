package rest;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.websocket.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import websocket.WebsocketEndpoint;

@Path("/sync")
@Stateless
public class SyncEndpoint {
	
	@GET
	@Path("/notify/{type}")
	@Produces(MediaType.TEXT_PLAIN)
	public String activateNode(@PathParam(value="type") String type) {
		System.out.println("Received notification to update");
		
		for (Session session : WebsocketEndpoint.loggedInSessions.values()) {
			try {
				session.getBasicRemote().sendText("REFRESH:"+type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "Received notification to update";
	}

}
