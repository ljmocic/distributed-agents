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

import beans.Group;
import beans.Message;
import beans.User;
import services.AuthorizationService;
import services.GroupServiceProxy;
import services.MessageServiceProxy;
import services.UserServiceProxy;
import websocket.command.Command;
import websocket.command.FriendCommand;
import websocket.command.GroupCommand;
import websocket.command.MessageCommand;
import websocket.command.UserCommand;

@ServerEndpoint("/websocket")
@Startup
@Singleton
@LocalBean
public class WebsocketEndpoint {
	
	@EJB
	UserServiceProxy userService;
	
	@EJB
	GroupServiceProxy groupService;
	
	@EJB
	MessageServiceProxy messageService;
	
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

	@SuppressWarnings("unlikely-arg-type")
	@OnMessage
	public void echoTextMessage(Session session, String message, boolean last) {
		if(session.isOpen()) {
			Gson gson = new GsonBuilder().create();
			Command command = gson.fromJson(message, Command.class);
			
			switch (command.getType()) {
				case "LOGIN":
					UserCommand loginCommand = gson.fromJson(message, UserCommand.class);
					System.out.println("Trying to login through Websockets!");
					System.out.println(loginCommand.getUsername() + loginCommand.getPassword());
					String responseLogin = userService.getRest().loginUser(loginCommand.getUsername(), loginCommand.getPassword());
					String debugLogin = responseLogin.equals("User succesfully logged in") ? "Logged in!" : "Not logged in!";
					
					if(debugLogin.equals("Logged in!")) {
						try {
							loggedInSessions.put(loginCommand.getUsername(), session);
							session.getBasicRemote().sendText(debugLogin);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				
				case "LOGOUT":
					UserCommand logoutCommand = gson.fromJson(message, UserCommand.class);
					System.out.println("Trying to logout through Websockets!");
					String responseLogout = userService.getRest().logoutUser(logoutCommand.getUsername());
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
	
				case "GROUP":
					GroupCommand groupCommand = gson.fromJson(message, GroupCommand.class);
					System.out.println("Trying to do something with groups through Websockets!");
					
					
					if(groupCommand.getAction().equals("CREATE")) {
						Group group = new Group();
						group.setId(null);
						group.setAdmin(groupCommand.getAdmin());
						group.setMembers(groupCommand.getMembers());
						group.setName(groupCommand.getName());
						groupService.getRest().createGroup(group);
					}
					if(groupCommand.getAction().equals("DELETE")) {
						groupService.getRest().deleteGroup(groupCommand.getName());
					}
					if(groupCommand.getAction().equals("UPDATEMEMBERS")) {
						Group group = new Group();
						group.setId(null);
						group.setMembers(groupCommand.getMembers());
						group.setName(groupCommand.getName());
						groupService.getRest().updateGroup(group, group.getName());
					}
					if(groupCommand.getAction().equals("GETGROUPS")) {
						List<Group> groups = groupService.getRest().getGroups();
						try {
							session.getBasicRemote().sendText(gson.toJson(groups));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					break;
					
				case "FRIEND":
					FriendCommand friendCommand = gson.fromJson(message, FriendCommand.class);
					
					if(friendCommand.getAction().equals("ADDFRIEND")) {
						userService.getRest().addFriend("testUsername", friendCommand.getFriendToAdd());
						System.out.println("testUsername " + friendCommand.getFriendToAdd());
					}
					if(friendCommand.getAction().equals("REMOVEFRIEND")) {
						userService.getRest().removeFriend("testUsername", friendCommand.getFriendToRemove());
						System.out.println("testUsername " + friendCommand.getFriendToRemove());
					}
					
					if(friendCommand.getAction().equals("GETFRIENDS")) {
						List<User> user = userService.getRest().getAllFriendsOf("testUsername");
						try {
							session.getBasicRemote().sendText(gson.toJson(user));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				
				case "MESSAGE":
					MessageCommand messageCommand = gson.fromJson(message, MessageCommand.class);
					
					
					if(messageCommand.getAction().equals("MESSAGEUSER")) {
						Message m = new Message();
						m.setId(null);
						m.setSender(new User("testUsername", ""));
						List<User> receivers = new ArrayList<User>();
						receivers.add(new User(messageCommand.getUserToMessage(), ""));
						m.setReceivers(receivers);
						m.setContent(messageCommand.getMessage());
						messageService.getRest().createMessage(m);
						
					}
					if(messageCommand.getAction().equals("MESSAGEGROUP")) {
						Message m = new Message();
						m.setId(null);
						m.setSender(new User("testUsername", ""));
						System.out.println(messageCommand.getGroupToMessage());
						Group groupToMessage = groupService.getRest().findGroup(messageCommand.getGroupToMessage());
						m.setReceivers(groupToMessage.getMembers());
						m.setContent(messageCommand.getMessage());
						messageService.getRest().createMessage(m);
					}
					if(messageCommand.getAction().equals("GETUSERMESSAGES")) {
						List<Message> messages = messageService.getRest().getMessage("testUsername");
						try {
							session.getBasicRemote().sendText(gson.toJson(messages));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}if(messageCommand.getAction().equals("GETGROUPMESSAGES")) {
						List<Message> messages = messageService.getRest().getMessageForGroup(messageCommand.getGroupToMessage());
						try {
							session.getBasicRemote().sendText(gson.toJson(messages));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					break;
					
				default:
					break;
			}
			
		}
	}

	@SuppressWarnings("unlikely-arg-type")
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

	@SuppressWarnings("unlikely-arg-type")
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