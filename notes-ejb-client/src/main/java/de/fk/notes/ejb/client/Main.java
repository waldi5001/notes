package de.fk.notes.ejb.client;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.omg.CORBA.ORB;

import de.fk.notes.corba.Note;
import de.fk.notes.corba.NotesCorbaService;
import de.fk.notes.corba.NotesCorbaServiceHelper;
import de.fk.notes.ejb.api.NotesService;

public class Main {

    private static final InitialContext ic;

    static {
        try {
            ic = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @EJB(name = "ejb/notesEjbService")
    private static NotesService injected1;

    @Resource(name = "testString")
    private static String injectedString;

    @Resource(name = "internerName")
    private static String internerName;

    @Resource(name = "java:comp/ORB")
    private static ORB orb;

    public Main() {
        System.out.println(orb);
        try {
            NotesCorbaService service = NotesCorbaServiceHelper.narrow((org.omg.CORBA.Object) ic.lookup("NotesCorbaService"));
            long parse = service.parse("vonCorba");
            Note note = service.getNote(parse);
            System.out.println("corbaNote " + note);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("injected " + injected1.parse("hh"));
        System.out.println("injected string " + injectedString);
        System.out.println("global jndi string " + internerName);

        NotesService notesEjbService = lookup("ejb/notesEjbService");
        notesEjbService = lookup("java:comp/env/ejb/notesEjbService");
        long parse = notesEjbService.parse("eineTestNote");
        System.out.println("parse returned: " + parse);
        System.out.println("getNote returned: " + notesEjbService.getNote(parse));
    }

    private NotesService lookup(String name) {
        try {
            System.out.println("lookup: " + name);
            NotesService service = (NotesService) ic.lookup(name);
            System.out.println("success. name: " + name);
            return service;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
