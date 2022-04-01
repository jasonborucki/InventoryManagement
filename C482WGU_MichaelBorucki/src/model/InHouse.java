package model;

/**
 * The In House class inherits from the Part class. Only a machineID needs to be declared.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     *  Constructor consisting of a super and machineID addition.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *  Returns machine ID
     */

    public int getMachineId() {
        return machineId;
    }
    /**
     *  Sets machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}