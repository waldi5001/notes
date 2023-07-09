package de.fk.notes.ejb.api;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface NotesService {

    long parse(String text);

    List<Todo> getToDos();

    Note getNote(long id);

    List<Note> getNotes(long folderId);

    List<Folder> getAllFolders();
}
