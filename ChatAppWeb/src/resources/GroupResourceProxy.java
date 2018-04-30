package resources;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import interfaces.GroupResourceInterface;

@Path("/group")
@Stateless
public class GroupResourceProxy implements GroupResourceInterface {

	private ResteasyClient client;
	
	public GroupResourceProxy() {
		client = new ResteasyClientBuilder().build();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGroups() {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group");
		Response r = target.request(MediaType.APPLICATION_JSON).get();
		return r.readEntity(List.class);
	}

	@Override
	public Object findGroup(String name) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group/"+name);
		Response r = target.request(MediaType.APPLICATION_JSON).get();
		return r.readEntity(Object.class);
	}

	@Override
	public Object createGroup(Object g) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group");
		Response r = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(g, MediaType.APPLICATION_JSON));
		return r.readEntity(Object.class);
	}

	@SuppressWarnings("unused")
	@Override
	public void updateGroup(Object g, String name) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group/"+name);
		Response r = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(g, MediaType.APPLICATION_JSON));	
	}

	@SuppressWarnings("unused")
	@Override
	public void deleteGroup(String name) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group/"+name);
		Response r = target.request().delete();	
	}

	@SuppressWarnings("unused")
	@Override
	public void addToGroup(String name, String username) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group/"+name+"/users/"+username);
		Response r = target.request().put(null);		
	}

	@SuppressWarnings("unused")
	@Override
	public void removeFromGroup(String name, String username) {
		ResteasyWebTarget target = client.target("http://localhost:8080/UserAppWeb/rest/group/"+name+"/users/"+username);
		Response r = target.request().delete();
	}

}
