package openrssparser.atom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public class Entry extends Element {

	private List<Person> authors = new ArrayList<Person>();
	private List<Category> categories = new ArrayList<Category>();
	private SimpleElement content;
	private List<Person> contributors = new ArrayList<Person>();
	private Text id;
	private List<SimpleElement> links = new ArrayList<SimpleElement>();
	private Date published;
	private Text rights;
	private Source source;
	private Text summary;
	private Text title;
	private AtomDate updated;

	public List<Person> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Person> author) {
		this.authors = author;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public SimpleElement getContent() {
		return content;
	}

	public void setContent(SimpleElement content) {
		this.content = content;
	}

	public List<Person> getContributors() {
		return contributors;
	}

	public void setContributors(List<Person> contributors) {
		this.contributors = contributors;
	}

	public Text getId() {
		return id;
	}

	public void setId(Text id) {
		this.id = id;
	}

	public List<SimpleElement> getLinks() {
		return links;
	}

	public void setLinks(List<SimpleElement> links) {
		this.links = links;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Text getRights() {
		return rights;
	}

	public void setRights(Text rights) {
		this.rights = rights;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Text getSummary() {
		return summary;
	}

	public void setSummary(Text summary) {
		this.summary = summary;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public AtomDate getUpdated() {
		return updated;
	}

	public void setUpdated(AtomDate updated) {
		this.updated = updated;
	}

}
