package com.krohajah.shwabramvpparser.model;

import java.util.List;

/**
 * @author Maxim Berezin
 */
public class FeedItem {
    private final String title;
    private final String link;
    private final String description;
    private final String pubDate;
    private final String creator;
    private final List<String> categories;

    public FeedItem(String title, String link, String description, String pubDate, String creator, List<String> categories) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.creator = creator;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCreator() {
        return creator;
    }

    public List<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return title + " "
                + link + ""
                + description + ""
                + pubDate + ""
                + creator + "";
    }
}
