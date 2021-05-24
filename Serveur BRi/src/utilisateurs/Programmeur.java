package utilisateurs;

import java.util.ArrayList;
import java.util.HashMap;

public class Programmeur {

	private String login;
	private String pwd;
	private String ftp;

	static {
		programmeurs = new HashMap<String, Programmeur>();
		onlineProgrammeurs = new ArrayList<>();
	}

	private static HashMap<String, Programmeur> programmeurs;
	private static ArrayList<String> onlineProgrammeurs;

	public static Programmeur inscription(Programmeur p) {
		synchronized (programmeurs) {
			if (programmeurs.containsKey(p.getLogin()))
				return null;
			programmeurs.put(p.getLogin(), p);
		}
		Programmeur.addOnlineUser(p.getLogin());
		return p;
	}

	public static Programmeur inscription(String login, String pwd, String ftp) {
		return inscription(new Programmeur(login, pwd, ftp));
	}

	public static Programmeur connexion(String login, String pwd) {
		synchronized (onlineProgrammeurs) {
			Programmeur p = programmeurs.get(login);
			if (p == null || onlineProgrammeurs.contains(p.getLogin()))
				return null;
			if (p.getPwd().equals(pwd)) {
				Programmeur.addOnlineUser(p.getLogin());
				return p;
			}
		}
		return null;
	}

	public static void updateProgammeurs(Programmeur p) {
		synchronized (programmeurs) {
			programmeurs.put(p.getLogin(), p);
		}
	}

	public Programmeur(String login, String pwd, String ftp) {
		this.login = login;
		this.pwd = pwd;
		this.ftp = ftp;
	}

	public String getFtp() {
		return ftp;
	}

	public void setFtp(String ftp) {
		this.ftp = ftp;
	}

	public String getLogin() {
		return login;
	}

	public String getPwd() {
		return pwd;
	}

	public static void removeOnlineUser(String s) {
		System.out.println(s + " s'est déconnecté");
		onlineProgrammeurs.remove(s);
	}

	public static void addOnlineUser(String s) {
		System.out.println(s + " s'est connecté");
		onlineProgrammeurs.add(s);
	}

}
