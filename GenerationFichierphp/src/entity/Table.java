package entity;
import java.sql.DatabaseMetaData;

import need.TraitementDonneeBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import java.sql.Connection;
public class Table {
    String nom;
    Colonne[] colonnes;

    public Table(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Colonne[] getColonnes() {
        return colonnes;
    }

    public void setColonnes(Colonne[] colonnes) {
       this.colonnes=colonnes;
    }

    public void setMyColumn(Connection conne,DatabaseMetaData meta,String schema,TraitementDonneeBase traitement){
        String query=traitement.getRequeteColonne().replace("[tableName]", getNom());
        PreparedStatement statement=null;
        try{
             statement=conne.prepareStatement(query);
            Vector<Colonne> listeCols=new Vector<>();
            Colonne column;
            DonneeEtrangere[] donnee;
                ResultSet result=statement.executeQuery();
                while(result.next()){
                    System.out.println(result.getString("column_name"));
                    column=new Colonne(result.getString("column_name"),result.getString("data_type"),result.getBoolean("is_foreign"),result.getBoolean("is_primary"));
                    if(column.isForeign()){
                        donnee=traitement.avoirDonneeEtrangeres(conne, column.getNom());
                        column.setDonneeEtrangeres(donnee);
                    }
                    listeCols.add(column);
                    this.setColonnes(Colonne.objectToColonnes(listeCols.toArray()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }
}
