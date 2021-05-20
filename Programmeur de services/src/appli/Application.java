package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Application {

	private final static int PORT_SERVICE = 3000;
	private final static String HOST = "localhost";

	public static void main(String[] args) {
		Socket s = null;
		try {
			s = new Socket(HOST, PORT_SERVICE);

			BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter sout = new PrintWriter(s.getOutputStream(), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			String line;

			System.out.println("Connecté au serveur " + s.getInetAddress() + ":" + s.getPort());

			while (true) {
				line = sin.readLine();
				System.out.println(line.replaceAll("##", "\n"));
				sout.println(clavier.readLine());
			}

		} catch (IOException e) {
			System.err.println("Fin de la connexion");
		}
		// Refermer dans tous les cas la socket
		try {
			if (s != null)
				s.close();
		} catch (IOException ignored) {
		}
	}
}