package openrssparser.models.rss2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import openrssparser.models.common.Entry;
import openrssparser.models.common.Person;
import openrssparser.models.common.interfaces.IEntry;

public class Rss2Item implements IEntry {

	private String title;
	private String link;
	private String description;
	private List<String> categories = new ArrayList<String>();
	private String comments;
	private List<Rss2Enclosure> enclosure = new ArrayList<Rss2Enclosure>();
	private String guid;
	private Date pubDate;
	private String source;
	private Rss2Person author;

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

	public List<Rss2Enclosure> getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(List<Rss2Enclosure> enclosure) {
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

	public Rss2Person getAuthor() {
		return author;
	}

	public void setAuthor(Rss2Person author) {
		this.author = author;
	}

	public Entry toCommon() {
		Entry common = new Entry();
		common.setInitial(this);
		if (author != null) {
			common.getAuthors().add(new Person(author.getName(), author.getEmail(), ""));
		}
		for (String category : categories) {
			common.getCategories().add(category);
		}
		common.setTitle(title);
		common.setUrl(link);
		common.setDescription(description);
		common.setPublicationDate(pubDate);
		common.setModificationDate(null);
		return common;
	}

}
