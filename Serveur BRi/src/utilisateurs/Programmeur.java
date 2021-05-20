package utilisateurs;

import java.util.HashMap;

public class Programmeur {
	
	private String login;
	private  String pwd;
	private String ftp;
	
	static {
        programmeurs = new HashMap<String, Programmeur>();
    }

    private static HashMap<String, Programmeur> programmeurs;
    
    public static Programmeur inscription(Programmeur p) {
    	if(programmeurs.containsKey(p.getLogin()))
    		return null;
    	programmeurs.put(p.getLogin(), p);
    	return p;
    }
	
    public static Programmeur inscription(String login, String pwd, String ftp) {
    	return inscription(new Programmeur(login, pwd, ftp));
    }
    
    public static Programmeur connexion(String login, String pwd) {
    	Programmeur p = programmeurs.get(login);
    	if(p == null)
    		return null;
    	if(p.getPwd().equals(pwd))
    		return p;
    	return null;
    }
    
    public static void updateProgammeurs (Programmeur p) {
    	programmeurs.put(p.getLogin(), p);
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
	
	
}
