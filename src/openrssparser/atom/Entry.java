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

	private List<Person> author = new ArrayList<Person>();
	private List<Category> category = new ArrayList<Category>();
	private Content content;
	private List<Person> contributor = new ArrayList<Person>();
	private Text id;
	private List<Link> link = new ArrayList<Link>();
	private Date published;
	private Text rights;
	private Source source;
	private Text summary;
	private Text title;
	private AtomDate updated;
	
	public List<Person> getAuthor() {
		return author;
	}

	public void setAuthor(List<Person> author) {
		this.author = author;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public List<Person> getContributor() {
		return contributor;
	}

	public void setContributor(List<Person> contributor) {
		this.contributor = contributor;
	}

	public Text getId() {
		return id;
	}

	public void setId(Text id) {
		this.id = id;
	}

	public List<Link> getLink() {
		return link;
	}

	public void setLink(List<Link> link) {
		this.link = link;
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
