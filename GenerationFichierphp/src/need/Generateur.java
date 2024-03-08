package need;

import java.io.BufferedReader;
import java.io.BufferedWriter;
// import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import entity.Colonne;
import entity.Table;

public class Generateur {
    HashMap<String, String> remplacement;

    public void setRemplacement(HashMap<String, String> remplacement) {
        this.remplacement = remplacement;
    }

    public Generateur() {

        HashMap<String, String> remplacement = new HashMap<String, String>();
        remplacement.put("numeric", "number");
        remplacement.put("double precision", "number");
        remplacement.put("decimal", "number");
        remplacement.put("integer", "number");
        remplacement.put("character varying", "text");
        remplacement.put("character", "text");
        remplacement.put("date", "date");
        remplacement.put("timestamp without time zone", "datetime");
        remplacement.put("time without time zone", "time");
        this.setRemplacement(remplacement);
    }

    public Generateur(HashMap<String, String> remplacement) {
        this.remplacement = remplacement;
    }

    public HashMap<String, String> getRemplacement() {
        return remplacement;
    }

    public String lireFichier(String path) throws Exception {
        // File file = new File(path);

        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();

        return String.valueOf(content);
    }

    public String creationChampColonne(Table t,Colonne c) throws Exception {
        if (c.isForeign() == false) {
            return "       <input name='" + c.getNom() + "' type='" + this.getRemplacement().get(c.getType())
                    + "' placeholder='" + c.getNom() + "' >";
        } else {
            String option = "";
            String template = this.lireFichier("template/view/updateForeign.txt");

            for (int i = 0; i < t.getColonnes().length; i++) {
                if(t.getColonnes()[i].isForeign() == true) {
                    String cl = c.getNom().split("id")[1];
                    template = template.replace("[nomTable]",cl);
                    template = template.replace("[Name]", cl);
                    option = option +template;
                }
            }
            return "       <select> \n" +
                    option
                    + "\n        </select>";
        }
    }

    public String creationChampToutColonne(Table t) throws Exception{
        String insertion="";
        System.out.println(t.getColonnes().length+"len");
        for (int i = 0; i < t.getColonnes().length; i++) {
            if(t.getColonnes()[i].isPrimary()==false){
                insertion=insertion+this.creationChampColonne(t,t.getColonnes()[i])+"\n ";
            }
        }
        return insertion;
    }

    public void creationInsertion(String path, Table t) throws Exception {
        // path = "template/view/insertion.txt";
        String template = this.lireFichier("template/view/insertion.txt");
        template=template.replace("[Champ]", this.creationChampToutColonne(t));
        template=template.replace("[nomTable]", t.getNom());

        // CREATION DU FICHIER
        /// String nomFichier = path + "/" + tableName + "." + dotnetOuJava.get("type");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./View/Insertion"+t.getNom()+".php"))) {
            writer.write(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
