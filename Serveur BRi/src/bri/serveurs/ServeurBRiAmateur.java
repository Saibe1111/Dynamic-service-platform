package bri.serveurs;

import bri.services.ServiceBRiAmateur;

import java.io.IOException;

public class ServeurBRiAmateur extends ServeurBRi{


    public ServeurBRiAmateur(int portAma) {
        super(portAma);
    }

    @Override
    void runService() throws IOException {
        new ServiceBRiAmateur(getSocket().accept()).start();
    }
}
