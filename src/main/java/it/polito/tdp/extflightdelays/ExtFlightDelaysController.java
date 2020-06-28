/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Arco;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="numeroTratteTxtInput"
    private TextField numeroTratteTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	String xS=this.compagnieMinimo.getText();
    	
        Integer x;
    	
    	try {
    		x=Integer.parseInt(xS);
    	}catch(NumberFormatException e) {
    		
    		txtResult.setText("Devi inserire solo numeri");
    		return ;
    	}
    	
    	this.model.creaGrafo(x);
    	txtResult.appendText("Grafo Creato!\n");
     	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
     	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
     	
     	this.cmbBoxAeroportoDestinazione.getItems().addAll(model.Airport());
     	this.cmbBoxAeroportoPartenza.getItems().addAll(model.Airport());
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	Airport a=this.cmbBoxAeroportoPartenza.getValue();
    	
    	List<Arco> aList=this.model.getConnessi(a);
    	
    	txtResult.appendText("Aereoporto"+a+"è connesso ad:\n");
    	
    	for(Arco aC: aList) {
    		txtResult.appendText(aC.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	Airport partenza=this.cmbBoxAeroportoPartenza.getValue();
    	Airport destinazione=this.cmbBoxAeroportoDestinazione.getValue();
    	
    	if(partenza==null && destinazione==null) {
    		txtResult.setText("Devi prima creare il grafo\n");
    		return ;
    	}
    	
    	if(partenza==null || destinazione==null) {
    		txtResult.setText("Devi inserire un aereporto di partenza e uno di arrivo\n");
    		return ;
    	}
    	
    	if(partenza.equals(destinazione)) {
    		txtResult.setText("Devi scegliere due aereoporti diversi!\n");
    		return ;
    	}
    	
        String xS=this.numeroTratteTxtInput.getText();
    	
        Integer x;
    	
    	try {
    		x=Integer.parseInt(xS);
    	}catch(NumberFormatException e) {
    		
    		txtResult.setText("Devi inserire solo numeri");
    		return ;
    	}
    	
    	List<Airport> cammino=model.getCammino(partenza, destinazione, x);
    	
    	txtResult.appendText("Il cammino dall'aereoporto "+partenza+" all'aereporto di arrivo "+destinazione+" ha durata "+ model.getBestTratta()+" e passa tramite:\n");
    	
    	for(Airport a: cammino) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroTratteTxtInput != null : "fx:id=\"numeroTratteTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    
    public void setModel(Model model) {
  		this.model = model;
  		
  	}
}
