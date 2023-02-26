/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.ProductionLine;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.ProductionLine.model.Model;
import it.polito.tdp.ProductionLine.model.Press;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;

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
    
    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="cmb_presses_opt"
    private ComboBox<Press> cmb_presses_opt; // Value injected by FXMLLoader

    @FXML // fx:id="cmb_presses_sim"
    private ComboBox<Press> cmb_presses_sim; // Value injected by FXMLLoader

    @FXML // fx:id="opt_description"
    private TextField opt_description; // Value injected by FXMLLoader

    @FXML // fx:id="opt_lot"
    private TextField opt_lot; // Value injected by FXMLLoader

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
			this.txt_area.setText("Invalid error value\n");
    		return;
		}
    	
		this.txt_area.setText("Added " + error_probability + " % of chance to fail the setup and need to redo the setup process.");
    }

    @FXML
    void doAddOrderOpt(ActionEvent event) {
    	LocalDateTime date;
    	try {
    		date = LocalDateTime.parse(this.datePicker.getValue()+"T00:00:00");
		} catch (Exception e) {
			this.txt_area.setText("Invalid date");
			return;
		}
    	
    	String lot = this.opt_lot.getText();
    	
    	Integer pieces;
    	try {
			pieces = Integer.parseInt(this.opt_pieces.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for pieces");
    		return;
		}
  
    	Integer tons;
    	try {
    		tons = Integer.parseInt(this.opt_tons.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for tons");
    		return;
		}
    	
    	String description = this.opt_description.getText();
    	String s;
    	
    	if(this.model.isPresent(lot, null)) {
    		s = "This lot number already exists";
    		this.txt_area.setText(s);
    	} 
    			
    	s = this.model.addOrderOpt(date, lot, pieces, description, tons);
    	
    	if(this.model.sizePress(tons) == null) {
    		s = "ATTENZIONE: NON ESISTE UNA PRESSA PER IL TONNELLAGGIO INSERITO.";
    		txt_area.setText(s);
    		return;
    	}

    		
    	if(this.model.sizePress(tons).size() >= 10) 
    		s = s + "\n\n ATTENZIONE: L'ELABORAZIONE RICORSIVA POTREBBE RICHIEDERE TANTO TEMPO DOVUTO ALL'ALTO NUMERO DI COMBINAZIONI POSSIBILI. \n\n";
    	
        this.txt_area.setText(s);
    	
    }

    @FXML
    void doAddPressSim(ActionEvent event) {
    	Integer id;
    	try {
    		id = Integer.parseInt(this.sim_press.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for ID");
    		return;
		}
    	
    	Integer tons;
    	try {
    		tons = Integer.parseInt(this.sim_press_tons.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for tons");
    		return;
		}
    	
    	Double cycle_time;
    	try {
    		cycle_time = Double.parseDouble(this.sim_cycle_time.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for Cycle time");
    		return;
		}
    	
    	Long setup_time;
    	try {
    		setup_time = Long.parseLong(this.sim_setup_time.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for Setup time");
    		return;
		}
    	
    	if(id <= 0 || tons <= 0 || cycle_time <= 0 || setup_time <= 0) { 
    		this.txt_area.setText("Invalid number format, numbers must be greater than 0");
    		return;
    	}
    	
    	Press press = new Press(id, tons, cycle_time, setup_time);

    	String s;
    	
    	if(this.model.isPresent(null, id)) {
    		s = "This press id already exists";
    		this.txt_area.setText(s);
    		return;
    	} 
        	
    	s = this.model.addPressSim(press);
        this.txt_area.setText(s);
        	
        List<Press> presses = this.model.getPresses();
        this.cmb_presses_sim.getItems().clear();
        this.cmb_presses_sim.getItems().addAll(presses);
        this.cmb_presses_opt.getItems().clear();
        this.cmb_presses_opt.getItems().addAll(presses);
    	
    }

    @FXML
    void doOptimize(ActionEvent event) {
    	String s = this.model.optimize(0);
    	this.txt_area.setText(s);
    	
    	this.VBoxOptimize.setDisable(false);
    	this.VBoxSim.setDisable(false);
    	
    	List<Press> presses = this.model.getPresses();
    	this.cmb_presses_sim.getItems().clear();
    	this.cmb_presses_sim.getItems().addAll(presses);
    	this.cmb_presses_opt.getItems().clear();
    	this.cmb_presses_opt.getItems().addAll(presses);
    }

    @FXML
    void doOptimizeForPress(ActionEvent event) {
    	Press p = this.cmb_presses_opt.getValue();
    	String s;
    	
    	if(p == null) 
    		s = "SELEZIONA UNA PRESSA";
    	else
    		s = this.model.optimizeForPress(p, 0);
    	
    	this.txt_area.setText(s);
    }

    @FXML
    void doRemoveOrderOpt(ActionEvent event) {
    	String lot = this.opt_lot.getText();
    	
    	String s = this.model.removeOrder(lot);
    	this.txt_area.setText(s);
    }

    @FXML
    void doRemovePressSim(ActionEvent event) {
    	Integer id;
    	try {
    		id = Integer.parseInt(this.sim_press.getText());
		} catch (Exception e) {
			this.txt_area.setText("Invalid number for ID");
    		return;
		}
    	
    	String s = this.model.removePress(id);
    	s = s + "\n\nATTENZIONE: LA RIMOZIONE DI UNA PRESSA PUO' COMPORTARE UN ACCUMULO DI ORDINI SU UN'ALTRA PRESSA "
    			+ "INCORRENDO IN UN TROPPO ALTO NUMERO DI COMBINAZIONI DA ANALIZZARE PER L'OTTIMIZZAZIONE.";
    	this.txt_area.setText(s);
    }

    @FXML
    void doClearOrder(ActionEvent event) {
    	this.datePicker.setValue(null);
    	this.opt_lot.clear();
    	this.opt_pieces.clear();
    	this.opt_tons.clear();
    	this.opt_description.clear();
    }

    @FXML
    void doClearPress(ActionEvent event) {
    	this.sim_press.clear();
    	this.sim_press_tons.clear();
    	this.sim_cycle_time.clear();
    	this.sim_setup_time.clear();
    }
    
    @FXML
    void doSimForPress(ActionEvent event) {
    	Double error_probability;
    	
    	try {
    		error_probability = Double.parseDouble(this.sim_error.getText());
		} catch (Exception e) {
			this.txt_area.setText("Wrong value for Error\n");
    		return;
		}
    	
    	Press p = this.cmb_presses_sim.getValue();
    	String s;
    	
    	if(p == null) 
    		s = "SELEZIONA UNA PRESSA";
    	else
    		s = this.model.optimizeForPress(p, error_probability);
    	
    	this.txt_area.setText(s);
    }

    @FXML
    void doSimulate(ActionEvent event) {
    	Double error_probability;
    	
    	try {
    		error_probability = Double.parseDouble(this.sim_error.getText());
		} catch (Exception e) {
			this.txt_area.setText("Wrong value for Error\n");
    		return;
		}
    
    	String s = this.model.optimize(error_probability);
    	this.txt_area.setText(s);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert VBoxOptimize != null : "fx:id=\"VBoxOptimize\" was not injected: check your FXML file 'Scene.fxml'.";
        assert VBoxSim != null : "fx:id=\"VBoxSim\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb_presses_opt != null : "fx:id=\"cmb_presses_opt\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmb_presses_sim != null : "fx:id=\"cmb_presses_sim\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_description != null : "fx:id=\"opt_description\" was not injected: check your FXML file 'Scene.fxml'.";
        assert opt_lot != null : "fx:id=\"opt_lot\" was not injected: check your FXML file 'Scene.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'Scene.fxml'.";
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
