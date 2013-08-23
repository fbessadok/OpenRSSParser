package openrssparser.atom;

import java.util.ArrayList;
import java.util.List;

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

public class Source extends Element {

	private List<Person> author = new ArrayList<Person>();
	private List<Category> category = new ArrayList<Category>();
	private List<Person> contributor = new ArrayList<Person>();
	private Generator generator;
	private Text icon;
	private Text id;
	private List<SimpleElement> link = new ArrayList<SimpleElement>();
	private Text logo;
	private Text rights;
	private Text subtitle;
	private Text title;
	private AtomDate updated;

	public List<Person> getAuthor() {
		return author;
	}

	public void setAuthor(List<Person> author) {
		this.author = author;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public List<Person> getContributor() {
		return contributor;
	}

	public void setContributor(List<Person> contributor) {
		this.contributor = contributor;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public Text getIcon() {
		return icon;
	}

	public void setIcon(Text icon) {
		this.icon = icon;
	}

	public Text getId() {
		return id;
	}

	public void setId(Text id) {
		this.id = id;
	}

	public List<SimpleElement> getLink() {
		return link;
	}

	public void setLink(List<SimpleElement> link) {
		this.link = link;
	}

	public Text getLogo() {
		return logo;
	}

	public void setLogo(Text logo) {
		this.logo = logo;
	}

	public Text getRights() {
		return rights;
	}

	public void setRights(Text rights) {
		this.rights = rights;
	}

	public Text getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(Text subtitle) {
		this.subtitle = subtitle;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public AtomDate getUpdated() {
		return updated;
	}

	public void setUpdated(AtomDate updated) {
		this.updated = updated;
	}

}
