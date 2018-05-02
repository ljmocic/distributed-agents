package resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Group;
import beans.User;
import interfaces.GroupServiceLocal;
import interfaces.UserServiceLocal;

@Stateless
@Path("/group")
public class GroupResource {

	@EJB
	GroupServiceLocal groupService;
	
	@EJB UserServiceLocal userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Group> getGroups() {
		return groupService.getGroups();
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Group findGroup(@PathParam(value="name") String name) {
		Group temp = groupService.getGroupByName(name);
		for(User user:temp.getMembers()) {
			user.setId(null);
		}
		temp.setId(null);
		return temp;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Group createGroup(Group g) {
		User user = userService.getUserByUsername(g.getAdmin().getUsername());
		g.setAdmin(user);
		List<User> tempMembers = new ArrayList<User>();
		for (User groupMember : g.getMembers()) {
			System.out.println(groupMember.getUsername());
			tempMembers.add(userService.getUserByUsername(groupMember.getUsername()));
		}
		g.setMembers(tempMembers);
		return groupService.createGroup(g);
	}
	
	@PUT
	@Path("/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateGroup(Group g, @PathParam(value="name") String name) {
		List<User> tempMembers = new ArrayList<User>();
		for (User groupMember : g.getMembers()) {
			System.out.println(groupMember.getUsername());
			tempMembers.add(userService.getUserByUsername(groupMember.getUsername()));
		}
		g.setMembers(tempMembers);
		groupService.updateGroup(g);
	}
	
	@DELETE
	@Path("/{name}")
	public void deleteGroup(@PathParam(value="name")  String name) {
		groupService.deleteGroup(name);
	}
	
	@PUT
	@Path("/{name}/users/{username}")
	public void addToGroup(@PathParam(value="name") String name, @PathParam(value="username") String username) {
		groupService.addUserToGroup(username, name);
	}
	
	@DELETE
	@Path("/{name}/users/{username}")
	public void removeFromGroup(@PathParam(value="name") String name, @PathParam(value="username") String username) {
		groupService.removeUserFromGroup(username, name);
	}
	
}
