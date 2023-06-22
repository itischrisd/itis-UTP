/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad1;


import java.util.*;

public class Main {

  static List<String> getPricesInPLN(List<String> destinations, double xrate) {
    return ListCreator.collectFrom(destinations)
                       .when(  e -> e.startsWith("WAW") && e.split(" ").length == 3
                        )
                       .mapEvery( e -> {
                            String[] input = e.split(" ");
                            String dest = input[1];
                            int priceInPLN = (int) (Double.parseDouble(input[2]) * xrate);
                            return "to " + dest + " - price in PLN: " + priceInPLN;
                        }
                        );
  }

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
    for (String r : result) System.out.println(r);
  }
}
