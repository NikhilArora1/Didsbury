package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="UpcomingEvents")
public class UpcomingEvents {
	
	//===============================	
	//Class Variables
	//===============================

	//===============================	
	//Instance Variables
	//===============================
	@ElementList(name = "Event", inline=true)
	private List<Event> event;

	//===============================	
	//Class Method
	//===============================

	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpcomingEvents [event=");
		builder.append(event);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================
	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

}
