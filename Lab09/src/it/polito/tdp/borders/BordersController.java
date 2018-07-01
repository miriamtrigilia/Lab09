/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	private Model model = new Model();

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader
	
    @FXML // fx:id="choiceBox"
    private ChoiceBox<Country> choiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="trovaVIcini"
    private Button trovaVIcini; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	
	

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		int anno;
		try {
			anno = Integer.parseInt(this.txtAnno.getText());
		}catch(NumberFormatException e) {
			this.txtResult.setText("Errore! Non hai inserito un numero");
			return;
		}
		
		if(!this.model.isAnnoValid(anno)) { // controllo che l'anno sia compreso tra 1816 e 2016
			this.txtResult.appendText("Non hai inserito un anno compreso tra 1816 e 2016\n");
			return; 
		} 
		
		// calcola confini
		this.model.creaGrafo(anno);
		this.txtResult.appendText("Grafo creato.\nElenco degli stati e del numero di confini:\n");
		this.txtResult.appendText(this.model.getCountriesAndBorders()+"\n");
		this.txtResult.appendText("\nNumero componenti connesse: "+this.model.getComponentiConnesse()+"\n");
		
	}
	
	
	@FXML
    void doTrovaVicini(ActionEvent event) {
		Country partenza = this.choiceBox.getValue();
		this.txtResult.appendText("\nLa lista dei vicini di "+ partenza+ " e':\n");
		for(Country c : this.model.trovaVicini(partenza)) {
			this.txtResult.appendText(c+"\n");
		}
		
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
		assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'Borders.fxml'.";
        assert trovaVIcini != null : "fx:id=\"trovaVIcini\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;
		this.choiceBox.getItems().addAll(model.getCountryList());
		
	}
}
