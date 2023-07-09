package de.fk.notes.corba;

public class NotesCorbaServiceImpl extends NotesCorbaServicePOA {

    @Override
    public long parse(String text) {
        return 0;
    }

    @Override
    public ToDo[] getToDos() {
        return new ToDo[] {};
    }

    @Override
    public Note getNote(long id) {
        return new Note(0, 0, "");
    }

    @Override
    public Note[] getNotes(long folderId) {
        return new Note[] {};
    }

    @Override
    public Folder[] getAllFolders() {
        return new Folder[] {};
    }

}
