package openrssparser.models.rss;

import java.util.Date;
import java.util.List;

public class Item {
	
	private String title;
	private String link;
	private String description;
	private List<String> category;
	private String comments;
	private List<Enclosure> enclosure;
	private String guid;
	private Date pubDate;
	private String source;
	private Person author;

}
