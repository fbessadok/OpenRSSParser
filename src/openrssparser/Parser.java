package openrssparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

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
import openrssparser.atom.Element;
import openrssparser.atom.Entry;
import openrssparser.atom.Feed;
import openrssparser.atom.Generator;
import openrssparser.atom.Link;
import openrssparser.atom.Person;
import openrssparser.atom.Source;
import openrssparser.atom.Text;

public enum Parser {
	
	PARSER;
	private XMLEventReader eventReader;
	private Feed feed = null;
	private boolean hasEntry = false;

	public void parseCursorFile(String feedUrl) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(new FileReader(feedUrl));
	}
	
	public void parseCursorURL(String feedUrl) throws XMLStreamException, FileNotFoundException, MalformedURLException {
		URL url = new URL(feedUrl);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(read(url));
	}
	
	@SuppressWarnings("unchecked")
	private Person getPerson(XMLEvent event, AtomElementName ATOMELEMENT) throws XMLStreamException {
		Person person = new Person();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			person.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(ATOMELEMENT.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.PERSONNAME.getName())) {
					event = eventReader.nextEvent();
					person.setName(event.asCharacters().getData());
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.PERSONEMAIL.getName())) {
					Text email = new Text();
					Iterator<Attribute> attributeMail = event.asStartElement().getAttributes();
					while (attributeMail.hasNext()) {
						email.getAttribute().add(attributeMail.next());
					}
					event = eventReader.nextEvent();
					email.setText(event.asCharacters().getData());
					person.setEmail(email);
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.URI.getName())) {
					Text uri = new Text();
					Iterator<Attribute> attributeMail = event.asStartElement().getAttributes();
					while (attributeMail.hasNext()) {
						uri.getAttribute().add(attributeMail.next());
					}
					event = eventReader.nextEvent();
					uri.setText(event.asCharacters().getData());
					person.setUri(uri);
				}
			}
		}
		return person;
	}
	
	@SuppressWarnings("unchecked")
	private Category getCategory(XMLEvent event) throws XMLStreamException {
		Category category = new Category();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			category.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.TERM.getName())) {
					event = eventReader.nextEvent();
					category.setTerm(event.asCharacters().getData());
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SCHEME.getName())) {
					event = eventReader.nextEvent();
					category.setScheme(event.asCharacters().getData());
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LABEL.getName())) {
					event = eventReader.nextEvent();
					category.setLabel(event.asCharacters().getData());
				}
			}
		}
		return category;
	}
	
	@SuppressWarnings("unchecked")
	private Generator getGenerator(XMLEvent event) throws XMLStreamException {
		Generator generator = new Generator();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			generator.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					break;
				}
			} else if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.URI.getName())) {
					event = eventReader.nextEvent();
					generator.setUri(event.asCharacters().getData());
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.VERSION.getName())) {
					event = eventReader.nextEvent();
					generator.setVersion(event.asCharacters().getData());
				} else if (event.asStartElement().isCharacters()) {
					generator.setText(event.asCharacters().getData());
				}
			}
		}
		return generator;
	}
	
	@SuppressWarnings("unchecked")
	private Text getText(XMLEvent event, AtomElementName ATOMELEMENT) throws XMLStreamException {
		Text text = new Text();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			text.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(ATOMELEMENT.getName())) {
					break;
				}
			} else if (event.isCharacters()) {
				text.setText(event.asCharacters().getData());
			} 
		}
		return text;
	}
	
	@SuppressWarnings("unchecked")
	private Link getLink(XMLEvent event) throws XMLStreamException {
		Link link = new Link();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			link.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LINK.getName())) {
					break;
				}
			} else if (event.isCharacters()) {
				link.setUndefinedContent(event.asCharacters().getData());
			} 
		}
		return link;
	}
	
	@SuppressWarnings("unchecked")
	private AtomDate getAtomDate(XMLEvent event, AtomElementName ATOMELEMENT) throws XMLStreamException {
		AtomDate atomDate = new AtomDate();
		Iterator<Attribute> attribute = event.asStartElement().getAttributes();
		while (attribute.hasNext()) {
			atomDate.getAttribute().add(attribute.next());
		}

		while (true) {
			event = eventReader.nextEvent();
			if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(ATOMELEMENT.getName())) {
					break;
				}
			} else if (event.isCharacters()) {
				atomDate.setDate(DatatypeConverter.parseDateTime(event.asCharacters().getData()).getTime());
			} 
		}
		return atomDate;
	}

	public Source getHeader() throws XMLStreamException, XMLParseException {
		Source header = new Source();
		
		while (eventReader.hasNext()) {
			XMLEvent peek = eventReader.peek();
			if (peek.isStartElement() && peek.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				hasEntry = true;
				break;
			}
			
			XMLEvent event = eventReader.nextEvent();
			
			if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						header.getAttribute().add(attribute.next());
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					header.setAuthor(getPerson(event, AtomElementName.AUTHOR));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					header.getCategory().add(getCategory(event));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CONTRIBUTOR.getName())) {
					header.getContributor().add(getPerson(event, AtomElementName.CONTRIBUTOR));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					header.setGenerator(getGenerator(event));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ICON.getName())) {
					header.setIcon(getText(event, AtomElementName.ICON));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ID.getName())) {
					header.setId(getText(event, AtomElementName.ID));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LINK.getName())) {
					header.setLink(getLink(event));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					header.setLogo(getText(event, AtomElementName.LOGO));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					header.setRights(getText(event, AtomElementName.RIGHTS));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					header.setSubtitle(getText(event, AtomElementName.SUBTITLE));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					header.setTitle(getText(event, AtomElementName.TITLE));
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					header.setUpdated(getAtomDate(event, AtomElementName.UPDATED));
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					break;
				}
			}
		}
		return header;
	}
	
	public boolean hasEntry() {
		return hasEntry;
	}
	
	public Entry nextEntry() {
		XMLEvent event;
		try {
			event = eventReader.nextEvent();
		} catch (XMLStreamException e) {
			return null;
		}
		Entry entry = new Entry();

		if (event.isStartElement()) {
			if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
				@SuppressWarnings("unchecked")
				Iterator<Attribute> attribute = event.asStartElement().getAttributes();
				while (attribute.hasNext()) {
					entry.getAttribute().add(attribute.next());
				}
			}
		}
		return entry;
	}

	public void parseFullFile(String feedUrl) throws FileNotFoundException, XMLStreamException, Exception {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		eventReader = inputFactory.createXMLEventReader(new FileReader(feedUrl));
		
		parseFull();
	}

	public void parseFullURL(String feedUrl) throws FileNotFoundException, XMLStreamException, Exception {
		URL url;
		try {
			url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in = read(url);
		eventReader = inputFactory.createXMLEventReader(in);
		
		parseFull();
	}
	
	@SuppressWarnings("unchecked")
	private void parseFull() throws FileNotFoundException, XMLStreamException, Exception {
		Feed feed = null;
		Entry entry = null;
		
		Element tree = null;
		
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			
			if (event.isStartElement()) {
				if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					if (feed != null) {
						throw new Exception("Malformed Atom document"); // TODO change type of Exception
					}
					feed = new Feed();
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						feed.getAttribute().add(attribute.next());
					}
					tree = feed;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.AUTHOR.getName())) {
					Person author = new Person();
					author.parent = tree;
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setAuthor(author);
					} else if (tree instanceof Source) {
						((Source)tree).setAuthor(author);
					} else if (tree instanceof Entry) {
						((Entry)tree).setAuthor(author);
					}
					tree = author;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.PERSONNAME.getName())) {
					event = eventReader.nextEvent();
					String name = event.asCharacters().getData();
					event = eventReader.nextEvent();
					if (tree instanceof Person) {
						((Person)tree).setName(name);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.PERSONEMAIL.getName())) {
					Text email = new Text();
					Iterator<Attribute> attributeMail = event.asStartElement().getAttributes();
					while (attributeMail.hasNext()) {
						email.getAttribute().add(attributeMail.next());
					}
					event = eventReader.nextEvent();
					email.setText(event.asCharacters().getData());
					if (tree instanceof Person) {
						((Person)tree).setEmail(email);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.URI.getName())) {
					Text uri = new Text();
					Iterator<Attribute> attributeMail = event.asStartElement().getAttributes();
					while (attributeMail.hasNext()) {
						uri.getAttribute().add(attributeMail.next());
					}
					event = eventReader.nextEvent();
					uri.setText(event.asCharacters().getData());
					if (tree instanceof Person) {
						((Person)tree).setUri(uri);
					} else if (tree instanceof Generator) {
						((Person)tree).setUri(uri);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CATEGORY.getName())) {
					Category category = new Category();
					category.parent = tree;
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().getCategory().add(category);
					} else if (tree instanceof Source) {
						((Source)tree).getCategory().add(category);
					} else if (tree instanceof Entry) {
						((Entry)tree).getCategory().add(category);
					}
					tree = category;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SCHEME.getName())) {
					event = eventReader.nextEvent();
					String scheme = event.asCharacters().getData();
					event = eventReader.nextEvent();
					if (tree instanceof Category) {
						((Category)tree).setScheme(scheme);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LABEL.getName())) {
					event = eventReader.nextEvent();
					String label = event.asCharacters().getData();
					event = eventReader.nextEvent();
					if (tree instanceof Category) {
						((Category)tree).setLabel(label);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.TERM.getName())) {
					event = eventReader.nextEvent();
					String term = event.asCharacters().getData();
					event = eventReader.nextEvent();
					if (tree instanceof Category) {
						((Category)tree).setTerm(term);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.GENERATOR.getName())) {
					Generator generator = new Generator();
					generator.parent = tree;
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setGenerator(generator);
					} else if (tree instanceof Source) {
						((Source)tree).setGenerator(generator);
					}
					tree = generator;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ICON.getName())) {
					Text icon = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						icon.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					icon.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setIcon(icon);
					} else if (tree instanceof Source) {
						((Source)tree).setIcon(icon);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ID.getName())) {
					Text id = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						id.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					id.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setId(id);
					} else if (tree instanceof Source) {
						((Source)tree).setId(id);
					} else if (tree instanceof Entry) {
						((Entry)tree).setId(id);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LINK.getName())) {
					Link link = new Link();
					link.parent = tree;
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setLink(link);
					} else if (tree instanceof Source) {
						((Source)tree).setLink(link);
					} else if (tree instanceof Entry) {
						((Entry)tree).setLink(link);
					}
					tree = link;
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LOGO.getName())) {
					Text logo = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						logo.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					logo.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setLogo(logo);
					} else if (tree instanceof Source) {
						((Source)tree).setLogo(logo);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.RIGHTS.getName())) {
					Text rights = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						rights.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					rights.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setRights(rights);
					} else if (tree instanceof Source) {
						((Source)tree).setRights(rights);
					} else if (tree instanceof Entry) {
						((Entry)tree).setRights(rights);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.SUBTITLE.getName())) {
					Text subtitle = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						subtitle.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					subtitle.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setSubtitle(subtitle);
					} else if (tree instanceof Source) {
						((Source)tree).setSubtitle(subtitle);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.TITLE.getName())) {
					Text title = new Text();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						title.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					title.setText(event.asCharacters().getData());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setTitle(title);
					} else if (tree instanceof Source) {
						((Source)tree).setTitle(title);
					} else if (tree instanceof Entry) {
						((Entry)tree).setTitle(title);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.UPDATED.getName())) {
					AtomDate updated = new AtomDate();
					Iterator<Attribute> attribute = event.asStartElement().getAttributes();
					while (attribute.hasNext()) {
						updated.getAttribute().add(attribute.next());
					}
					event = eventReader.nextEvent();
					updated.setDate(DatatypeConverter.parseDateTime(event.asCharacters().getData()).getTime());
					event = eventReader.nextEvent();
					if (tree instanceof Feed) {
						((Feed)tree).getHeader().setUpdated(updated);
					} else if (tree instanceof Source) {
						((Source)tree).setUpdated(updated);
					} else if (tree instanceof Entry) {
						((Entry)tree).setUpdated(updated);
					}
				} else if (event.asStartElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
					entry = new Entry();
					// TODO review this part in particular
					feed.getEntry().add(entry);
					entry.parent = tree;
					tree = entry;
				}
			} else if (event.isEndElement()) {
				if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.FEED.getName())) {
					return;
				} else if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.AUTHOR.getName())
					|| event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.CATEGORY.getName())
					|| event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.GENERATOR.getName())
					|| event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.LINK.getName())
					|| event.asEndElement().getName().getLocalPart().equalsIgnoreCase(AtomElementName.ENTRY.getName())) {
					tree = tree.parent;
				}
			}
		}
	}

	private InputStream read(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
