package App;

import bri.serveurs.ServeurBRiAmateur;
import bri.serveurs.ServeurBRiProgrammeur;

public class App {

    private final static int PORT_PROG = 3000;
    private final static int PORT_AMA = 3001;

    public static void main(String[] args) {

        System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");

        new Thread(new ServeurBRiProgrammeur(PORT_PROG)).start();
        new Thread(new ServeurBRiAmateur(PORT_AMA)).start();

    }
}
