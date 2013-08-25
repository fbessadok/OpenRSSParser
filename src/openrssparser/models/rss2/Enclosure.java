package openrssparser.models.rss2;

import java.util.List;

import javax.xml.stream.events.Attribute;

public class Enclosure {

	private List<Attribute> attributes;

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
