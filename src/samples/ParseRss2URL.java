package samples;

import openrssparser.OpenRss;
import openrssparser.models.rss2.Rss2Header;
import openrssparser.models.rss2.Rss2Item;

public class ParseRss2URL {

	public static void main(String args[]) {
		String rss2Feed = "http://cyber.law.harvard.edu/blogs/gems/tech/rss2sample.xml";
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				OpenRss.PARSER.declareURL(rss2Feed);
				Rss2Header header = (Rss2Header) OpenRss.getInstance().getHeader();
				while (OpenRss.getInstance().hasEntry()) {
					Rss2Item item = (Rss2Item) OpenRss.getInstance().nextEntry();
					item.getAuthor();
				}
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
