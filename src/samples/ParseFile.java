package samples;

import openrssparser.OpenRSS;
import openrssparser.models.atom.Source;

public class ParseFile {

	public static void main(String args[]) {
		String atomFeed = System.getProperty("user.dir") + "/src/samples/atom1.xml";
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				OpenRSS.PARSER.declareFile(atomFeed);
				Source header = (Source) OpenRSS.getInstance().getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
