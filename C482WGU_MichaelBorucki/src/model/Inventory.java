package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Combines the products and parts into their various observable lists that can be referenced by using the methods below
 * in the various controllers.
 */
public class Inventory{

    /**
     * Observable List containing all the parts that can be referenced to using other public methods
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Observable List containing all the products that can be referenced to using other public methods
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the allParts observable list
     * @param part
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * Adds a product to the allProducts observable list
     * @param product
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * look up a part in the allParts observable list by part ID and return the part found
     */
    public static Part lookupPart(int partID) {
        Part searchedPart = null;
        for (Part part : allParts) {
            if (partID == part.getId()) {
                searchedPart = part;
            }

        }return searchedPart;
    }
    /**
     * look up a product in the allproducts observable list by product ID and return the product found
     */
    public static Product lookupProduct(int productID) {
        Product searchedProduct = null;
        for (Product product : allProducts) {
            if (productID == product.getId()) {
                searchedProduct = product;
            }

        }return searchedProduct;
    }

    /**
     * look up a product in the allproducts observable list by product name and return products found
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product: allProducts){
            if (product.getName().equals(productName)){
                foundProducts.add(product);
            }
        } return foundProducts;
    }
    /**
     * look up a part in the allparts observable list by part name and return parts founds
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part: allParts){
            if (part.getName().equals(partName)){
                foundParts.add(part);
            }
        } return foundParts;
    }
    /**
     * Update a part based on it's index and selectedPart in the allParts observable list
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * Update a product based on it's index and selectedProduct in the allProducts observable list
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    /**
     * Delete selected product and return True or return False from allProducts observable list
     */
    public static boolean deleteProduct(Product selectedProduct){
        for (Product product: allProducts){
            if (product == selectedProduct){
                allProducts.remove(product);
                return true;
            }
        }   return false;
    }
    /**
     * Delete selected part and return True or return False from allParts observable list
     */
    public static boolean deletePart(Part selectedPart){
        for (Part part: allParts){
            if (part == selectedPart){
                allProducts.remove(part);
                return true;
            }
        }   return false;
    }
    /**
     * return allParts observable list
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * return allProducts observable list
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


    /**
     * increment the part ID each time a new one is created
     */
    public static int getPartID() {
        return ++partID;
    }
    /**
     * increment the product ID each time a new one is created
     */
    public static int getProductID() {
        return ++productID;
    }

    /**
     * declare the starting point for the part ID
     */
    private static int partID = 0;
    /**
     * declare the starting point for the product ID
     */
    private static int productID = 0;




}