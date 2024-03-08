package need;

import java.sql.Connection;

import Connection.Connexion;
import entity.Table;

public class Choix {
    String choixBase;
    String choixTable;
    Table[] table;

    

    public Choix() {
    }

    public Table[] getTable() {
        return table;
    }

    public void setTable(Table[] table) {
        this.table = table;
    }

    public Choix(String choixTable){
        Connexion co=new Connexion();
        Connection conne=null;
        Generateur g=new Generateur();
        try {
             conne=co.postgres();
            this.choixTable= choixTable;
            if(choixTable.equals("*")==false && choixTable.contains(",")==true){
                TraitementDonneeBase traitementDonneeBase=new TraitementDonneeBase();
                String[] nomTable=choixTable.split(",");
                Table[] table=new Table[nomTable.length];
                for (int i = 0; i < table.length; i++) {
                    table[i]=new Table(nomTable[i]);
                    table[i].setMyColumn(conne, conne.getMetaData(), conne.getSchema(), traitementDonneeBase);
                    g.creationInsertion("./Template/insertion.temp", table[i]);
                }
                this.setTable(table);
            }else if(choixTable.equals("*")==false && choixTable.contains(",")==false){
                TraitementDonneeBase traitementDonneeBase=new TraitementDonneeBase();
                Table[] table=new Table[1];
                table[0]=new Table(choixTable);
                table[0].setMyColumn(conne, conne.getMetaData(), conne.getSchema(), traitementDonneeBase);
                this.setTable(table);
                g.creationInsertion("./Template/insertion.temp", table[0]);
            }else if(choixTable.equals("*")){
                TraitementDonneeBase traitementDonneeBase=new TraitementDonneeBase();
                this.setTable(traitementDonneeBase.avoirTable(conne, conne.getMetaData(), conne.getSchema()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conne.close();
            } catch (Exception e1) {
            }
        }
    }

    public String getChoixTable() {
        return choixTable;
    }

    public void setChoixTable(String choix) {
        this.choixTable = choix;
    }
}
