package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class consisting of an Observable List of associated parts, and constructor variables
 */
public class Product{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;
    /**
     * Product Class constructor
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /**
     *  return id
     */
    public int getId() {
        return id;
    }
    /**
     *  set id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     *  return name
     */
    public String getName() {
        return name;
    }
    /**
     *  set name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *  return price
     */
    public Double getPrice() {
        return price;
    }
    /**
     *  set price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    /**
     *  get stock
     */
    public int getStock() {
        return stock;
    }
    /**
     *  set stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     *  get min
     */
    public int getMin() {
        return min;
    }
    /**
     *  set min
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     *  get max
     */
    public int getMax() {
        return max;
    }
    /**
     *  set max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *  add part to associated parts Observable List
     */
    public void addPart(Part part){
        associatedParts.add(part);
    }
    /**
     *  remove part from associated parts Observable List and return true or return false
     */
    public boolean removePart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
            return false;
    }
    /**
     *  return associated parts
     */
    public ObservableList<Part> getAssociatedParts(){
        return associatedParts;
    }



}