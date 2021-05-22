package bri.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

abstract public class ServiceBRi implements Runnable {

    private Socket socket;

    public ServiceBRi(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            process(in, out);
        } catch (IOException ignored) {
        }
    }

    @Override
    protected void finalize() {
        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
    }

    Socket getSocket() {
        return socket;
    }

    public void start() {
        (new Thread(this)).start();
    }

    abstract void process(BufferedReader in,  PrintWriter out) throws IOException;

}