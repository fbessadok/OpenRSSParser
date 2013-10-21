# Welcome to Open RSS Parser

Open RSS Parser, as its name says, is an RSS/Atom feeds parser. It aims to be light, easy to use, and doesn't rely on external libraries.
Created on august 23th, 2013, it's obviously extremely small and not usable in production yet.

[![Build Status](https://travis-ci.org/fbessadok/OpenRSSParser.png?branch=master)](https://travis-ci.org/fbessadok/OpenRSSParser)

## Current Version

For now it supports

* Atom feeds as described in the [RFC4287](http://www.ietf.org/rfc/rfc4287.txt) specification
* RSS 2 feeds as described in [Harvard](http://cyber.law.harvard.edu/tech/rss) specification

All cases are not yet implemented but most of them are; And the code is voluntarily very simple. There are no errors handling and no optimizations.

## HowTo

You'll have to first declare your source:
* If it's a local feed file use declareFile(filePath) method
* If it's a remote feed use declareURL(url) method

Then, there are two ways to use Open Rss Parser :
1. Let the parser do all the work and returns you a `Feed` object containing all the data in the atom/rss file
2. Iterate yourself on the feed to get the elements (one header and one or many entries) (be sure to close the parser at the end)

**Example 1**

    OpenRssParser myParser = new OpenRssParser();
    myParser.declareFile(feedPath); // Or myParser.declareURL(feedUrl);
    Feed feed = myParser.getFeed(); // The parser closes itself before returning the Feed object

**Example 2**

    OpenRssParser myParser = new OpenRssParser();
    myParser.declareFile(feedPath); // Or myParser.declareURL(feedUrl);
    Header header = myParser.getHeader();
    while (myParser.hasEntry()) {
      Entry entry = myParser.nextEntry();
      entry.getTitle();
    }
    myParser.close();

The difference between these two ways is that the first one reads and parses all the file, create all the objects and returns them all at once in a Feed instance, but the second one is like a cursor: it reads the elements one by one and return them on the go. So the second way is faster than the first one. If you need to get all the data of an atom/rss file, you may need to use the first method, if you need to get only one or two entries of a feed you should use the second way (don't forget to `getHeader()` first and `close()` at the end). Both ways take a few milliseconds to execute on regular feeds (a dozens of entries).

You can get the type of your feed using this method

    myParser.getFeedType(); // Returns either FeedType.ATOM or FeedType.RSS2

The `Header` and `Entry` objects are two unifications between Atom and Rss2 models. Some informations that are not widely used might have been lost in the unification process. If you want to use them anyway, the real objects are stored inside these ones. So you can use the `getInitial()` method to get them:

    header.getInitial(); // Returns an instance of AtomSource or Rss2Header depending on the value of FeedType
    entry.getInitial(); // Returns an instance of AtomEntry or Rss2Item depending on the value of FeedType

More examples in the samples package.

## Wanna help ?

Feel free to give suggestions, corrections. You can open issues and pull requests are welcome.