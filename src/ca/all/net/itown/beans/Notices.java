package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Notices")
public class Notices {

	//===============================	
	//Instance Variables
	//===============================
	@Element(name = "NoticeID")
	private String noticeID;
	@Element(name = "Date")
	private String date;
	@Element(name = "NoticeTitle")
	private String noticeTitle;
	@Element(name = "NoticeDescription")
	private String noticeDescription;
	
	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notices [noticeID=");
		builder.append(noticeID);
		builder.append(", date=");
		builder.append(date);
		builder.append(", noticeTitle=");
		builder.append(noticeTitle);
		builder.append(", noticeDescription=");
		builder.append(noticeDescription);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================

	public String getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeDescription() {
		return noticeDescription;
	}

	public void setNoticeDescription(String noticeDescription) {
		this.noticeDescription = noticeDescription;
	}

}
