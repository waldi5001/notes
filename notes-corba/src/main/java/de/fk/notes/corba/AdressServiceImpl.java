package de.fk.notes.corba;

import de.wwag.adress.Adresse;
import de.wwag.adress.ServicePOA;
import org.omg.CORBA.ORB;

class AdressServiceImpl extends ServicePOA {

	private ORB orb;

	public void setORB(ORB orb) {
		this.orb = orb;
	}

	@Override
	public String sayHello() {
		return "HeyHey";
	}

	@Override
	public void shutdown() {
		orb.shutdown(true);
	}

	@Override
	public long[] create(Adresse[] adresses) {
		for (Adresse adresse : adresses) {

		}
		return new long[] { 5 };
	}

}