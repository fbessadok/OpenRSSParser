package openrssparser.engines;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import openrssparser.models.atom.Text;
import openrssparser.models.rss2.Enclosure;
import openrssparser.models.rss2.Header;
import openrssparser.models.rss2.Image;
import openrssparser.models.rss2.Item;
import openrssparser.models.rss2.Person;
import openrssparser.models.rss2.RSS2ElementName;
import openrssparser.models.rss2.TextInput;

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
	
	private List<Attribute> getAttributes(XMLEvent event) {
		List<Attribute> attributes = new ArrayList<Attribute>();
		@SuppressWarnings("unchecked")
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			attributes.add(attribute.next());
		}
		return attributes;
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
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.IMAGE.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(RSS2ElementName.URL.getName())) {
					event = eventReader.nextEvent();
					image.setUrl(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.TITLE.getName())) {
					event = eventReader.nextEvent();
					image.setTitle(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LINK.getName())) {
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
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.TEXTINPUT.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(RSS2ElementName.TITLE.getName())) {
					event = eventReader.nextEvent();
					textInput.setTitle(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.DESCRIPTION.getName())) {
					event = eventReader.nextEvent();
					textInput.setDescription(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.NAME.getName())) {
					event = eventReader.nextEvent();
					textInput.setName(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LINK.getName())) {
					event = eventReader.nextEvent();
					textInput.setLink(event.asCharacters().getData());
				}
			}
		}
		return textInput;
	}
	
	private Enclosure getEnclosure(XMLEvent event) throws XMLStreamException {
		Enclosure enclosure = new Enclosure();
		enclosure.setAttributes(getAttributes(event));
		return enclosure;
	}

	public Header getHeader() throws XMLStreamException, XMLParseException {
		Header header = new Header();

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.ITEM.getName())) {
				break;
			}

			event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
				if (currentElementName.equalsIgnoreCase(RSS2ElementName.TITLE.getName())) {
					header.setTitle(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LINK.getName())) {
					header.setLink(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.DESCRIPTION.getName())) {
					header.setDescription(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LANGUAGE.getName())) {
					header.setLanguage(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.COPYRIGHT.getName())) {
					header.setCopyright(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.MANAGINGEDITOR.getName())) {
					header.setManagingEditor(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.WEBMASTER.getName())) {
					header.setWebMaster(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.PUBDATE.getName())) {
					try {
						header.setPubDate(formatter.parse(getString(event, currentElementName)));
					} catch (ParseException e) {
						header.setPubDate(null);
						e.printStackTrace();
					}
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LASTBUILDDATE.getName())) {
					try {
						header.setLastBuildDate(formatter.parse(getString(event, currentElementName)));
					} catch (ParseException e) {
						header.setLastBuildDate(null);
						e.printStackTrace();
					}
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.CATEGORY.getName())) {
					header.getCategories().add(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.GENERATOR.getName())) {
					header.setGenerator(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.DOCS.getName())) {
					header.setDocs(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.TTL.getName())) {
					header.setTtl(Integer.valueOf(getString(event, currentElementName)));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.IMAGE.getName())) {
					header.setImage(getImage(event));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.RATING.getName())) {
					header.setRating(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.TEXTINPUT.getName())) {
					header.setTextInput(getTextInput(event));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.SKIPHOURS.getName())) {
					header.getSkipHours().add(Integer.valueOf(getString(event, currentElementName)));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.SKIPDAYS.getName())) {
					header.getSkipDays().add(getString(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.CHANNEL.getName())) {
				break;
			}
		}
		return header;
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		if (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.ITEM.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Item nextEntry() throws XMLStreamException, XMLParseException {
		Item item = new Item();

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
				if (currentElementName.equalsIgnoreCase(RSS2ElementName.TITLE.getName())) {
					item.setTitle(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.LINK.getName())) {
					item.setLink(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.DESCRIPTION.getName())) {
					item.setDescription(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.AUTHOR.getName())) {
					if (item.getAuthor() == null) {
						item.setAuthor(new Person());
					}
					item.getAuthor().setEmail(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.CREATOR.getName())) {
					if (item.getAuthor() == null) {
						item.setAuthor(new Person());
					}
					item.getAuthor().setName(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.CATEGORY.getName())) {
					item.getCategories().add(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.COMMENTS.getName())) {
					item.setComments(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.ENCLOSURE.getName())) {
					item.getEnclosure().add(getEnclosure(event));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.GUID.getName())) {
					item.setGuid(getString(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.PUBDATE.getName())) {
					try {
						item.setPubDate(formatter.parse(getString(event, currentElementName)));
					} catch (ParseException e) {
						item.setPubDate(null);
						e.printStackTrace();
					}
				} else if (currentElementName.equalsIgnoreCase(RSS2ElementName.SOURCE.getName())) {
					item.setSource(getString(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(RSS2ElementName.ITEM.getName())) {
				break;
			}
		}
		return item;
	}

}