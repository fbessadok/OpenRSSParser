package openrssparser.models.rss2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Attribute;

public class Rss2Enclosure {

	private List<Attribute> attributes = new ArrayList<Attribute>();

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
