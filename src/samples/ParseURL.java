package samples;

import openrssparser.Parser;
import openrssparser.atom.Source;

public class ParseURL {

	public static void main(String args[]) {
		String atomFeed = "http://www.katanapg.com/xml/latest/atom.xml";
		try {
			for (int i = 0; i < 17; i++) {
				long begin = System.currentTimeMillis();
				Parser.PARSER.parseCursorURL(atomFeed);
				Source header = Parser.PARSER.getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
