package websocket.command;

import java.util.ArrayList;
import java.util.List;

import beans.User;

public class GroupCommand extends Command {
	
	private String adminUsername;
	private List<String> memberUsernames;
	private String groupName;
	private String action;

	public GroupCommand() {
		
	}

	public User getAdmin() {
		return new User(adminUsername, "");
	}

	public List<User> getMembers() {
		List<User> users = new ArrayList<User>();
		for (String memberUsername : memberUsernames) {
			users.add(new User(memberUsername, ""));
		}
		return users;
	}

	public String getName() {
		return groupName;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public List<String> getMemberUsernames() {
		return memberUsernames;
	}

	public void setMemberUsernames(List<String> memberUsernames) {
		this.memberUsernames = memberUsernames;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
