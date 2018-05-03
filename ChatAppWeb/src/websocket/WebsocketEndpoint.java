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
	
	public static HashMap<String, Session> loggedInSessions = new HashMap<String, Session>();
	private List<Session> allActiveSessions = new ArrayList<Session>();

	public WebsocketEndpoint() {
		System.out.println("LOADWS");
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
			Command command = gson.fromJson(message, Command.class);
			String loggedInUser = getUsernameFromSession(session);
			
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
					
					if(loggedInUser != null) {
						String responseLogout = userService.getRest().logoutUser(logoutCommand.getUsername());
						String debugLogout = responseLogout.equals("User succesfully logged in") ? "Logged in!" : "Not logged in!";
						try {
							handleLogout(session);
							session.getBasicRemote().sendText(debugLogout);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						System.out.println("WTF");
						try {
							session.getBasicRemote().sendText("User must be logged in!");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;

				case "REGISTER":
					UserCommand registerCommand = gson.fromJson(message, UserCommand.class);
					System.out.println("Trying to register through Websockets!");
					System.out.println(registerCommand.getUsername() + registerCommand.getPassword());
					
					User responseRegister = userService.getRest().createUser(registerCommand.getUsername(), registerCommand.getPassword());
					
					if(responseRegister != null) {
						try {
							loggedInSessions.put(registerCommand.getUsername(), session);
							session.getBasicRemote().sendText(gson.toJson(responseRegister));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
					
				case "USER":
					UserCommand getUserCommand = gson.fromJson(message, UserCommand.class);
					System.out.println("Trying to get user through Websockets!");
					System.out.println(getUserCommand.getUsername());
				
					User responseGetUser = userService.getRest().getUser(getUserCommand.getUsername());
					
					if(responseGetUser != null) {
						try {
							session.getBasicRemote().sendText(gson.toJson(responseGetUser));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
					
				case "USERS":
					System.out.println("Trying to get all users through Websockets!");
					List<User> responseGetUsers = userService.getRest().getAllUsers();
					
					if(responseGetUsers != null) {
						try {
							session.getBasicRemote().sendText(gson.toJson(responseGetUsers));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
					
				case "GROUP":
					GroupCommand groupCommand = gson.fromJson(message, GroupCommand.class);
					System.out.println("Trying to do something with groups through Websockets!");
					
					
					if(loggedInUser != null) {
						if(groupCommand.getAction().equals("CREATE")) {
							System.out.println("Trying to CREATE GROUP through Websockets!");
							Group group = new Group();
							group.setId(null);
							group.setAdmin(groupCommand.getAdmin());
							group.setMembers(groupCommand.getMembers());
							group.setName(groupCommand.getName());
							groupService.getRest().createGroup(group);
							try {
								session.getBasicRemote().sendText("GROUP CREATED");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(groupCommand.getAction().equals("DELETE")) {
							groupService.getRest().deleteGroup(groupCommand.getName());
							try {
								session.getBasicRemote().sendText("GROUP CREATED");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(groupCommand.getAction().equals("UPDATEMEMBERS")) {
							System.out.println("Trying to UPDATE GROUP through Websockets!");
							Group group = new Group();
							group.setId(null);
							group.setMembers(groupCommand.getMembers());
							group.setName(groupCommand.getName());
							group.setAdmin(groupCommand.getAdmin());
							groupService.getRest().updateGroup(group, group.getName());
							try {
								session.getBasicRemote().sendText("GROUP UPDATED");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(groupCommand.getAction().equals("GETGROUPS")) {
							System.out.println("Trying to get all GROUPS through Websockets!");
							List<Group> groups = groupService.getRest().getGroups();
							try {
								session.getBasicRemote().sendText(gson.toJson(groups));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(groupCommand.getAction().equals("GETGROUP")) {
							System.out.println("Trying to get GROUP through Websockets!");
							Group group = groupService.getRest().findGroup(groupCommand.getName());
							try {
								session.getBasicRemote().sendText(gson.toJson(group));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(groupCommand.getAction().equals("GETGROUPSOFUSER")) {
							System.out.println("Trying to get all GROUPS OF USER "+groupCommand.getAdminUsername()+" through Websockets!");
							List<Group> groups = groupService.getRest().getGroupsOfUser(groupCommand.getAdminUsername());
							try {
								session.getBasicRemote().sendText(gson.toJson(groups));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else {
						System.out.println("WTF");
						try {
							session.getBasicRemote().sendText("User must be logged in!");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					break;
					
				case "FRIEND":
					System.out.println("Trying to DO SOMETHING WITH FRIENDS through Websockets!");
					
					FriendCommand friendCommand = gson.fromJson(message, FriendCommand.class);
					
					if(loggedInUser != null) {
						if(friendCommand.getAction().equals("ADDFRIEND")) {
							System.out.println("Trying to ADD FR through Websockets!");
							userService.getRest().addFriend(loggedInUser, friendCommand.getFriendToAdd());
							System.out.println(loggedInUser + friendCommand.getFriendToAdd());
							try {
								session.getBasicRemote().sendText("friend added");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if(friendCommand.getAction().equals("REMOVEFRIEND")) {
							System.out.println("Trying to REMOVE FR through Websockets!");
							userService.getRest().removeFriend(loggedInUser, friendCommand.getFriendToRemove());
							System.out.println(loggedInUser + friendCommand.getFriendToRemove());
							try {
								session.getBasicRemote().sendText("friend REMOVED");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						if(friendCommand.getAction().equals("GETFRIENDS")) {
							System.out.println("Trying to GET FRIENDS through Websockets!");
							System.out.println(loggedInUser);
							List<User> user = userService.getRest().getAllFriendsOf(loggedInUser);
							try {
								session.getBasicRemote().sendText(gson.toJson(user));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else {
						try {
							session.getBasicRemote().sendText("User must be logged in!");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				
				case "MESSAGE":
					MessageCommand messageCommand = gson.fromJson(message, MessageCommand.class);
					
					
					if(loggedInUser != null) {
						if(messageCommand.getAction().equals("MESSAGEUSER")) {
							System.out.println("Trying to MESSAGE USER through Websockets!");
							Message m = new Message();
							m.setId(null);
							m.setSender(new User(loggedInUser, ""));
							List<User> receivers = new ArrayList<User>();
							receivers.add(new User(messageCommand.getUserToMessage(), ""));
							m.setReceivers(receivers);
							m.setContent(messageCommand.getMessage());
							messageService.getRest().createMessage(m);
							try {
								session.getBasicRemote().sendText("MESSAGE SENT");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(messageCommand.getAction().equals("MESSAGEGROUP")) {
							System.out.println("Trying to get MESSAGE GROUP through Websockets!");
							Message m = new Message();
							m.setId(null);
							m.setSender(new User(loggedInUser, ""));
							System.out.println(messageCommand.getGroupToMessage());
							Group groupToMessage = groupService.getRest().findGroup(messageCommand.getGroupToMessage());
							m.setReceivers(groupToMessage.getMembers());
							m.setContent(messageCommand.getMessage());
							messageService.getRest().createMessage(m);
							try {
								session.getBasicRemote().sendText("MESSAGE SENT");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(messageCommand.getAction().equals("GETUSERMESSAGES")) {
							System.out.println("Trying to get all user MSGS through Websockets!");
							List<Message> messages = messageService.getRest().getMessage(loggedInUser, messageCommand.getUserToMessage());
							try {
								session.getBasicRemote().sendText(gson.toJson(messages));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}if(messageCommand.getAction().equals("GETGROUPMESSAGES")) {
							System.out.println("Trying to get all GROUP MSGS through Websockets!");
							List<Message> messages = messageService.getRest().getMessageForGroup(messageCommand.getGroupToMessage());
							try {
								session.getBasicRemote().sendText(gson.toJson(messages));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else {
						try {
							session.getBasicRemote().sendText("User must be logged in!");
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
		handleLogout(sessionToRemove);
		log.info("[CLOSE] Closed session: " + sessionToRemove.getId());
	}

	private void handleLogout(Session sessionToRemove) {
		// TODO Auto-generated method stub
		for(String username : loggedInSessions.keySet()) {
			if(sessionToRemove.equals(loggedInSessions.get(username))) {
				loggedInSessions.remove(username);
				break;
			}
		}
		allActiveSessions.remove(sessionToRemove);
	}

	@OnError
	public void error(Session sessionToRemove, Throwable t) {
		handleLogout(sessionToRemove);
		log.info("[ERROR] " + sessionToRemove.getId());
		t.printStackTrace();
	}
	
	public String getUsernameFromSession(Session session) {
		for (String username : loggedInSessions.keySet()) {
			if(loggedInSessions.get(username).equals(session)) {
				return username;
			}
		}
		return null;
	}

	public HashMap<String, Session> getLoggedInSessions() {
		return loggedInSessions;
	}

	public void setLoggedInSessions(HashMap<String, Session> loggedInSessions) {
		this.loggedInSessions = loggedInSessions;
	}

}