package bri;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurBRiAmateur implements Runnable{

    private ServerSocket socket_ama;

    public ServeurBRiAmateur(int portAma) {
        System.out.println("Serveur BRi Amateur initialisé");
        try {

            socket_ama = new ServerSocket(portAma);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                new ServiceBRiAmateur(socket_ama.accept()).start();
            }
        }
        catch (IOException ignored) {
            try {
                this.socket_ama.close();
            } catch (IOException ignored2) {}
        }
    }
}
