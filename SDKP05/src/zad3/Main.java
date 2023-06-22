/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt").openStream()));
        Map<String, String> a = r.lines().map(String::toLowerCase).collect(Collectors.groupingBy(w -> new String(w.chars().sorted().toArray(), 0, w.length()), Collectors.joining(" ")));
        int m = a.values().stream().mapToInt(s -> s.split(" ").length).max().orElse(0);
        a.values().stream().filter(s -> s.split(" ").length == m).sorted().forEach(System.out::println);
    }
}