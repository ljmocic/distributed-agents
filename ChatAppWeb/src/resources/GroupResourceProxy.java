package resources;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

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
	public List<Object> getGroups() {
		return groupServiceProxy.getRest().getGroups();
	}

	@Override
	public Object findGroup(String name) {
		return groupServiceProxy.getRest().findGroup(name);
	}

	@Override
	public Object createGroup(Object g) {
		return groupServiceProxy.getRest().createGroup(g);
	}

	@Override
	public void updateGroup(Object g, String name) {
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
