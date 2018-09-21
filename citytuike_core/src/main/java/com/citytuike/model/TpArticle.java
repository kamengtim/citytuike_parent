package com.citytuike.model;

import java.io.Serializable;

public class TpArticle implements Serializable{
    private Integer article_id;

    private Short cat_id;

    private String title;

    private String author;

    private String author_email;

    private String keywords;

    private Boolean article_type;

    private Boolean is_open;

    private Integer add_time;

    private String file_url;

    private Boolean open_type;

    private String link;

    private Integer click;

    private Integer publish_time;

    private String thumb;

    private Boolean top;

    private String content;

    private String description;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public Short getCat_id() {
        return cat_id;
    }

    public void setCat_id(Short cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getArticle_type() {
        return article_type;
    }

    public void setArticle_type(Boolean article_type) {
        this.article_type = article_type;
    }

    public Boolean getIs_open() {
        return is_open;
    }

    public void setIs_open(Boolean is_open) {
        this.is_open = is_open;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public Boolean getOpen_type() {
        return open_type;
    }

    public void setOpen_type(Boolean open_type) {
        this.open_type = open_type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Integer publish_time) {
        this.publish_time = publish_time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}