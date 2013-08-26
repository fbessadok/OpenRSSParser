package samples;

import openrssparser.OpenRss;
import openrssparser.models.atom.AtomSource;

public class ParseAtomURL {

	public static void main(String args[]) {
		// A list of some atom feeds here http://www.intertwingly.net/wiki/pie/KnownAtomFeeds
		String atomFeed = "http://www.katanapg.com/xml/latest/atom.xml";
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				OpenRss.PARSER.declareURL(atomFeed);
				AtomSource header = (AtomSource) OpenRss.getInstance().getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
