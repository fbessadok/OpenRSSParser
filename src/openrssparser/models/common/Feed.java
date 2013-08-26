package openrssparser.models.common;

import java.util.List;

import javax.xml.stream.events.Attribute;

public class Feed {

	private List<Attribute> attributes;
	private Header header;
	private List<Entry> entries;

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

}
