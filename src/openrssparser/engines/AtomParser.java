package openrssparser.engines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import openrssparser.models.atom.AtomCategory;
import openrssparser.models.atom.AtomDate;
import openrssparser.models.atom.AtomElementName;
import openrssparser.models.atom.AtomEntry;
import openrssparser.models.atom.AtomGenerator;
import openrssparser.models.atom.AtomPerson;
import openrssparser.models.atom.AtomSimpleElement;
import openrssparser.models.atom.AtomSource;
import openrssparser.models.atom.AtomText;
import openrssparser.models.common.Entry;
import openrssparser.models.common.Header;

/*
 * Atom Feed Parser
 * Following the RFC 4287 specifications
 * http://www.ietf.org/rfc/rfc4287.txt
 * 
 */

// TODO Review and maybe refractor the blocs in getHeader() getSource() and nextEntry()

public class AtomParser implements IParser {

	private XMLEventReader eventReader;

	public AtomParser(XMLEventReader eventReader) {
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

	private AtomPerson getPerson(XMLEvent event, String elementName) throws XMLStreamException {
		AtomPerson person = new AtomPerson();
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
					AtomText email = new AtomText();
					email.setAttributes(getAttributes(event));
					event = eventReader.nextEvent();
					email.setText(event.asCharacters().getData());
					person.setEmail(email);
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.URI.getName())) {
					AtomText url = new AtomText();
					url.setAttributes(getAttributes(event));
					event = eventReader.nextEvent();
					url.setText(event.asCharacters().getData());
					person.setUrl(url);
				}
			}
		}
		return person;
	}

	private AtomCategory getCategory(XMLEvent event) throws XMLStreamException {
		AtomCategory category = new AtomCategory();
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

	private AtomGenerator getGenerator(XMLEvent event) throws XMLStreamException {
		AtomGenerator generator = new AtomGenerator();
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

	private AtomText getText(XMLEvent event, String elementName) throws XMLStreamException {
		AtomText text = new AtomText();
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

	private AtomSimpleElement getSimpleElement(XMLEvent event, String elementName) throws XMLStreamException {
		AtomSimpleElement content = new AtomSimpleElement();
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

	private AtomSource getSource(XMLEvent event) throws XMLStreamException, XMLParseException {
		AtomSource source = new AtomSource();
		source.setAttributes(getAttributes(event));

		while (eventReader.hasNext()) {
			event = eventReader.nextEvent();

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					source.getAuthors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					source.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					source.getContributors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					source.setGenerator(getGenerator(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ICON.getName())) {
					source.setIcon(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					source.setId(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					source.getLinks().add(getSimpleElement(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					source.setLogo(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					source.setRights(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					source.setSubtitle(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					source.setTitle(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					source.setUpdated(getAtomDate(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SOURCE.getName())) {
				break;
			}
		}
		return source;
	}

	@Override
	public Header getHeader() throws XMLStreamException, XMLParseException {
		AtomSource header = new AtomSource();

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
					header.getAuthors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					header.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					header.getContributors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					header.setGenerator(getGenerator(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ICON.getName())) {
					header.setIcon(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					header.setId(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					header.getLinks().add(getSimpleElement(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					header.setLogo(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					header.setRights(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					header.setSubtitle(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					header.setTitle(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					header.setUpdated(getAtomDate(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
				break;
			}
		}
		return header.toCommon();
	}

	@Override
	public boolean hasEntry() throws XMLStreamException {
		if (eventReader.hasNext()) {
			XMLEvent event = eventReader.peek();
			if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Entry nextEntry() throws XMLStreamException, XMLParseException {
		AtomEntry entry = null;
		while (eventReader.hasNext()) {
			XMLEvent event;
			try {
				event = eventReader.nextEvent();
			} catch (XMLStreamException e) {
				return null;
			}

			if (entry == null) {
				entry = new AtomEntry();
			}

			if (event.isStartElement()) {
				String currentElementName = event.asStartElement().getName().getLocalPart();
				if (currentElementName.equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
					entry.setAttributes(getAttributes(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					entry.getAuthors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					entry.getCategories().add(getCategory(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTENT.getName())) {
					entry.setContent(getSimpleElement(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					entry.getContributors().add(getPerson(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.ID.getName())) {
					entry.setId(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.LINK.getName())) {
					entry.getLinks().add(getSimpleElement(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.PUBLISHED.getName())) {
					entry.setPublished(getAtomDate(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					entry.setRights(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SOURCE.getName())) {
					entry.setSource(getSource(event));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.SUMMARY.getName())) {
					entry.setSummary(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					entry.setTitle(getText(event, currentElementName));
				} else if (currentElementName.equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					entry.setUpdated(getAtomDate(event, currentElementName));
				}
			} else if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				break;
			}
		}
		return entry.toCommon();
	}

}
