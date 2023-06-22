/**
 *
 *  @author Dudek Krzysztof S25692
 *
 */

package zad1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
		Function<String, List<String>> flines = filename -> {
            List<String> lines = new ArrayList<>();
            try (Stream<String> stream = Files.lines(Paths.get(filename))) {
                stream.forEach(lines::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        };
        Function<List<String>, String> join = lines -> (String.join("", lines));
        Function<String, List<Integer>> collectInts = line -> {
            List<Integer> integers = new ArrayList<>();
            Matcher matcher = Pattern.compile("\\d+").matcher(line);
            while (matcher.find())
                integers.add(Integer.valueOf(matcher.group()));
            return integers;
        };
        Function<List<Integer>, Integer> sum = integers -> integers.stream().mapToInt(i -> i).sum();

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
