package bri.serveurs;

import bri.services.ServiceBRiProgrammeur;

import java.io.IOException;

public class ServeurBRiProgrammeur extends ServeurBRi{

	public ServeurBRiProgrammeur(int portAma) {
		super(portAma);
	}

	@Override
	void runService() throws IOException {
		new ServiceBRiProgrammeur(getSocket().accept()).start();
	}
}
