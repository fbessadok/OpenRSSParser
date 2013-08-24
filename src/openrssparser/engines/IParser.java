package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;

import openrssparser.models.atom.Entry;
import openrssparser.models.atom.Source;

public interface IParser {
	
	public Source getHeader() throws XMLStreamException, XMLParseException;
	
	public boolean hasEntry() throws XMLStreamException;
	
	public Entry nextEntry() throws XMLStreamException, XMLParseException;

}
