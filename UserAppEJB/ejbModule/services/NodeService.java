package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import beans.Host;
import interfaces.NodeServiceLocal;


@Singleton(mappedName = "NodeService")
@Startup
@LocalBean
public class NodeService implements NodeServiceLocal {
	
	private List<Host> nodes;

    /**
     * Default constructor. 
     */
    public NodeService() {
    	nodes = new ArrayList<Host>();
    }

	@Override
	public void activateNode(Host host) {
		nodes.add(host);
	}

	@Override
	public void deactivateNode(String alias) {
		for (Host h : nodes) {
			if(h.getAlias().equals(alias)) {
				nodes.remove(h);
				break;
			}
		}
	}
	
	@Override
	public void notifyNodes(String message) {
		for (Host h : nodes) {
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://" + h.getAddress() + ":8080/ChatAppWeb/rest/sync/notify");
	        target.path(message).request(MediaType.TEXT_PLAIN).get();
		}
		
	}

}
