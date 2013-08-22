package openrssparser.atom;

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

public class Category extends Element {
	
	private String term;
	private String scheme;
	private String label;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
