package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * LOGICAL ERROR - The biggest logical error I couldn't figure out throughout the entirety of this project was populating
 * the associated parts list on the bottom right-hand corner. Whenever I would click the modify button, every single part
 * ever associated with a product was added to this list, and not product specific parts. I ended up making a mistake
 * when I created the associated parts observable list by declaring the observable list as static, which gives the product
 * class one table to hold all the associated parts. Removing the static label ended up giving every product their own
 * observable list of associated parts which fixed the problem.
 *
 * FUTURE ENHANCEMENT - Being able to delete the product under the modify product or parts from the modify parts page would
 * be a good feature. It can be implemented by taking the product that was clicked on the main screen prior to selecting modify.
 * If a user clicks modify and then decides it would be better off not having the product, the delete option would be there
 * on the screen instead of having to back out and click delete on the main screen. Also, if there are associated parts
 * in this situation, a user would have to go back and forth a few more times than if the delete button were on the screen.
 */
public class ModifyProduct implements Initializable {
    public Label assocPartsLbl;
    public Label prodIDAssoc;
    public TextField modProdName;
    public TextField modProdInv;
    public TextField modProdCost;
    public TextField modProdMin;
    public TextField modProdMax;
    public TextField modProdID;
    public TableView modAddParts;
    public TableColumn addPartID;
    public TableColumn addPartName;
    public TableColumn addInv;
    public TableColumn addCost;
    public TableView modViewParts;
    public TableColumn viewPartID;
    public TableColumn viewPartName;
    public TableColumn viewInv;
    public TableColumn viewCost;
    public TextField modProdSearch;

    /**
     * This method will add a part from the parts list on the top right of the screen to the associated parts on the
     * bottom right of the screen. If the save button is clicked afterwards, the part will be an associated part for the
     * product. If the cancel button is clicked, the part will not be added to the product's associated parts.
     *
     * @author Michael Borucki
     *
     * @param actionEvent
     */

    public void onClickAddPart(ActionEvent actionEvent) {
        Part selected = (Part) modAddParts.getSelectionModel().getSelectedItem();
        Product selectedBefore = MainScreen.getProduct();
        ObservableList<Part> associatedPart = selectedBefore.getAssociatedParts();
        modViewParts.setItems(associatedPart);
        associatedPart.add(selected);
        viewPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        viewCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * The save feature will both delete the selected item from the main screen and add the modified part to the table.
     * If changes were made to the product's associated parts list, then the adjustments will be made to the observable
     * list containing the associated parts for the product.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSave(ActionEvent event) throws IOException{
        Product selected = MainScreen.getProduct();
        ObservableList<Part> associatedPart = selected.getAssociatedParts();
        try {
            int prodID = selected.getId();
            String prodName = modProdName.getText();
            int prodInv = Integer.parseInt(modProdInv.getText());
            double prodCost = Double.parseDouble(modProdCost.getText());
            int prodMin = Integer.parseInt(modProdMin.getText());
            int prodMax = Integer.parseInt(modProdMax.getText());

            if (prodInv < prodMin || prodInv > prodMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory must be in between Min and Max");
                alert.showAndWait();
            } else {

                Product createProduct = new Product(prodID, prodName, prodCost, prodInv, prodMin, prodMax);

                Inventory.addProduct(createProduct);
                for (Part part: associatedPart){
                    createProduct.addPart(part);
                }
                Inventory.getAllProducts().remove(selected);
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 936, 390);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException num) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The Inventory, Min, and Max fields must be Integers. Price must be a number.");
            alert.showAndWait();
        }
    }

    /**
     * Method returning the user to the main screen after pressing the cancel button
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
     * This method will search for a part based on either the part's ID or the part's name. If the input in the search
     * textfield matches the ID, matches the name, or the input is a substring of the name, all the appropriate parts
     * will display. If nothing is found, an error will pop up indicating that the search did not find anything, and it
     * will repopulate the table with the full parts list.
     * @param event
     */
    public void partSearch(ActionEvent event){
        String stringToSearch = modProdSearch.getText();
        ObservableList<Part> parts = Inventory.getAllParts();
        ObservableList<Part> returned = FXCollections.observableArrayList();
        for (Part part : parts){
            if (String.valueOf(part.getId()).contains(stringToSearch) ||
                    part.getName().toLowerCase().contains(stringToSearch.toLowerCase())){
                returned.add(part);
            }
        }
        modAddParts.setItems(returned);
        if (returned.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your search did not find anything.");
            alert.showAndWait();
            modAddParts.setItems(Inventory.getAllParts());
        }

    }

    /**
     * This method will remove an associated part from the product's observable list of associated parts. If nothing
     * is selected before removal, an error will pop up indicating the user to select a part. Otherwise, a confirmation
     * box will pop up asking the user to confirm the removal of the associated part. Afterwards, the part will be removed.
     * @param actionEvent
     */
    public void onClickRemovePart(ActionEvent actionEvent) {
        Product selectedBefore = MainScreen.getProduct();
        ObservableList<Part> associatedPart = ((Product) selectedBefore).getAssociatedParts();
        Part selected = (Part) modViewParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a part");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Are you sure you want to delete this?");
            Optional<ButtonType> clickOk = alert.showAndWait();
            if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
                associatedPart.remove(selected);
            }
        }

    }

    /**
     * This method initializes the screen of the interface for this class by filling it with the correct information.
     * Such information is the information about the product, the parts table displaying all parts on the top-right, and
     * the product's associated parts table on the bottom right.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdSearch.setPromptText("Search for parts here");
        Product selected = MainScreen.getProduct();
        ObservableList<Part> associatedPart = ((Product) selected).getAssociatedParts();
        assocPartsLbl.setText("Associated Parts for: " + selected.getName());

        modProdName.setText(String.valueOf(selected.getName()));
        modProdInv.setText(String.valueOf(selected.getStock()));
        modProdCost.setText(String.valueOf(selected.getPrice()));
        modProdMin.setText(String.valueOf(selected.getMin()));
        modProdMax.setText(String.valueOf(selected.getMax()));


        addPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        viewPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        viewCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        modAddParts.setItems(Inventory.getAllParts());
        modViewParts.setItems(associatedPart);

    }

}
