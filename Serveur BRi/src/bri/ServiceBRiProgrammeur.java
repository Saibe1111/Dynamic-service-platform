package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceBRiProgrammeur implements Runnable{

    private Socket socket;

    public ServiceBRiProgrammeur(Socket s) {
        socket = s;
    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
            PrintWriter out = new PrintWriter (socket.getOutputStream ( ), true);

            out.println("Login");
            String login = in.readLine();
            out.println("Mot de passe");
            String mdp = in.readLine();

            System.out.println("Login: " + login);
            System.out.println("Mot de passe: " + mdp);

        } catch (IOException ignored) {}

    }

    @Override
    protected void finalize() throws IOException {
        socket.close();
    }

    public void start() {
        (new Thread(this)).start();
    }

}