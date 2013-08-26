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
import openrssparser.models.common.FeedType;
import openrssparser.models.common.interfaces.IEntry;
import openrssparser.models.common.interfaces.IHeader;

public class OpenRss implements IParser {

	private static IParser commonParser;
	private XMLEventReader eventReader;

	
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
			commonParser = new AtomParser(eventReader);
		} else if (getFeedType().equals(FeedType.RSS)) {
			commonParser = new Rss2Parser(eventReader);
		}
	}

	@Override
	public IHeader getHeader() throws XMLStreamException, XMLParseException {
		return commonParser.getHeader();
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		return commonParser.hasEntry();
	}

	@Override
	public IEntry nextEntry() throws XMLStreamException, XMLParseException {
		return commonParser.nextEntry();
	}

}
