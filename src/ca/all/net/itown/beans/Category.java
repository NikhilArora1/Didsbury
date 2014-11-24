package ca.all.net.itown.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Category")
public class Category {

	//===============================	
	//Instance Variables
	//===============================
	@Element(name = "MapID", required = false)
	private String mapID;
	@Element(name = "MapName", required = false)
	private String mapName;
	
	@Element(name = "CatID", required = false)
	private String catID;
	@Element(name = "CatName", required = false)
	private String catName;
	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [mapID=");
		builder.append(mapID);
		builder.append(", mapName=");
		builder.append(mapName);
		builder.append(", catID=");
		builder.append(catID);
		builder.append(", catName=");
		builder.append(catName);
		builder.append("]");
		return builder.toString();
	}
	
	//===============================	
	//Access Method
	//===============================
	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getCatID() {
		return catID;
	}

	public void setCatID(String catID) {
		this.catID = catID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}
}
