package samples;

import java.io.FileReader;
import java.util.List;

import openrssparser.OpenRssParser;
import openrssparser.models.common.Entry;
import openrssparser.models.common.Feed;
import openrssparser.models.common.Header;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

public class ParseAtomFileComparisonWithRome {

	public static void main(String args[]) {
		String atomFeed = System.getProperty("user.dir") + "/src/samples/atom1.xml";
		try {
			for (int i = 0; i < 1000; i++) {
				long begin1 = System.currentTimeMillis();
				parseFullWithOpenRSSParser(atomFeed);
				long end1 = System.currentTimeMillis();

				long begin2 = System.currentTimeMillis();
				parseWithRome(atomFeed);
				long end2 = System.currentTimeMillis();
				System.out.println(i + "\t-\t" + (end1 - begin1) + "\t-\t" + (end2 - begin2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void parseFullWithOpenRSSParser(String atomFeed) throws Exception {
		OpenRssParser myParser = new OpenRssParser();
		myParser.declareFile(atomFeed);
		Feed feed = myParser.getFeed();
		int i = 0;
	}

	public static void parseWithOpenRSSParser(String atomFeed) throws Exception {
		OpenRssParser myParser = new OpenRssParser();
		myParser.declareFile(atomFeed);
		Header header = myParser.getHeader();
		while (myParser.hasEntry()) {
			Entry entry = myParser.nextEntry();
			entry.getAuthors();
		}
	}

	public static void parseWithRome(String atomFeed) throws Exception {
		SyndFeed feed = new SyndFeedInput().build(new FileReader(atomFeed));
		List<SyndEntry> entries = feed.getEntries();
		for (SyndEntry entry : entries) {
			entry.getAuthor();
		}
	}
}
