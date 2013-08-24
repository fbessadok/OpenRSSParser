package openrssparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import openrssparser.models.atom.Entry;
import openrssparser.models.atom.Source;
import openrssparser.engines.AtomParser;
import openrssparser.engines.FeedType;
import openrssparser.engines.IParser;

public enum OpenRSS implements IParser {
	PARSER;
	private static IParser realParser;
	private XMLEventReader eventReader;
	
	public static IParser getInstance() {
		if (realParser != null) {
			return realParser;
		}
		return PARSER;
	}
	
	public void declareFile(String feedUrl) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(new FileReader(feedUrl));
		createInstance();
	}

	public void declareURL(String feedUrl) throws XMLStreamException, FileNotFoundException, MalformedURLException {
		URL url = new URL(feedUrl);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(read(url));
		createInstance();
	}
	
	private InputStream read(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private FeedType getFeedType() {
		return FeedType.ATOM;
	}
	
	private void createInstance() {
		if (getFeedType().equals(FeedType.ATOM)) {
			AtomParser.PARSER.setEventReader(eventReader);
			realParser = AtomParser.PARSER;
		}
	}

	@Override
	public Source getHeader() throws XMLStreamException, XMLParseException {
		return null;
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		return false;
	}

	@Override
	public Entry nextEntry() throws XMLStreamException, XMLParseException {
		return null;
	}

}
