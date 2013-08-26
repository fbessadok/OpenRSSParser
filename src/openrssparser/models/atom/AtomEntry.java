package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

import openrssparser.models.common.Entry;
import openrssparser.models.common.Person;
import openrssparser.models.common.interfaces.IEntry;

/*
 * 
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
	private AtomSimpleElement content;
	private List<AtomPerson> contributors = new ArrayList<AtomPerson>();
	private AtomText id;
	private List<AtomSimpleElement> links = new ArrayList<AtomSimpleElement>();
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

	public AtomSimpleElement getContent() {
		return content;
	}

	public void setContent(AtomSimpleElement content) {
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

	public List<AtomSimpleElement> getLinks() {
		return links;
	}

	public void setLinks(List<AtomSimpleElement> links) {
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
			Person authorCommon = new Person(author.getName(), author.getEmail().getText(), author.getUrl().getText());
			common.getAuthors().add(authorCommon);
		}
		for (AtomCategory category : categories) {
			for (int i = 0; i < category.getAttributes().size(); i++) {
				if (category.getAttributes().get(i).getName().equals("term")) {
					common.getCategories().add(category.getAttributes().get(i).getValue());
				}
			}
		}
		if (title != null) {
			common.setTitle(title.getText());
		}
		for (AtomSimpleElement link : links) {
			for (int i = 0; i < link.getAttributes().size(); i++) {
				if (link.getAttributes().get(i).getName().equals("href")) {
					common.setUrl(link.getAttributes().get(i).getValue());
					break;
				}
			}
			if (common.getUrl() != null) {
				break;
			}
		}
		if (summary != null) {
			common.setDescription(summary.getText());
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
