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

public class AtomFeed extends AtomElement {

	private AtomSource header = new AtomSource();
	private List<AtomEntry> entries = new ArrayList<AtomEntry>();

	public AtomSource getHeader() {
		return header;
	}

	public void setHeader(AtomSource header) {
		this.header = header;
	}

	public List<AtomEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<AtomEntry> entries) {
		this.entries = entries;
	}

}