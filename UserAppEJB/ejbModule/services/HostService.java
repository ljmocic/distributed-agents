package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import interfaces.HostServiceLocal;

/**
 * Session Bean implementation class HostService
 */
@Stateless(mappedName = "HostService")
@LocalBean
public class HostService implements HostServiceLocal {

    /**
     * Default constructor. 
     */
    public HostService() {
        // TODO Auto-generated constructor stub
    }

}
