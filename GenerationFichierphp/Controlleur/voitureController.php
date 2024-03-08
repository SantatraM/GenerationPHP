<?php 
    defined ('BASEPATH') OR exit ('No direct script access allowed');
    class voitureController extends CI_Controller {

        public function insertvoiture(){
            $designation=$this->input->post('designation'); 
$idmarque=$this->input->post('idmarque'); 
$numero=$this->input->post('numero'); 

            $value = "'".$designation."','".$idmarque."',".$numero.")";
            $this->Generalisation->insertion("voiture(designation,idmarque,numero)",$value);
            redirect("voitureController/affichagevoiture");
        }

        public function affichagevoiture() {
            $data['listevoiture'] = $this->Generalisation->avoirTableConditionnee("voiture");
            $this->load->view('Listevoiture',$data);
        }

        public function deletevoiture() {
            $id = $this->input->get("idvoiture");
            $this->Generalisation->delete("voiture","idvoiture='".$id."'");
            redirect('voitureController/affichagevoiture');
        }

        public function FormulaireUpdatevoiture() {
            $id = $this->input->get("idvoiture");
            $data['voiture']=$this->Generalisation->avoirTableSpecifique("voiture","*","idvoiture='".$id."'");
            $data['listemarque'] =$this->Generalisation->avoirTableConditionnee('marque');
            $this->load->view('updatevoiture',$data);
        }

        public function updatevoiture(){
            $idvoiture=$this->input->post('idvoiture'); 
$designation=$this->input->post('designation'); 
$idmarque=$this->input->post('idmarque'); 
$numero=$this->input->post('numero'); 

            $nouveu = "designation='".$designation."',idmarque='".$idmarque."',numero=".$numero.")";
            $this->Generalisation->miseAJour("voiture", $nouveau, $idvoiture);
            redirect('voitureController/affichagevoiture');
        }

}
