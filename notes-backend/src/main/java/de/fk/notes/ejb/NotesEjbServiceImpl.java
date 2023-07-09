package de.fk.notes.ejb;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fk.notes.ejb.api.Folder;
import de.fk.notes.ejb.api.Note;
import de.fk.notes.ejb.api.NotesService;
import de.fk.notes.ejb.api.Todo;

@Stateless(name = "NotesEjbService")
public class NotesEjbServiceImpl implements NotesService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long parse(String text) {
        de.fk.notes.model.Note note = new de.fk.notes.model.Note();
        note.setDescription(text);
        em.persist(note);
        em.flush();
        return note.getId();
    }

    @Override
    public List<Todo> getToDos() {
        return em.createQuery("from Todo td order by td.createdAt desc", de.fk.notes.model.Todo.class).getResultList().stream()
                .map(todo -> {
                    Todo result = new Todo();
                    result.setId(todo.getId());
                    result.setDescription(todo.getDescription());
                    result.setCreatedAt(new Date(todo.getCreatedAt().toInstant().toEpochMilli()));
                    return result;
                }).collect(toList());
    }

    @Override
    public Note getNote(long id) {
        de.fk.notes.model.Note note = em.find(de.fk.notes.model.Note.class, id);
        Note result = new Note();
        result.setId(note.getId());
        result.setDescription(note.getDescription());
        result.setCreatedAt(new Date(note.getCreatedAt().toInstant().toEpochMilli()));
        return result;
    }

    @Override
    public List<Note> getNotes(long folderId) {
        return em.createQuery("from Note n order by n.createdAt desc", de.fk.notes.model.Note.class).getResultList().stream().map(note -> {
            Note result = new Note();
            result.setId(note.getId());
            result.setDescription(note.getDescription());
            result.setCreatedAt(new Date(note.getCreatedAt().toInstant().toEpochMilli()));
            return result;
        }).collect(toList());
    }

    @Override
    public List<Folder> getAllFolders() {
        return em.createQuery("from Folder f order by f.createdAt desc", de.fk.notes.model.Folder.class).getResultList().stream()
                .map(folder -> {
                    Folder result = new Folder();
                    result.setId(folder.getId());
                    result.setName(folder.getDescription());
//                    result.setCreatedAt(new Date(folder.getCreatedAt().toInstant().toEpochMilli()));
                    return result;
                }).collect(toList());
    }
}
