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

import openrssparser.engines.AtomParser;
import openrssparser.engines.IParser;
import openrssparser.engines.Rss2Parser;
import openrssparser.models.atom.AtomEntry;
import openrssparser.models.atom.AtomSource;
import openrssparser.models.common.FeedType;

public enum OpenRss implements IParser {
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
		return FeedType.RSS;
	}
	
	private void createInstance() {
		if (getFeedType().equals(FeedType.ATOM)) {
			AtomParser.PARSER.setEventReader(eventReader);
			realParser = AtomParser.PARSER;
		} else if (getFeedType().equals(FeedType.RSS)) {
			Rss2Parser.PARSER.setEventReader(eventReader);
			realParser = Rss2Parser.PARSER;
		}
	}

	@Override
	public AtomSource getHeader() throws XMLStreamException, XMLParseException {
		return null;
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		return false;
	}

	@Override
	public AtomEntry nextEntry() throws XMLStreamException, XMLParseException {
		return null;
	}

}
