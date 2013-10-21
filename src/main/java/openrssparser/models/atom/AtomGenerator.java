package openrssparser.models.atom;

/*
 * atomGenerator = element atom:generator {
      atomCommonAttributes,
      attribute uri { atomUri }?,
      attribute version { text }?,
      text
   }
 */

public class AtomGenerator extends AtomElement {

	private String uri;
	private String version;
	private String text;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
