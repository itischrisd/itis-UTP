/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class CustomersPurchaseSortFind {

    private final List<Purchase> purchaseList;

    public CustomersPurchaseSortFind() {
        this.purchaseList = new ArrayList<>();
    }

    public void readFile(String fname) {
        try (Stream<String> lines = Files.lines(Paths.get(fname))) {
            lines.map(line -> line.split(";")).forEach(parts -> {
                String customerId = parts[0];
                String surname = parts[1].split(" ")[0];
                String name = parts[1].split(" ")[1];
                String item = parts[2];
                double price = Double.parseDouble(parts[3]);
                double quantity = Double.parseDouble(parts[4]);
                purchaseList.add(new Purchase(customerId, surname, name, item, price, quantity));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSortedBy(String byOption) {
        switch (byOption) {
            case "Nazwiska":
                showSortedByName();
                break;
            case "Koszty":
                showSortedByCosts();
                break;
        }
    }

    private void showSortedByName() {
        System.out.println("\nNazwiska");
        purchaseList.stream()
                .sorted(Comparator.comparing(Purchase::getSurame).thenComparing(Purchase::getCustomerId))
                .forEach(System.out::println);
    }

    private void showSortedByCosts() {
        System.out.println("\nKoszty");
        purchaseList.stream()
                .sorted(Comparator.comparing(Purchase::getCost).reversed().thenComparing(Purchase::getCustomerId))
                .forEach(e -> System.out.println(e + " (koszt: " + e.getCost() + ")"));
    }

    public void showPurchaseFor(String id) {
        System.out.println("\nKlient " + id);
        purchaseList.stream()
                .filter(e -> e.getCustomerId().equals(id))
                .forEach(System.out::println);
    }
}
