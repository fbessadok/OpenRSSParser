package openrssparser.engines;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import openrssparser.models.atom.Entry;
import openrssparser.models.rss.Header;
import openrssparser.models.rss.Image;
import openrssparser.models.rss.RSSElementName;
import openrssparser.models.rss.TextInput;

/*
 * RSS 2 Feed Parser
 * Following these specifications
 * http://cyber.law.harvard.edu/tech/rss
 * http://feed2.w3.org/docs/rss2.html (a mirror on w3c website)
 * 
 * And taking into account the <dc:creator> element as it's widely used
 * http://dublincore.org/documents/1999/07/02/dces/
 * 
 */

public enum RSS2Parser implements IParser {

	PARSER;
	private XMLEventReader eventReader;

	public void setEventReader(XMLEventReader eventReader) {
		this.eventReader = eventReader;
	}

	private String getString(XMLEvent event, String elementName) throws XMLStreamException {
		String string = "";

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(elementName)) {
					break;
				}
			} else if (event.isCharacters()) {
				string = event.asCharacters().getData();
			}
		}
		return string;
	}

	private Image getImage(XMLEvent event) throws XMLStreamException {
		Image image = new Image();

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSSElementName.IMAGE.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(RSSElementName.URL.getName())) {
					event = eventReader.nextEvent();
					image.setUrl(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.TITLE.getName())) {
					event = eventReader.nextEvent();
					image.setTitle(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.LINK.getName())) {
					event = eventReader.nextEvent();
					image.setLink(event.asCharacters().getData());
				}
			}
		}
		return image;
	}

	private TextInput getTextInput(XMLEvent event) throws XMLStreamException {
		TextInput textInput = new TextInput();

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSSElementName.TEXTINPUT.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(RSSElementName.TITLE.getName())) {
					event = eventReader.nextEvent();
					textInput.setTitle(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.DESCRIPTION.getName())) {
					event = eventReader.nextEvent();
					textInput.setDescription(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.NAME.getName())) {
					event = eventReader.nextEvent();
					textInput.setName(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.LINK.getName())) {
					event = eventReader.nextEvent();
					textInput.setLink(event.asCharacters().getData());
				}
			}
		}
		return textInput;
	}

	public Header getHeader() throws XMLStreamException, XMLParseException {
		Header header = new Header();

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(RSSElementName.ITEM.getName())) {
				break;
			}

			event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(RSSElementName.TITLE.getName())) {
					header.setTitle(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.LINK.getName())) {
					header.setLink(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.DESCRIPTION.getName())) {
					header.setDescription(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.LANGUAGE.getName())) {
					header.setLanguage(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.COPYRIGHT.getName())) {
					header.setCopyright(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.MANAGINGEDITOR.getName())) {
					header.setManagingEditor(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.WEBMASTER.getName())) {
					header.setWebMaster(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.PUBDATE.getName())) {
					header.setPubDate(DatatypeConverter.parseDateTime(getString(event, currentElementName)).getTime());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.LASTBUILDDATE.getName())) {
					header.setLastBuildDate(DatatypeConverter.parseDateTime(getString(event, currentElementName)).getTime());
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.CATEGORY.getName())) {
					header.getCategories().add(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.GENERATOR.getName())) {
					header.setGenerator(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.DOCS.getName())) {
					header.setDocs(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.TTL.getName())) {
					header.setTtl(Integer.valueOf(getString(event, currentElementName)));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.IMAGE.getName())) {
					header.setImage(getImage(event));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.RATING.getName())) {
					header.setRating(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.TEXTINPUT.getName())) {
					header.setTextInput(getTextInput(event));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.SKIPHOURS.getName())) {
					header.getSkipHours().add(Integer.valueOf(getString(event, currentElementName)));
				} else if (currentElementName.equalsIgnoreCase(RSSElementName.SKIPDAYS.getName())) {
					header.getSkipDays().add(getString(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSSElementName.CHANNEL.getName())) {
				break;
			}
		}
		return header;
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entry nextEntry() throws XMLStreamException, XMLParseException {
		// TODO Auto-generated method stub
		return null;
	}

}