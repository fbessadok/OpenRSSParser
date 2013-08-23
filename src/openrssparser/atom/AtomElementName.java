package openrssparser.atom;

/*
 * We're putting all the element names here and not
 * in their respective classes because some elements
 * can have different names but the same type.
 * For example Icon, Id, and Rights have different names
 * but are all handled using the Text.java class.
 */

public enum AtomElementName {

	FEED("feed"),
	AUTHOR("author"),
	CATEGORY("category"),
	CONTRIBUTOR("contributor"),
	GENERATOR("generator"),
	ICON("icon"),
	ID("id"),
	LINK("link"),
	LOGO("logo"),
	RIGHTS("rights"),
	SUBTITLE("subtitle"),
	TITLE("title"),
	UPDATED("updated"),
	PUBLISHED("published"),
	SOURCE("source"),
	ENTRY("entry"),
	CONTENT("content"),
	SUMMARY("summary"),
	PERSONNAME("name"),
	PERSONEMAIL("email"),
	URI("uri"),
	VERSION("version"),
	SCHEME("scheme"),
	LABEL("label"),
	TERM("term");

	private String name;

	AtomElementName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
