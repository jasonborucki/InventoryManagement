package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * RUNTIME ERROR - I would experience many runtime errors when attempting to fill in the addPart form with improper
 * data types. This was corrected with a Try/Catch statement that displays error messages when this occurs
 * rather than crashing
 *
 * FUTURE ENHANCEMENT - Having separate error messages pertaining to which textfield has an incorrect data type
 * would be better, or maybe having labels display error messages of what exactly is wrong. My current program simply
 * uses an all-encompassing error statement saying which fields need to be integers or doubles.
 *
 * @author Michael Borucki
 *
 * The AddPart class is responsible for adding parts to the allParts observable list in the Inventory class that
 * populates the Parts table in the main screen and product add/modify product screens.
 *

 */

public class AddPart extends MainScreen implements Initializable {
    public TextField addPartName;
    public TextField addPartInv;
    public TextField addPartCost;
    public TextField addPartMin;
    public TextField addPartMax;
    public TextField addPartMachineId;
    public TextField addPartID;
    public Label machineIdLabel;
    public Label nameLabel;
    public Label minLabel;
    public Label maxLabel;
    public Label machineIdLabels;
    public Label invLabel;
    public Label priceLabel;
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;

    /**
     * Initializes any data that must be pre-populated, but in this case, it does not.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Changes text to appropriate label if the part is In-House
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText(("Machine ID"));
    }
    /**
     * Changes text to appropriate label if the part is Outsourced.
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText(("Company Name"));
    }


    /**
     * @param event
     * @throws IOException
     *
     * This method stores the added part into the allParts Observable List in Inventory.java that is displayed on the
     * MainScreen, Add Products, and Modify Products views. It also brings the user back to the main screen. the method
     * exercises conditions where the min must be less than max, the inventory must be in between min and max, and the
     * machine ID must be greater than 0. If inappropriate data types are input, then an error will display.
     */
    @FXML
    void onSaveAddPart(ActionEvent event) throws IOException {
        try {
            if (inHouseButton.isSelected()) {

                int partID = Inventory.getPartID();
                String partName = addPartName.getText();
                int partInv = Integer.parseInt(addPartInv.getText());
                double partCost = Double.parseDouble(addPartCost.getText());
                int partMin = Integer.parseInt(addPartMin.getText());
                int partMax = Integer.parseInt(addPartMax.getText());
                int machineId = Integer.parseInt(addPartMachineId.getText());

                if (partInv < partMin || partInv > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inventory must be in between the min and max fields");
                    alert.showAndWait();
                } else if (partMin > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Min must be smaller than max");
                    alert.showAndWait();
                } else if (machineId <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Machine ID must be a number greater than 0.");
                    alert.showAndWait();
                } else {

                    Inventory.addPart(new InHouse(partID, partName, partCost, partInv, partMin, partMax, machineId));
                        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        Object scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    }
                }

            if (outsourcedButton.isSelected()) {

                int partID = Inventory.getPartID();
                String partName = addPartName.getText();
                int partInv = Integer.parseInt(addPartInv.getText());
                double partCost = Double.parseDouble(addPartCost.getText());
                int partMin = Integer.parseInt(addPartMin.getText());
                int partMax = Integer.parseInt(addPartMax.getText());
                String companyName = addPartMachineId.getText();

                if (partInv < partMin || partInv > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inventory must be in between the min and max fields");
                    alert.showAndWait();
                } else if (partMin > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Min must be smaller than max");
                    alert.showAndWait();
                } else {

                    Inventory.addPart(new Outsourced(partID, partName, partCost, partInv, partMin, partMax, companyName));
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }

            } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The Inventory, Min, Max, and Machine ID fields must be Integers. Price must be a " +
                        "number with decimals.");
                alert.showAndWait();
            }


        }


    /**
     *
     * @param actionEvent
     * @throws IOException
     *
     * This method returns the user to the main screen when the cancel button is clicked.
     */
    public void onCancelAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 936, 390);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
