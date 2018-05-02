package websocket.command;

public class MessageCommand extends Command {
	
	private String action;
	private String userToMessage;
	private String groupToMessage;
	private String message;
	
	public MessageCommand() {
		
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUserToMessage() {
		return userToMessage;
	}
	public void setUserToMessage(String userToMessage) {
		this.userToMessage = userToMessage;
	}
	public String getGroupToMessage() {
		return groupToMessage;
	}
	public void setGroupToMessage(String groupToMessage) {
		this.groupToMessage = groupToMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
