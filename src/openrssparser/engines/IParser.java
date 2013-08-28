package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;

import openrssparser.models.common.Entry;
import openrssparser.models.common.Header;

public interface IParser {

	Header getHeader() throws XMLStreamException, XMLParseException;

	boolean hasEntry() throws XMLStreamException;

	Entry nextEntry() throws XMLStreamException, XMLParseException;
	
	void close();

}
