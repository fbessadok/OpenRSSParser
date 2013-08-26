package openrssparser.models.rss2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import openrssparser.models.common.Header;
import openrssparser.models.common.Person;
import openrssparser.models.common.interfaces.IHeader;

public class Rss2Header implements IHeader {

	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String managingEditor;
	private String webMaster;
	private Date pubDate;
	private Date lastBuildDate;
	private List<String> categories = new ArrayList<String>();
	private String generator;
	private String docs;
	private String cloud;
	private Integer ttl;
	private String rating;
	private Rss2TextInput textInput;
	private List<Integer> skipHours = new ArrayList<Integer>();
	private List<String> skipDays = new ArrayList<String>();
	private Rss2Image image;

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Rss2TextInput getTextInput() {
		return textInput;
	}

	public void setTextInput(Rss2TextInput textInput) {
		this.textInput = textInput;
	}

	public List<Integer> getSkipHours() {
		return skipHours;
	}

	public void setSkipHours(List<Integer> skipHours) {
		this.skipHours = skipHours;
	}

	public List<String> getSkipDays() {
		return skipDays;
	}

	public void setSkipDays(List<String> skipDays) {
		this.skipDays = skipDays;
	}

	public Rss2Image getImage() {
		return image;
	}

	public void setImage(Rss2Image image) {
		this.image = image;
	}

	public Header toCommon() {
		Header common = new Header();
		common.setInitial(this);
		common.getAuthors().add(new Person("", managingEditor, ""));
		for (String category : categories) {
			common.getCategories().add(category);
		}
		common.setTitle(title);
		common.setUrl(link);
		common.setDescription(description);
		if (image != null) {
			common.setLogoUrl(image.getLink());
		}
		common.setPublicationDate(pubDate);
		common.setModificationDate(lastBuildDate);
		return common;
	}

}
