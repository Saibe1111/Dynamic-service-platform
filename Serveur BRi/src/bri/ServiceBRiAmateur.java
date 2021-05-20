package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ServiceBRiAmateur implements Runnable {

	private Socket socket;

	public ServiceBRiAmateur(Socket s) {
		socket = s;
	}

	@Override
	public void run() {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println(ServiceRegistry.toStringue() + "##Tapez le numéro du service désiré :");
			int choix = 0;

			try {
				choix = Integer.parseInt(in.readLine());
			} catch (Exception e) {
				out.println("Numéro incorrect !!");
				System.err.println("Impossible de convertir la saisie utilisateur en un int !");
				return;
			}

			Service s;
			try {
				s = (Service) ServiceRegistry.getServiceClass(choix).getDeclaredConstructor(Socket.class).newInstance(socket);
				s.run();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
            
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

	public void start() {
		(new Thread(this)).start();
	}

}
