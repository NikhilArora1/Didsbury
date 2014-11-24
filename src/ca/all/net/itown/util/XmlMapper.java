package ca.all.net.itown.util;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


public class XmlMapper {

	public <T> Object mapData(Class<T> className, String dataSource) throws Exception {
		Serializer serializer = new Persister();
		T extractcedClass = serializer.read(className, dataSource);
		return extractcedClass;
	}
}
