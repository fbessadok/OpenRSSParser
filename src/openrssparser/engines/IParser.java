package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;

public interface IParser {

	Object getHeader() throws XMLStreamException, XMLParseException;

	boolean hasEntry() throws XMLStreamException;

	Object nextEntry() throws XMLStreamException, XMLParseException;

}
