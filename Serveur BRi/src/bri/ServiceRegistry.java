package bri;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
    // cette classe est un registre de services
    // partagée en concurrence par les clients et les "ajouteurs" de services,
    // un Vector pour cette gestion est pratique

    static {
        servicesClasses = new Vector<>();
    }

    private static List<Class<?>> servicesClasses;

    // ajoute une classe de service après contrôle de la norme BLTi
    public static void addService(Class<?> classeName) throws Exception {

        int modifiers = classeName.getModifiers();
        Class<?>[] interfacesImplementees = classeName.getInterfaces();
        try {
            Method methode = classeName.getMethod("toStringue");
        } catch (NoSuchMethodException | SecurityException e) {
            throw new Exception("##Erreur : Le service doit avoir une méthode public static String toStringue()");
        }

        if (Modifier.isPrivate(modifiers))
            throw new Exception("##Erreur : Le service doit être public");

        if (Modifier.isAbstract(modifiers))
            throw new Exception("##Erreur : Le service ne doit pas être abstract");

        if (!Arrays.toString(interfacesImplementees).contains(Service.class.toString()))
            throw new Exception("##Erreur : Le service doit implémenter l'interface Service");

        try {
            servicesClasses.add(classeName);
            System.out.println("Service ajouté avec succès");
        } catch (SecurityException e1) {
            throw new Exception("##Erreur : Le service doit avoir un constructeur public (Socket)");
        }

    }

    // renvoie la classe de service (numService -1)
    public static Class<?> getServiceClass(int numService) {
        return servicesClasses.get(numService - 1);
    }

    // liste les activités présentes
    public static String toStringue() {
        StringBuilder result = new StringBuilder("Activités présentes :##");
        int i = 0;
        for (Class c : servicesClasses) {
            i++;
            result.append(i).append(". ").append(c.getName()).append("##");
        }
        return result.toString();
    }
}