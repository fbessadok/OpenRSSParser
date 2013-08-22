package samples;

import openrssparser.Parser;

public class DummyParseFile {

	public static void main(String args[]) {
		String atomFeed = System.getProperty("user.dir") + "/src/samples/atom1.xml";
		try {
			for (int i = 0; i < 17; i++) {
				long begin = System.currentTimeMillis();
				Parser.PARSER.parseFullFile(atomFeed);
				long end = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end - begin));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
