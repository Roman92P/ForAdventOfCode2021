package org.pashkov.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheTreacheryOfWhalesSecondPart {
    public static void main(String[] args) {
        int lowestDevider = findLowestDevider();
        System.out.println(lowestDevider);
//        System.out.println(calculateCost(16));
    }
    public static int findLowestDevider(){
        List<String> strings = readInputFromFile("input7_7.txt");
        List<Integer> deviders = strings.stream()
                .distinct()
                .mapToInt(Integer::parseInt).boxed()
                .sorted()
                .collect(Collectors.toList());

        int toCheck = deviders.get(0);
        List<Integer> objects = new ArrayList<>();
        for (int i = 1; i < deviders.size(); i++) {
            if(deviders.get(i)-toCheck!=1) objects.add(deviders.get(i)-toCheck+1);
        }

        deviders.addAll(objects);

        List<Integer> collect = deviders.stream().mapToInt(v -> v).boxed().sorted().collect(Collectors.toList());

        System.out.println(collect);

        List<Integer> numbers = strings.stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> sums = new ArrayList<>();
        for (int i = 0; i < collect.size(); i++) {
            int sum = 0;
            for (int j = 0; j < numbers.size(); j++) {
                int devideResult = Math.abs(numbers.get(j) - collect.get(i));
                int totalCost = calculateCost(devideResult);
                sum = totalCost + sum;
            }
            sums.add(sum);
        }
        System.out.println(sums);
        int result = sums.stream()
                .mapToInt(v -> v)
                .min()
                .orElseThrow(NoSuchElementException::new);
        return result;
    }

    private static int calculateCost(int devideResult) {
        int result = 0;
        for (int i = devideResult; i > 0; i--) {
            result = result+i;
        }
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
