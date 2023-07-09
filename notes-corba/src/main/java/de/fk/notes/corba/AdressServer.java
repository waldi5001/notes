package de.fk.notes.corba;

import java.util.Properties;

import de.wwag.adress.Service;
import de.wwag.adress.ServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;


public class AdressServer {

	public static void main(String[] args) {
		try {
			System.setProperty("org.glassfish.gmbal.no.multipleUpperBoundsException","true");

			Properties props = new Properties();
			props.setProperty("org.omg.CORBA.ORBInitialPort", "1050");

			ORB orb = ORB.init(args, props);

			// get reference to rootpoa and activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			AdressServiceImpl helloImpl = new AdressServiceImpl();
			helloImpl.setORB(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);

			Service href = ServiceHelper.narrow(ref);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			String name = "Adress";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			System.out.println("AdressServer ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
