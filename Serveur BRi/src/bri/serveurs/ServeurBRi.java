package bri.serveurs;

import java.io.IOException;
import java.net.ServerSocket;

abstract public class ServeurBRi implements Runnable{

    private ServerSocket socket;

    public ServeurBRi(int portAma) {
        System.out.println("Serveur BRi initialisé sur le port:" + portAma);
        try {
            socket = new ServerSocket(portAma);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                runService();
            }
        }
        catch (IOException ignored) {
            try {
                this.socket.close();
            } catch (IOException ignored2) {}
        }
    }

    ServerSocket getSocket() {
        return socket;
    }

    abstract void runService() throws IOException;

}