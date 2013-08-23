package openrssparser.atom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.Attribute;

public class Element {

	private List<Attribute> attribute = new ArrayList<Attribute>();

	public List<Attribute> getAttribute() {
		return attribute;
	}

	public void setAttribute(List<Attribute> attribute) {
		this.attribute = attribute;
	}

}
