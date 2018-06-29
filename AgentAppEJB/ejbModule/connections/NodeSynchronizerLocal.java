package connections;

import javax.ejb.Local;

@Local
public interface NodeSynchronizerLocal {
	
	public void setupNode();
	public void removeNode();
}
