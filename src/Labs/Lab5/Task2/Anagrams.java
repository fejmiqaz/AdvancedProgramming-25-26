package Labs.Lab5.Task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        List<String> parts = br.lines().collect(Collectors.toList());

        Map<Integer, String> map = IntStream.range(0, parts.size())
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> parts.get(i)));

        Map<String, List<String>> grouped = map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        m -> {
                            char[] c = m.getValue().toLowerCase().toCharArray();
                            Arrays.sort(c);
                            return new String(c);
                        },
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        grouped.entrySet().stream()
                .sorted(Comparator.comparing(e ->
                        e.getValue().stream()
                                .map(word -> map.entrySet().stream()
                                        .filter(me -> me.getValue().equals(word))
                                        .findFirst().get().getKey()).min(Integer::compare).get()
                )).forEach(e-> {
                    System.out.println(String.join(" ", e.getValue()));
                });
    }
}

