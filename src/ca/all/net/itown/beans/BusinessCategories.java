package ca.all.net.itown.beans;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="BusinessCategories")
public class BusinessCategories {
	
	//===============================	
	//Class Variables
	//===============================

	//===============================	
	//Instance Variables
	//===============================
	@ElementList(name = "Category", inline=true)
	private List<Category> category;

	//===============================	
	//Class Method
	//===============================

	//===============================	
	//Instance Method
	//===============================
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusinessCategories [category=");
		builder.append(category);
		builder.append("]");
		return builder.toString();
	}
	//===============================	
	//Access Method
	//===============================

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

}
