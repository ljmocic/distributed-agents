package resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import interfaces.GroupResourceInterface;

@Path("/group")
@Stateless
public class GroupResourceProxy implements GroupResourceInterface {

	@Override
	public List<Object> getGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findGroup(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGroup(Object g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGroup(Object g, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGroup(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToGroup(String name, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromGroup(String name, String username) {
		// TODO Auto-generated method stub
		
	}

}
