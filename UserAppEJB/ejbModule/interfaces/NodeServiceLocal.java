package interfaces;

import javax.ejb.Local;

import beans.Host;

@Local
public interface NodeServiceLocal {
	
	void activateNode(Host host);
	void deactivateNode(String alias);
	void notifyNodes(String message);

}
