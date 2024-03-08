package need;

import entity.Colonne;
import entity.DonneeEtrangere;
import entity.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Vector;

import java.sql.PreparedStatement;

public class TraitementDonneeBase {
    String choixTable;

    public String getChoix() {
        return choixTable;
    }

    public void setChoix(String choix) {
        this.choixTable = choix;
    }

    String requeteColonne = "select cols.table_name, cols.column_name, cols.data_type, fk.foreign_table_name, fk.foreign_column_name, coalesce(fk.is_primary, 'false') as is_primary, coalesce(fk.is_foreign, 'false') as is_foreign from information_schema.columns as cols left join (SELECT tc.table_name, kcu.column_name, ccu.table_name AS foreign_table_name, ccu.column_name AS foreign_column_name, case when tc.constraint_type='PRIMARY KEY' then 'true' else 'false' end as is_primary, case when tc.constraint_type='FOREIGN KEY' then 'true' else 'false' end as is_foreign FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE tc.table_schema='public' AND tc.table_name='[tableName]') as fk on cols.column_name=fk.column_name and cols.table_name=fk.table_name where cols.table_name='[tableName]'";

    String requeteTable = "SELECT tablename as table_name FROM pg_catalog.pg_tables WHERE schemaname = 'public'";

    String requeteForeign = "select id[tableName],nom[tableName] from [tableName]";

    public String getRequeteForeign() {
        return requeteForeign;
    }

    public void setRequeteForeign(String requeteForeign) {
        this.requeteForeign = requeteForeign;
    }

    public String getRequeteColonne() {
        return requeteColonne;
    }

    public void setRequeteColonne(String requeteColonne) {
        this.requeteColonne = requeteColonne;
    }

    public String getRequeteTable() {
        return requeteTable;
    }

    public void setRequeteTable(String requeteTable) {
        this.requeteTable = requeteTable;
    }

    public static String[] objectToDim(Object[] obj) {
        String[] str = new String[obj.length];
        for (int i = 0; i < str.length; i++) {
            str[i] = (String) obj[i];
        }
        return str;
    }

    public static Table[] objectToTable(Object[] obj) {
        Table[] tables = new Table[obj.length];
        for (int i = 0; i < obj.length; i++) {
            tables[i] = (Table) obj[i];
        }
        return tables;
    }

