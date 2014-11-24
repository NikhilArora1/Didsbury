package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="BusinessDetails")
public class BusinessDetails {
	
	//===============================	
	//Class Variables
	//===============================

	//===============================	
	//Instance Variables
	//===============================
	@ElementList(name = "Business", inline=true)
	private List<Business> business;

	//===============================	
	//Class Method
	//===============================

	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusinessDetails [business=");
		builder.append(business);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================

	public List<Business> getBusiness() {
		return business;
	}

	public void setBusiness(List<Business> business) {
		this.business = business;
	}

}
