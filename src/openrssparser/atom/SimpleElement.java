package openrssparser.atom;

public class SimpleElement extends Element {
	
	// TODO maybe transform this in an interface and create the 4 other Content types

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
