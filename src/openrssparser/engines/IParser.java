package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;

import openrssparser.models.atom.Entry;
import openrssparser.models.atom.Source;

public interface IParser {
	
	Object getHeader() throws XMLStreamException, XMLParseException;
	
	boolean hasEntry() throws XMLStreamException;
	
	Entry nextEntry() throws XMLStreamException, XMLParseException;

}
