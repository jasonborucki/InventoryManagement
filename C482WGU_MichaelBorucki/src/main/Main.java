package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * JAVADOCS - I have included the JavaDocs in the compressed folder along with the project folder.
 *
 * @author Michael Borucki WGU ID- 001511118
 *
 * The Main class is responsible for 3 things:
 * 1) Inheriting the JavaFX Library for the entire project
 * 2) Initializing the main screen with the appropriate view
 * 3) Populating the main screen Parts and Products tables with appropriate testing data
 *
 * RUNTIME ERROR - I couldn't get the main screen to run because I thought I had the correct path to the fxml file.
 * Placing "/view/" before the filename fixed everything up.
 *
 * FUTURE ENHANCEMENT - Two things I can do to enhance the functionality would be to eliminate the dimensions of the
 * setScene on line 28. I found out midway through the project the dimensions can be made redundant, so it will make the
 * code a little cleaner. In addition, I populated the main screen tables too late in my project development. I should
 * have done it much earlier so I didn't have to keep adding parts and products every single time I tried to get
 * the modify parts and products functions to work.
 *
 * */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Mainscreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 936, 390));
        stage.show();
    }

    /**
     * Populating the tables with parts and products
     *
     */
    public static void main(String[] args){

        int id = Inventory.getPartID();
        Part goodyearTires = new Outsourced(id, "Goodyear Tires", 99.99, 200, 1, 300, "Goodyear" );

        Part michelinTires = new Outsourced(Inventory.getPartID(), "Michelin Tires", 99.99, 200, 1, 300, "Michelin" );

        Part pirelliTires = new Outsourced(Inventory.getPartID(), "Pirelli Tires", 99.99, 200, 1, 300, "Pirelli" );

        Part accordBody = new InHouse(Inventory.getPartID(), "Accord Chassis", 9000, 100, 1, 300, 1 );

        Part civicBody = new InHouse(Inventory.getPartID(), "Civic Chassis", 4000, 100, 1, 300, 1 );

        Part elementSUVBody = new InHouse(Inventory.getPartID(), "Element Chassis", 11100, 100, 1, 300, 1 );

        Part ridgelineBody = new InHouse(Inventory.getPartID(), "Ridgeline Chassis", 13000, 100, 1, 300, 1 );

        Part rims = new InHouse(Inventory.getPartID(), "Honda Rims", 119.99, 100, 1, 300, 1 );

        Product accord = new Product(Inventory.getProductID(), "Honda Accord", 26666, 100, 1, 100);

        Product civic = new Product(Inventory.getProductID(), "Honda Civic", 17000, 100, 1, 100);

        Product elementSUV = new Product(Inventory.getProductID(), "Honda Element", 35000, 100, 1, 100);

        Product ridgeline = new Product(Inventory.getProductID(), "Honda Ridgeline", 41000, 100, 1, 100);

        Inventory.addPart(goodyearTires);
        Inventory.addPart(rims);
        Inventory.addPart(michelinTires);
        Inventory.addPart(pirelliTires);
        Inventory.addPart(accordBody);
        Inventory.addPart(civicBody);
        Inventory.addPart(elementSUVBody);
        Inventory.addPart(ridgelineBody);
        Inventory.addProduct(accord);
        Inventory.addProduct(civic);
        Inventory.addProduct(elementSUV);
        Inventory.addProduct(ridgeline);

        accord.addPart(accordBody);
        accord.addPart(goodyearTires);
        accord.addPart(rims);
        civic.addPart(michelinTires);
        civic.addPart(rims);
        civic.addPart(civicBody);
        elementSUV.addPart(rims);
        elementSUV.addPart(pirelliTires);
        elementSUV.addPart(elementSUVBody);
        ridgeline.addPart(ridgelineBody);
        ridgeline.addPart(rims);
        ridgeline.addPart(goodyearTires);

        launch(args);
    }
}
