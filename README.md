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

    OpenRSS.PARSER.declareFile(atomFeed);
    Source header = OpenRSS.getInstance().getHeader();
    while (OpenRSS.getInstance().hasEntry()) {
      Entry/Item entry = (Entry/Item)OpenRSS.getInstance().nextEntry();
      entry.getAuthors();
    }

You can store the parser in its own variable after declaring your source:

    IParser myParser = OpenRSS.getInstance();

More examples in the samples package.

## Wanna help ?

Feel free to give suggestions, corrections. You can open issues and pull requests are welcome.