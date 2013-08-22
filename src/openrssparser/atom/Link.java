package openrssparser.atom;

/*
 * atomLink =
      element atom:link {
         atomCommonAttributes,
         attribute href { atomUri },
         attribute rel { atomNCName | atomUri }?,
         attribute type { atomMediaType }?,
         attribute hreflang { atomLanguageTag }?,
         attribute title { text }?,
         attribute length { text }?,
         undefinedContent
      }
 */

public class Link extends Element {
	// Attribute
	// TODO maybe change some attribute type to Enum
	private String href;
	private String rel;
	private String type;
	private String hreflang;
	private String title;
	private String length;
	private String undefinedContent;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHreflang() {
		return hreflang;
	}

	public void setHreflang(String hreflang) {
		this.hreflang = hreflang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getUndefinedContent() {
		return undefinedContent;
	}

	public void setUndefinedContent(String undefinedContent) {
		this.undefinedContent = undefinedContent;
	}

}
