package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Attribute;

public class AtomElement {

	private List<Attribute> attributes = new ArrayList<Attribute>();

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

}
