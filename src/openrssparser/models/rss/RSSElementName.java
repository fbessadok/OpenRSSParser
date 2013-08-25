package openrssparser.models.rss;

public enum RSSElementName {

	CHANNEL("channel"),
	TITLE("title"),
	LINK("link"),
	DESCRIPTION("description"),
	LANGUAGE("language"),
	COPYRIGHT("copyright"),
	MANAGINGEDITOR("managingEditor"),
	WEBMASTER("webMaster"),
	PUBDATE("pubDate"),
	LASTBUILDDATE("lastBuildDate"),
	CATEGORY("category"),
	GENERATOR("generator"),
	DOCS("docs"),
	CLOUD("cloud"),
	TTL("ttl"),
	IMAGE("image"),
	URL("url"),
	RATING("rating"),
	TEXTINPUT("textInput"),
	NAME("name"),
	SKIPHOURS("skipHours"),
	SKIPDAYS("skipDays"),
	ITEM("item"),
	AUTHOR("author"),
	COMMENTS("comments"),
	ENCLOSURE("enclosure"),
	GUID("guid"),
	SOURCE("source"),
	CREATOR("dc:creator");

	private String name;

	RSSElementName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
