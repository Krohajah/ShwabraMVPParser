package com.krohajah.shwabramvpparser.network;

import android.util.Xml;

import com.krohajah.shwabramvpparser.model.FeedItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Berezin
 */
public class ShwabraXmlParser {

    private static final String TAG_CHANEL = "channel";
    private static final String TAG_ITEM = "item";
    private static final String TAG_TITLE = "title";
    private static final String TAG_LINK = "link";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PUB_DATE = "pubDate";
    private static final String TAG_CREATOR = "dc:creator";
    private static final String TAG_CATEGORY = "category";

    private static final String ns = null;

    public List<FeedItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readRSS(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<FeedItem> readRSS(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<FeedItem> items = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_CHANEL)) {
                items = readChanel(parser);
            } else {
                skip(parser);
            }
        }
        return items;
    }

    private List<FeedItem> readChanel(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<FeedItem> items = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, TAG_CHANEL);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(TAG_ITEM)) {
                items.add(readItem(parser));
            } else {
                skip(parser);
            }
        }

        return items;
    }

    private FeedItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TAG_ITEM);
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String creator = null;
        List<String> categories = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (name) {
                case TAG_TITLE:
                    title = readTag(parser, TAG_TITLE);
                    break;
                case TAG_LINK:
                    link = readTag(parser, TAG_LINK);
                    break;
                case TAG_DESCRIPTION:
                    description = readTag(parser, TAG_DESCRIPTION);
                    break;
                case TAG_CREATOR:
                    creator = readTag(parser, TAG_CREATOR);
                    break;
                case TAG_PUB_DATE:
                    pubDate = readTag(parser, TAG_PUB_DATE);
                    break;
                case TAG_CATEGORY:
                    categories.add(readTag(parser, TAG_CATEGORY));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new FeedItem(title, link, description, pubDate, creator, categories);
    }

    private String readTag(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tagName);
        String tagText = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tagName);
        return tagText;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
