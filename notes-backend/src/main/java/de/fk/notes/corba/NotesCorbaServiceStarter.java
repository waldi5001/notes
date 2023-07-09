package de.fk.notes.corba;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

@Singleton(name = "NotesCorbaServiceStarter")
@Startup
public class NotesCorbaServiceStarter {

    @Resource(name = "java:comp/ORB")
    private ORB orb;

    @PostConstruct
    public void initialize() {
        try {
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            NotesCorbaServiceImpl notesCorbaService = new NotesCorbaServiceImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(notesCorbaService);
            NotesCorbaService serviceRef = NotesCorbaServiceHelper.narrow(ref);
            Context ctx = new InitialContext();
            ctx.rebind("NotesCorbaService", serviceRef);
            System.out.println("NotesCorbaService ready and waiting ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
