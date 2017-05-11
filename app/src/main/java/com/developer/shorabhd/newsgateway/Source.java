package com.developer.shorabhd.newsgateway;

import java.io.Serializable;

/**
 * Created by shorabhd on 5/7/17.
 */

public class Source implements Serializable {

    String id;
    String source;
    String description;

    public Source(String id, String name, String desc, String sourceUrl, String category) {
        this.id = id;
        this.source = name;
        this.description = desc;
        this.url = sourceUrl;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String url;
    String category;

}
