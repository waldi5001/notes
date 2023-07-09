package de.fk.notes.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;

@Entity
public class Todo extends AbstractEntity {
    private static final long serialVersionUID = 1L;
    private OffsetDateTime doneAt;
    private boolean done;
    private String description;

    public OffsetDateTime getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(OffsetDateTime doneAt) {
        this.doneAt = doneAt;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
