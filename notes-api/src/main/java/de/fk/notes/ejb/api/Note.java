package de.fk.notes.ejb.api;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private Date createdAt;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
