package model;

/**
 * The Outsourced class inherits from the Part class. Only a company name needs to be declared.
 */

public class Outsourced extends Part {
    private String companyName;
    /**
     *  Constructor consisting of a super and company name addition.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }
    /**
     *  return company Name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *  Constructor set company Name.
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
