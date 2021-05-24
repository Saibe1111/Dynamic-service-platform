package bri.services;


import bri.Service;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ServiceBRiAmateur extends ServiceBRi {

	public ServiceBRiAmateur(Socket s) {
		super(s);
	}

	@Override
	void process(BufferedReader in, PrintWriter out) {
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
			s = (Service) ServiceRegistry.getServiceClass(choix).getDeclaredConstructor(Socket.class).newInstance(getSocket());
			s.run();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

	}
}
