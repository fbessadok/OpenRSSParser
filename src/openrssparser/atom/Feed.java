package openrssparser.atom;

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

	private Source header;
	private List<Entry> entry;

	public Feed() {
		header = new Source();
		entry = new ArrayList<Entry>();
	}

	public Source getHeader() {
		return header;
	}

	public void setHeader(Source header) {
		this.header = header;
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}

}