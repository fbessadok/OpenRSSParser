package openrssparser.atom;

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
