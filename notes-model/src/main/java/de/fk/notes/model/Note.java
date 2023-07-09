package de.fk.notes.model;

import javax.persistence.Entity;

@Entity
public class Note extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    private String description;
    private Folder folder;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
