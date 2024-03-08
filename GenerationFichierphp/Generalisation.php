<?php
class Generalisation extends CI_Model {
    //Ces Fonctions retournent des tableaux d'objet

    public function avoirTable($NomTable){
        $query = $this->db->get($NomTable); // Remplacez 'table_name' par le nom de votre table PostgreSQL
		$resultats = $query->result(); // Récupération des résultats
        $raisons = array();
        $i = 0;
		foreach ($resultats as $row) {
			$raisons[$i]=$row; // Remplacez 'column_name' par le nom de la colonne que vous souhaitez afficher
            $i+=1;
		}
        return $raisons;
    }

    public function avoirTableConditionnee($NomTable){
        $sql = "SELECT * FROM $NomTable";
        $query = $this->db->query($sql);
        $resultats = array();
        $a=0;
        foreach($query->result() as $row){
            $resultats[$a] = $row;
            $a++;
        }
        return $resultats;
    }

    public function insertion($NomTable, $values){ // Metre values comme => '(data1, data2, 'data3')' par exemple
        $sql = sprintf('insert into %s values%s',$NomTable, $values);
        // echo $sql;
        $this->db->query($sql);  

    }

    function miseAJour($NomTable, $nouveau, $conditions){ // Metre values comme => '(data1, data2, 'data3')' par exemple
        $sql = sprintf( 'Update %s set %s where %s',$NomTable, $nouveau, $conditions);
        // echo $sql;
        $this->db->query($sql);
    }

    function delete($NomTable,$condition) {
        $sql = sprintf('Delete from %s where %s',$NomTable,$condition);
        $this->db->query($sql);
    }

    public function avoirTableSpecifique($NomTable, $colonnes, $conditions){
        // $sql = "SELECT $colonnes FROM $NomTable WHERE $conditions";
        $sql = "SELECT $colonnes FROM $NomTable WHERE $conditions";
        // echo $sql;
        $query = $this->db->query($sql);
        $resultats = array();
        $a=0;
        foreach($query->result() as $row){
            $resultats[$a] = $row;
            $a++;
        }
        return $resultats;
    }

    public function avoirTableAutrement($NomTable, $colonnes, $conditions ){//mety hoe limit ny eo
        $sql = "SELECT $colonnes FROM $NomTable $conditions";
        $query = $this->db->query($sql);
        $resultats = array();
        $a=0;
        foreach($query->result() as $row){
            $resultats[$a] = $row;
            $a++;
        }
        return $resultats;
    }

    function dateLisible($dateSql) {
        date_default_timezone_set('Europe/Paris');
           // Crée un objet DateTime à partir de la date SQL
        $date = new DateTime($dateSql);
        
        // Crée un objet IntlDateFormatter pour formater la date en français
        $formatter = new IntlDateFormatter('fr_FR', IntlDateFormatter::LONG, IntlDateFormatter::NONE);
        
        // Formate la date
        $date_lisible = $formatter->format($date);
        
        return $date_lisible;
    }

    function differenceEntreDeuxDates($date2, $format) {
        // Crée deux objets DateTime à partir des dates
        $date1 = new DateTime();
        // $datetime1 = new DateTime($date1);
        $datetime2 = new DateTime($date2);
        
        // Calcule la différence entre les deux dates
        $interval = $date1->diff($datetime2);
        
        // Récupère la différence sous forme de jours, mois ou années en fonction du format spécifié
        if ($format === 'jours') {
            return $interval->days;
        } elseif ($format === 'mois') {
            return $interval->y * 12 + $interval->m;
        } elseif ($format === 'annees') {
            return $interval->y;
        } else {
            return false; // Format non valide
        }
    }
}
