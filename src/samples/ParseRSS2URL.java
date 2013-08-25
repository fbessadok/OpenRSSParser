package samples;

import openrssparser.OpenRSS;
import openrssparser.models.rss.Header;

public class ParseRSS2URL {

	public static void main(String args[]) {
		String rss2Feed = "http://cyber.law.harvard.edu/blogs/gems/tech/rss2sample.xml";
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				OpenRSS.PARSER.declareURL(rss2Feed);
				Header header = (Header) OpenRSS.getInstance().getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
