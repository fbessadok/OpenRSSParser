package samples;

import openrssparser.OpenRssParser;
import openrssparser.models.common.Header;

public class ParseAtomURL {

	public static void main(String args[]) {
		// A list of some atom feeds here http://www.intertwingly.net/wiki/pie/KnownAtomFeeds
		String atomFeed = "http://www.katanapg.com/xml/latest/atom.xml";
		OpenRssParser myParser = new OpenRssParser();
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				myParser.declareURL(atomFeed);
				Header header = myParser.getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
