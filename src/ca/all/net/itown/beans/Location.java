package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Location")
public class Location {
	// ===============================
	// Class Variables
	// ===============================

	// ===============================
	// Instance Variables
	// ===============================
	@Element(name = "LocID")
	private String locID;
	
	@Element(name = "LocName", required=false)
	private String locName;
	
	@Element(name = "LocLat")
	private String locLat;
	
	@Element(name = "LocLong")
	private String locLong;
	
	@Element(name = "LocDesc", required=false)
	private String locDesc;
	
	// ===============================
	// Class Method
	// ===============================

	// ===============================
	// Instance Method
	// ===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [locID=");
		builder.append(locID);
		builder.append(", locName=");
		builder.append(locName);
		builder.append(", locLat=");
		builder.append(locLat);
		builder.append(", locLong=");
		builder.append(locLong);
		builder.append(", locDesc=");
		builder.append(locDesc);
		builder.append("]");
		return builder.toString();
	}

	// ===============================
	// Access Method
	// ===============================
	public String getLocID() {
		return locID;
	}

	public void setLocID(String locID) {
		this.locID = locID;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getLocLat() {
		return locLat;
	}

	public void setLocLat(String locLat) {
		this.locLat = locLat;
	}

	public String getLocDesc() {
		return locDesc;
	}

	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}

	public String getLocLong() {
		return locLong;
	}

	public void setLocLong(String locLong) {
		this.locLong = locLong;
	}
}
