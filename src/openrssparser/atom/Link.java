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

	private String undefinedContent;

	public String getUndefinedContent() {
		return undefinedContent;
	}

	public void setUndefinedContent(String undefinedContent) {
		this.undefinedContent = undefinedContent;
	}

}
