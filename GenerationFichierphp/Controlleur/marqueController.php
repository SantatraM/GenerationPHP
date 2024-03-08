<?php 
    defined ('BASEPATH') OR exit ('No direct script access allowed');
    class marqueController extends CI_Controller {

        public function insertmarque(){
            $nommarque=$this->input->post('nommarque'); 
$datesortie=$this->input->post('datesortie'); 

            $value = "'".$nommarque."','".$datesortie.")";
            $this->Generalisation->insertion("marque(nommarque,datesortie)",$value);
            redirect("marqueController/affichagemarque");
        }

        public function affichagemarque() {
            $data['listemarque'] = $this->Generalisation->avoirTableConditionnee("marque");
            $this->load->view('Listemarque',$data);
        }

        public function deletemarque() {
            $id = $this->input->get("idmarque");
            $this->Generalisation->delete("marque","idmarque='".$id."'");
            redirect('marqueController/affichagemarque');
        }

        public function FormulaireUpdatemarque() {
            $id = $this->input->get("idmarque");
            $data['marque']=$this->Generalisation->avoirTableSpecifique("marque","*","idmarque='".$id."'");
            
            $this->load->view('updatemarque',$data);
        }

        public function updatemarque(){
            $idmarque=$this->input->post('idmarque'); 
$nommarque=$this->input->post('nommarque'); 
$datesortie=$this->input->post('datesortie'); 

            $nouveu = "nommarque='".$nommarque."',datesortie='".$datesortie.")";
            $this->Generalisation->miseAJour("marque", $nouveau, $idmarque);
            redirect('marqueController/affichagemarque');
        }

}
