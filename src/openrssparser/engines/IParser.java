package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;

import openrssparser.models.common.interfaces.IEntry;
import openrssparser.models.common.interfaces.IHeader;

public interface IParser {

	IHeader getHeader() throws XMLStreamException, XMLParseException;

	boolean hasEntry() throws XMLStreamException;

	IEntry nextEntry() throws XMLStreamException, XMLParseException;

}
