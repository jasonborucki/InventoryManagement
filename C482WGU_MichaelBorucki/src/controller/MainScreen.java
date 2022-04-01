package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import static model.Inventory.getAllProducts;

/**
 * RUNTIME ERROR - A common runtime error that occurred was when I would click the delete button with nothing selected.
 * I ended up correcting this by having an error message display upon clicking telling the user to select a part or
 * product to delete.
 *
 * FUTURE ENHANCEMENT - An enhancement that would help the functionality of the inventory management system would be to
 * have the search fields search in real time as a user was searching for a part instead of requiring the user to click
 * the search button. It would save a few clicks, and could even remove the need for a search button.
 *
 * @author Michael Borucki
 *
 * Initializing the fxml variables
 */
public class MainScreen implements Initializable {


    public TextField partSearch;
    public TableView partsTable;
    public TableColumn colPartId;
    public TableColumn colPartName;
    public TableColumn colPartInv;
    public TableColumn colPartCost;
    public TextField prodSearch;
    public TableView prodTable;
    public TableColumn colProdId;
    public TableColumn colProdInv;
    public TableColumn colProdCost;
    public TableColumn colProdName;
    public static Part getParts;
    public static Part getPart(){
        return getParts;
    }
    public static Product getProduct;
    public static Product getProduct(){
        return getProduct;
    }


    /**
     *
     * This initializes the main screen with the appropriate tables coming from the observable lists allParts and
     * allProducts. In addition, placeholders have been set in the search text fields to tell the user that it is a
     * search bar.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partSearch.setPromptText("Search for parts here");
        prodSearch.setPromptText("Search for products here");
        partsTable.setItems(Inventory.getAllParts());
        colPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(Inventory.getAllProducts());
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colProdCost.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * This will bring a user to the add part screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddPartClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 502);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This will bring a user to the modify part screen. If nothing is selected prior to calling the method, the user will
     * be asked to select a part.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPartClick(ActionEvent actionEvent) throws IOException {
        getParts = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (getParts == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a part");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 502);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method deletes a part using the delete button on the parts table. If nothing is selected, an error
     * will pop up telling the user to choose a part. Otherwise, a popup will occur asking the user to confirm the
     * deletion.
     * @param actionEvent
     * @throws IOException
     */
    public void onDeletePartClick(ActionEvent actionEvent) throws IOException {
        Part clickedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (clickedPart == null) {
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
                Inventory.getAllParts().remove(clickedPart);

            }
        }
    }
    /**
     * This will search the part table by id and by name. If the input from the search textfield matches a part's
     * id, name, or the input is a substring of a product, the part will be returned to the observable list
     * @param event
     */
    public void partSearch(ActionEvent event){
        String stringToSearch = partSearch.getText();
        ObservableList<Part> parts = Inventory.getAllParts();
        ObservableList<Part> returned = FXCollections.observableArrayList();
        for (Part part : parts){
            if (String.valueOf(part.getId()).contains(stringToSearch) ||
                    part.getName().toLowerCase().contains(stringToSearch.toLowerCase())){
                returned.add(part);
            }
        }
        partsTable.setItems(returned);
        if (returned.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your search did not find anything.");
            alert.showAndWait();
            partsTable.setItems(Inventory.getAllParts());
        }

    }

    /**
     * This will search the products table by id and by name. If the input from the search textfield matches a products
     * id, name, or the input is a substring of a product, the product will be returned to the observable list
     * @param event
     */
    public void prodSearch(ActionEvent event){
        String stringToSearch = prodSearch.getText();
        ObservableList<Product> products = Inventory.getAllProducts();
        ObservableList<Product> returned = FXCollections.observableArrayList();
        for (Product product : products){
            if (String.valueOf(product.getId()).contains(stringToSearch) ||
                    product.getName().toLowerCase().contains(stringToSearch.toLowerCase())){
                returned.add(product);
            }
        }
        prodTable.setItems(returned);
        if (returned.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your search did not find anything.");
            alert.showAndWait();
            prodTable.setItems(Inventory.getAllProducts());
        }

    }

    /**
     * This will bring a user to the add product screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProdClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 723, 502);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This selection will bring a user to the modify product screen. If nothing is selected, an error will display telling
     * the user to select a product to modify.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProdClick(ActionEvent actionEvent) throws IOException {
        getProduct = (Product) prodTable.getSelectionModel().getSelectedItem();

        if (getProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a product");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 723, 502);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method will delete the product that is selected by the user. If nothing is clicked, an alert will pop up saying
     * to select a product. If a user selects a product and the product has associated parts, then the program will bring
     * up another error stating that the associated parts must be deleted. If the associated parts are deleted, then
     * a popup will occur simply asking a user to confirm the deletion.
     * @param actionEvent
     * @throws IOException
     */
    public void onDeleteProdClick(ActionEvent actionEvent) throws IOException {
        Product clickedProd = (Product) prodTable.getSelectionModel().getSelectedItem();
        if (clickedProd == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a product");
            alert.showAndWait();
        } else if (clickedProd.getAssociatedParts().size() > 0) {
                Alert alertOne = new Alert(Alert.AlertType.ERROR);
                alertOne.setTitle("ERROR");
                alertOne.setHeaderText("You must delete associated parts to delete the product.");
                alertOne.showAndWait();
            } else {

                Alert alertTwo = new Alert(Alert.AlertType.CONFIRMATION);
                alertTwo.setTitle("Confirm");
                alertTwo.setHeaderText("Are you sure you want to delete this?");
                Optional<ButtonType> clickOk = alertTwo.showAndWait();
                if (clickOk.isPresent() && clickOk.get() == ButtonType.OK) {
                    Inventory.getAllProducts().remove(clickedProd);
                }

            }
        }


    /**
     * Clicking the exit button will leave the program
     * @param actionEvent
     * @throws IOException
     */
    public void onExitClick(ActionEvent actionEvent) throws IOException {
        System.exit(0);

    }

}
