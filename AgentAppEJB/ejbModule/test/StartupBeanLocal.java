package test;

import javax.ejb.Local;

@Local
public interface StartupBeanLocal {

	public void greeting();
}
