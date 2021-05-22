package bri.serveurs;

import bri.services.ServiceBRiProgrammeur;
import utilisateurs.Programmeur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ServeurBRiProgrammeur extends ServeurBRi{

	public ServeurBRiProgrammeur(int portAma) {
		super(portAma);
	}

	@Override
	void runService() throws IOException {
		new ServiceBRiProgrammeur(getSocket().accept()).start();
	}
}
