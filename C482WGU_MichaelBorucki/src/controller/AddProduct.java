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

import static model.Inventory.getAllParts;
//import static model.Product.associatedParts;

/**
 * The AddProduct class will add a product used by the allProducts Observable List in the Inventory class.
 * The user will also see a list of available parts from the allParts Observable List that they may add as an associated
 * part.
 *
 * RUNTIME ERROR - I experienced a LoadException runtime error after I manually entered the search method as opposed to
 * automatically adding it from the fxml file. A simple typo correction solved this issue.
 *
 * @author Michael Borucki
 *
 * FUTURE ENHANCEMENT - The same application of specifying the specific numberformatexceptions on the AddParts controller
 * would be very beneficial here. Having a separate error message instead of grouping everything into one error statement
 * would look a lot cleaner.
 */

/**
 *  Create AddProduct class intializing all fxml items
 */
public class AddProduct implements Initializable {
    public TableColumn ProductIDAssoc;
    private ObservableList<Part> addAssocPart = FXCollections.observableArrayList();
    public TextField addProdName;
    public TextField addProdInv;
    public TextField addProdCost;
    public TextField addProdMin;
    public TextField addProdMax;
    public TextField addProdId;
    public TableView addAssociatedPart;
    public TableColumn addProductID;
    public TableColumn addProductName;
    public TableColumn addInv;
    public TableColumn addCost;
    public TableView viewAssociatedParts;
    public TableColumn viewProductId;
    public TableColumn viewProductName;
    public TableColumn viewInvLevel;
    public TableColumn viewCost;
    public TextField addProdSearch;
    /**
     *  Initialize the screen with two features: Pre-populating the Parts table with available parts and adding a
     *  placeholder in the parts search bar to let the user know this is a search bar.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProdSearch.setPromptText("Search for parts");
        addAssociatedPart.setItems(Inventory.getAllParts());


        addProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        viewProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        viewCost.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
    /**
     *  Returns the view to the main screen upon clicking the cancel button.
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 936, 390);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public int idCount =0;
    /**
     *  Removes an associated part if one has been added. If nothing is selected, an error will pop up telling the user
     *  to select a part, and if a part is selected, it will confirm with the user if they want to delete the part before
     *  deletion.
     */

    public void onClickRemove(ActionEvent actionEvent) {
        Part selected = (Part) viewAssociatedParts.getSelectionModel().getSelectedItem();
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
                addAssocPart.remove(selected);
            }
        }

    }
    /**
     *  Adds an associated part from the allParts table on top to the product's associated parts table on the bottom.
     */
    public void onClickAdd(ActionEvent actionEvent) {
        Part selected = (Part) addAssociatedPart.getSelectionModel().getSelectedItem();
        addAssocPart.add(selected);
        viewAssociatedParts.setItems(addAssocPart);
        viewProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        viewProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        viewCost.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
    /**
     *  Searches parts by ID or if the name contains the search string. If nothing is found, an error will pop up
     *  saying the search did not find anything and the full table screen will be on display again. After a successful
     *  search, the user can clear the search bar, click the button, and the full screen wil display.
     */
    public void partSearch(ActionEvent event){
        String stringToSearch = addProdSearch.getText();
        ObservableList<Part> parts = Inventory.getAllParts();
        ObservableList<Part> returned = FXCollections.observableArrayList();
        for (Part part : parts){
            if (String.valueOf(part.getId()).contains(stringToSearch) ||
                    part.getName().contains(stringToSearch)){
                returned.add(part);
            }
        }
        addAssociatedPart.setItems(returned);
        if (returned.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your search did not find anything.");
            alert.showAndWait();
            addAssociatedPart.setItems(Inventory.getAllParts());
        }

    }
    /**
     *  This will create a new product object and save the product in the allProducts Observable list in the Inventory
     *  class. In addition, the associated parts will also be stored in the associatedParts Observable List in the
     *  Product class for later retrieval if necessary. Various conditions must be met such as proper data type input,
     *  the inventory number being in between the min and max fields, and min being larger than max. It will return the
     *  user to the main screen after successful completion.
     */
    @FXML
    void onClickSave(ActionEvent event) throws IOException {
        try {
            int prodID = Inventory.getProductID();
            String prodName = addProdName.getText();
            int prodInv = Integer.parseInt(addProdInv.getText());
            double prodCost = Double.parseDouble(addProdCost.getText());
            int prodMin = Integer.parseInt(addProdMin.getText());
            int prodMax = Integer.parseInt(addProdMax.getText());

            if (prodInv < prodMin || prodInv > prodMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory must be in between Min and Max");
                alert.showAndWait();
            } else {

                Product createProduct = new Product(prodID, prodName, prodCost, prodInv, prodMin, prodMax);

                Inventory.addProduct(createProduct);
                for (Part part: addAssocPart){
                    createProduct.addPart(part);
                }


                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 936, 390);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();


            }
        } catch (NumberFormatException num){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The Inventory, Min, and Max fields must be Integers. Price must be a number.");
            alert.showAndWait();
        }
    }
}

