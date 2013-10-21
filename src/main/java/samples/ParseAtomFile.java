package samples;

import openrssparser.OpenRssParser;
import openrssparser.models.common.Header;

public class ParseAtomFile {

	public static void main(String args[]) {
		String atomFeed = ParseAtomFile.class.getResource("/atom1.xml").getPath();
		OpenRssParser myParser = new OpenRssParser();
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				myParser.declareFile(atomFeed);
				Header header = myParser.getHeader();
				myParser.close();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
