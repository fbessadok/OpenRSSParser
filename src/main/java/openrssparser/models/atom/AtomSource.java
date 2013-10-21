package openrssparser.models.atom;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML.Attribute;

import openrssparser.models.common.Header;
import openrssparser.models.common.Person;
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
	private List<AtomText> links = new ArrayList<AtomText>();
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

	public List<AtomText> getLinks() {
		return links;
	}

	public void setLinks(List<AtomText> links) {
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

	public Header toCommon() {
		Header common = new Header();
		common.setInitial(this);
		for (AtomPerson author : authors) {
			String name = author.getName() == null ? "" : author.getName();
			String email = author.getEmail() == null ? "" : author.getEmail().getText();
			String url = author.getUrl() == null ? "" : author.getUrl().getText();
			Person authorCommon = new Person(name, email, url);
			common.getAuthors().add(authorCommon);
		}
		for (AtomCategory category : categories) {
			for (int i = 0; i < category.getAttributes().size(); i++) {
				if (category.getAttributes().get(i).getName().equals("term")) {
					common.getCategories().add(category.getAttributes().get(i).getValue());
				}
			}
		}
		if (title != null) {
			common.setTitle(title.getText());
		}
		for (AtomText link : links) {
			if (link.getAttributes() == null) {
				continue;
			}
			boolean alternate = false;
			for (int i = 0; i < link.getAttributes().size(); i++) {
				if (!alternate) {
					alternate = link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.REL.toString()) && link.getAttributes().get(i).getValue().equalsIgnoreCase("alternate");
				} else if (link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.HREF.toString())) {
					common.setUrl(link.getAttributes().get(i).getValue().trim());
					break;
				}
			}
			if (common.getUrl() != null) {
				break;
			}
		}
		if (common.getUrl() == null) {
			for (AtomText link : links) {
				if (link.getAttributes() == null) {
					continue;
				}
				boolean rel = false;
				String value = null;
				for (int i = 0; i < link.getAttributes().size(); i++) {
					if (!rel) {
						rel = link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.REL.toString());
					}
					if (link.getAttributes().get(i).getName().toString().equalsIgnoreCase(Attribute.HREF.toString())) {
						value = link.getAttributes().get(i).getValue().trim();
					}
				}
				if (rel) {
					common.setUrl(value);
					break;
				}
			}
		}
		if (subtitle != null) {
			common.setDescription(subtitle.getText());
		}
		if (logo != null) {
			common.setLogoUrl(logo.getText());
		}
		common.setPublicationDate(null);
		if (updated != null) {
			common.setModificationDate(updated.getDate());
		}
		return common;
	}

}
