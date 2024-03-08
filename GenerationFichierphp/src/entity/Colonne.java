package entity;

public class Colonne{
    String nom;
    String type;
    boolean isForeign;
    boolean isPrimary;
    DonneeEtrangere[] donneeEtrangeres;

    public Colonne() {
    }

    public Colonne(String nom, String type, boolean isForeign, boolean isPrimary) {
        this.nom = nom;
        this.type = type;
        this.isForeign = isForeign;
        this.isPrimary = isPrimary;
    }

    public DonneeEtrangere[] getDonneeEtrangeres() {
        return donneeEtrangeres;
    }

    public void setDonneeEtrangeres(DonneeEtrangere[] donneeEtrangeres) {
        this.donneeEtrangeres = donneeEtrangeres;
    }

    public boolean isForeign() {
        return isForeign;
    }

    public void setForeign(boolean isForeign) {
        this.isForeign = isForeign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public static Colonne[] objectToColonnes(Object[] obj){
        Colonne[] colonnes=new Colonne[obj.length];
        for (int i = 0; i < colonnes.length; i++) {
            colonnes[i]=(Colonne)obj[i];
        }
        return colonnes;
    }
}