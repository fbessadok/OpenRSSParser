package openrssparser.models.atom;

/*
 * atomPersonConstruct =
      atomCommonAttributes,
      (element atom:name { text }
       & element atom:uri { atomUri }?
       & element atom:email { atomEmailAddress }?
       & extensionElement*)
 */

public class AtomPerson extends AtomElement {

	private String name;
	private AtomText uri;
	private AtomText email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtomText getUri() {
		return uri;
	}

	public void setUri(AtomText uri) {
		this.uri = uri;
	}

	public AtomText getEmail() {
		return email;
	}

	public void setEmail(AtomText email) {
		this.email = email;
	}

}
