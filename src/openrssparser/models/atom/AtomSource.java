package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

import openrssparser.models.common.interfaces.IHeader;

/*
 *  atomSource =
      element atom:source {
         atomCommonAttributes,
         (atomAuthor*
          & atomCategory*
          & atomContributor*
          & atomGenerator?
          & atomIcon?
          & atomId?
          & atomLink*
          & atomLogo?
          & atomRights?
          & atomSubtitle?
          & atomTitle?
          & atomUpdated?
          & extensionElement*)
      }
 */

public class AtomSource extends AtomElement implements IHeader {

	private List<AtomPerson> authors = new ArrayList<AtomPerson>();
	private List<AtomCategory> categories = new ArrayList<AtomCategory>();
	private List<AtomPerson> contributors = new ArrayList<AtomPerson>();
	private AtomGenerator generator;
	private AtomText icon;
	private AtomText id;
	private List<AtomSimpleElement> links = new ArrayList<AtomSimpleElement>();
	private AtomText logo;
	private AtomText rights;
	private AtomText subtitle;
	private AtomText title;
	private AtomDate updated;

	public List<AtomPerson> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AtomPerson> authors) {
		this.authors = authors;
	}

	public List<AtomCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<AtomCategory> categories) {
		this.categories = categories;
	}

	public List<AtomPerson> getContributors() {
		return contributors;
	}

	public void setContributors(List<AtomPerson> contributors) {
		this.contributors = contributors;
	}

	public AtomGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(AtomGenerator generator) {
		this.generator = generator;
	}

	public AtomText getIcon() {
		return icon;
	}

	public void setIcon(AtomText icon) {
		this.icon = icon;
	}

	public AtomText getId() {
		return id;
	}

	public void setId(AtomText id) {
		this.id = id;
	}

	public List<AtomSimpleElement> getLinks() {
		return links;
	}

	public void setLinks(List<AtomSimpleElement> links) {
		this.links = links;
	}

	public AtomText getLogo() {
		return logo;
	}

	public void setLogo(AtomText logo) {
		this.logo = logo;
	}

	public AtomText getRights() {
		return rights;
	}

	public void setRights(AtomText rights) {
		this.rights = rights;
	}

	public AtomText getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(AtomText subtitle) {
		this.subtitle = subtitle;
	}

	public AtomText getTitle() {
		return title;
	}

	public void setTitle(AtomText title) {
		this.title = title;
	}

	public AtomDate getUpdated() {
		return updated;
	}

	public void setUpdated(AtomDate updated) {
		this.updated = updated;
	}

}
