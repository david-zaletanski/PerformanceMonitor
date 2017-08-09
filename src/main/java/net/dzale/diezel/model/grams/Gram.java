package net.dzale.diezel.model.grams;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * A model class defining a basic unit of text, a gram.
 *
 * @dzale
 */
public class Gram {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date created;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date modified;
    private String body;

    public Gram() {
        this.body = "";
        created = new Date();
        modified = created;
    }

    public Gram(String body) {
        this.body = body;
        created = new Date();
        modified = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        this.modified = new Date();
    }

    public String getModifiedAsMinutesAgo() {
        Date current = new Date();
        long seconds = (current.getTime() - modified.getTime()) / 1000;
        if (seconds < 60)
            return seconds + " seconds ago";
        long minutes = seconds / 60;
        if (minutes < 60)
            return minutes + " minutes ago";
        long hours = minutes / 60;
        if (hours < 24)
            return hours + " hours ago";
        long days = hours / 24;
        if (days < 365)
            return days + " days ago";
        long years = days / 365;
        return years + " years ago";
    }
}
