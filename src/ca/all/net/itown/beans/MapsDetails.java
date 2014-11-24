package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="MapsDetails")
public class MapsDetails {
	//===============================	
	//Class Variables
	//===============================

	//===============================	
	//Instance Variables
	//===============================
	@ElementList(name = "Location", inline = true)
	private List<Location> location;
	//===============================	
	//Class Method
	//===============================

	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [location=");
		builder.append(location);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================

	public List<Location> getLocation() {
		return location;
	}

	public void setLocation(List<Location> location) {
		this.location = location;
	}
}
