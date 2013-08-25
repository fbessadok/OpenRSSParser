package openrssparser.models.rss2;

import java.util.Date;
import java.util.List;

public class Item {

	private String title;
	private String link;
	private String description;
	private List<String> categories;
	private String comments;
	private List<Enclosure> enclosure;
	private String guid;
	private Date pubDate;
	private String source;
	private Person author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Enclosure> getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(List<Enclosure> enclosure) {
		this.enclosure = enclosure;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

}
