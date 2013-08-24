package openrssparser.models.rss;

import java.util.Date;
import java.util.List;

public class Header {
	
	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String managingEditor;
	private String webMaster;
	private Date pubDate;
	private String lastBuildDate;
	private List<String> category;
	private String generator;
	private String docs;
	private String cloud;
	private Integer ttl;
	private String rating;
	private TextInput textInput;
	private List<Integer> skipHours;
	private List<Integer> skipDays;
	private Image image;

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

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
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

	public TextInput getTextInput() {
		return textInput;
	}

	public void setTextInput(TextInput textInput) {
		this.textInput = textInput;
	}

	public List<Integer> getSkipHours() {
		return skipHours;
	}

	public void setSkipHours(List<Integer> skipHours) {
		this.skipHours = skipHours;
	}

	public List<Integer> getSkipDays() {
		return skipDays;
	}

	public void setSkipDays(List<Integer> skipDays) {
		this.skipDays = skipDays;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
