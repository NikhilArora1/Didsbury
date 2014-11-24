package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="UpcomingNotices")
public class UpcomingNotices {
	
	//===============================	
	//Class Variables
	//===============================

	//===============================	
	//Instance Variables
	//===============================
	@ElementList(name = "Notices", inline=true)
	private List<Notices> notices;

	//===============================	
	//Class Method
	//===============================

	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpcomingNotices [notices=");
		builder.append(notices);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================

	public List<Notices> getNotices() {
		return notices;
	}

	public void setNotices(List<Notices> notices) {
		this.notices = notices;
	}
}
