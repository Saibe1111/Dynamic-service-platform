package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import utilisateurs.Programmeur;

public class ServiceBRiProgrammeur implements Runnable {

	private Socket socket;

	public ServiceBRiProgrammeur(Socket s) {
		socket = s;
	}

	@Override
	public void run() {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println("1 - Se connecter##2 - S'inscrire");

			int choix = 0;

			try {
				choix = Integer.parseInt(in.readLine());
			} catch (Exception e) {
				out.println("Numéro incorrect !!");
				System.err.println("Impossible de convertir la saisie utilisateur en un int !");
				return;
			}

			Programmeur p = null;

			switch (choix) {
			case 1: {
				out.println("Login");
				String login = in.readLine();
				out.println("Mot de passe");
				String mdp = in.readLine();
				p = Programmeur.connexion(login, mdp);
				if (p == null) {
					out.println("Impossible de se connecter. Login ou mot de passe incorrect");
					System.err.println("Impossible de se connecter. Login ou mot de passe incorrect");
					return;
				}

				break;
			}
			case 2: {
				out.println("Veuillez choisir un login");
				String login = in.readLine();
				out.println("Veuillez choisir un mot de passe");
				String mdp = in.readLine();
				out.println("Veuillez donner l’url de votre serveur ftp");
				String ftp = in.readLine();
				p = Programmeur.inscription(login, mdp, ftp);
				if (p == null) {
					out.println("Impossible de s'inscrire. Login déjà existant");
					System.err.println("Impossible d'inscrire. Login déjà existant");
					return;
				}

				break;
			}
			default:
				out.println("Numéro incorrect !!");
				System.err.println("Numéro incorrect !!");
				return;
			}

			choix = 0;

			out.println(
					"1 - Fournir un nouveau service##2 - Mettre-à-jour un service##3 - Changer l’adresse du serveur ftp");
			try {
				choix = Integer.parseInt(in.readLine());
			} catch (Exception e) {
				out.println("Numéro incorrect !!");
				System.err.println("Impossible de convertir la saisie utilisateur en un int !");
				return;
			}

			switch (choix) {
			case 1: {
				// URLClassLoader sur ftp
				URLClassLoader urlcl = new URLClassLoader(new URL[] { new URL(p.getFtp()) });

				out.println("Veuillez donner le nom de votre service");
				String service = in.readLine();
				
				// charger la classe et la déclarer au ServiceRegistry
				Class<?> clazz;
				try {
					clazz = urlcl.loadClass(p.getLogin() + "." + service);
					ServiceRegistry.addService(clazz);
				} catch (Exception e) {
					e.printStackTrace();
				}
				

				break;
			}
			case 2: {

				break;
			}
			case 3: {
				out.println("Veuillez donner l’url de votre serveur ftp");
				String ftp = in.readLine();
				p.setFtp(ftp);
				Programmeur.updateProgammeurs(p);
				break;
			}
			default:
				out.println("Numéro incorrect !!");
				System.err.println("Numéro incorrect !!");
				return;
			}
		} catch (IOException ignored) {
		}

	}

	@Override
	protected void finalize() throws IOException {
		socket.close();
	}

	public void start() {
		(new Thread(this)).start();
	}

}