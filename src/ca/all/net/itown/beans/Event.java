package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Event")
public class Event {

	//===============================	
	//Instance Variables
	//===============================
	@Element(name = "EventID")
	private String eventID;
	@Element(name = "StartDate")
	private String startDate;
//	@Element(name = "StartTime")
//	private String startTime;
//	@Element(name = "EndDate")
//	private String endDate;
//	@Element(name = "EndTime")
//	private String endTime;
	@Element(name = "EventTitle")
	private String eventTitle;
//	@Element(name = "EventLocation")
//	private String eventLocation;
	@Element(name = "EventDescription", required=false)
	private String eventDescription;
	
	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [eventID=");
		builder.append(eventID);
		builder.append(", startDate=");
		builder.append(startDate);
//		builder.append(", startTime=");
//		builder.append(startTime);
//		builder.append(", endDate=");
//		builder.append(endDate);
//		builder.append(", endTime=");
//		builder.append(endTime);
		builder.append(", eventTitle=");
		builder.append(eventTitle);
//		builder.append(", eventLocation=");
//		builder.append(eventLocation);
		builder.append(", eventDescription=");
		builder.append(eventDescription);
		builder.append("]");
		return builder.toString();
	}
	
	//===============================	
	//Access Method
	//===============================
	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

//	public String getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
//
//	public String getEndDate() {
//		return endDate;
//	}

//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(String endTime) {
//		this.endTime = endTime;
//	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

//	public String getEventLocation() {
//		return eventLocation;
//	}
//
//	public void setEventLocation(String eventLocation) {
//		this.eventLocation = eventLocation;
//	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		System.out.println("InSide the setEventDescription");
		this.eventDescription = eventDescription;
	}
}
