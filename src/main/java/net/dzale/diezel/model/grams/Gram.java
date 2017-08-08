package net.dzale.diezel.model.grams;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * A model class defining a basic unit of text, a gram.
 *
 * @drzale
 */
public class Gram {


    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date created;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date modified;

    String body;

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
}
