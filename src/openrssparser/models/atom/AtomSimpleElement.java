package openrssparser.models.atom;

public class AtomSimpleElement extends AtomElement {
	
	// TODO maybe transform this in an interface and create the 4 other Content types

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
