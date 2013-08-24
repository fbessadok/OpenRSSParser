package openrssparser.models.atom;

/*
 * atomPersonConstruct =
      atomCommonAttributes,
      (element atom:name { text }
       & element atom:uri { atomUri }?
       & element atom:email { atomEmailAddress }?
       & extensionElement*)
 */

public class Person extends Element {

	private String name;
	private Text uri;
	private Text email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Text getUri() {
		return uri;
	}

	public void setUri(Text uri) {
		this.uri = uri;
	}

	public Text getEmail() {
		return email;
	}

	public void setEmail(Text email) {
		this.email = email;
	}

}
