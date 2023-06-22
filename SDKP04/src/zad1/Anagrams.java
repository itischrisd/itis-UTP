/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad1;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {

    private final Map<String, List<String>> anagramMap;

    public Anagrams(String filename) throws FileNotFoundException {
        this.anagramMap = new HashMap<>();
        Scanner scanner = new Scanner(new FileReader(filename));
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            String sortedLine = sortLetters(line);
            List<String> anagrams = anagramMap.getOrDefault(sortedLine, new ArrayList<>());
            anagrams.add(line);
            anagramMap.put(sortedLine, anagrams);
        }
        scanner.close();
    }

    private String sortLetters(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public List<List<String>> getSortedByAnQty() {
        List<List<String>> result = new ArrayList<>(anagramMap.values());
        return result.stream().sorted((list1, list2) -> {
            int sizeCompare = Integer.compare(list2.size(), list1.size());
            if (sizeCompare == 0) return list1.get(0).compareTo(list2.get(0));
            else return sizeCompare;
        }).collect(Collectors.toList());
    }

    public String getAnagramsFor(String word) {
        List<String> anagrams = anagramMap.get(sortLetters(word));
        if (!anagrams.contains(word)) return word + ": null";
        anagrams.remove(word);
        Collections.sort(anagrams);
        return word + ": " + anagrams;
    }

}
