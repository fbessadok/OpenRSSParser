package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Attribute;

import openrssparser.models.common.Entry;
import openrssparser.models.common.Person;
import openrssparser.models.common.interfaces.IEntry;

/*
 * url:			http://www.ietf.org/rfc/rfc4287.txt
 * section:		4.1.2.  The "atom:entry" Element
 * model:
 * 
 * atomEntry =
 * 	element atom:entry {
 * 		atomCommonAttributes,
 * 		(atomAuthor*
 * 		 & atomCategory*
 * 		 & atomContent?
 * 		 & atomContributor*
 * 		 & atomId
 * 		 & atomLink*
 * 		 & atomPublished?
 * 		 & atomRights?
 * 		 & atomSource?
 * 		 & atomSummary?
 * 		 & atomTitle
 * 		 & atomUpdated
 * 		 & extensionElement*)
 * 	}
 * 
 */

public class AtomEntry extends AtomElement implements IEntry {

	private List<AtomPerson> authors = new ArrayList<AtomPerson>();
	private List<AtomCategory> categories = new ArrayList<AtomCategory>();
	private AtomText content;
	private List<AtomPerson> contributors = new ArrayList<AtomPerson>();
	private AtomText id;
	private List<AtomText> links = new ArrayList<AtomText>();
	private AtomDate published;
	private AtomText rights;
	private AtomSource source;
	private AtomText summary;
	private AtomText title;
	private AtomDate updated;

	public List<AtomPerson> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AtomPerson> author) {
		this.authors = author;
	}

	public List<AtomCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<AtomCategory> categories) {
		this.categories = categories;
	}

	public AtomText getContent() {
		return content;
	}

	public void setContent(AtomText content) {
		this.content = content;
	}

	public List<AtomPerson> getContributors() {
		return contributors;
	}

	public void setContributors(List<AtomPerson> contributors) {
		this.contributors = contributors;
	}

	public AtomText getId() {
		return id;
	}

	public void setId(AtomText id) {
		this.id = id;
	}

	public List<AtomText> getLinks() {
		return links;
	}

	public void setLinks(List<AtomText> links) {
		this.links = links;
	}

	public AtomDate getPublished() {
		return published;
	}

	public void setPublished(AtomDate published) {
		this.published = published;
	}

	public AtomText getRights() {
		return rights;
	}

	public void setRights(AtomText rights) {
		this.rights = rights;
	}

	public AtomSource getSource() {
		return source;
	}

	public void setSource(AtomSource source) {
		this.source = source;
	}

	public AtomText getSummary() {
		return summary;
	}

	public void setSummary(AtomText summary) {
		this.summary = summary;
	}

	public AtomText getTitle() {
		return title;
	}

	public void setTitle(AtomText title) {
		this.title = title;
	}

	public AtomDate getUpdated() {
		return updated;
	}

	public void setUpdated(AtomDate updated) {
		this.updated = updated;
	}

	public Entry toCommon() {
		Entry common = new Entry();
		common.setInitial(this);
		for (AtomPerson author : authors) {
			String name = author.getName() == null ? "" : author.getName();
			String email = author.getEmail() == null ? "" : author.getEmail().getText();
			String url = author.getUrl() == null ? "" : author.getUrl().getText();
			Person authorCommon = new Person(name, email, url);
			common.getAuthors().add(authorCommon);
		}
		for (AtomCategory category : categories) {
			for (int i = 0; i < category.getAttributes().size(); i++) {
				if (category.getAttributes() != null && category.getAttributes().get(i).getName().equals("term")) {
					common.getCategories().add(category.getAttributes().get(i).getValue());
				}
			}
		}
		if (title != null) {
			common.setTitle(title.getText());
		}
		for (AtomText link : links) {
			if (link.getAttributes() == null) {
				continue;
			}
			boolean alternate = false;
			for (int i = 0; i < link.getAttributes().size(); i++) {
				if (!alternate) {
					alternate = link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.REL.toString()) && link.getAttributes().get(i).getValue().equalsIgnoreCase("alternate");
				} else if (link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.HREF.toString())) {
					common.setUrl(link.getAttributes().get(i).getValue().trim());
					break;
				}
			}
			if (common.getUrl() != null) {
				break;
			}
		}
		if (common.getUrl() == null) {
			for (AtomText link : links) {
				if (link.getAttributes() == null) {
					continue;
				}
				boolean rel = false;
				String value = null;
				for (int i = 0; i < link.getAttributes().size(); i++) {
					if (!rel) {
						rel = link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.REL.toString());
					}
					if (link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.HREF.toString())) {
						value = link.getAttributes().get(i).getValue().trim();
					}
				}
				if (rel) {
					common.setUrl(value);
					break;
				}
			}
		}
		if (content != null) {
			common.setDescription(content.getText());
		}
		if (published != null) {
			common.setPublicationDate(published.getDate());
		}
		if (updated != null) {
			common.setModificationDate(updated.getDate());
		}
		return common;
	}

}
