package net.dzale.diezel.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * The database entity representation of the Gram class.
 *
 * @author dzale
 */
@Entity
public class GramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    Date created;

    @Column
    Date modified;

    @Column
    String body;

    public GramEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    }
}
