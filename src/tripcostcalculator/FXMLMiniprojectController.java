/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripcostcalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ahmetduser
 */
public class FXMLMiniprojectController implements Initializable {

    DecimalFormat df; //Decimal format for cost of trip result
    public String type;

    @FXML
    private RadioButton radioButton, radioButton1;

    @FXML
    private TextField txtGetDistance, txtGetMPG;

    @FXML
    private Label lblResult, lblError;

    @FXML
    private Label inputError, inputError1;

    @FXML
    private Label lblTripInfo;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        // display with 2 decimal points
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double dis = 0;
        double mpg = 0;

        Socket socket = new Socket("localhost", 9999);
        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());

        // If-Else blocks for checking the user's input
        if (txtGetDistance.getText().isEmpty() && txtGetMPG.getText().isEmpty()) {
            lblError.setText("Please enter the paramteres");
        } else if (txtGetDistance.getText().isEmpty()) {
            lblError.setText("Please enter distance value");
        } else if (txtGetMPG.getText().isEmpty()) {
            lblError.setText("Please enter MPG value");
        } else if (radioButton.isSelected() == false && radioButton1.isSelected() == false) {
            lblError.setText("Please select fuel option");
        } else if ("0".equals(txtGetMPG.getText())) {
            inputError1.setText("0 is not valid MPG value");
        } else {

            try {
                // Parse the user's distance input into double
                dis = Double.parseDouble(txtGetDistance.getText());
                inputError.setText(""); // remove the error message
            } catch (NumberFormatException e) {
                inputError.setText("Please enter only number values");
            }

            try {
                // Parse the user's MPG input into double
                mpg = Double.parseDouble(txtGetMPG.getText());
                inputError1.setText(""); // remove the error message
            } catch (NumberFormatException e) {
                inputError1.setText("Please enter only number values");
            }
            lblError.setText(""); // remove the error message
        }

        // !! After user's input passes the if-else checking part...
        if (radioButton.isSelected()) {
            type = "octane";
            // send the object with the user's inputs to the Server
            objectOut.writeObject(new Parameters(dis, mpg, type, 0)); // cost variable's value is 0 for now!!
            double result = 0;
            try {
                // read the object that is sent from the Server
                Parameters par = (Parameters) objectIn.readObject();
                result = par.getCost();
            } catch (NumberFormatException ex) {
                lblError.setText("Please enter numbers");
            } catch (ClassNotFoundException ex) {
                lblError.setText("Class not Found");
            }
            lblResult.setText(" £" + df.format(result));  // radioButton

        } else {
            type = "diesel";
            // send the object with the user's inputs to the Server
            objectOut.writeObject(new Parameters(dis, mpg, type, 0)); // cost variable's value is 0 for now!!
            double result = 0;
            try {
                // read the object that is sent from the Server
                Parameters par = (Parameters) objectIn.readObject();
                result = par.getCost();
            } catch (NumberFormatException ex) {
                lblError.setText("Please enter numbers");
            } catch (ClassNotFoundException ex) {
                lblError.setText("Class not Found");
            }

            lblResult.setText(" £" + df.format(result)); // radioButton1

        }

        lblTripInfo.setText("Distance: " + dis
                + "\nMiles per Galon:  " + mpg
                + "\nFuel type: " + type);

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
