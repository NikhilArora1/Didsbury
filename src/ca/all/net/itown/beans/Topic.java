package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Topic")
public class Topic {
	// ===============================
	// Class Variables
	// ===============================

	// ===============================
	// Instance Variables
	// ===============================
	@Element(name = "id")
	private String id;
	@Element(name = "name")
	private String name;
	@Element(name = "icon")
	private String icon;
	@Element(name = "color")
	private String color;
	// ===============================
	// Class Method
	// ===============================

	// ===============================
	// Instance Method
	// ===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Topic [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", color=");
		builder.append(color);
		builder.append("]");
		return builder.toString();
	}

	// ===============================
	// Access Method
	// ===============================
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
