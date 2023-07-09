package de.fk.notes.corba;

import java.util.Properties;

import de.wwag.adress.Adresse;
import de.wwag.adress.Geschlecht;
import de.wwag.adress.Service;
import de.wwag.adress.ServiceHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;



public class AdressClient {

	public static void main(String[] args) {
		try {
			//https://github.com/eclipse-ee4j/orb-gmbal/issues/22
			System.setProperty("org.glassfish.gmbal.no.multipleUpperBoundsException","true");

			Properties props = new Properties();
			props.setProperty("org.omg.CORBA.ORBInitialPort", "1050");
			ORB orb = ORB.init(args, props);

			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// resolve the Object Reference in Naming
			String name = "Adress";
			Service service = ServiceHelper.narrow(ncRef.resolve_str(name));

			System.out.println("Obtained a handle on server object: " + service);
			Adresse a = new Adresse();
			a.vorname="";
			a.geschlecht= Geschlecht.M;
			a.ort="";
			a.plz="";
			System.out.println(service.create(new Adresse[] { a }));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
