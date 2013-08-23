package openrssparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import openrssparser.atom.AtomDate;
import openrssparser.atom.AtomElementName;
import openrssparser.atom.Category;
import openrssparser.atom.Entry;
import openrssparser.atom.Generator;
import openrssparser.atom.Person;
import openrssparser.atom.SimpleElement;
import openrssparser.atom.Source;
import openrssparser.atom.Text;

// TODO Review and maybe refractor the blocs in getHeader() getSource() and nextElement()

public enum Parser {

	PARSER;
	private XMLEventReader eventReader;

	public void declareFile(String feedUrl) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(new FileReader(feedUrl));
	}

	public void declareURL(String feedUrl) throws XMLStreamException, FileNotFoundException, MalformedURLException {
		URL url = new URL(feedUrl);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(read(url));
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

	private Person getPerson(XMLEvent event, String elementName) throws XMLStreamException {
		Person person = new Person();
		person.setAttributes(getAttributes(event));

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(elementName)) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.PERSONNAME.getName())) {
					event = eventReader.nextEvent();
					person.setName(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.PERSONEMAIL.getName())) {
					Text email = new Text();
					email.setAttributes(getAttributes(event));
					event = eventReader.nextEvent();
					email.setText(event.asCharacters().getData());
					person.setEmail(email);
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.URI.getName())) {
					Text uri = new Text();
					uri.setAttributes(getAttributes(event));
					event = eventReader.nextEvent();
					uri.setText(event.asCharacters().getData());
					person.setUri(uri);
				}
			}
		}
		return person;
	}

	private Category getCategory(XMLEvent event) throws XMLStreamException {
		Category category = new Category();
		category.setAttributes(getAttributes(event));

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					break;
				}
			} else if (event.isCharacters()) {
				category.setUndefinedContent(event.asCharacters().getData());
			}
		}
		return category;
	}

	private Generator getGenerator(XMLEvent event) throws XMLStreamException {
		Generator generator = new Generator();
		generator.setAttributes(getAttributes(event));

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.URI.getName())) {
					event = eventReader.nextEvent();
					generator.setUri(event.asCharacters().getData());
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.VERSION.getName())) {
					event = eventReader.nextEvent();
					generator.setVersion(event.asCharacters().getData());
				} else if (event.asStartElement().isCharacters()) {
					generator.setText(event.asCharacters().getData());
				}
			}
		}
		return generator;
	}

	private Text getText(XMLEvent event, String elementName) throws XMLStreamException {
		Text text = new Text();
		text.setAttributes(getAttributes(event));

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(elementName)) {
					break;
				}
			} else if (event.isCharacters()) {
				text.setText(event.asCharacters().getData());
			}
		}
		return text;
	}

	private AtomDate getAtomDate(XMLEvent event, String elementName) throws XMLStreamException {
		AtomDate atomDate = new AtomDate();
		atomDate.setAttributes(getAttributes(event));

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(elementName)) {
					break;
				}
			} else if (event.isCharacters()) {
				atomDate.setDate(DatatypeConverter.parseDateTime(event.asCharacters().getData()).getTime());
			}
		}
		return atomDate;
	}

	private SimpleElement getSimpleElement(XMLEvent event, String elementName) throws XMLStreamException {
		SimpleElement content = new SimpleElement();
		content.setAttributes(getAttributes(event));

		while (eventReader.hasNext()) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(elementName)) {
					break;
				}
			} else if (event.isCharacters()) {
				content.setText(event.asCharacters().getData());
			}
		}
		return content;
	}

	private Source getSource(XMLEvent event) throws XMLStreamException, XMLParseException {
		Source source = new Source();
		source.setAttributes(getAttributes(event));

		while (eventReader.hasNext()) {
			event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					source.getAuthors().add(getPerson(event, AtomElementName.AUTHOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					source.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					source.getContributors().add(getPerson(event, AtomElementName.CONTRIBUTOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					source.setGenerator(getGenerator(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ICON.getName())) {
					source.setIcon(getText(event, AtomElementName.ICON.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					source.setId(getText(event, AtomElementName.ID.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					source.getLinks().add(getSimpleElement(event, AtomElementName.LINK.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					source.setLogo(getText(event, AtomElementName.LOGO.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					source.setRights(getText(event, AtomElementName.RIGHTS.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					source.setSubtitle(getText(event, AtomElementName.SUBTITLE.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					source.setTitle(getText(event, AtomElementName.TITLE.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					source.setUpdated(getAtomDate(event, AtomElementName.UPDATED.getName()));
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SOURCE.getName())) {
					break;
				}
			}
		}
		return source;
	}

	public Source getHeader() throws XMLStreamException, XMLParseException {
		Source header = new Source();

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				break;
			}

			event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.FEED.getName())) {
					header.setAttributes(getAttributes(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					header.getAuthors().add(getPerson(event, AtomElementName.AUTHOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					header.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					header.getContributors().add(getPerson(event, AtomElementName.CONTRIBUTOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					header.setGenerator(getGenerator(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ICON.getName())) {
					header.setIcon(getText(event, AtomElementName.ICON.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					header.setId(getText(event, AtomElementName.ID.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					header.getLinks().add(getSimpleElement(event, AtomElementName.LINK.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					header.setLogo(getText(event, AtomElementName.LOGO.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					header.setRights(getText(event, AtomElementName.RIGHTS.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					header.setSubtitle(getText(event, AtomElementName.SUBTITLE.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					header.setTitle(getText(event, AtomElementName.TITLE.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					header.setUpdated(getAtomDate(event, AtomElementName.UPDATED.getName()));
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					break;
				}
			}
		}
		return header;
	}

	public boolean hasEntry() throws XMLStreamException {
		if (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				return true;
			}
		}
		return false;
	}

	public Entry nextEntry() throws XMLStreamException, XMLParseException {
		Entry entry = null;
		while (eventReader.hasNext()) {
			XMLEvent event;
			try {
				event = eventReader.nextEvent();
			} catch (XMLStreamException e) {
				return null;
			}

			if (entry == null) {
				entry = new Entry();
			}

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
					entry.setAttributes(getAttributes(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					entry.getAuthors().add(getPerson(event, AtomElementName.AUTHOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					entry.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTENT.getName())) {
					entry.setContent(getSimpleElement(event, AtomElementName.CONTENT.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					entry.getContributors().add(getPerson(event, AtomElementName.CONTRIBUTOR.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					entry.setId(getText(event, AtomElementName.ID.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					entry.getLinks().add(getSimpleElement(event, AtomElementName.LINK.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.PUBLISHED.getName())) {
					entry.setUpdated(getAtomDate(event, AtomElementName.PUBLISHED.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					entry.setRights(getText(event, AtomElementName.RIGHTS.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SOURCE.getName())) {
					entry.setSource(getSource(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUMMARY.getName())) {
					entry.setSummary(getText(event, AtomElementName.SUMMARY.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					entry.setTitle(getText(event, AtomElementName.TITLE.getName()));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					entry.setUpdated(getAtomDate(event, AtomElementName.UPDATED.getName()));
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
					break;
				}
			}
		}
		return entry;
	}

	private InputStream read(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
