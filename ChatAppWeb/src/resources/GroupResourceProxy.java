package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import beans.Group;
import interfaces.GroupResourceInterface;
import services.GroupServiceProxy;

@Path("/group")
@Stateless
public class GroupResourceProxy implements GroupResourceInterface {

	@EJB
	GroupServiceProxy groupServiceProxy;
	
	public GroupResourceProxy() {
	}
	
	@Override
	public List<Group> getGroups() {
		return groupServiceProxy.getRest().getGroups();
	}

	@Override
	public Group findGroup(String name) {
		return groupServiceProxy.getRest().findGroup(name);
	}

	@Override
	public Group createGroup(Group g) {
		return groupServiceProxy.getRest().createGroup(g);
	}

	@Override
	public void updateGroup(Group g, String name) {
		groupServiceProxy.getRest().updateGroup(g, name);	
	}

	@Override
	public void deleteGroup(String name) {
		groupServiceProxy.getRest().deleteGroup(name);
	}

	@Override
	public void addToGroup(String name, String username) {
		groupServiceProxy.getRest().addToGroup(name, username);		
	}

	@Override
	public void removeFromGroup(String name, String username) {
		groupServiceProxy.getRest().removeFromGroup(name, username);
	}

}
