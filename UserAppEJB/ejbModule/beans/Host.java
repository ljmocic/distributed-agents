package beans;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("hosts")
public class Host {
	
	@Id
	private ObjectId id;
	private String address;
	private String alias;
	
	public Host(ObjectId id, String address, String alias) {
		super();
		this.id = id;
		this.address = address;
		this.alias = alias;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
