package samples;

import openrssparser.OpenRss;
import openrssparser.models.atom.AtomSource;

public class ParseAtomFile {

	public static void main(String args[]) {
		String atomFeed = System.getProperty("user.dir") + "/src/samples/atom1.xml";
		try {
			for (int i = 1; i <= 17; i++) {
				long begin = System.currentTimeMillis();
				OpenRss.PARSER.declareFile(atomFeed);
				AtomSource header = (AtomSource) OpenRss.getInstance().getHeader();
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
