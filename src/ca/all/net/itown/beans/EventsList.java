package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "EventsList")
public class EventsList {
	// ===============================
	// Class Variables
	// ===============================

	// ===============================
	// Instance Variables
	// ===============================
	@ElementList(name = "Topic", inline = true)
	private List<Topic> topic;
	// ===============================
	// Class Method
	// ===============================

	// ===============================
	// Instance Method
	// ===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventsList [topic=");
		builder.append(topic);
		builder.append("]");
		return builder.toString();
	}
	// ===============================
	// Access Method
	// ===============================

	public List<Topic> getTopic() {
		return topic;
	}

	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}

}
