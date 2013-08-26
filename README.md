# Welcome to Open RSS Parser

Open RSS Parser, as its name says, is an RSS/Atom feeds parser. It aims to be light, easy to use, and doesn't rely on external libraries.
Created on august 23th, 2013, it's obviously extremely small and not usable in real projects yet.  

## Current Version

For now it supports

* Atom feeds as described in the [RFC4287](http://www.ietf.org/rfc/rfc4287.txt) specification
* RSS 2 feeds as described in [Harvard](http://cyber.law.harvard.edu/tech/rss) specification

All cases are not yet implemented but most of them are; And the code is voluntarily very simple. There are no errors handling and no optimizations.

## HowTo

1. Declare your source
	* If it's a local feed file use declareFile(filePath) method
	* If it's a remote feed use declareURL(url) method
2. getHeader() First
3. Then iterate using hasEntry() and nextEntry() each time

**Example**

    OpenRssParser myParser = new OpenRssParser();
    myParser.declareFile(feedUrl/feedPath);
    Header header = myParser.getHeader();
    while (myParser.hasEntry()) {
      Entry entry = myParser.nextEntry();
      entry.getTitle();
    }

You can get the type of your feed using this method

    myParser.getFeedType(); // Returns either FeedType.ATOM or FeedType.RSS2

The `Header` and `Entry` objects are two unifications between Atom and Rss2 models. Some informations that are not widely used might have been lost in the unification process. If you want to use them anyway, the real objects are stored inside these ones. So you can use the `getInitial()` method to get them:

    header.getInitial(); // Returns an instance of AtomSource or Rss2Header depending on the value of FeedType
    entry.getInitial(); // Returns an instance of AtomEntry or Rss2Item depending on the value of FeedType

More examples in the samples package.

## Wanna help ?

Feel free to give suggestions, corrections. You can open issues and pull requests are welcome.