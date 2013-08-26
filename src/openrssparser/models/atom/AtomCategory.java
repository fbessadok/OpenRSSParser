package openrssparser.models.atom;

/*
 * 
 * atomCategory =
      element atom:category {
         atomCommonAttributes,
         attribute term { text },
         attribute scheme { atomUri }?,
         attribute label { text }?,
         undefinedContent
      }
 */

public class AtomCategory extends AtomElement {

	private String undefinedContent;

	public String getUndefinedContent() {
		return undefinedContent;
	}

	public void setUndefinedContent(String undefinedContent) {
		this.undefinedContent = undefinedContent;
	}

}