    public static String[] objectToString(Object[] obj) {
        String[] string = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            string[i] = (String) obj[i];
        }
        return string;
    }

    public Table[] avoirTable(Connection conne, DatabaseMetaData metaData, String schema) throws Exception {
        ResultSet tableResultSet = metaData.getTables(null, schema, "%", new String[] { "TABLE" });
        Vector<Table> table = new Vector<Table>();
        Table t;
        while (tableResultSet.next()) {
            t = new Table(tableResultSet.getString("TABLE_NAME"));
            t.setMyColumn(conne, metaData, schema, this);
            table.add(t);
        }
        tableResultSet.close();
        return TraitementDonneeBase.objectToTable(table.toArray());
    }

    public DonneeEtrangere[] objectToDonneeEtrangeres(Object[] obj) {
        DonneeEtrangere[] d = new DonneeEtrangere[obj.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = (DonneeEtrangere) obj[i];
        }
        return d;
    }

    public DonneeEtrangere[] avoirDonneeEtrangeres(Connection conne, String foreign) throws Exception {
        String requete = this.getRequeteForeign().replace("[tableName]", foreign.split("id")[1]);
        PreparedStatement statement = conne.prepareStatement(requete);
        ResultSet result = statement.executeQuery();
        DonneeEtrangere d;
        Vector<DonneeEtrangere> donneeEtrangeres = new Vector<DonneeEtrangere>();
        while (result.next()) {
            d = new DonneeEtrangere(result.getString(1), result.getString(2));
            donneeEtrangeres.add(d);
        }
        // System.out.println(donneeEtrangeres.toArray().length+"len");
        return this.objectToDonneeEtrangeres(donneeEtrangeres.toArray());
    }

    public String[] avoirTableString(Connection conne, DatabaseMetaData metaData, String schema) throws Exception {
        ResultSet tableResultSet = metaData.getTables(null, schema, "%", new String[] { "TABLE" });
        Vector<String> table = new Vector<String>();
        String t;
        while (tableResultSet.next()) {
            t = tableResultSet.getString("TABLE_NAME");
            table.add(t);
        }
        tableResultSet.close();
        return TraitementDonneeBase.objectToString(table.toArray());
    }

    public String lireFichier(String path) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();

        return String.valueOf(content);
    }

    public void GenerateControlleur(Table table, String path) throws Exception {
        String template = this.lireFichier("template/Controllertemplate.txt");
        String nomFichier = path + "/" + table.getNom() + "Controller.php";
        template = template.replace("[nomTable]", table.getNom());
        String foreign = "";
        String n = "";
        String values = "\"";
        String inputvalue = "";
        String colonne = "(";
        String nouveau = "\"";
        for (int j = 1; j < table.getColonnes().length; j++) {
            inputvalue = inputvalue + "$" + table.getColonnes()[j].getNom() + "=$this->input->post('"
                    + table.getColonnes()[j].getNom() + "'); \n";
            colonne = colonne + table.getColonnes()[j].getNom();
            if (table.getColonnes()[j].getType().equals("integer")
                    || table.getColonnes()[j].getType().equals("double precision")
                    || table.getColonnes()[j].getType().equals("decimal") || table.getColonnes()[j].getType().equals("numeric")) {
                values = values + "\"." + "$" + table.getColonnes()[j].getNom();
                nouveau = nouveau + table.getColonnes()[j].getNom() + "=\"." + "$" + table.getColonnes()[j].getNom();
            } else {
                values = values + "'\".$" + table.getColonnes()[j].getNom();
                nouveau = nouveau + table.getColonnes()[j].getNom() + "='\"." + "$" + table.getColonnes()[j].getNom();
            }
            if (j < table.getColonnes().length - 1) {
                colonne += ",";
                if (table.getColonnes()[j].getType().equals("integer")
                        || table.getColonnes()[j].getType().equals("double precision")
                        || table.getColonnes()[j].getType().equals("decimal") || table.getColonnes()[j].getType().equals("numeric")) {
                    nouveau += ".\",";
                    values += ".\",";
                } else {
                    nouveau += ".\"',";
                    values += ".\"',";
                }
            }
        }
        colonne = colonne + ")";
        values += ".\")\"";
        nouveau += ".\")\"";
        for (int i = 1; i < table.getColonnes().length; i++) {
            n = n + "$" + table.getColonnes()[i].getNom() + "=$this->input->post('" + table.getColonnes()[i].getNom()
                    + "'); \n";
            if (table.getColonnes()[i].isForeign() == true) {
                String colonneForeign = table.getColonnes()[i].getNom();
                String c = colonneForeign.split("id")[1];
                foreign = foreign + "$data['liste" + c + "'] =$this->Generalisation->avoirTableConditionnee('" + c
                        + "');";
            }
        }
        template = template.replace("[values]", values);
        template = template.replace("[colonne]", colonne);
        template = template.replace("[inputValue]", inputvalue);
        template = template.replace("[Foreign]", foreign);
        template = template.replace("[nouveau]", n);
        template = template.replace("[nouveauValeur]", nouveau);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            writer.write(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GenerateListeView(Table table, String path) throws Exception {
        String nomTable = table.getNom();
        String entete = "";
        String content = "";
        String template = lireFichier("template/view/ListeTemplate.txt");
        String nomFichier = path + "/Listes" + nomTable + ".php";
        template = template.replace("[nomTable]", nomTable);
        for (int j = 0; j < table.getColonnes().length; j++) {
            entete += "<td>" + table.getColonnes()[j].getNom() + "</td>";
            content += "<td><?= $" + nomTable + "s->" + table.getColonnes()[j].getNom() + "; ?></td>";
        }
        template = template.replace("[enTete]", entete);
        template = template.replace("[Liste]", content);
        template = template.replace("[valeur]", "$" + nomTable + "s->id" + nomTable);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            writer.write(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String creationChampColonneUpdate(Table t, Colonne c) throws Exception {
        if (c.isPrimary() == true) {
            return "<input name='id' type='hidden' value='<?php= $" + t.getNom() + "->" + c.getNom() + " ?>' > \n";
        } else if (c.isForeign() == false) {
            Generateur g = new Generateur();
            return "       <input name='" + c.getNom() + "' type='" + g.getRemplacement().get(c.getType())
                    + "' value='<?php echo $" + t.getNom() + "->" + c.getNom() + "; ?>'>";
        } else {
            String option = "";
            String template = this.lireFichier("template/view/updateForeign.txt");
            for (int i = 0; i < t.getColonnes().length; i++) {
                if (t.getColonnes()[i].isForeign() == true) {
                    String cl = c.getNom().split("id")[1];
                    template = template.replace("[nomTable]", cl);
                    template = template.replace("[Name]", cl);
                    option = option + template;
                }
            }
            return "       <select> \n" + option + "\n        </select>";
        }
    }

    public String creationChampToutColonne(Table t) throws Exception {
        String update = "";
        for (int i = 0; i < t.getColonnes().length; i++) {
            //    System.out.println(t.getColonnes()[i].getNom()+"id" + t.getNom());
                update += this.creationChampColonneUpdate(t, t.getColonnes()[i]) + "\n";
        }
        return update;
    }

    public void creationUpdate(String path, Table t) throws Exception {
        String template = this.lireFichier("template/view/updateTemplate.txt");
        String nomFichier = path + "/update" + t.getNom() + ".php";
        template = template.replace("[Champ]", this.creationChampToutColonne(t));
        template = template.replace("[nomTable]", t.getNom());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            writer.write(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}