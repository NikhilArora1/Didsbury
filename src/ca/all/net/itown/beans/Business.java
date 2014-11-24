package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Business")
public class Business {

	//===============================	
	//Instance Variables
	//===============================
	@Element(name = "BusID", required = false)
	private String busID;
	@Element(name = "BusName", required = false)
	private String busName;
	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Business [busID=");
		builder.append(busID);
		builder.append(", busName=");
		builder.append(busName);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================
	
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	
}
