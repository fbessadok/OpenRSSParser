package samples;

import openrssparser.OpenRssParser;
import openrssparser.models.common.Entry;
import openrssparser.models.common.Header;

public class ParseRss2URL {

	public static void main(String args[]) {
		String rss2Feed = "http://cyber.law.harvard.edu/blogs/gems/tech/rss2sample.xml";
		OpenRssParser myParser = new OpenRssParser();
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				myParser.declareURL(rss2Feed);
				Header header = myParser.getHeader();
				while (myParser.hasEntry()) {
					Entry entry = myParser.nextEntry();
					entry.getTitle();
				}
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
