package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

/**
 * LOGICAL ERROR - The only error I had on this class was that parts would get duplicated whenever the save button was
 * clicked. I ended up fixing this by assigning the variable "selected" to account for the part that was clicked on the
 * main screen, and when the save button is clicked, the class deletes the selected part and replaces it with the modified
 * version.
 *
 * FUTURE ENHANCEMENT - I defined the "selected" variable twice under both if conditions in the try/catch statement
 * in the class' save method. Next time I would place the selected variable above the try/catch statement to avoid the
 * redundancy.
 *
 * @author Michael Borucki
 *
 * Initializing the ModifyPart class with the items from the fxml file.
 *
 */
public class ModifyPart implements Initializable {
    public TextField modPartName;
    public TextField modPartInv;
    public TextField modPartCost;
    public TextField modPartMin;
    public TextField modPartMax;
    public TextField modPartMachineId;
    public TextField modPartId;
    public Label machineIdLabel;
    public RadioButton inHouseButton;
    public RadioButton outsourcedButton;

    /**
     * Sets the appropriate text if the in-house button on the page is clicked.
     * @param actionEvent
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }


    /**
     * Sets the appropriate text if the outsourced button on the page is clicked
     * @param actionEvent
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * When the user saves the modified part, the program will delete the old product to avoid duplicated parts while
     * storing the modified part as either an in-house or an outsourced part based on which button is selected. Conditions
     * must be met such as the inventory being in between min and max as min being smaller than max. Also,
     * NumberFormatExceptions will be raised with an error indicating that the relevant integer or double based fields
     * must be filled in appropriately.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSave(ActionEvent event) throws IOException {
        try {
            if (inHouseButton.isSelected()) {

                Part selected = MainScreen.getPart();
                int partID = selected.getId();
                String partName = modPartName.getText();
                int partInv = Integer.parseInt(modPartInv.getText());
                double partCost = Double.parseDouble(modPartCost.getText());
                int partMin = Integer.parseInt(modPartMin.getText());
                int partMax = Integer.parseInt(modPartMax.getText());
                int machineId = Integer.parseInt(modPartMachineId.getText());

                if (partInv < partMin || partInv > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inventory must be in between Min and Max");
                    alert.showAndWait();
                }
                if (machineId == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Machine ID must be a number greater than 0.");
                    alert.showAndWait();
                }

                Inventory.addPart(new InHouse(partID, partName, partCost, partInv, partMin, partMax, machineId));
                Inventory.getAllParts().remove(selected);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
            if (outsourcedButton.isSelected()){
                Part selected = MainScreen.getPart();
                int partID = selected.getId();
                String partName = modPartName.getText();
                int partInv = Integer.parseInt(modPartInv.getText());
                double partCost = Double.parseDouble(modPartCost.getText());
                int partMin = Integer.parseInt(modPartMin.getText());
                int partMax = Integer.parseInt(modPartMax.getText());
                String companyName = modPartMachineId.getText();

                if (partInv < partMin || partInv > partMax) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Inventory must be in between Min and Max");
                    alert.showAndWait();
                }

                Inventory.addPart(new Outsourced(partID, partName, partCost, partInv, partMin, partMax, companyName));
                Inventory.getAllParts().remove(selected);
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The Inventory, Min, Max, and MachineID fields must be Integers. Price must be a number.");
            alert.showAndWait();
        }
    }

    /**
     * Returns the user to the main screen when the cancel button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 936, 390);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Initializes the Modify Part view with the proper information. Uses the selected part from the Main Screen to
     * indicate whether the chosen part to modify was an in-house or an outsourced part. Then it fills the form with
     * the appropriate information.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Part selected = (Part) MainScreen.getPart();

        if (selected instanceof InHouse) {
            InHouse selectedInHouse = (InHouse) MainScreen.getPart();
            inHouseButton.setSelected(true);
            machineIdLabel.setText("Machine ID");
            modPartName.setText(String.valueOf(selectedInHouse.getName()));
            modPartInv.setText(String.valueOf(selectedInHouse.getStock()));
            modPartCost.setText(String.valueOf(selectedInHouse.getPrice()));
            modPartMin.setText(String.valueOf(selectedInHouse.getMin()));
            modPartMax.setText(String.valueOf(selectedInHouse.getMax()));
            modPartMachineId.setText(String.valueOf(selectedInHouse.getMachineId()));
        } else {
            Outsourced selectedOutsourced = (Outsourced) MainScreen.getPart();
            outsourcedButton.setSelected(true);
            machineIdLabel.setText("Company Name");
            modPartName.setText(String.valueOf(selectedOutsourced.getName()));
            modPartInv.setText(String.valueOf(selectedOutsourced.getStock()));
            modPartCost.setText(String.valueOf(selectedOutsourced.getPrice()));
            modPartMin.setText(String.valueOf(selectedOutsourced.getMin()));
            modPartMax.setText(String.valueOf(selectedOutsourced.getMax()));
            modPartMachineId.setText(String.valueOf(selectedOutsourced.getCompanyName()));
        }
    }
}
