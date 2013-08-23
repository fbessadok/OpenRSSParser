package openrssparser.atom;

public class Content extends Element {
	
	// TODO maybe transform this in an interface and create the 4 other Content types
	
	// Attribute
	private String type; // change to Enum
	private String src; // uri to media if atomOutOfLineContent
	
	// Element
	private String text;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
