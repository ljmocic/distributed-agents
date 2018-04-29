package database;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import beans.User;

/**
 * Session Bean implementation class MongoDBConnector
 */
@Stateless(mappedName = "MongoDBConnector")
@LocalBean
public class MongoDBConnector {
 
	private MongoClient mongoClient;
	private MongoDatabase database;
	private Morphia morphia;
	
    public MongoDBConnector() {
    	mongoClient = new MongoClient();
    	database = mongoClient.getDatabase("userappdb");
    	morphia = new Morphia();
    	morphia.mapPackage("beans");
    	Datastore datastore = morphia.createDatastore(mongoClient, "users");
    	datastore.save(new User("testUser", "testUser"));
    }

	public MongoDatabase getDatabase() {
		return database;
	}

}
