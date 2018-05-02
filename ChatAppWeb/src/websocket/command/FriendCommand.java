package websocket.command;

public class FriendCommand extends Command {
	
	private String action;
	private String friendToAdd;
	private String friendToRemove;
	
	public FriendCommand() {
		super();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFriendToAdd() {
		return friendToAdd;
	}

	public void setFriendToAdd(String friendToAdd) {
		this.friendToAdd = friendToAdd;
	}

	public String getFriendToRemove() {
		return friendToRemove;
	}

	public void setFriendToRemove(String friendToRemove) {
		this.friendToRemove = friendToRemove;
	}

}
