package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

/*
 * url:			http://www.ietf.org/rfc/rfc4287.txt
 * section:		4.1.1.  The "atom:feed" Element
 * model:
 * 
 * atomFeed =
 * 	element atom:feed {
 * 		atomCommonAttributes,
 * 		(atomAuthor*
 * 		 & atomCategory*
 * 		 & atomContributor*
 * 		 & atomGenerator?
 * 		 & atomIcon?
 * 		 & atomId
 * 		 & atomLink*
 * 		 & atomLogo?
 * 		 & atomRights?
 * 		 & atomSubtitle?
 * 		 & atomTitle
 * 		 & atomUpdated
 * 		 & extensionElement*),
 * 		 atomEntry*
 * 	}
 */

public class Feed extends Element {

	private Source header = new Source();
	private List<Entry> entries = new ArrayList<Entry>();

	public Source getHeader() {
		return header;
	}

	public void setHeader(Source header) {
		this.header = header;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

}