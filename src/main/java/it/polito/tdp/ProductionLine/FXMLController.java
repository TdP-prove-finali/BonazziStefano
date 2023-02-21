/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.ProductionLine;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import it.polito.tdp.ProductionLine.model.Model;
import it.polito.tdp.ProductionLine.model.Order;
import it.polito.tdp.ProductionLine.model.Press;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="VBoxOptimize"
    private VBox VBoxOptimize; // Value injected by FXMLLoader

    @FXML // fx:id="VBoxSim"
    private VBox VBoxSim; // Value injected by FXMLLoader

    @FXML // fx:id="cmb_presses_opt"
    private ComboBox<?> cmb_presses_opt; // Value injected by FXMLLoader

    @FXML // fx:id="cmb_presses_sim"
    private ComboBox<?> cmb_presses_sim; // Value injected by FXMLLoader

    @FXML // fx:id="opt_description"
    private TextField opt_description; // Value injected by FXMLLoader

    @FXML // fx:id="opt_lot"
    private TextField opt_lot; // Value injected by FXMLLoader

    @FXML // fx:id="opt_order_date"
    private TextField opt_order_date; // Value injected by FXMLLoader

    @FXML // fx:id="opt_pieces"
    private TextField opt_pieces; // Value injected by FXMLLoader

    @FXML // fx:id="opt_tons"
    private TextField opt_tons; // Value injected by FXMLLoader

    @FXML // fx:id="sim_cycle_time"
    private TextField sim_cycle_time; // Value injected by FXMLLoader

    @FXML // fx:id="sim_error"
    private TextField sim_error; // Value injected by FXMLLoader

    @FXML // fx:id="sim_press"
    private TextField sim_press; // Value injected by FXMLLoader

    @FXML // fx:id="sim_press_tons"
    private TextField sim_press_tons; // Value injected by FXMLLoader

    @FXML // fx:id="sim_setup_time"
    private TextField sim_setup_time; // Value injected by FXMLLoader

    @FXML // fx:id="txt_area"
    private TextArea txt_area; // Value injected by FXMLLoader

    @FXML
    void doAddErrorSim(ActionEvent event) {
    	Double error_probability;
    	
    	try {
    		error_probability = Double.parseDouble(this.sim_error.getText());
		} catch (Exception e) {
			
    		return;
		}
    	
    	String s = this.model.addErrorSim(error_probability);	

    	this.txt_area.setText(s); // Da aggiungere il testo non settarlo.
    }

    @FXML
    void doAddOrderOpt(ActionEvent event) {
    	LocalDateTime date;
    	try {
    		date = LocalDateTime.parse(this.opt_order_date.getText());
		} catch (Exception e) {
			this.txt_area.setText("Data non valida");
			return;
		}
    	
    	String lot = this.opt_lot.getText();
    	
    	Integer pieces;
    	try {
			pieces = Integer.parseInt(this.opt_pieces.getText());
		} catch (Exception e) {
			this.txt_area.setText("Pezzi non validi");
    		return;
		}
  
    	Integer tons;
    	try {
    		tons = Integer.parseInt(this.opt_tons.getText());
		} catch (Exception e) {
			this.txt_area.setText("Tons non valide");
    		return;
		}
    	
    	String description = this.opt_description.getText();
    	
    	Order order = new Order(date, lot, pieces, description, tons);
    	
    	String s = this.model.addOrderOpt(order);	

    	this.txt_area.setText(s); // Da aggiungere il testo non settarlo.
    	
    	//Rimuovi i campi (TASTO cancel?)  Tasto delete?
    }

    @FXML
    void doAddPressSim(ActionEvent event) {
    	Integer id;
    	try {
    		id = Integer.parseInt(this.sim_press.getText());
		} catch (Exception e) {
			
    		return;
		}
    	
    	Integer tons;
    	try {
    		tons = Integer.parseInt(this.sim_press_tons.getText());
		} catch (Exception e) {
			
    		return;
		}
    	
    	Double cycle_time;
    	try {
    		cycle_time = Double.parseDouble(this.sim_cycle_time.getText());
		} catch (Exception e) {
			
    		return;
		}
    	
    	Double setup_time;
    	try {
    		setup_time = Double.parseDouble(this.sim_setup_time.getText());
		} catch (Exception e) {
			
    		return;
		}
    	
    	if(id == null || tons == null) { // Casistiche + feedback
    		
    	}
    	
    	Press press = new Press(id, tons, cycle_time, setup_time);
    	
    	String s = this.model.addPressSim(press);
    	
    	this.txt_area.setText(s);	// Da aggiungere il testo non settarlo.
    }

    @FXML
    void doOptimize(ActionEvent event) {
    	//Visibile il simula
    	String s = this.model.optimize();
    	this.txt_area.setText(s);
    }

    @FXML
    void doOptimizeForPress(ActionEvent event) {

    }

    @FXML
    void doRemoveOrderOpt(ActionEvent event) {

    }

    @FXML
    void doRemovePressSim(ActionEvent event) {

    }

    @FXML
    void doSelectPressOpt(ActionEvent event) {

    }

    @FXML
    void doSelectPressSim(ActionEvent event) {

    }

    @FXML
    void doSimForPress(ActionEvent event) {

    }

    @FXML
    void doSimulate(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert VBoxOptimize != null : "fx:id=\"VBoxOptimize\" was not injected: check your FXML file 'Scene.fxml'.";
        assert VBoxSim != null : "fx:id=\"VBoxSim\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb_presses_opt != null : "fx:id=\"cmb_presses_opt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb_presses_sim != null : "fx:id=\"cmb_presses_sim\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_description != null : "fx:id=\"opt_description\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_lot != null : "fx:id=\"opt_lot\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_order_date != null : "fx:id=\"opt_order_date\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_pieces != null : "fx:id=\"opt_pieces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_tons != null : "fx:id=\"opt_tons\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sim_cycle_time != null : "fx:id=\"sim_cycle_time\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sim_error != null : "fx:id=\"sim_error\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sim_press != null : "fx:id=\"sim_press\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sim_press_tons != null : "fx:id=\"sim_press_tons\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sim_setup_time != null : "fx:id=\"sim_setup_time\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txt_area != null : "fx:id=\"txt_area\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    }
    
}
