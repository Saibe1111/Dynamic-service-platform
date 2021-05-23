package bri.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import utilisateurs.Programmeur;

public class ServiceBRiProgrammeur extends ServiceBRi {

	public ServiceBRiProgrammeur(Socket s) {
		super(s);
	}

	@Override
	void process(BufferedReader in, PrintWriter out) throws IOException {

		Programmeur p = connexionInscription(in,out);
		if(p != null){
			boolean continuer = true;
			while (continuer){
				continuer = choixAction(in,out,p);
			}
			Programmeur.removeOnlineUser(p.getLogin());
		}

	}

	private Programmeur connexionInscription(BufferedReader in, PrintWriter out) throws IOException {
		int choix = 0;

		out.println("1 - Se connecter##2 - S'inscrire");
		try {
			choix = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			out.println("Numéro incorrect !!");
			System.err.println("Impossible de convertir la saisie utilisateur en un int !");
			return null;
		}

		Programmeur p = null;
		switch (choix) {
			case 1 -> {
				out.println("Login");
				String login = in.readLine();
				out.println("Mot de passe");
				String mdp = in.readLine();
				p = Programmeur.connexion(login, mdp);
				if (p == null) {
					out.println("Impossible de se connecter. Login ou mot de passe incorrect");
					System.err.println("Impossible de se connecter. Login ou mot de passe incorrect");
					return null;
				}
				break;
			}
			case 2 -> {
				out.println("Veuillez choisir un login");
				String login = in.readLine();
				out.println("Veuillez choisir un mot de passe");
				String mdp = in.readLine();
				out.println("Veuillez donner l'url de votre serveur ftp");
				String ftp = in.readLine();
				p = Programmeur.inscription(login, mdp, ftp);
				if (p == null) {
					out.println("Impossible de s'inscrire. Login déjà  existant");
					System.err.println("Impossible d'inscrire. Login déjà  existant");
					return null;
				}
				break;
			}
			default -> {
				out.println("Numéro incorrect !!");
				System.err.println("Numéro incorrect !!");
				return null;
			}
		}
		return p;
	}

	private boolean choixAction(BufferedReader in, PrintWriter out, Programmeur p) throws IOException {

		int choix = 0;
		out.println(
				"1 - Fournir un nouveau service##2 - Mettre-à-jour un service##3 - Changer l'adresse du serveur ftp##4 - Quitter");
		try {
			choix = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			out.println("Numéro incorrect !!");
			System.err.println("Impossible de convertir la saisie utilisateur en un int !");
			return false;
		}

		switch (choix) {
			case 1 -> {

				URLClassLoader urlcl = new URLClassLoader(new URL[]{new URL(p.getFtp())});

				out.println("Veuillez donner le nom de votre service");
				String service = in.readLine();
				Class<?> clazz;
				try {
					clazz = urlcl.loadClass(p.getLogin() + "." + service);
					ServiceRegistry.addService(clazz);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
			case 2 -> {
				// URLClassLoader sur ftp
				URLClassLoader urlcl = new URLClassLoader(new URL[]{new URL(p.getFtp())});

				out.println("Veuillez donner le nom de votre service à  mettre à  jour");
				String service = in.readLine();

				// charger la classe et la déclarer au ServiceRegistry
				Class<?> clazz;
				try {
					clazz = urlcl.loadClass(p.getLogin() + "." + service);
					ServiceRegistry.updateService(clazz);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
			case 3 -> {
				out.println("Veuillez donner l'url de votre serveur ftp");
				String ftp = in.readLine();
				p.setFtp(ftp);
				Programmeur.updateProgammeurs(p);
				return true;
			}
			case 4 -> {
				return false;
			}
			default -> {
				out.println("Numéro incorrect !!");
				System.err.println("Numéro incorrect !!");
				return false;
			}
		}

	}

}