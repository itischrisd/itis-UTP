/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad2;


public class Purchase {
    private final String customerId;
    private final String surname;
    private final String name;
    private final String item;
    private final double price;
    private final double quantity;

    public Purchase(String customerId, String surname, String name, String item, double price, double quantity) {
        this.customerId = customerId;
        this.surname = surname;
        this.name = name;
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getSurame() {
        return surname;
    }

    public double getCost() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return customerId + ";" + surname + " " + name + ";" + item + ";" + price + ";" + quantity;
    }
}
