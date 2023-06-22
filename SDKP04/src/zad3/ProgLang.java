package zad3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

    private final Map<String, Set<String>> langsMap = new LinkedHashMap<>();
    private final Map<String, Set<String>> progsMap = new LinkedHashMap<>();

    public ProgLang(String nazwaPliku) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            String lang = parts[0];
            Set<String> programmers = new LinkedHashSet<>(Arrays.asList(parts).subList(1, parts.length));
            langsMap.put(lang, programmers);
            for (String programmer : programmers) {
                Set<String> langs = progsMap.getOrDefault(programmer, new LinkedHashSet<>());
                langs.add(lang);
                progsMap.put(programmer, langs);
            }
        }
        reader.close();
    }

    public Map<String, Set<String>> getLangsMap() {
        return Collections.unmodifiableMap(langsMap);
    }

    public Map<String, Set<String>> getProgsMap() {
        return Collections.unmodifiableMap(progsMap);
    }

    public Map<String, Set<String>> getLangsMapSortedByNumOfProgs() {
        return sorted(langsMap, (entry1, entry2) -> {
            int cmp = Integer.compare(entry2.getValue().size(), entry1.getValue().size());
            return cmp != 0 ? cmp : entry1.getKey().compareTo(entry2.getKey());
        });
    }

    public Map<String, Set<String>> getProgsMapSortedByNumOfLangs() {
        return sorted(progsMap, (entry1, entry2) -> {
            int cmp = Integer.compare(entry2.getValue().size(), entry1.getValue().size());
            return cmp != 0 ? cmp : entry1.getKey().compareTo(entry2.getKey());
        });
    }

    public Map<String, Set<String>> getProgsMapForNumOfLangsGreaterThan(int n) {
        return filtered(progsMap, entry -> entry.getValue().size() > n);
    }

    public <K, V> Map<K, V> sorted(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return map.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public <K, V> Map<K, V> filtered(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return map.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
