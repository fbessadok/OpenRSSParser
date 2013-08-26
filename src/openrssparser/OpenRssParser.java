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
import javax.xml.stream.events.XMLEvent;

import openrssparser.engines.AtomParser;
import openrssparser.engines.IParser;
import openrssparser.engines.Rss2Parser;
import openrssparser.models.atom.AtomElementName;
import openrssparser.models.common.Entry;
import openrssparser.models.common.FeedType;
import openrssparser.models.common.Header;
import openrssparser.models.rss2.Rss2ElementName;

public class OpenRssParser implements IParser {

	private static IParser commonParser;
	private XMLEventReader eventReader;
	private FeedType feedType;
	
	public FeedType getFeedType() {
		return feedType;
	}

	public void declareFile(String feedUrl) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(new FileReader(feedUrl));
		createInstance(inputFactory.createXMLEventReader(new FileReader(feedUrl)));
	}

	public void declareURL(String feedUrl) throws XMLStreamException, FileNotFoundException, MalformedURLException {
		URL url = new URL(feedUrl);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(read(url));
		createInstance(inputFactory.createXMLEventReader(read(url)));
	}

	private InputStream read(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createInstance(XMLEventReader reader) throws XMLStreamException {
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					this.feedType = FeedType.ATOM;
					commonParser = new AtomParser(eventReader);
					break;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(Rss2ElementName.CHANNEL.getName())) {
					this.feedType = FeedType.RSS2;
					commonParser = new Rss2Parser(eventReader);
					break;
				}
			}
		}
	}

	@Override
	public Header getHeader() throws XMLStreamException, XMLParseException {
		return commonParser.getHeader();
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		return commonParser.hasEntry();
	}

	@Override
	public Entry nextEntry() throws XMLStreamException, XMLParseException {
		return commonParser.nextEntry();
	}

}
