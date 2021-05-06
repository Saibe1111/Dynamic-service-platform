package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceBRiAmateur implements Runnable{

    private Socket socket;

    public ServiceBRiAmateur(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
            PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);

            out.println("Liste des services"+"##Tapez le numéro de service désiré :");
            int choix = Integer.parseInt(in.readLine());

            System.out.println("Numéro du choix:" + choix);


        } catch (IOException ignored) {}
    }

    @Override
    protected void finalize()  {
        try {this.socket.close();} catch (IOException ignored) {}
    }

    public void start() {
        (new Thread(this)).start();
    }

}
