package main;

import need.Choix;
import need.Generateur;
import need.TraitementDonneeBase;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String path = "Controlleur";
            String pathView = "View";
            Choix choix=new Choix("*");
            TraitementDonneeBase t = new TraitementDonneeBase();
            
            Generateur g = new Generateur();
            for(int i=0;i<choix.getTable().length;i++) {
                t.GenerateControlleur(choix.getTable()[i],path);
                t.GenerateListeView(choix.getTable()[i], pathView);
                t.creationUpdate(pathView,choix.getTable()[i]);
                g.creationInsertion("View",choix.getTable()[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
