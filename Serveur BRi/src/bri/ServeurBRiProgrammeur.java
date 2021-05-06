package bri;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurBRiProgrammeur implements Runnable{

    private ServerSocket socket_prog;

    public ServeurBRiProgrammeur(int portProg) {
        System.out.println("Serveur BRi Programmeur initialisé");
        try {
            socket_prog = new ServerSocket(portProg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                new ServiceBRiProgrammeur(socket_prog.accept()).start();
                }
        }
        catch (IOException ignored) {
            System.out.println(ignored);
            try {
                this.socket_prog.close();
            } catch (IOException ignored2) {}
        }
    }
}
