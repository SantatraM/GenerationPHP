package entity;

public class DonneeEtrangere {
    String id;
    String nom;

    public String getId() {
        return id;
    }
    public DonneeEtrangere(String id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
