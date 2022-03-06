package org.pashkov.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TheTreacheryOfWhales2 {
    public static void main(String[] args) {
        System.out.println(findLowestDevider());
    }

    public static int findLowestDevider(){
        List<String> strings = readInputFromFile("input7_7.txt");
        List<Integer> deviders = strings.stream()
                .distinct()
                .mapToInt(Integer::parseInt).boxed()
                .sorted()
                .collect(Collectors.toList());
        List<Integer> numbers = strings.stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> sums = new ArrayList<>();
        for (int i = 0; i < deviders.size(); i++) {
            int sum = 0;
            for (int j = 0; j < numbers.size(); j++) {
                int devideResult = Math.abs(numbers.get(j) - deviders.get(i));
                sum = devideResult + sum;
            }
            sums.add(sum);
        }
        int result = sums.stream()
                .mapToInt(v -> v)
                .min()
                .orElseThrow(NoSuchElementException::new);
        return result;
    }

    public static List<String> readInputFromFile(String fileName){
        Path path = Paths.get(fileName);
        try {
            List<String> strings = Files.readAllLines(path);
            String s = strings.get(0);
            String[] split = s.split(",");
            List<String> objects = new ArrayList<>();
            for (String a : split) objects.add(a);
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
