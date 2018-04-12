package com.krohajah.shwabramvpparser.ui.activity.main.loader;


import com.krohajah.shwabramvpparser.model.FeedItem;
import com.krohajah.shwabramvpparser.network.ShwabraXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @author Maxim Berezin
 */
public class Loader {

    private static final String URL_ADDR = "https://habrahabr.ru/rss/hubs/all/";


    public List<FeedItem> load() {
        try {
            return loadXmlFromNetwork(URL_ADDR);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<FeedItem> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        ShwabraXmlParser shwabraXmlParser = new ShwabraXmlParser();
        List<FeedItem> entries;

        try {
            stream = downloadUrl(urlString);
            entries = shwabraXmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}
